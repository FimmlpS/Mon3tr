package Mon3tr.action;

import Mon3tr.card.express.SYSReperfusionInjury;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.PowerExpireTextEffect;

import java.util.Iterator;

public class ReperfusionAction extends AbstractGameAction {
    public ReperfusionAction(boolean enable, AbstractCreature target, SYSReperfusionInjury injury) {
        this.enable = enable;
        this.target = target;
        this.injury = injury;
        if(enable) {
            this.duration = startDuration = Settings.ACTION_DUR_FAST;
        }
    }

    boolean enable;

    SYSReperfusionInjury injury;

    @Override
    public void update() {
        if(enable){
            if(duration == startDuration){
                injury.powers.clear();
                Iterator<AbstractPower> powers = target.powers.iterator();
                while(powers.hasNext()){
                    AbstractPower power = powers.next();
                    if(power.type == AbstractPower.PowerType.DEBUFF){
                        AbstractDungeon.effectList.add(new PowerExpireTextEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, power.name, power.region128));
                        power.onRemove();
                        injury.powers.add(power);
                        powers.remove();
                    }
                }
                AbstractDungeon.onModifyPower();
            }
            this.tickDuration();
        }
        else{
            for(int i = injury.powers.size() - 1; i >= 0; i--){
                AbstractPower p = injury.powers.get(i);
                p.amount *= 2;
                addToTop(new ApplyPowerAction(target,AbstractDungeon.player,p,p.amount));
            }
            this.isDone = true;
        }
    }
}
