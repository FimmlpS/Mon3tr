package Mon3tr.card.express;

import Mon3tr.action.ExpressAction;
import Mon3tr.card.AbstractExpressCard;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class MALIncapacitated extends AbstractExpressCard {
    public static final String ID = "mon3tr:MALIncapacitated";
    private static final CardStrings cardStrings;

    public MALIncapacitated(){
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
                int mulAmt = ReflectionHacks.getPrivate(m,AbstractMonster.class,"intentMultiAmt");
                if(mulAmt>1){
                    return basic+1;
                }
            }
        }
        return returnSuppressedPrivilege(basic);
    }

    @Override
    public void expressAppliedToTarget(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,target,new StrengthPower(target,-2),-2));
    }

    @Override
    public void expressAbandonedFromTarget(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,target,new StrengthPower(target,-2),-2));
    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}

