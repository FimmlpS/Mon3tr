package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class IncreaseDamageAction extends AbstractGameAction {
    public IncreaseDamageAction(AbstractCard card,int amt){
        this.c = card;
        this.amount = amt;
    }

    AbstractCard c;

    @Override
    public void update() {
        if(c!=null && c.baseDamage>=0){
            c.baseDamage += this.amount;
            c.damage = c.baseDamage;
            c.isDamageModified = false;
        }
        this.isDone = true;
    }
}
