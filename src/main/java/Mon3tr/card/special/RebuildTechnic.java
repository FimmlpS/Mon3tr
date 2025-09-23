package Mon3tr.card.special;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RebuildTechnic extends CustomCard {
    private static final String ID = "mon3tr:RebuildTechnic";
    private static final CardStrings cardStrings;

    public RebuildTechnic(){
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.SKILL),-2,cardStrings.DESCRIPTION,CardType.STATUS,CardColor.COLORLESS,CardRarity.COMMON,CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = 8;
        this.baseDamage = this.damage = MeltdownPatch.meltdownCounterStart;
        this.baseBlock = this.block = MeltdownPatch.meltDownCounterMax;
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



