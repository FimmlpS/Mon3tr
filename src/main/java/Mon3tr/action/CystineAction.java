package Mon3tr.action;

import Mon3tr.power.CrystalPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CystineAction extends AbstractGameAction {
    public CystineAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {
        if(target!=null){
            int amt = target.currentHealth/10;
            if(amt>0){
                addToTop(new ApplyPowerAction(target,AbstractDungeon.player,new CrystalPower(target,amt),amt));
                addToTop(new LoseHPAction(target, AbstractDungeon.player,amt,AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }
}
