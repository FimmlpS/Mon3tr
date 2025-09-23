package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class IncreaseIterationAction extends AbstractGameAction {
    public IncreaseIterationAction(AbstractMon3trCard c){
        this.c = c;
    }

    AbstractMon3trCard c;

    @Override
    public void update() {
        if(c!=null){
            c.increaseIteration(1);
        }
        this.isDone = true;
    }
}
