package Mon3tr.helper;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class StringHelper {
    public static final UIStrings tags;
    public static final UIStrings actions;
    public static final UIStrings cards;
    public static final UIStrings modifiers;
    public static final UIStrings charSelects;
    public static final UIStrings modeSelects;
    public static final UIStrings config;

    public static final String moduleRelicID = "Module";

    public static String getRelicIMGPATH(String ID,boolean outline){
        //replace
        ID = ID.replace("mon3tr:","");
        if(outline){
            return "Mon3trResources/img/relics/"+ID+"_O.png";
        }
        return "Mon3trResources/img/relics/"+ID+".png";
    }

    public static String getCardIMGPatch(String ID, AbstractCard.CardType type){
        //replace
        ID = ID.replace("mon3tr:","");
        switch (type) {
            case ATTACK: {
                ID += "_attack";
                break;
            }
            case CURSE:
            case STATUS:
            case SKILL: {
                ID += "_skill";
                break;
            }
            case POWER: {
                ID += "_power";
                break;
            }
        }
        return "Mon3trResources/img/cards/" + ID + ".png";
    }

    public static String getCardStrategyIMGPath(String ID, AbstractCard.CardType type){
        //replace
        ID = ID.replace("mon3tr:","");
        switch (type) {
            case ATTACK: {
                ID += "_attack";
                break;
            }
            case CURSE:
            case STATUS:
            case SKILL: {
                ID += "_skill";
                break;
            }
            case POWER: {
                ID += "_power";
                break;
            }
        }
        return "Mon3trResources/img/cards/strategy/" + ID + ".png";
    }

    public static String getCardTmpIMGPatch(AbstractCard.CardType type){
        //replace
        String ID = "";
        switch (type) {
            case ATTACK: {
                ID = "Strike_attack";
                break;
            }
            case CURSE:
            case STATUS:
            case SKILL: {
                ID = "Defend_skill";
                break;
            }
            case POWER: {
                ID = "Power_power";
                break;
            }
        }
        return "Mon3trResources/img/cards/" + ID + ".png";
    }

    static {
        tags = CardCrawlGame.languagePack.getUIString("mon3tr:Tags");
        actions = CardCrawlGame.languagePack.getUIString("mon3tr:Actions");
        cards = CardCrawlGame.languagePack.getUIString("mon3tr:Cards");
        modifiers = CardCrawlGame.languagePack.getUIString("mon3tr:Modifiers");
        charSelects = CardCrawlGame.languagePack.getUIString("mon3tr:CharSelects");
        modeSelects = CardCrawlGame.languagePack.getUIString("mon3tr:ModeSelects");
        config = CardCrawlGame.languagePack.getUIString("mon3tr:Config");
    }
}
