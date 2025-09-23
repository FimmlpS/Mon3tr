package Mon3tr.card.express;

import Mon3tr.action.ExpressAction;
import Mon3tr.card.AbstractExpressCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class MALCureless extends AbstractExpressCard {
    public static final String ID = "mon3tr:MALCureless";
    private static final CardStrings cardStrings;

    public MALCureless(){
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.STATUS,CardRarity.UNCOMMON,CardTarget.ENEMY);
        isEthereal = true;
        purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ExpressAction((AbstractExpressCard) this.makeCopy(),abstractMonster));
    }

    @Override
    public int getExpressPrivilege(ArrayList<AbstractCard> transcriptCards) {
        int basic = getExtraBase(transcriptCards);
        for(AbstractCard c:transcriptCards){
            if(c.type == CardType.ATTACK){
                basic++;
            }
        }
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                if(m.intent == AbstractMonster.Intent.DEFEND || m.intent == AbstractMonster.Intent.DEFEND_BUFF || m.intent == AbstractMonster.Intent.DEFEND_DEBUFF || m.intent == AbstractMonster.Intent.ATTACK_DEFEND)
                    return basic+1;
            }
        }
        return returnSuppressedPrivilege(basic);
    }

    @Override
    public float gainBlockOrHp(float amount, AbstractCreature target, boolean isBlock) {
        if(amount<1F)
            return 0;
        return Math.max(amount * 0.2F,1F);
    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




