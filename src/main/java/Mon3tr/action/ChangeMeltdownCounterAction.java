package Mon3tr.action;

import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class ChangeMeltdownCounterAction extends AbstractGameAction {
    public ChangeMeltdownCounterAction(boolean add, int amt){
        this.amount = amt;
        this.add = add;
    }

    @Override
    public void update() {
        if(add){
            MeltdownPatch.increaseMeltdownCounter(amount);
        }
        else{
            MeltdownPatch.decreaseMeltdownCount(amount);
        }

        this.isDone = true;
    }

    boolean add;
}
