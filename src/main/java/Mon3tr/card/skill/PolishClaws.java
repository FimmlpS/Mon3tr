package Mon3tr.card.skill;

import Mon3tr.action.RandomIterationAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class PolishClaws extends AbstractMon3trCard {
    public static final String ID = "mon3tr:PolishClaws";
    private static final CardStrings cardStrings;

    public PolishClaws(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber = 6;
        this.magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(iterationCounter>0){
            int amt = Math.min(iterationCounter,magicNumber);
            addToBot(new ApplyPowerAction(p,p,new PlatedArmorPower(p,amt),amt));
        }
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return card.type == CardType.ATTACK;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        return 1;
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




