package Mon3tr.card.skill;

import Mon3tr.action.NewLiveAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NewLive extends AbstractMon3trCard {
    public static final String ID = "mon3tr:NewLive";
    private static final CardStrings cardStrings;

    public NewLive(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new NewLiveAction(this.magicNumber));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



