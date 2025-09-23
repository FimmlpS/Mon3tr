package Mon3tr.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CellActivationPower extends AbstractPower {
    public int extraAmount;

    public static final String POWER_ID = "mon3tr:CellActivationPower";
    private static final PowerStrings powerStrings;

    public CellActivationPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/CellActivationPower_84.png";
        String path48 = "Mon3trResources/img/powers/CellActivationPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 100;
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        addToBot(new ApplyPowerAction(this.owner,this.owner,new CrystalPower(this.owner, this.amount), this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+this.amount+powerStrings.DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}




