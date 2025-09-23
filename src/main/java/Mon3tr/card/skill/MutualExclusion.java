package Mon3tr.card.skill;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.CrystalPower;
import Mon3tr.power.DiscordCrystalPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MutualExclusion extends AbstractMon3trCard {
    public static final String ID = "mon3tr:MutualExclusion";
    private static final CardStrings cardStrings;

    public MutualExclusion(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.RARE,CardTarget.ENEMY);
        this.baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,p,new CrystalPower(m,magicNumber),magicNumber));
        addToBot(new ApplyPowerAction(m,p,new DiscordCrystalPower(m,magicNumber),magicNumber));
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




