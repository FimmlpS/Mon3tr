package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class IncreaseIterationAction extends AbstractGameAction {
    public IncreaseIterationAction(AbstractMon3trCard c){
        this(c,1);
    }

    public IncreaseIterationAction(AbstractMon3trCard c, int amount){
        this.c = c;
        this.amount = amount;
    }

    AbstractMon3trCard c;

    @Override
    public void update() {
        if(c!=null && this.amount >= 0){
            c.increaseIteration(this.amount);
        }
        this.isDone = true;
    }
}
