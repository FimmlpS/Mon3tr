package Mon3tr.card.skill;

import Mon3tr.action.DegreeChangeAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.StringHelper;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChangesByDegree extends AbstractMon3trCard {
    public static final String ID = "mon3tr:ChangesByDegree";
    private static final CardStrings cardStrings;

    public ChangesByDegree(){
        super(ID, cardStrings.NAME,0, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DegreeChangeAction(upgraded,true));
    }


    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0] + "+";
        loadCardImage(StringHelper.getCardStrategyIMGPath(ID,CardType.SKILL));
        this.initializeTitle();
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



