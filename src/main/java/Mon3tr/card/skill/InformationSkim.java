package Mon3tr.card.skill;

import Mon3tr.action.InformationSkimAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.status.ClockInsight;
import Mon3tr.card.status.SpaceMiracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InformationSkim extends AbstractMon3trCard {
    public static final String ID = "mon3tr:InformationSkim";
    private static final CardStrings cardStrings;

    public InformationSkim(){
        super(ID, cardStrings.NAME,-1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        this.exhaust = true;
        showAnotherPreview = true;
        cardsToPreview = new ClockInsight();
        anotherPreview = new SpaceMiracle();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InformationSkimAction(p, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
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






