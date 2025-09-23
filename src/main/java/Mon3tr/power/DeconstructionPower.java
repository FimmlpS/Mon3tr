package Mon3tr.power;

import Mon3tr.card.AbstractMon3trCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DeconstructionPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:DeconstructionPower";
    private static final PowerStrings powerStrings;

    public DeconstructionPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/DeconstructionPower_84.png";
        String path48 = "Mon3trResources/img/powers/DeconstructionPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 50;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+ this.amount+ powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        float increase = Math.abs(AbstractDungeon.player.hand.size() - 5) * this.amount;
        return type == DamageInfo.DamageType.NORMAL ? damage + increase: damage;
    }

//    @Override
//    public float modifyBlock(float blockAmount, AbstractCard card) {
//        float increase = Math.abs(AbstractDungeon.player.hand.size() - 5) * this.amount;
//        return blockAmount + increase;
//    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}



