package Mon3tr.card;

import Mon3tr.helper.ImageHelper;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.ColorEnum;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.Mon3trTag;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;

public abstract class AbstractMon3trCard extends CustomCard {

    protected boolean showAnotherPreview = false;
    protected AbstractCard anotherPreview = null;
    public boolean isStrategyCard = false;
    public boolean canChangeStrategy = false;
    public boolean dontSetStrategyDescription = false;
    public boolean specialType = false;

    //打出时需要的技力
    public int technicalNeeded = 0;
    //迭代值
    public int iterationCounter = 0;
    //自旋转角度
    private float gearAngle = 0;
    //渲染颜色
    private Color gearRenderColor = new Color(-1);
    //旋转矩阵
    public static Matrix4 mx4 = new Matrix4();
    //旋转矩阵-默认
    public static Matrix4 mxNormal = new Matrix4();
    //旋转向量
    public static Vector2 rotatedTmp = new Vector2();

    public int increaseIteration(int amt){
        return increaseIteration(amt,true);
    }

    public int increaseIteration(int amt,boolean canTechnic){
        if(this.hasTag(Mon3trTag.MISSING_MON3TR)){
            amt+=2;
        }
        if(amt>0){
            iterationCounter += amt;
            if(canTechnic){
                MeltdownPatch.increaseMeltdownCounter(amt);
            }
        }
        return amt;
    }

    public AbstractMon3trCard(String id, String name, int cost, String rawDescription, CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardIMGPatch(id, type), cost, rawDescription, type, ColorEnum.Mon3tr_COLOR, rarity, target);
    }

    //临时卡图
    public AbstractMon3trCard(boolean isTmp, String id, String name, int cost, String rawDescription, CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardTmpIMGPatch(type), cost, rawDescription, type, ColorEnum.Mon3tr_COLOR, rarity, target);
    }

    //暂时其他卡图
    public AbstractMon3trCard(String id, String name,String imgPath, int cost, String rawDescription, CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, new RegionName(imgPath), cost, rawDescription, type, ColorEnum.Mon3tr_COLOR, rarity, target);
    }

    //暂时其他卡图
    public AbstractMon3trCard(String id, String name,String imgPath, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, imgPath, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void initializeDescription() {
        if(!dontSetStrategyDescription){
            String changeDes = StringHelper.cards.TEXT[1];
            if(canChangeStrategy){
                if(!rawDescription.contains(changeDes)){
                    rawDescription += changeDes;
                }
            }
            else{
                rawDescription = rawDescription.replaceAll(changeDes,"");
            }
        }
        super.initializeDescription();
    }

    public void changeStrategy(boolean flash, boolean changeNumber){}

    public boolean inBattleAndInHand(){
        if(AbstractDungeon.currMapNode == null)
            return false;
        if(AbstractDungeon.getCurrRoom()==null)
            return false;
        if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
            return false;
        //不能切换时也不能执行
        if(!canChangeStrategy)
            return false;
        return AbstractDungeon.player.hand.contains(this);
    }

    public boolean inBattleAndInDiscard(boolean yourTurn){
        if(AbstractDungeon.currMapNode == null)
            return false;
        if(AbstractDungeon.getCurrRoom()==null)
            return false;
        if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
            return false;
        if(yourTurn && AbstractDungeon.actionManager.turnHasEnded)
            return false;
        return AbstractDungeon.player.discardPile.contains(this);
    }

    public boolean inBattleAndInExhaust(boolean yourTurn){
        if(AbstractDungeon.currMapNode == null)
            return false;
        if(AbstractDungeon.getCurrRoom()==null)
            return false;
        if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
            return false;
        if(yourTurn && AbstractDungeon.actionManager.turnHasEnded)
            return false;
        CardGroup copyExhaust = ReflectionHacks.getPrivate(AbstractDungeon.exhaustPileViewScreen,ExhaustPileViewScreen.class,"exhaustPileCopy");
        if(copyExhaust==null)
            return false;
        return copyExhaust.group.contains(this);
    }

    @Override
    public void update() {
        super.update();
        //考虑加速度
        if(iterationCounter>0){
            int rotateLevel = iterationCounter;
            if(rotateLevel>5){
                rotateLevel = 5;
            }
            gearAngle += Gdx.graphics.getDeltaTime() * rotateLevel * 30F;
            if(gearAngle>360F){
                gearAngle -= 360F;
            }
        }
    }

    @SpireOverride
    protected void renderImage(SpriteBatch sb, boolean hovered, boolean selected) {
        float x = (float) MathUtils.round(current_x);
        float y = (float) MathUtils.round(current_y);
        mx4.setToRotation(0F,0F,1F,angle);
        rotatedTmp.x = (float) MathUtils.round(84F* this.drawScale * Settings.scale);
        rotatedTmp.y = (float) MathUtils.round(144F* this.drawScale * Settings.scale);
        rotatedTmp.rotate(angle);
        mx4.trn(x + rotatedTmp.x, y + rotatedTmp.y, 0F);
        sb.setColor(gearRenderColor);
        sb.end();
        sb.setTransformMatrix(mx4);
        sb.begin();
        sb.draw(ImageHelper.gear, 0f,0f,64f,64f,128f,128f,drawScale* Settings.scale,drawScale* Settings.scale,gearAngle);
        sb.end();
        sb.setTransformMatrix(mxNormal);
        sb.begin();
        SpireSuper.call(sb,hovered,selected);
    }

    @SpireOverride
    protected void renderEnergy(SpriteBatch sb){
        SpireSuper.call(sb);
        String text = String.valueOf(iterationCounter);
        FontHelper.cardEnergyFont_L.getData().setScale(this.drawScale);
        BitmapFont font = FontHelper.cardEnergyFont_L;
        FontHelper.renderRotatedText(sb, font, text, this.current_x, this.current_y, 132.0F * this.drawScale * Settings.scale, 192.0F * this.drawScale * Settings.scale, this.angle, false,gearRenderColor);
    }

    @SpireOverride
    protected void updateTransparency(){
        SpireSuper.call();
        if(gearRenderColor!=null)
            this.gearRenderColor.a = transparency;
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if(canChangeStrategy){
            if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {
                float tmpScale = this.drawScale * 0.7F;
                this.cardsToPreview.current_x = this.current_x;
                this.cardsToPreview.current_y = this.current_y + IMG_HEIGHT * 0.9F * this.drawScale;
                this.cardsToPreview.drawScale = tmpScale;
                this.cardsToPreview.render(sb);
                if(showAnotherPreview && anotherPreview!=null){
                    if (this.current_x > (float) Settings.WIDTH * 0.75F) {
                        this.anotherPreview.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.7F + 16.0F) * this.drawScale;
                    } else {
                        this.anotherPreview.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.7F + 16.0F) * this.drawScale;
                    }
                    this.anotherPreview.current_y = this.current_y + (IMG_HEIGHT / 2.0F + IMG_HEIGHT / 2.0F * 0.43F) * this.drawScale;
                    this.anotherPreview.drawScale = tmpScale;
                    this.anotherPreview.render(sb);
                }
            }
        }
        else if(showAnotherPreview){
            if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {
                float tmpScale = this.drawScale * 0.7F;
                if (this.current_x > (float) Settings.WIDTH * 0.75F) {
                    this.cardsToPreview.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.7F + 16.0F) * this.drawScale;
                    this.anotherPreview.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.7F + 16.0F) * this.drawScale;
                } else {
                    this.cardsToPreview.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.7F + 16.0F) * this.drawScale;
                    this.anotherPreview.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.7F + 16.0F) * this.drawScale;
                }

                this.cardsToPreview.current_y = this.current_y;
                this.cardsToPreview.drawScale = tmpScale;
                this.cardsToPreview.render(sb);
                this.anotherPreview.current_y = this.current_y + (IMG_HEIGHT / 2.0F + IMG_HEIGHT / 2.0F * 0.43F) * this.drawScale;
                this.anotherPreview.drawScale = tmpScale;
                this.anotherPreview.render(sb);
            }
        }
        else
            super.renderCardPreview(sb);
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if(c instanceof AbstractMon3trCard){
            AbstractMon3trCard mc = (AbstractMon3trCard) c;
            mc.iterationCounter = this.iterationCounter;
            if(!mc.isStrategyCard)
                return mc;
            mc.canChangeStrategy = this.canChangeStrategy;
            if(!mc.canChangeStrategy){
                mc.cardsToPreview = null;
            }
            if(this.specialType){
                mc.changeStrategy(false,false);
            }
            return mc;
        }
        return c;
    }

    @Override
    public void unfadeOut() {
        super.unfadeOut();
        if(gearRenderColor!=null)
            this.gearRenderColor.a = transparency;
    }

    public int getEvolutionCount(AbstractCard card) {
        return 0;
    }

    public boolean shouldEvolution(AbstractCard card) {
        return false;
    }

    //演化X：条件
    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        if (cardPlayed != this) {
            if (shouldEvolution(cardPlayed)) {
                this.flash();
                increaseIteration(getEvolutionCount(cardPlayed));
            }
        }
    }
}
