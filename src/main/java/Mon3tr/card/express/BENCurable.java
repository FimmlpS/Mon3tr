package Mon3tr.card.express;

import Mon3tr.action.ExpressAction;
import Mon3tr.card.AbstractExpressCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BENCurable extends AbstractExpressCard {
    public static final String ID = "mon3tr:BENCurable";
    private static final CardStrings cardStrings;

    public BENCurable(){
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.STATUS,CardRarity.UNCOMMON,CardTarget.SELF);
        isEthereal = true;
        purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ExpressAction((AbstractExpressCard) this.makeCopy(),abstractPlayer));
    }

    @Override
    public int getExpressPrivilege(ArrayList<AbstractCard> transcriptCards) {
        int basic = getExtraBase(transcriptCards);
        for(AbstractCard c:transcriptCards){
            if(c.type == CardType.SKILL){
                basic++;
            }
        }
        int bBlock = 0;
        for(AbstractCard c: transcriptCards){
            if(c.baseBlock>0){
                bBlock += c.baseBlock;
            }
        }
        basic += bBlock/10;
        return returnSuppressedPrivilege(basic);
    }

    @Override
    public float gainBlockOrHp(float amount, AbstractCreature target, boolean isBlock) {
        return amount * 1.4F;
    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




