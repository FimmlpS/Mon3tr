package Mon3tr.power;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FateReflowPower extends AbstractPower {

    public static final String POWER_ID = "mon3tr:FateReflowPower";
    public static final String POWER_ID_UPGRADE = "mon3tr:UpgradeFateReflowPower";
    private static final PowerStrings powerStrings;

    public FateReflowPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("talk_to_hand");
//        String path128 = "Mon3trResources/img/powers/MonsterPower_84.png";
//        String path48 = "Mon3trResources/img/powers/MonsterPower_32.png";
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.DEBUFF;
        this.priority = 99;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+ this.amount*25 + powerStrings.DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}






