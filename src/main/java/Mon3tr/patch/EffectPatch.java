package Mon3tr.patch;

import Mon3tr.character.Mon3tr;
import Mon3tr.effect.Mon3trFlashAtkImgEffect;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class EffectPatch {

    private static boolean useMon3trEffect(AbstractGameAction.AttackEffect effect){
        return effect == OtherEnum.MON3TR_ATTACK_EFFECT;
    }

    private static boolean useMon3trHeavyEffect(AbstractGameAction.AttackEffect effect){
        return effect == OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT;
    }

    @SpirePatch(clz = FlashAtkImgEffect.class,method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class,float.class, AbstractGameAction.AttackEffect.class,boolean.class})
    public static class FlashAtkImgEffectPatch {
        @SpirePrefixPatch
        public static void Prefix(FlashAtkImgEffect _inst, float x, float y, AbstractGameAction.AttackEffect effect, @ByRef boolean[] mute){
            if(useMon3trEffect(effect)){
                mute[0] = true;
                AbstractDungeon.effectList.add(new Mon3trFlashAtkImgEffect(x, y,false));
            }
            else if(useMon3trHeavyEffect(effect)){
                mute[0] = true;
                AbstractDungeon.effectList.add(new Mon3trFlashAtkImgEffect(x, y,true));
            }
        }

        @SpirePostfixPatch
        public static void Postfix(FlashAtkImgEffect _inst, float x, float y, AbstractGameAction.AttackEffect effect, boolean mute){
            if(useMon3trEffect(effect)||useMon3trHeavyEffect(effect)){
                _inst.duration = 0F;
                _inst.isDone = true;
            }
        }
    }


}
