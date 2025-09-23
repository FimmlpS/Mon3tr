package Mon3tr.card.skill;

import Mon3tr.action.RedirectAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Redirect extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Redirect";
    private static final CardStrings cardStrings;

    public Redirect(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        this.baseBlock = 5;
        this.block = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        this.addToBot(new RedirectAction(p));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



