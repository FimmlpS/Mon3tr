package Mon3tr.action;

import Mon3tr.helper.Mon3trHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class ObtainExpressCardAction extends AbstractGameAction {
    public ArrayList<AbstractCard> cards;

    public ObtainExpressCardAction(AbstractCard card) {
        this(new ArrayList<>());
        cards.add(card);
    }

    public ObtainExpressCardAction(ArrayList<AbstractCard> cards) {
        this.cards = cards;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> generated = Mon3trHelper.getExpressFromTranscription(cards);
        for (int i = generated.size() - 1; i >= 0; i--) {
            addToTop(new MakeTempCardInHandAction(generated.get(i)));
        }
        this.isDone = true;
    }
}
