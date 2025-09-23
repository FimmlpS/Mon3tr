package Mon3tr.card.skill;

import Mon3tr.action.RandomIterationAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LullabyOfDeath extends AbstractMon3trCard {
    public static final String ID = "mon3tr:LullabyOfDeath";
    private static final CardStrings cardStrings;

    public LullabyOfDeath(){
        super(ID, cardStrings.NAME,2, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        this.baseBlock = 11;
        this.block = 11;
        this.baseMagicNumber = 6;
        this.magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new RandomIterationAction(this.magicNumber));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(4);
            upgradeMagicNumber(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



