package Mon3tr.patch;

import Mon3tr.power.CrystalPower;
import Mon3tr.power.DiscordCrystalPower;
import Mon3tr.power.FateReflowPower;
import Mon3tr.power.NeverTherePower;
import Mon3tr.relic.MainModuleColdness;
import Mon3tr.relic.ModuleRadiate;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class PowerPatch {

    @SpirePatch(clz = AbstractMonster.class,method = "damage",paramtypez = {DamageInfo.class})
    public static class NeverTherePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster _inst, DamageInfo info){
            if(_inst.lastDamageTaken>0 && info.owner!=null){
                if(info.type == DamageInfo.DamageType.NORMAL){
                    AbstractPower neverThere = info.owner.getPower(NeverTherePower.POWER_ID);
                    if(neverThere!=null){
                        neverThere.onSpecificTrigger();
                    }
                }
            }

            //FateReflowPower
            if(_inst.lastDamageTaken>0 && info.type != DamageInfo.DamageType.HP_LOSS){
                for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!m.isDeadOrEscaped()){
                        AbstractPower fateFlow = m.getPower(FateReflowPower.POWER_ID);
                        if(fateFlow!=null){
                            int amt = _inst.lastDamageTaken * fateFlow.amount/4;
                            if(amt>0){
                                fateFlow.flashWithoutSound();
                                AbstractDungeon.actionManager.addToTop(new DamageAction(m,new DamageInfo(AbstractDungeon.player,amt, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE,true));
                            }
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(clz = ApplyPowerAction.class,method = "update")
    public static class ApplyPowerActionPatch{
        @SpireInsertPatch(rloc = 18)
        public static void Insert(ApplyPowerAction _inst) {
            if(_inst.target.isPlayer)
                return;
            AbstractPower discord = _inst.target.getPower(DiscordCrystalPower.POWER_ID);
            if (discord != null) {
                AbstractPower applied = ReflectionHacks.getPrivate(_inst, ApplyPowerAction.class, "powerToApply");
                if (applied != null && applied.type == AbstractPower.PowerType.BUFF) {
                    discord.onSpecificTrigger();
                }
            }
        }
    }

    @SpirePatch(clz = ApplyPowerAction.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {AbstractCreature.class,AbstractCreature.class,AbstractPower.class,int.class,boolean.class, AbstractGameAction.AttackEffect.class})
    public static class ApplyPowerActionConstructPatch{
        @SpirePostfixPatch
        public static void Postfix(ApplyPowerAction _inst, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
            AbstractRelic r = AbstractDungeon.player.getRelic(ModuleRadiate.ID);
            if(r!=null){
                if(powerToApply.ID.equals(CrystalPower.POWER_ID)){
                    r.onTrigger();
                    powerToApply.amount++;
                    _inst.amount++;
                }
            }
        }
    }

    @SpirePatch(clz = RemoveSpecificPowerAction.class,method = "update")
    public static class RemoveSpecificPowerActionPatch{
        @SpireInsertPatch(rloc = 26,localvars = {"removeMe"})
        public static void Insert(RemoveSpecificPowerAction _inst, AbstractPower removeMe) {
            if(_inst.target.isPlayer)
                return;
            AbstractPower discord = _inst.target.getPower(DiscordCrystalPower.POWER_ID);
            if (discord != null) {
                if(removeMe!=null && removeMe.type == AbstractPower.PowerType.DEBUFF){
                    discord.onSpecificTrigger();
                }
            }
        }
    }

    @SpirePatch(clz = AbstractMonster.class,method = "init")
    public static class MonsterInitPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster _inst) {
            if(AbstractDungeon.getCurrMapNode()!=null && AbstractDungeon.getCurrRoom()!=null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
                AbstractRelic r = AbstractDungeon.player.getRelic(MainModuleColdness.ID);
                if(r!=null){
                    r.onTrigger(_inst);
                }
            }
        }
    }

}
