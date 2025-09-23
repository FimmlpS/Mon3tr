package Mon3tr.card.express;

import Mon3tr.action.ExpressAction;
import Mon3tr.card.AbstractExpressCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class MALNeuralRegression extends AbstractExpressCard {
    public static final String ID = "mon3tr:MALNeuralRegression";
    private static final CardStrings cardStrings;

    public MALNeuralRegression(){
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.STATUS,CardRarity.COMMON,CardTarget.ENEMY);
        isEthereal = true;
        purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ExpressAction((AbstractExpressCard) this.makeCopy(),abstractMonster));
    }

    @Override
    public int getExpressPrivilege(ArrayList<AbstractCard> transcriptCards) {
        int basic = 0;
        for(AbstractCard c:transcriptCards){
            if(c.type == CardType.ATTACK){
                basic++;
            }
        }
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                if(m.intent == AbstractMonster.Intent.DEBUFF || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m.intent == AbstractMonster.Intent.DEFEND_DEBUFF || m.intent == AbstractMonster.Intent.STRONG_DEBUFF)
                    return basic+1;
            }
        }
        return returnSuppressedPrivilege(basic);
    }

    @Override
    public void expressAppliedToTarget(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new WeakPower(target,1,false),1));
        addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new VulnerablePower(target,1,false),1));
    }

    @Override
    public void expressAbandonedFromTarget(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new WeakPower(target,1,false),1));
        addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new VulnerablePower(target,1,false),1));
    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


