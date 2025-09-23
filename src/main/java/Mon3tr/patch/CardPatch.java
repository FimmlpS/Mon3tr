package Mon3tr.patch;

import Mon3tr.helper.StringHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;
import javassist.CtBehavior;

import java.util.ArrayList;

public class CardPatch {
    //在 演化keyword后追加 迭代keyword
    @SpirePatch(clz = AbstractCard.class,method = "initializeDescription")
    public static class RenderCardTipPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractCard _inst){
            ArrayList<String> keywordsToAdd = new ArrayList<>();
            for(String keyword:_inst.keywords){
                if(keyword.equals(StringHelper.tags.TEXT[1])){
                    keywordsToAdd.add(StringHelper.tags.TEXT[0]);
                }
                if(keyword.equals(StringHelper.tags.TEXT[3])){
                    keywordsToAdd.add(StringHelper.tags.TEXT[2]);
                }
            }
            for(String keyword:keywordsToAdd){
                if(!_inst.keywords.contains(keyword))
                    _inst.keywords.add(keyword);
            }
        }
    }

    private static class TabNameLocator extends SpireInsertLocator {
        private TabNameLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
            return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
        }
    }

    @SpirePatch(
            cls = "basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix$Render",
            method = "Insert"
    )
    public static class TabNamePatch {
        public TabNamePatch() {
        }

        @SpireInsertPatch(
                locator = TabNameLocator.class,
                localvars = {"tabName"}
        )
        public static void InsertFix(ColorTabBar _instance, SpriteBatch sb, float y, ColorTabBar.CurrentTab curTab, @ByRef String[] tabName) {
            if (tabName[0].equals("Mon3tr_express_color")) {
                tabName[0] = StringHelper.cards.TEXT[0];
            }

        }
    }

}
