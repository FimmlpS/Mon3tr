package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TakeStrengthAction extends AbstractGameAction {
    public TakeStrengthAction(AbstractCreature t,int amt){
        this.target = t;
        this.amount = amt;
    }

    @Override
    public void update() {
        if(target!=null&&amount>0){
            AbstractPower strength = target.getPower(StrengthPower.POWER_ID);
            if(strength!=null&&strength.amount>0){
                this.amount = Math.min(amount,strength.amount);
                if(!target.hasPower(ArtifactPower.POWER_ID))
                    addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,this.amount),this.amount));
                addToTop(new ApplyPowerAction(target,AbstractDungeon.player,new StrengthPower(target,-amount),-amount));
            }
        }
        this.isDone = true;
    }
}
