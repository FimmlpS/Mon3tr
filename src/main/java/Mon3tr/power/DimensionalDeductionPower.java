package Mon3tr.power;

import Mon3tr.card.AbstractMon3trCard;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DimensionalDeductionPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:DimensionalDeductionPower";
    private static final PowerStrings powerStrings;

    public DimensionalDeductionPower(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("panache");
//        String path128 = "Mon3trResources/img/powers/MonsterPower_84.png";
//        String path48 = "Mon3trResources/img/powers/MonsterPower_32.png";
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 99;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof AbstractMon3trCard){
            AbstractMon3trCard mc = (AbstractMon3trCard) card;
            if(mc.canChangeStrategy && mc.cardsToPreview!=null){
                this.flash();
                AbstractMon3trCard another = (AbstractMon3trCard) (mc.cardsToPreview.makeStatEquivalentCopy());
                another.canChangeStrategy = false;
                another.cardsToPreview = null;
                CardModifierManager.addModifier(another,new ExhaustMod());
                CardModifierManager.addModifier(another,new EtherealMod());
                addToBot(new MakeTempCardInHandAction(another));
            }
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}



