package Mon3tr.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DiscordCrystalPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:DiscordCrystalPower";
    private static final PowerStrings powerStrings;
    public static int tryTimes = 0;

    public DiscordCrystalPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/DiscordCrystalPower_84.png";
        String path48 = "Mon3trResources/img/powers/DiscordCrystalPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.DEBUFF;
        this.priority = 98;
    }

    @Override
    public void onSpecificTrigger() {
        this.flashWithoutSound();
        addToBot(new ApplyPowerAction(this.owner,this.owner,new CrystalPower(this.owner,this.amount),this.amount));
    }

    @Override
    public void onRemove() {
        if(tryTimes<5){
            tryTimes++;
            addToTop(new ApplyPowerAction(this.owner,this.owner,new DiscordCrystalPower(this.owner,this.amount),this.amount));
        }
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}
