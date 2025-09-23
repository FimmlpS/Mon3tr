package Mon3tr.patch;

import Mon3tr.card.AbstractExpressCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ExpressPatch {
    public static ArrayList<AbstractCard> returnedCardsThisCombat = new ArrayList<>();

    public static void clearPlayerExpress(){
        if(AbstractDungeon.player!=null){
            AddExpressPatch.creatureExpress.set(AbstractDungeon.player,new CreatureExpress());
        }
        returnedCardsThisCombat.clear();
    }

    //生物表达牌表示类
    public static class CreatureExpress {
        public static final float SMALL_PREVIEW_OFFSET_Y = 300F * Settings.scale;
        public static final float SMALL_PREVIEW_DRAW_SCALE = 0.2F;
        public static final float SMALL_PREVIEW_OFFSET_X = -125F*Settings.scale;
        public static final float BIG_PREVIEW_ABSOLUTE_Y = Settings.HEIGHT * 0.75F;
        public static final float BIG_PREVIEW_DRAW_SCALE = 0.7F;
        public static final float BIG_PREVIEW_OFFSET_X = -100F*Settings.scale;

        public CreatureExpress(){
        }

        public int maxExpresses = 1;
        public ArrayList<AbstractExpressCard> expressCards = new ArrayList<>();
        public AbstractCard lastExpressCard = null;

        //渲染相关 -- DOWN
        public void renderCards(SpriteBatch sb, AbstractCreature owner){
            int index = 0;
            boolean big = owner.hb.hovered;
            if(AbstractDungeon.player.hoveredCard!=null && !(AbstractDungeon.player.hoveredCard instanceof AbstractExpressCard)){
                big = false;
            }
            if(big){
                float wSize = 210F *Settings.scale;
                for(AbstractCard c:expressCards){
                    c.targetDrawScale = BIG_PREVIEW_DRAW_SCALE;
                    float pY = -(expressCards.size()-1) * (wSize/2F + 10F*Settings.scale);
                    c.target_y = BIG_PREVIEW_ABSOLUTE_Y;
                    c.target_x = owner.hb.x - BIG_PREVIEW_OFFSET_X + pY + index * (wSize + 10F * Settings.scale);
                    index++;
                    c.render(sb);
                }
            }
            else{
                float wSize = 50F *Settings.scale;
                for(AbstractCard c:expressCards){
                    c.targetDrawScale = SMALL_PREVIEW_DRAW_SCALE;
                    float pY = -(expressCards.size()-1) * (wSize/2F + 10F*Settings.scale);
                    c.target_y = owner.hb.y + SMALL_PREVIEW_OFFSET_Y;
                    c.target_x = owner.hb.cX - SMALL_PREVIEW_OFFSET_X + pY + index * (wSize + 10F * Settings.scale);
                    index++;
                    c.render(sb);
                }
            }
        }

        public void updateCards(){
            for(AbstractCard c: expressCards){
                c.update();
            }
        }


        //战斗相关 -- DOWN
        public boolean isFull(){
            return expressCards.size()>=maxExpresses;
        }

        //ADD
        public void addExpressCard(AbstractExpressCard c, AbstractCreature owner){
            if(!expressCards.contains(c)){
                expressCards.add(c);
                expressAppliedToTarget(c,owner);
            }
        }

        //REMOVE
        public void removeExpressCard(AbstractExpressCard c, AbstractCreature owner){
            if(expressCards.contains(c)){
                lastExpressCard = c;
                expressCards.remove(c);
                expressAbandonedFromTarget(c,owner);
            }
        }

        //REMOVE THEN ADD
        public void replaceExpressCard(AbstractExpressCard removed, AbstractExpressCard c, AbstractCreature owner){
            removeExpressCard(removed,owner);
            addExpressCard(c,owner);
        }

        //表达牌相关trigger
        private void expressAppliedToTarget(AbstractExpressCard c, AbstractCreature owner){
            c.expressAppliedToTarget(owner);
        }

        private void expressAbandonedFromTarget(AbstractExpressCard c, AbstractCreature owner){
            c.expressAbandonedFromTarget(owner);
        }

        public void expressWhenPlayCard(AbstractCard c,AbstractCreature target){
            for(AbstractExpressCard c2: expressCards){
                c2.expressWhenPlayCard(c,target);
            }
        }

        public void expressAtStartOfTurn(AbstractCreature owner){
            for(AbstractExpressCard c:expressCards){
                c.expressAtStartOfTurn(owner);
            }
        }

        //此处info.owner为表达牌拥有者
        public void expressWhenAttack(int damage, AbstractCreature attackedCreature, DamageInfo info){
            for(AbstractExpressCard c:expressCards){
                c.expressWhenAttack(damage,attackedCreature,info);
            }
        }

        //此处target为表达牌拥有者
        public void expressWhenAttacked(int damage, AbstractCreature owner, DamageInfo info){
            for(AbstractExpressCard c:expressCards){
                c.expressWhenAttacked(damage,owner,info);
            }
        }

        public float gainBlockOrHp(float amount, AbstractCreature owner, boolean isBlock){
            for(AbstractExpressCard c:expressCards){
                amount = c.gainBlockOrHp(amount,owner,isBlock);
            }
            return amount;
        }
    }

    //相关patch
    @SpirePatch(clz = AbstractCreature.class, method = SpirePatch.CLASS)
    public static class AddExpressPatch{
        public static SpireField<CreatureExpress> creatureExpress = new SpireField<CreatureExpress>(()->new CreatureExpress());
    }

    //渲染在creature统一完成
    @SpirePatch(clz = AbstractCreature.class, method = "renderHealth", paramtypez = {SpriteBatch.class})
    public static class CreatureRenderPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractCreature _inst, SpriteBatch sb) {
            if(Settings.hideCombatElements){
                return;
            }
            if(!_inst.isDeadOrEscaped()){
                CreatureExpress express = AddExpressPatch.creatureExpress.get(_inst);
                if(express==null)
                    return;
                express.renderCards(sb,_inst);
            }
        }
    }

    //玩家和敌人分开来update
    @SpirePatch(clz = AbstractMonster.class, method = "update")
    public static class MonsterUpdatePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster _inst){
            CreatureExpress express = AddExpressPatch.creatureExpress.get(_inst);
            if(express==null)
                return;
            express.updateCards();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "update")
    public static class PlayerUpdatePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst){
            CreatureExpress express = AddExpressPatch.creatureExpress.get(_inst);
            if(express==null)
                return;
            express.updateCards();
        }
    }

    //attack 和 attacked 完成在 AbstractCreature的 decrementBlock前
    @SpirePatch(clz = AbstractCreature.class, method = "decrementBlock")
    public static class AttackPatch{
        @SpirePrefixPatch
        public static void Prefix(AbstractCreature _inst, DamageInfo info, int damageAmount){
            CreatureExpress express;
            if(info.owner!=null){
                express = AddExpressPatch.creatureExpress.get(info.owner);
                if(express!=null){
                    express.expressWhenAttack(damageAmount,_inst,info);
                }
            }
            express = AddExpressPatch.creatureExpress.get(_inst);
            if(express !=null){
                express.expressWhenAttacked(damageAmount,_inst,info);
            }
        }
    }

    //获得格挡
    @SpirePatch(clz = AbstractCreature.class, method = "addBlock")
    public static class BlockPatch{
        @SpireInsertPatch(rloc = 20, localvars = {"tmp"})
        public static void Insert(AbstractCreature _inst, int blockAmount, @ByRef float[] tmp){
            CreatureExpress express = AddExpressPatch.creatureExpress.get(_inst);
            if(express!=null){
                tmp[0] = express.gainBlockOrHp(tmp[0],_inst,true);
            }
        }
    }

    //回复生命 Monster 和 Player（属于Creature）分开
    @SpirePatch(clz = AbstractMonster.class, method = "heal")
    public static class MonsterHealPatch{
        @SpireInsertPatch(rloc = 8)
        public static void Insert(AbstractMonster _inst, @ByRef int[] healAmount){
            CreatureExpress express = AddExpressPatch.creatureExpress.get(_inst);
            if(express!=null){
                float tmp = (float) healAmount[0];
                tmp = express.gainBlockOrHp(tmp,_inst,false);
                healAmount[0] = (int) tmp;
            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "heal", paramtypez = {int.class, boolean.class})
    public static class CreatureHealPatch{
        @SpireInsertPatch(rloc = 22)
        public static void Insert(AbstractCreature _inst, @ByRef int[] healAmount, boolean showEffect){
            CreatureExpress express = AddExpressPatch.creatureExpress.get(_inst);
            if(express!=null){
                float tmp = (float) healAmount[0];
                tmp = express.gainBlockOrHp(tmp,_inst,false);
                healAmount[0] = (int) tmp;
            }
        }
    }

    //打出牌后
    @SpirePatch(clz = UseCardAction.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {AbstractCard.class,AbstractCreature.class})
    public static class UseCardActionPatch{
        @SpirePostfixPatch
        public static void Postfix(UseCardAction _inst, AbstractCard card, AbstractCreature target){
            for(AbstractMonster m :AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!m.isDeadOrEscaped()){
                    if(card.target == AbstractCard.CardTarget.ENEMY || card.target == AbstractCard.CardTarget.SELF_AND_ENEMY){
                        if(target == m){
                            CreatureExpress exp = AddExpressPatch.creatureExpress.get(m);
                            if(exp!=null){
                                exp.expressWhenPlayCard(card,m);
                            }
                        }
                    }
                    else if(card.target == AbstractCard.CardTarget.ALL||card.target== AbstractCard.CardTarget.ALL_ENEMY){
                        CreatureExpress exp = AddExpressPatch.creatureExpress.get(m);
                        if(exp!=null){
                            exp.expressWhenPlayCard(card,m);
                        }
                    }
                }
            }
        }
    }
}
