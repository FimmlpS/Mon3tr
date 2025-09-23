package Mon3tr.card.status;

import Mon3tr.action.IterationDrawAction;
import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightBulbEffect;

public class ClockInsight extends CustomCard {
    public static final String ID = "mon3tr:ClockInsight";
    private static final CardStrings cardStrings;

    public ClockInsight() {
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.STATUS), 0, cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new LightBulbEffect(p.hb)));
        } else {
            this.addToBot(new VFXAction(new LightBulbEffect(p.hb), 0.2F));
        }

        this.addToBot(new DrawCardAction(this.magicNumber,new IterationDrawAction(2),true));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}

