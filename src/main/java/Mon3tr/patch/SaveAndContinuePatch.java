package Mon3tr.patch;

import Mon3tr.save.Mon3trSave;
import Mon3tr.save.Mon3trSaveAndContinue;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

public class SaveAndContinuePatch {
    public static Mon3trSave mon3trSave = new Mon3trSave();

    @SpirePatch(clz = SaveAndContinue.class, method = "save")
    public static class SavePatch {
        @SpirePostfixPatch
        public static void Postfix(SaveFile save) {
            mon3trSave.onSave();
            Mon3trSaveAndContinue.saveMon3tr(mon3trSave);
        }
    }

    @SpirePatch(clz = SaveAndContinue.class, method = "loadSaveFile",paramtypez = {AbstractPlayer.PlayerClass.class})
    public static class LoadPatch {
        @SpirePostfixPatch
        public static SaveFile Postfix(SaveFile _ret, AbstractPlayer.PlayerClass c) {
            Mon3trSave e = Mon3trSaveAndContinue.loadMon3tr(c);
            if(e!=null){
                mon3trSave = e;
                mon3trSave.onLoad();
            }
            return _ret;
        }
    }

    @SpirePatch(clz = SaveAndContinue.class,method = "deleteSave")
    public static class DeletePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer p){
            mon3trSave.onDelete();
            Mon3trSaveAndContinue.deleteMon3tr(p.chosenClass);
            //下面不用抄
            TechnicPatch.hideUI();
            MeltdownPatch.hideMeltdown();
        }
    }
}
