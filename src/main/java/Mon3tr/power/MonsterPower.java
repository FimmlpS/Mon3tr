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

public class MonsterPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:MonsterPower";
    private static final PowerStrings powerStrings;

    public MonsterPower(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/MonsterPower_84.png";
        String path48 = "Mon3trResources/img/powers/MonsterPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 99;
    }

    @Override
    public void updateDescription() {
        //this.description = powerStrings.DESCRIPTIONS[0]+ this.amount + powerStrings.DESCRIPTIONS[1]+ this.amount + powerStrings.DESCRIPTIONS[2];
        this.description = powerStrings.DESCRIPTIONS[3];
    }

    //    @Override
//    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
//        if(card instanceof AbstractMon3trCard){
//            AbstractMon3trCard c = (AbstractMon3trCard) card;
//            if(c.iterationCounter>0){
//                float increase = (float)this.amount * c.iterationCounter;
//                increase = Math.min(increase,card.baseDamage * this.amount);
//                return type == DamageInfo.DamageType.NORMAL ? damage + increase: damage;
//            }
//        }
//        return damage;
//    }
//
//    @Override
//    public float modifyBlock(float blockAmount, AbstractCard card) {
//        if(card instanceof AbstractMon3trCard){
//            AbstractMon3trCard c = (AbstractMon3trCard) card;
//            if(c.iterationCounter>0){
//                float increase = (float)this.amount * c.iterationCounter;
//                increase = Math.min(increase,card.baseBlock * this.amount);
//                return blockAmount + increase;
//            }
//        }
//        return blockAmount;
//    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}


