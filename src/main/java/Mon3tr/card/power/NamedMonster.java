package Mon3tr.card.power;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.MonsterPower;
import Mon3tr.power.PersonalityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NamedMonster extends AbstractMon3trCard {
    public static final String ID = "mon3tr:NamedMonster";
    private static final CardStrings cardStrings;

    public NamedMonster(){
        super(ID, cardStrings.NAME,3, cardStrings.DESCRIPTION, CardType.POWER,CardRarity.RARE,CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new PersonalityPower(p,magicNumber),magicNumber));
        addToBot(new ApplyPowerAction(p,p,new MonsterPower(p)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


