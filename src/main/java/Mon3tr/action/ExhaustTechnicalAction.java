package Mon3tr.action;

import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class ExhaustTechnicalAction extends AbstractGameAction {
    public ExhaustTechnicalAction(int amt){
        this.amount = amt;
    }

    @Override
    public void update() {
        if(MeltdownPatch.isMeltdownTurn()){
            this.isDone = true;
            return;
        }
        MeltdownPatch.decreaseMeltdownCount(this.amount);
        this.isDone = true;
    }
}
