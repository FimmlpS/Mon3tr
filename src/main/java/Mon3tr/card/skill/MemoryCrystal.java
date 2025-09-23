package Mon3tr.card.skill;

import Mon3tr.action.MemoryCrystalAction;
import Mon3tr.action.OneTimeAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MemoryCrystal extends AbstractMon3trCard {
    public static final String ID = "mon3tr:MemoryCrystal";
    private static final CardStrings cardStrings;

    public MemoryCrystal(){
        super(ID, cardStrings.NAME,0, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        this.purgeOnUse = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new OneTimeAction(this));
        addToBot(new MemoryCrystalAction(this));
    }

    @Override
    public void upgrade() {

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}






