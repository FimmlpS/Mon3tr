package Mon3tr.power;

import Mon3tr.patch.MeltdownPatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ResonancePower extends AbstractPower {

    public static final String POWER_ID = "mon3tr:ResonancePower";
    private static final PowerStrings powerStrings;

    int extraLevel;
    private final Color greenColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);
    private static final float DISTANCE = 17F * Settings.scale;

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (!this.isTurnBased) {
            this.greenColor.a = c.a;
            c = this.greenColor;
        }
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.extraLevel), x, y+DISTANCE, this.fontScale, c);
    }

    public ResonancePower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.extraLevel = 1;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/ResonancePower_84.png";
        String path48 = "Mon3trResources/img/powers/ResonancePower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
        this.priority = 99;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.extraLevel++;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if(MeltdownPatch.isMeltdownTurn){
            this.flashWithoutSound();
            this.amount += this.extraLevel;
            this.updateDescription();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if(!m.isDeadOrEscaped())
                    this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+ this.amount + powerStrings.DESCRIPTIONS[1] + extraLevel + powerStrings.DESCRIPTIONS[2];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}






