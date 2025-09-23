package Mon3tr.card.power;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.DeconstructionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DeconstructToChaos extends AbstractMon3trCard {
    public static final String ID = "mon3tr:DeconstructToChaos";
    private static final CardStrings cardStrings;

    public DeconstructToChaos(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber =1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DeconstructionPower(p,magicNumber),magicNumber));
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



