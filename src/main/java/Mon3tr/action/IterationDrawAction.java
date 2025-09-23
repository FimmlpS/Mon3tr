package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class IterationDrawAction extends AbstractGameAction {
    public IterationDrawAction(int amt){
        this.amount = amt;
    }

    @Override
    public void update() {
        for(AbstractCard c: DrawCardAction.drawnCards){
            if(c instanceof AbstractMon3trCard)
                ((AbstractMon3trCard) c).increaseIteration(this.amount);
        }
        this.isDone = true;
    }
}
