package Mon3tr.action;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InstantReducePowerAction extends AbstractGameAction {
    private String powerID;
    private AbstractPower powerInstance;

    public InstantReducePowerAction(AbstractCreature target, AbstractCreature source, String power, int amount) {
        this.setValues(target, source, amount);

        this.powerID = power;
        this.actionType = ActionType.REDUCE_POWER;
    }

    public InstantReducePowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerInstance, int amount) {
        this.setValues(target, source, amount);

        this.powerInstance = powerInstance;
        this.actionType = ActionType.REDUCE_POWER;
    }

    public void update() {
        AbstractPower reduceMe = null;
        if (this.powerID != null) {
            reduceMe = this.target.getPower(this.powerID);
        } else if (this.powerInstance != null) {
            reduceMe = this.powerInstance;
        }
        if (reduceMe != null) {
            if (this.amount < reduceMe.amount) {
                reduceMe.reducePower(this.amount);

                reduceMe.updateDescription();
                AbstractDungeon.onModifyPower();
            } else {
                boolean add = true;
                for(AbstractGameAction action:AbstractDungeon.actionManager.actions){
                    if(action instanceof  RemoveSpecificPowerAction){
                        RemoveSpecificPowerAction r = (RemoveSpecificPowerAction) action;
                        AbstractPower p = ReflectionHacks.getPrivate(r,RemoveSpecificPowerAction.class,"powerInstance");
                        if(p==reduceMe){
                            add = false;
                            break;
                        }
                    }
                }
                if(add)
                    this.addToTop(new RemoveSpecificPowerAction(this.target, this.source, reduceMe));
            }
        }
        this.isDone = true;
    }
}

