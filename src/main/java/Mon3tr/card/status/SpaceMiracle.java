package Mon3tr.card.status;

import Mon3tr.action.IterationAllHandAction;
import Mon3tr.card.skill.DimensionMiracle;
import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

public class SpaceMiracle extends CustomCard {
    public static final String ID = "mon3tr:SpaceMiracle";
    private static final CardStrings cardStrings;

    public SpaceMiracle(boolean preview){
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.STATUS), 0, cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
        this.selfRetain = true;
        if(preview)
            cardsToPreview = new DimensionMiracle(false);
    }

    public SpaceMiracle() {
        this(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!Settings.DISABLE_EFFECTS) {
            this.addToBot(new VFXAction(new BorderFlashEffect(Color.GOLDENROD, true)));
        }

        this.addToBot(new VFXAction(new MiracleEffect()));
        if (this.upgraded) {
            this.addToBot(new GainEnergyAction(2));
        } else {
            this.addToBot(new GainEnergyAction(1));
        }
        addToBot(new IterationAllHandAction(1,DimensionMiracle.ID));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}

