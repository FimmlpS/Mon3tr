package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OneTimeAction extends AbstractGameAction {

    AbstractCard c1;

    public OneTimeAction(AbstractCard c) {
        this.c1 = c;
    }

    @Override
    public void update() {
        if(c1!=null){
            AbstractCard removed = null;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.cardID == this.c1.cardID && c.uuid == this.c1.uuid) {
                    removed = c;
                    break;
                }
            }
            if(removed!=null){
                AbstractDungeon.player.masterDeck.removeCard(removed);
            }
        }
        this.isDone = true;
    }
}
