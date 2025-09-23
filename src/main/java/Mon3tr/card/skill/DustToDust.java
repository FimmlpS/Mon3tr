package Mon3tr.card.skill;

import Mon3tr.action.DustToDustAction;
import Mon3tr.action.MeshFollowUpAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DustToDust extends AbstractMon3trCard {
    public static final String ID = "mon3tr:DustToDust";
    private static final CardStrings cardStrings;

    public DustToDust(){
        super(ID, cardStrings.NAME,-1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        this.baseMagicNumber = 5;
        this.magicNumber = 5;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DustToDustAction(p,magicNumber,freeToPlayOnce,energyOnUse));
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






