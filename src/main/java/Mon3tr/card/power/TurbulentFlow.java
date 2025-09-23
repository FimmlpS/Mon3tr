package Mon3tr.card.power;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.ResonancePower;
import Mon3tr.power.TurbulentFlowPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TurbulentFlow extends AbstractMon3trCard {
    public static final String ID = "mon3tr:TurbulentFlow";
    private static final CardStrings cardStrings;

    public TurbulentFlow(){
        super(ID, cardStrings.NAME,2, cardStrings.DESCRIPTION, CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new TurbulentFlowPower(p,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




