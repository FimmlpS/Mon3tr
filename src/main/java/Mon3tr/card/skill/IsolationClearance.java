package Mon3tr.card.skill;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IsolationClearance extends AbstractMon3trCard {
    public static final String ID = "mon3tr:IsolationClearance";
    private static final CardStrings cardStrings;

    public IsolationClearance(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MeltdownPatch.energyToStrength(magicNumber);
        MeltdownPatch.drawToDexterity(magicNumber);
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(1);
        }
    }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




