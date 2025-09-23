package Mon3tr.patch;

import Mon3tr.character.Mon3tr;
import Mon3tr.helper.PatchHelper;
import Mon3tr.ui.CharSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

public class CharSelectPatch {


    @SpirePatch(clz = CharacterOption.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class, AbstractPlayer.class, Texture.class,Texture.class})
    public static class SelectSetRightPatch{
        @SpirePostfixPatch
        public static void Postfix(CharacterOption _inst, String optionName, AbstractPlayer c, Texture buttonImg, Texture portraitImg){
            if(c!=null && c.chosenClass == ClassEnum.Mon3tr_CLASS){
                ReflectionHacks.setPrivate(_inst,CharacterOption.class,"infoX",(float) Settings.WIDTH/2F);
            }
        }
    }

    @SpirePatch(clz = CharacterOption.class,method = "updateInfoPosition")
    public static class SelectUpdateRightPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CharacterOption _inst){
            if(_inst.c !=null && _inst.c.chosenClass == ClassEnum.Mon3tr_CLASS){
                float infoX = ReflectionHacks.getPrivate(_inst,CharacterOption.class,"infoX");
                if(_inst.selected){
                    ReflectionHacks.setPrivate(_inst,CharacterOption.class,"infoX", MathHelper.uiLerpSnap(infoX, PatchHelper.SP_DEST_INFO_X));
                }
                else{
                    ReflectionHacks.setPrivate(_inst,CharacterOption.class,"infoX", MathHelper.uiLerpSnap(infoX, PatchHelper.SP_START_INFO_X));
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CharacterOption.class,method = "renderRelics")
    public static class SelectRenderRightPatch{
        @SpirePrefixPatch
        public static void Prefix(CharacterOption _inst, SpriteBatch sb){
            if(_inst.c !=null && _inst.c.chosenClass == ClassEnum.Mon3tr_CLASS){
                if(CharSelectScreen.getChar().index==0){
                    Settings.CREAM_COLOR.r = 0;
                    Settings.CREAM_COLOR.g = 0;
                    Settings.CREAM_COLOR.b = 0;
                }
            }
        }

        @SpirePostfixPatch
        public static void Postfix(CharacterOption _inst, SpriteBatch sb){
            Settings.CREAM_COLOR.r = PatchHelper.CREAM_COLOR.r;
            Settings.CREAM_COLOR.g = PatchHelper.CREAM_COLOR.g;
            Settings.CREAM_COLOR.b = PatchHelper.CREAM_COLOR.b;
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class,method = "deselectOtherOptions")
    public static class ChangeRefreshPatch{
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen _inst, CharacterOption characterOption){
            if(characterOption.c instanceof Mon3tr){
                CharSelectScreen.Inst.refresh();
            }
        }
    }

    public static boolean isPuzzlerSelected() {
        return CardCrawlGame.chosenCharacter == ClassEnum.Mon3tr_CLASS && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected");
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "render"
    )
    public static class RenderButtonPatch {
        public RenderButtonPatch() {
        }

        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (CharSelectPatch.isPuzzlerSelected()) {
                CharSelectScreen.Inst.render(sb);
            }

        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "update"
    )
    public static class UpdateButtonPatch {
        public UpdateButtonPatch() {
        }

        public static void Prefix(CharacterSelectScreen _inst) {
            if (CharSelectPatch.isPuzzlerSelected()) {
                CharSelectScreen.Inst.update();
            }

        }
    }
}
