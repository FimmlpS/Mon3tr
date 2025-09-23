package Mon3tr.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class MonsterPatch {

    public static void resetMonsterRecord(){
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            MonsterField.attackedThisTurn.set(m,false);
        }
    }

    public static boolean attackedThisTurn(AbstractMonster m){
        return MonsterField.attackedThisTurn.get(m);
    }

    //记录所有敌人是否本回合受到过攻击伤害
    @SpirePatch(clz = AbstractMonster.class,method = SpirePatch.CLASS)
    public static class MonsterField {
        public static SpireField<Boolean> attackedThisTurn = new SpireField<Boolean>(()->{return false;});
    }

    @SpirePatch(clz = AbstractMonster.class,method = "damage",paramtypez = {DamageInfo.class})
    public static class MonsterDamagePatch{
        @SpirePrefixPatch
        public static void Prefix(AbstractMonster _inst, DamageInfo info){
            if(info.type == DamageInfo.DamageType.NORMAL){
                MonsterField.attackedThisTurn.set(_inst,true);
            }
        }
    }
}
