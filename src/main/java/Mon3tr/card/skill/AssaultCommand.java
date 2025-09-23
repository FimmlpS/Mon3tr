package Mon3tr.card.skill;

import Mon3tr.action.IncreaseTechnicalAction;
import Mon3tr.action.NewLiveAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.CrystalOverloadPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AssaultCommand extends AbstractMon3trCard {
    public static final String ID = "mon3tr:AssaultCommand";
    private static final CardStrings cardStrings;

    public AssaultCommand(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        this.baseMagicNumber = 40;
        this.magicNumber = 40;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseTechnicalAction(magicNumber,true));
        addToBot(new ApplyPowerAction(p,p,new CrystalOverloadPower(p,20)));
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




