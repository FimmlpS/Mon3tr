package Mon3tr.card.skill;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Defend";
    private static final CardStrings cardStrings;

    public Defend(){
        super(ID, cardStrings.NAME, 1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.BASIC,CardTarget.SELF);
        this.tags.add(CardTags.STARTER_DEFEND);
        this.baseBlock = 5;
        this.block =5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}

