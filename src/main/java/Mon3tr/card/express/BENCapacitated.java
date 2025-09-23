package Mon3tr.card.express;

import Mon3tr.action.ExpressAction;
import Mon3tr.card.AbstractExpressCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class BENCapacitated extends AbstractExpressCard {
    public static final String ID = "mon3tr:BENCapacitated";
    private static final CardStrings cardStrings;

    public BENCapacitated(){
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
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if(strength==null||strength.amount<=0){
            return basic+1;
        }
        AbstractPower dexterity = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        if(dexterity==null||dexterity.amount<=0){
            return basic+1;
        }
        return returnSuppressedPrivilege(basic);
    }


    @Override
    public void expressAppliedToTarget(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,target,new DexterityPower(target,2),2));
    }

    @Override
    public void expressAbandonedFromTarget(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,target,new StrengthPower(target,2),2));
    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


