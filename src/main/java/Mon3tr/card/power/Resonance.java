package Mon3tr.card.power;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.ResonancePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Resonance extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Resonance";
    private static final CardStrings cardStrings;

    public Resonance(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber =3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ResonancePower(p,magicNumber),magicNumber));
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




