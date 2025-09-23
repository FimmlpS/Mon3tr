package Mon3tr.power;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ProgressiveApproachPower extends AbstractPower {
    public int extraAmount;
    public int lastExtra;
    private final Color greenColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);
    private static final float DISTANCE = 17F * Settings.scale;

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (!this.isTurnBased) {
            this.greenColor.a = c.a;
            c = this.greenColor;
        }
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.extraAmount), x, y+DISTANCE, this.fontScale, c);
    }

    public static final String POWER_ID = "mon3tr:ProgressiveApproachPower";
    private static final PowerStrings powerStrings;

    public ProgressiveApproachPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.extraAmount = 1;
        this.lastExtra = 1;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/ProgressiveApproachPower_84.png";
        String path48 = "Mon3trResources/img/powers/ProgressiveApproachPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 99;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.extraAmount--;
        if(extraAmount==0){
            this.flash();
            lastExtra+=2;
            extraAmount = lastExtra;
            addToBot(new DrawCardAction(this.amount));
        }
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            this.extraAmount = 1;
            this.lastExtra = 1;
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+ extraAmount + powerStrings.DESCRIPTIONS[1] + this.amount + powerStrings.DESCRIPTIONS[2];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}




