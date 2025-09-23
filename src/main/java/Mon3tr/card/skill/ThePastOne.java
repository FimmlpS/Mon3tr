package Mon3tr.card.skill;

import Mon3tr.action.RandomIterationAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.attack.TheNowOne;
import Mon3tr.helper.StringHelper;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThePastOne extends AbstractMon3trCard {
    public static final String ID = "mon3tr:ThePastOne";
    private static final CardStrings cardStrings;

    public ThePastOne(){
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.SKILL),0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.SELF);
        this.isEthereal = true;
        this.baseBlock = 7;
        this.block = 7;
        this.baseMagicNumber = 7;
        this.magicNumber = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return card instanceof TheNowOne;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        return 2;
    }

    @Override
    public void triggerOnExhaust() {
        applyPowers();
        for(int i =0;i<this.iterationCounter;i++){
            addToBot(new GainBlockAction(AbstractDungeon.player,this.magicNumber,true));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



