package Mon3tr.card.skill;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.attack.TheNowOne;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class CircularTwins extends AbstractMon3trCard {
    public static final String ID = "mon3tr:CircularTwins";
    private static final CardStrings cardStrings;

    public CircularTwins(){
        super(ID, cardStrings.NAME,3, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        showAnotherPreview = true;
        cardsToPreview = new ThePastOne();
        anotherPreview = new TheNowOne();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(cardsToPreview));
        addToBot(new MakeTempCardInHandAction(anotherPreview));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




