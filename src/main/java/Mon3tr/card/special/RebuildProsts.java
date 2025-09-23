package Mon3tr.card.special;

import Mon3tr.card.skill.SelfFix;
import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RebuildProsts extends CustomCard {
    private static final String ID = "mon3tr:RebuildProsts";
    private static final CardStrings cardStrings;

    public RebuildProsts(){
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.SKILL),-2,cardStrings.DESCRIPTION,CardType.STATUS,CardColor.COLORLESS,CardRarity.UNCOMMON,CardTarget.NONE);
        this.cardsToPreview = new SelfFix();
        this.baseMagicNumber = this.magicNumber = 20;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



