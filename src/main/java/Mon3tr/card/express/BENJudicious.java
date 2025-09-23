package Mon3tr.card.express;

import Mon3tr.action.ExpressAction;
import Mon3tr.card.AbstractExpressCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BENJudicious extends AbstractExpressCard {
    public static final String ID = "mon3tr:BENJudicious";
    private static final CardStrings cardStrings;

    public BENJudicious(){
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
        int alive = 0;
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                alive++;
            }
        }
        basic += alive/2;
        return returnSuppressedPrivilege(basic);
    }

    @Override
    public void expressWhenAttacked(int damage, AbstractCreature target, DamageInfo info) {
        if(info.type!= DamageInfo.DamageType.NORMAL)
            return;
        int trueDamage = (int)(damage * 0.5F);
        if(trueDamage<1){
            if(damage>0){
                trueDamage = 1;
            }
        }
        if(trueDamage>0){
            addToTop(new GainBlockAction(target,trueDamage,true));
        }
    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



