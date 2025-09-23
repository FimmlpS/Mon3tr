package Mon3tr.action;

import Mon3tr.power.DiscordCrystalPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;

import java.util.Collections;

public class SpawnDiscordAction extends AbstractGameAction {
    public SpawnDiscordAction(AbstractCreature target,int amt) {
        this.target = target;
        this.amount = amt;
        actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        if(target != null) {
            AbstractPower p = new DiscordCrystalPower(target,amount);
            target.powers.add(p);
            Collections.sort(this.target.powers);
            p.onInitialApplication();
            p.flash();
            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, p.name));
        }
        this.isDone = true;
    }
}
