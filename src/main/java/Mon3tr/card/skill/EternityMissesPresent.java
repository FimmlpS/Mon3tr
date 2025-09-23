package Mon3tr.card.skill;

import Mon3tr.action.EternityMissesPresentAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EternityMissesPresent extends AbstractMon3trCard {
    public static final String ID = "mon3tr:EternityMissesPresent";
    private static final CardStrings cardStrings;

    public EternityMissesPresent(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EternityMissesPresentAction());
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            this.selfRetain = true;
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}






