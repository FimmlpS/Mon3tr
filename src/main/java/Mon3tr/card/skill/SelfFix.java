package Mon3tr.card.skill;

import Mon3tr.action.AbsorbProstsAction;
import Mon3tr.action.DegreeChangeAction;
import Mon3tr.action.SpawnProstsAction;
import Mon3tr.card.AbstractMon3trCard;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SelfFix extends CustomCard {
    public static final String ID = "mon3tr:SelfFix";
    private static final CardStrings cardStrings;

    public SelfFix(){
        super(ID, cardStrings.NAME, new RegionName("red/skill/impervious"),2, cardStrings.DESCRIPTION, CardType.SKILL,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded){
            addToBot(new AbsorbProstsAction(false));
        }
        addToBot(new SpawnProstsAction());
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




