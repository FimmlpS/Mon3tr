package Mon3tr.patch;

import Mon3tr.action.AddMeltdownAction;
import Mon3tr.action.IncreaseTechnicalAction;
import Mon3tr.action.Mon3trChangeStyleAction;
import Mon3tr.card.status.StrategyMeltdown;
import Mon3tr.card.status.StrategyOverload;
import Mon3tr.character.Mon3tr;
import Mon3tr.modcore.Mon3trMod;
import Mon3tr.ui.CharSelectScreen;
import Mon3tr.ui.TechnicUI;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;


public class MeltdownPatch {
    //说是熔毁区，其实有两种牌- 策略：超负荷 和 策略：熔毁 战斗中可以通过右击切换

    public static CardGroup meltdownGroup = new CardGroup(OtherEnum.HAND_MELTDOWN);
    public static boolean isMeltdownTurn = false;
    public static boolean shouldEndMeltdownTurn = false;
    public static boolean canRecoverCounter = false;
    public static MeltdownType meltdownType = MeltdownType.Default;
    //临时力敏获取
    public static int tmpStrengthObtained = 0;
    public static int tmpDexterityObtained = 0;

    //每回合开始时回合保存
    //2025/7/11 技力固定每回合30
    public static int meltdownCounterStart = 30;
    public static int meltDownCounter = 0;
    public static int meltDownCounterMax = 80;

    //2025/6/12 超负荷回合技力减半
    public static int technicObtained = 0;

    public static void battleStart(){
        meltDownCounter = meltdownCounterStart;
        if(meltDownCounter>meltDownCounterMax)
            meltDownCounter = meltDownCounterMax;
        TechnicUI.onAmountChange(false);
    }

    public static boolean isMeltdownTurn(){
        return isMeltdownTurn && meltdownType==MeltdownType.Meltdown;
    }

    public static void clearMeltdown(){
        meltdownGroup.clear();
        isMeltdownTurn = false;
        shouldEndMeltdownTurn = false;
        meltdownType = MeltdownType.Default;
        tmpStrengthObtained = 0;
        tmpDexterityObtained = 0;
    }

    public static void resetMon3tr(){
        if(AbstractDungeon.player instanceof Mon3tr){
            ((Mon3tr) AbstractDungeon.player).resetAnimation();
        }
    }

    public static void addMeltdown(){
        if(!(AbstractDungeon.player instanceof Mon3tr))
            return;
        if(!isMeltdownTurn){
            AbstractDungeon.actionManager.addToBottom(new AddMeltdownAction(new StrategyOverload()));
        }
    }

    public static void setEndTurn(){
        canRecoverCounter = false;
        if(isMeltdownTurn){
            if(meltdownType==MeltdownType.Meltdown){
                canRecoverCounter = true;
            }
            shouldEndMeltdownTurn = true;
        }
    }

    public static void drawToDexterity(int amt){
        if(amt>0){
            tmpDexterityObtained+=amt;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,amt),amt,true));
        }
    }

    public static void energyToStrength(int amt){
        if(amt>0){
            tmpStrengthObtained+=amt;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,amt),amt,true));
        }
    }

    public static void resetStrengthAndDexterity(){
        if(tmpStrengthObtained>0){
            AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
            int maxLose = tmpStrengthObtained;
            if(strength!=null&&strength.amount>0){
                maxLose = Math.min(tmpStrengthObtained,strength.amount);
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,-maxLose),-maxLose));
        }
        if(tmpDexterityObtained>0){
            AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
            int maxLose = tmpDexterityObtained;
            if(dexterity!=null&&dexterity.amount>0){
                maxLose = Math.min(tmpDexterityObtained,dexterity.amount);
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,-maxLose),-maxLose));
        }

        tmpStrengthObtained = 0;
        tmpDexterityObtained = 0;
    }

    public static void increaseMeltdownCounterStart(int amt){
        meltdownCounterStart+=amt;
    }

    public static void increaseMeltdownCounter(int amt){
        if(isMeltdownTurn && meltdownType == MeltdownType.Overload){
            technicObtained += amt;
            amt = 0;
            while(technicObtained>=2){
                technicObtained -= 2;
                amt++;
            }
        }
        if(amt>0){
            meltDownCounter += amt;
            if(meltDownCounter>meltDownCounterMax){
                meltDownCounter = meltDownCounterMax;
            }
            TechnicUI.onAmountChange(false);
        }
    }

    public static void decreaseMeltdownCount(int amt){
        if(amt>0){
            meltDownCounter -= amt;
            if(meltDownCounter<0){
                meltDownCounter = 0;
            }
            TechnicUI.onAmountChange(false);
        }
    }

    public static void startMeltdown(MeltdownType type){
        isMeltdownTurn = true;
        meltdownType = type;
        technicObtained = 0;
        if(CharSelectScreen.hardModeEnable(1)){
            decreaseMeltdownCount(10);
        }
    }

    public static void endMeltdown(){
        isMeltdownTurn = false;
        resetStrengthAndDexterity();
        meltdownType = MeltdownType.Default;
    }

    //以effect形式在非熔毁回合添加 策略：熔毁

    public static void addToTop(AbstractCard c){
        if(c!=null){
            if(AbstractDungeon.player.hoveredCard == c){
                AbstractDungeon.player.releaseCard();
            }
            c.untip();
            c.unhover();
            c.stopGlowing();
            c.lighten(true);
            meltdownGroup.addToTop(c);
        }
    }


    //手牌同步相关
    public static void glowCheck(){
        meltdownGroup.glowCheck();
    }
    public static void renderHand(SpriteBatch sb, AbstractCard except){
        meltdownGroup.renderHand(sb,except);
    }
    public static void update(){
        meltdownGroup.update();
    }
    public static void updateLogic(){
        meltdownGroup.updateHoverLogic();
    }
    public static void renderTip(SpriteBatch sb){
        meltdownGroup.renderTip(sb);
    }
    public static void applyPowers(){
        meltdownGroup.applyPowers();
    }
    public static void resetBeforeRemove(AbstractCard c){
        meltdownGroup.removeCard(c);
    }

    public static void hideMeltdown(){
        for(AbstractCard card:meltdownGroup.group){
            card.target_y = -AbstractCard.IMG_HEIGHT;
        }
    }

    public static void moveToMeltdown(AbstractCard c){
        //熔毁区上限为1张，所以添加牌前清空
        meltdownGroup.clear();
        if(c!=null){
            if(AbstractDungeon.player.hoveredCard == c){
                AbstractDungeon.player.releaseCard();
            }
            AbstractDungeon.actionManager.removeFromQueue(c);
            c.untip();
            c.unhover();
            c.stopGlowing();
            c.lighten(true);
            c.setAngle(0F);
            c.drawScale = 0.12F;
            c.targetDrawScale = 0.75F;
            c.current_x = CardGroup.DRAW_PILE_X;
            c.current_y = CardGroup.DRAW_PILE_Y + 100F* Settings.scale;
            meltdownGroup.addToTop(c);

        }
    }

    public static void changeStrategy(AbstractCard c){
        Mon3trMod.logSomething("=== "+ c.name + " 即将切换策略 ===");
        if(AbstractDungeon.currMapNode == null)
            return;
        if(AbstractDungeon.getCurrRoom()==null)
            return;
        if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
            return;
        if(!meltdownGroup.contains(c))
            return;
        AbstractDungeon.player.releaseCard();
        AbstractCard target = null;
        if(c instanceof StrategyOverload){
            target = new StrategyMeltdown();
            target.flash(Color.RED.cpy());
        }
        else if(c instanceof StrategyMeltdown){
            target = new StrategyOverload();
            target.flash(Color.BLUE.cpy());
        }

        if(target!=null){
            target.untip();
            target.unhover();
            target.stopGlowing();
            target.lighten(true);
            target.drawScale = c.drawScale;
            target.targetDrawScale = c.targetDrawScale;
            target.current_x = c.current_x;
            target.current_y = c.current_y;
            target.target_x = c.target_x;
            target.target_y = c.target_y;
            target.angle = c.angle;
            target.targetAngle = c.targetAngle;
            meltdownGroup.clear();
            meltdownGroup.addToTop(target);
            target.applyPowers();
        }
    }

    //*IMPORTANT 熔毁牌绑定在最左侧手牌的左边，保持与其相同的角度
    public static void refreshMeltdownLayout(){
        if(AbstractDungeon.getCurrRoom().monsters==null||!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            if(meltdownGroup.size()==0){
                return;
            }
            AbstractPlayer p = AbstractDungeon.player;
            AbstractCard meltdown = meltdownGroup.getBottomCard();
            float sinkStart = (float) 80F* Settings.scale;
            if(p.hand.size()==0){
                meltdown.target_y = sinkStart;
                meltdown.targetDrawScale = 0.75F;
                meltdown.setAngle(0F);
                meltdown.target_x = (float) Settings.WIDTH/2F;
            }
            else {
                AbstractCard firstHand = p.hand.getBottomCard();
                meltdown.target_y = firstHand.target_y;
                meltdown.targetDrawScale = firstHand.targetDrawScale;
                meltdown.setAngle(firstHand.targetAngle);
                if(p.hand.size()>1){
                    AbstractCard secondHand = p.hand.group.get(1);
                    meltdown.target_x = firstHand.target_x - (secondHand.target_x - firstHand.target_x);
                }
                else{
                    meltdown.target_x = firstHand.target_x - AbstractCard.IMG_WIDTH_S;
                }
            }

            glowCheck();
        }
    }

    //选择卡牌（修改CardGroup*HAND），统一排在手牌后选中。
    @SpirePatch(clz = CardGroup.class,method = "getHoveredCard")
    public static class HoverMeltdownPatch{
        @SpirePostfixPatch
        public static AbstractCard Postfix(AbstractCard ret,CardGroup _inst){
            if(ret==null&&!(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT&&AbstractDungeon.isScreenUp)&&_inst == AbstractDungeon.player.hand&&AbstractDungeon.topPanel.potionUi.isHidden&&!AbstractDungeon.topPanel.potionUi.targetMode){
                return meltdownGroup.getHoveredCard();
            }
            return ret;
        }
    }

    //更新手牌，统一在手牌前更新。
    @SpirePatch(clz = CardGroup.class,method = "update")
    public static class UpdateMeltdownPatch{
        @SpirePrefixPatch
        public static void Prefix(CardGroup _inst){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                update();
            }
        }
    }

    //更新手牌LOGIC，统一在手牌前更新。
    @SpirePatch(clz = CardGroup.class,method = "updateHoverLogic")
    public static class UpdateMeltdownLogicPatch{
        @SpirePrefixPatch
        public static void Prefix(CardGroup _inst){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                updateLogic();
            }
        }
    }

    //GLOW CHECK
    @SpirePatch(clz = CardGroup.class,method = "glowCheck")
    public static class GlowCheckGroup{
        @SpirePrefixPatch
        public static void Prefix(CardGroup _inst){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                glowCheck();
            }
        }
    }

    //渲染卡牌，统一在手牌前渲染
    @SpirePatch(clz = CardGroup.class,method = "renderHand")
    public static class RenderMeltdownPatch{
        @SpirePrefixPatch
        public static void Prefix(CardGroup _inst, SpriteBatch sb,AbstractCard exceptThis){
            if(AbstractDungeon.player!=null && _inst == AbstractDungeon.player.hand){
                renderHand(sb,exceptThis);
            }
        }
    }

    //渲染卡牌TIP
    @SpirePatch(clz = CardGroup.class,method = "renderTip")
    public static class RenderMeltdownTipPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst,SpriteBatch sb){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                renderTip(sb);
            }
        }
    }

    //设置applyPowers
    @SpirePatch(clz = CardGroup.class,method = "applyPowers")
    public static class ApplyPowersPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst){
            if(_inst == AbstractDungeon.player.hand){
                applyPowers();
            }
        }
    }

    //设置布局，统一在手牌处理hoveredCard前更新
    @SpirePatch(clz = CardGroup.class,method = "refreshHandLayout")
    public static class RefreshCloudPatch{
        @SpireInsertPatch(rloc = 201)
        public static void Insert(CardGroup _inst){
            if(_inst == AbstractDungeon.player.hand){
                refreshMeltdownLayout();
            }
        }
    }

    //HoverPush，选中牌后，把其他牌位置挤开 主要是选中手牌时，把熔毁牌向左挤
    @SpirePatch(clz = CardGroup.class, method = "hoverCardPush")
    public static class PushMeltDownPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst, AbstractCard c){
            if(meltdownGroup.size()==0){
                return;
            }
            if(_inst == AbstractDungeon.player.hand){
                int cardNum = -1;
                for(int i =0;i<_inst.size();i++){
                    if(c.equals(_inst.group.get(i))){
                        cardNum = i;
                        break;
                    }
                }
                if(cardNum==-1)
                    return;

                float pushAmt = 0.4F;
                if(_inst.size() == 1){
                    pushAmt = 0.2F;
                }
                else if(_inst.size()==2||_inst.size()==3){
                    pushAmt = 0.27F;
                }
                for(int slot = cardNum-1;slot>-1&&slot<_inst.group.size();slot--){
                    pushAmt *= 0.25F;
                }
                AbstractCard meltDown = meltdownGroup.getBottomCard();
                //push
                meltDown.target_x -= AbstractCard.IMG_WIDTH_S * pushAmt;
            }
        }
    }

    //HIDE
    @SpirePatch(clz = OverlayMenu.class,method = "hideCombatPanels")
    public static class HideMeltdownPatch{
        @SpirePostfixPatch
        public static void Postfix(OverlayMenu _inst){
            hideMeltdown();
        }
    }

    //熔毁回合结束时添加结束action
    @SpirePatch(clz = EndTurnButton.class, method = "disable",paramtypez = {boolean.class})
    public static class EndTurnStylePatch{
        @SpirePostfixPatch
        public static void Postfix(EndTurnButton _inst, boolean isEnemyTurn){
            if(isEnemyTurn){
                if(shouldEndMeltdownTurn&&AbstractDungeon.player instanceof Mon3tr){
                    shouldEndMeltdownTurn = false;
                    AbstractDungeon.actionManager.addToTop(new Mon3trChangeStyleAction(1));
                }
            }
        }
    }

    //重置手牌，用以移动到其他区域时使用
    @SpirePatch(clz = CardGroup.class,method = "resetCardBeforeMoving")
    public static class ResetMeltdownPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst, AbstractCard c){
            if(_inst == AbstractDungeon.player.hand){
                resetBeforeRemove(c);
            }
        }
    }

    //策略牌销毁
    @SpirePatch(clz = ShowCardAndPoofAction.class,method = "update")
    public static class RemoveStrategyCardPatch{
        @SpirePrefixPatch
        public static void Prefix(ShowCardAndPoofAction _inst){
            AbstractCard c = ReflectionHacks.getPrivate(_inst,ShowCardAndPoofAction.class,"card");
            if(c!=null && meltdownGroup.contains(c)){
                meltdownGroup.removeCard(c);
            }
        }
    }

    //回合结束回复技力
    @SpirePatch(clz = AbstractRoom.class,method = "applyEndOfTurnRelics")
    public static class ApplyEndOfTurnRelicsPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractRoom _inst){
            int energy = EnergyPanel.getCurrentEnergy();
            if(energy>0){
                AbstractDungeon.actionManager.addToBottom(new IncreaseTechnicalAction(energy*2));
            }
        }
    }

    //TIP时渲染技力
    @SpirePatch(clz = FtueTip.class,method = "render")
    public static class RenderFtueTipPatch{
        @SpirePostfixPatch
        public static void Postfix(FtueTip _inst, SpriteBatch sb){
            if(AbstractDungeon.player instanceof Mon3tr && _inst.type == FtueTip.TipType.CARD){
                AbstractCard c = ReflectionHacks.getPrivate(_inst,FtueTip.class,"c");
                if(meltdownGroup.contains(c)){
                    TechnicPatch.ui.render(sb);
                }
            }
        }
    }

    //策略：熔毁转化抽牌和能量获取
    //UPDATE 2025/6/10删除
//    @SpirePatch(clz = DrawCardAction.class,method = "update")
//    public static class StopDrawPatch{
//        @SpirePrefixPatch
//        public static SpireReturn<Void> Prefix(DrawCardAction _inst){
//            if(isMeltdownTurn && meltDownCounter==0 && meltdownType==MeltdownType.Meltdown){
//                DrawCardAction.drawnCards.clear();
//                drawToDexterity(_inst.amount);
//                _inst.isDone = true;
//                return SpireReturn.Return();
//            }
//            return SpireReturn.Continue();
//        }
//    }
//
//    @SpirePatch(clz = EnergyPanel.class,method = "addEnergy")
//    public static class StopEnergyPatch{
//        @SpirePrefixPatch
//        public static SpireReturn<Void> Prefix(int e){
//            if(isMeltdownTurn && meltDownCounter==0 && meltdownType==MeltdownType.Meltdown){
//                energyToStrength(e);
//                return SpireReturn.Return();
//            }
//
//            return SpireReturn.Continue();
//        }
//    }

    public enum MeltdownType{
        Default,
        Meltdown,
        Overload
    }

}
