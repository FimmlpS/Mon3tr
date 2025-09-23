package Mon3tr.card.skill;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.status.ClockInsight;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeInsight extends AbstractMon3trCard {
    public static final String ID = "mon3tr:TimeInsight";
    private static final CardStrings cardStrings;
    int lastTriggeredCounter = 0;

    public TimeInsight(){
        super(ID, cardStrings.NAME,0, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        this.iterationCounter = 1;
        this.cardsToPreview = new ClockInsight();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
    }

    @Override
    public int increaseIteration(int amt, boolean canTechnic) {
        amt = super.increaseIteration(amt,canTechnic);
        while (this.iterationCounter>=lastTriggeredCounter+3){
            lastTriggeredCounter+=3;
            addToBot(new MakeTempCardInDrawPileAction(cardsToPreview,1,true, true));
        }
        return amt;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            cardsToPreview.upgrade();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




