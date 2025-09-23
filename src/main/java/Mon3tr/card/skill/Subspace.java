package Mon3tr.card.skill;

import Mon3tr.action.DrawToHandAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.power.CrystalPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Subspace extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Subspace";
    private static final CardStrings cardStrings;

    public Subspace(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);
        this.baseMagicNumber = 5;
        this.magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,p,new CrystalPower(m,magicNumber),magicNumber));
    }

    @Override
    public void atTurnStart() {
        if(MeltdownPatch.isMeltdownTurn){
            addToBot(new DrawToHandAction(this));
            addToBot(new DiscardToHandAction(this));
        }
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}







