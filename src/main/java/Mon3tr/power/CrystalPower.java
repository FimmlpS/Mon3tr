package Mon3tr.power;

import Mon3tr.action.InstantReducePowerAction;
import Mon3tr.card.attack.ReverseHalfLife;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CrystalPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:CrystalPower";
    private static final PowerStrings powerStrings;
    public int extraAmount;
    private final Color greenColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);
    private final Color redColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
    private static final float DISTANCE = 17F * Settings.scale;

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        boolean doublePreview = false;
        if(AbstractDungeon.player.hoveredCard instanceof ReverseHalfLife){
            AbstractMonster m = ReflectionHacks.getPrivate(AbstractDungeon.player, AbstractPlayer.class,"hoveredMonster");
            if(m == this.owner)
                doublePreview = true;
        }
        if (this.amount > 0) {
            if (!this.isTurnBased) {
                this.greenColor.a = c.a;
                c = this.greenColor;
            }
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(doublePreview?this.amount*2:this.amount), x, y, this.fontScale, c);
        } else if (this.amount < 0 && this.canGoNegative) {
            this.redColor.a = c.a;
            c = this.redColor;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(doublePreview?this.amount*2:this.amount), x, y, this.fontScale, c);
        }
        if (!this.isTurnBased) {
            this.greenColor.a = c.a;
            c = this.greenColor;
        }
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(doublePreview?((this.amount*2+4)/5):this.extraAmount), x, y+DISTANCE, this.fontScale, c);
    }

    public int getReduceAmount(){
        //return Math.max(1,this.amount/4);
        return (this.amount+4)/5;
    }

    public CrystalPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.extraAmount = getReduceAmount();
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/CrystalPower_84.png";
        String path48 = "Mon3trResources/img/powers/CrystalPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        if(owner!=null && owner.isPlayer)
            this.type = PowerType.BUFF;
        else
            this.type = PowerType.DEBUFF;
        this.priority = 99;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+ this.amount+ powerStrings.DESCRIPTIONS[1] + this.extraAmount +powerStrings.DESCRIPTIONS[2] + AbstractDungeon.player.name + powerStrings.DESCRIPTIONS[3] + this.amount + powerStrings.DESCRIPTIONS[4];
    }

    @Override
    public void atStartOfTurn() {
        AbstractPower p = this.owner.getPower(CellActivationPower.POWER_ID);
        if(p==null){
            addToBot(new GainBlockAction(AbstractDungeon.player,this.amount));
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
        else if(this.amount>0){
            int saveAmt = (amount+2)/3;
            int removeAmt = this.amount-saveAmt;
            addToBot(new GainBlockAction(AbstractDungeon.player,amount));
            addToBot(new ReducePowerAction(this.owner,this.owner,this,removeAmt));
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL){
            if(this.amount>0){
                this.flashWithoutSound();
                addToBot(new DamageAllEnemiesAction(null,DamageInfo.createDamageMatrix(this.amount,true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY,true));
                addToBot(new InstantReducePowerAction(this.owner,this.owner,this,this.extraAmount));
            }
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        extraAmount = getReduceAmount();
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        extraAmount = getReduceAmount();
        updateDescription();
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}





