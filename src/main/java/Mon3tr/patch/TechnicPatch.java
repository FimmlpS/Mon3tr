package Mon3tr.patch;

import Mon3tr.character.Mon3tr;
import Mon3tr.ui.TechnicUI;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class TechnicPatch {
    //专门用来渲染技力
    public static BitmapFont technicFont;
    public static BitmapFont technicTotalFont;

    public static TechnicUI ui = new TechnicUI();

    public static void hideUI(){
        ui.hide();
    }

    @SpirePatch(clz = FontHelper.class,method = "initialize")
    public static class FontPatch{

        @SpireInsertPatch(rloc = 198)
        public static void Insert(){
            technicTotalFont = FontHelper.prepFont(32F,true);
        }

        @SpireInsertPatch(rloc = 200)
        public static void Insert2(){
            technicFont = FontHelper.prepFont(32F,true);
        }
    }

    @SpirePatch(clz = AbstractPanel.class,method = "show")
    public static class ShowPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPanel _inst){
            if(_inst instanceof EnergyPanel){
                ui.show();
            }
        }
    }

    @SpirePatch(clz = AbstractPanel.class,method = "hide")
    public static class HidePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPanel _inst){
            if(_inst instanceof EnergyPanel){
                ui.hide();
            }
        }
    }

    @SpirePatch(clz = EnergyPanel.class,method = "update")
    public static class UpdatePatch{
        @SpirePostfixPatch
        public static void Postfix(EnergyPanel _inst){
            if(AbstractDungeon.player instanceof Mon3tr){
                ui.update();
            }
        }
    }

    @SpirePatch(clz = EnergyPanel.class,method = "render")
    public static class RenderPatch{
        @SpirePrefixPatch
        public static void Prefix(EnergyPanel _inst, SpriteBatch sb){
            if(AbstractDungeon.player instanceof Mon3tr){
                ui.render(sb);
            }
        }
    }

    //快速选派调整
    @SpirePatch(clz = InputHelper.class,method = "getCardSelectedByHotkey")
    public static class HotKeyGetPatch{
        @SpirePostfixPatch
        public static AbstractCard Postfix(AbstractCard _ret,CardGroup cards){
            if(AbstractDungeon.screen != AbstractDungeon.CurrentScreen.HAND_SELECT &&MeltdownPatch.meltdownGroup.group.size()==1){
                for(int i = 0; i < InputActionSet.selectCardActions.length && i < cards.size()+1; ++i) {
                    if (InputActionSet.selectCardActions[i].isJustPressed()) {
                        if(i==0){
                            return MeltdownPatch.meltdownGroup.group.get(0);
                        }
                        else
                            return (AbstractCard)cards.group.get(i-1);
                    }
                }
            }
            return _ret;
        }
    }
}
