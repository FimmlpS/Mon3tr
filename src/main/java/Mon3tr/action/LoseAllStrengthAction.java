package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class LoseAllStrengthAction extends AbstractGameAction {
    public LoseAllStrengthAction(AbstractCreature target){
        this.target = target;
    }

    @Override
    public void update() {
        if(target!=null){
            AbstractPower st = target.getPower(StrengthPower.POWER_ID);
            if(st!=null){
                int amt = st.amount;
                if(amt>0){
                    addToTop(new RemoveSpecificPowerAction(target,target,st));
                }
            }
        }
        this.isDone = true;
    }
}
