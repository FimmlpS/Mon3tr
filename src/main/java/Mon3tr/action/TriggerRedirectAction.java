package Mon3tr.action;

import Mon3tr.modifier.RedirectMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TriggerRedirectAction extends AbstractGameAction {
    public TriggerRedirectAction(AbstractCard c) {
        this.c = c;
    }

    @Override
    public void update() {
        CardModifierManager.removeModifiersById(c, RedirectMod.ID,true);
        c.flash();
        addToTop(new DrawCardAction(1));
        this.isDone = true;
    }

    AbstractCard c;
}
