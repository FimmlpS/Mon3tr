package Mon3tr.patch;

import Mon3tr.power.SelfMagnetismPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class GAManagerPatch {
    public static boolean inserted = false;

    //插入判断是否执行回合开始的失去格挡
    @SpirePatch(clz = GameActionManager.class,method = "getNextAction")
    public static class LoseBlockInsertPatch{
        @SpireInsertPatch(rloc = 248)
        public static void Insert1(GameActionManager _inst){
            inserted = true;
        }

        @SpireInsertPatch(rloc = 257)
        public static void Insert2(GameActionManager _inst){
            inserted = false;
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "loseBlock",paramtypez = {int.class,boolean.class})
    public static class LoseBlockCancelPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCreature _inst, int amount, boolean noAnimation){
            if(inserted){
                if(MeltdownPatch.isMeltdownTurn && _inst.hasPower(SelfMagnetismPower.POWER_ID)){
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

}
