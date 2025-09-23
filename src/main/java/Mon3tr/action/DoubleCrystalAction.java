package Mon3tr.action;

import Mon3tr.power.CrystalPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DoubleCrystalAction extends AbstractGameAction {
    public DoubleCrystalAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {
        AbstractPower cry = target.getPower(CrystalPower.POWER_ID);
        if (cry != null) {
            addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new CrystalPower(target, cry.amount), cry.amount));
        }
        this.isDone = true;
    }
}
