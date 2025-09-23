package Mon3tr.card.power;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.DimensionalDeductionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DimensionalDeduction extends AbstractMon3trCard {
    public static final String ID = "mon3tr:DimensionalDeduction";
    private static final CardStrings cardStrings;

    public DimensionalDeduction(){
        super(ID, cardStrings.NAME, "colorless/power/panache",1, cardStrings.DESCRIPTION, CardType.POWER,CardRarity.RARE,CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DimensionalDeductionPower(p)));
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


