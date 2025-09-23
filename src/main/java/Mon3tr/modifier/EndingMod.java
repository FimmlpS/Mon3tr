package Mon3tr.modifier;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.Mon3trTag;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class EndingMod extends AbstractCardModifier {
    public static String ID = "eyjafjalla:EndingMod";

    public EndingMod(){}

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !card.hasTag(Mon3trTag.ENDING_MON3TR);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(Mon3trTag.ENDING_MON3TR);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(Mon3trTag.ENDING_MON3TR);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EndingMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + StringHelper.modifiers.TEXT[0] + (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
