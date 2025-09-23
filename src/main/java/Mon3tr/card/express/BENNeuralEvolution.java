package Mon3tr.card.express;

import Mon3tr.action.ExpressAction;
import Mon3tr.card.AbstractExpressCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class BENNeuralEvolution extends AbstractExpressCard {
    public static final String ID = "mon3tr:BENNeuralEvolution";
    private static final CardStrings cardStrings;

    public BENNeuralEvolution(){
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.STATUS,CardRarity.COMMON,CardTarget.SELF);
        isEthereal = true;
        purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ExpressAction((AbstractExpressCard) this.makeCopy(),abstractPlayer));
    }

    @Override
    public int getExpressPrivilege(ArrayList<AbstractCard> transcriptCards) {
        int basic = 0;
        for(AbstractCard c:transcriptCards){
            if(c.type == CardType.SKILL){
                basic++;
            }
        }
        if(AbstractDungeon.player.hasPower(WeakPower.POWER_ID)){
            basic++;
        }
        if(AbstractDungeon.player.hasPower(VulnerablePower.POWER_ID)){
            basic++;
        }
        if(AbstractDungeon.player.hasPower(FrailPower.POWER_ID)){
            basic++;
        }
        return returnSuppressedPrivilege(basic);
    }

    @Override
    public void expressAppliedToTarget(AbstractCreature target) {
        addToBot(new ReducePowerAction(target,target,WeakPower.POWER_ID,1));
        addToBot(new ReducePowerAction(target,target,VulnerablePower.POWER_ID,1));
        addToBot(new ReducePowerAction(target,target,FrailPower.POWER_ID,1));
    }

    @Override
    public void expressAbandonedFromTarget(AbstractCreature target) {
        addToBot(new ReducePowerAction(target,target,WeakPower.POWER_ID,1));
        addToBot(new ReducePowerAction(target,target,VulnerablePower.POWER_ID,1));
        addToBot(new ReducePowerAction(target,target,FrailPower.POWER_ID,1));
    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


