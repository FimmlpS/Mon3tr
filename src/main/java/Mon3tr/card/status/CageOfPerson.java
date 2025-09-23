package Mon3tr.card.status;

import Mon3tr.action.TakeTurnAction;
import Mon3tr.helper.StringHelper;
import Mon3tr.monster.Empgrd;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CageOfPerson extends CustomCard {
    public static final String ID = "mon3tr:CageOfPerson";
    private static final CardStrings cardStrings;

    public CageOfPerson() {
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.STATUS), 1, cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public boolean canPlay(AbstractCard card) {
        if(card.cardID.equals(ID)) {
            return true;
        }
        if(AbstractDungeon.player.hand.contains(this) && AbstractDungeon.player.hand.contains(card)) {
            int index = AbstractDungeon.player.hand.group.indexOf(this);
            int index2 = AbstractDungeon.player.hand.group.indexOf(card);
            return Math.abs(index - index2) > this.magicNumber;
        }
        return true;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



