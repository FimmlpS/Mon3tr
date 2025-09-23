package Mon3tr.power;

import Mon3tr.card.status.BlackSnow;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AltCountryPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:AltCountryPower";
    private static final PowerStrings powerStrings;

    public AltCountryPower(AbstractCreature owner, int amt){
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        String path128 = "Mon3trResources/img/powers/AltCountryPower_84.png";
        String path48 = "Mon3trResources/img/powers/AltCountryPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128),0,0,84,84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48),0,0,32,32);
        this.updateDescription();
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
    }

    @Override
    public void onSpecificTrigger() {
        this.flash();
        this.owner.heal(amount);
    }

    @Override
    public void updateDescription() {
        String des = powerStrings.DESCRIPTIONS[0]+this.amount+powerStrings.DESCRIPTIONS[1];
        this.description = des;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}



