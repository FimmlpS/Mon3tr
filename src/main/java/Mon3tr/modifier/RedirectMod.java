package Mon3tr.modifier;

import Mon3tr.action.TriggerRedirectAction;
import Mon3tr.helper.StringHelper;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class RedirectMod extends AbstractCardModifier {
    public static String ID = "eyjafjalla:RedirectMod";

    public RedirectMod(){}

    @Override
    public boolean shouldApply(AbstractCard card) {
        return true;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {

    }

    @Override
    public void onRemove(AbstractCard card) {

    }

    @Override
    public void onDrawn(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new TriggerRedirectAction(card));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RedirectMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + StringHelper.modifiers.TEXT[2] + (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}

