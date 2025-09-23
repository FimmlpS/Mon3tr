package Mon3tr.card.express;

import Mon3tr.action.CystineAction;
import Mon3tr.action.ExpressAction;
import Mon3tr.action.ReperfusionAction;
import Mon3tr.card.AbstractExpressCard;
import Mon3tr.patch.ExpressPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class SYSReperfusionInjury extends AbstractExpressCard {
    public static final String ID = "mon3tr:SYSReperfusionInjury";
    private static final CardStrings cardStrings;

    public ArrayList<AbstractPower> powers = new ArrayList<>();

    public SYSReperfusionInjury(){
        super(ID,cardStrings.NAME,"green/skill/catalyst",0,cardStrings.DESCRIPTION,CardType.STATUS,CardRarity.RARE,CardTarget.ENEMY);
        isEthereal = true;
        purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ExpressAction((AbstractExpressCard) this.makeCopy(),abstractMonster));
    }

    @Override
    public void expressAppliedToTarget(AbstractCreature target) {
        addToBot(new ReperfusionAction(true,target,this));
    }

    @Override
    public void expressAbandonedFromTarget(AbstractCreature target) {
        addToBot(new ReperfusionAction(false,target,this));
    }

    @Override
    public int getExpressPrivilege(ArrayList<AbstractCard> transcriptCards) {
        int basic = -2;
        int extra = ExpressPatch.returnedCardsThisCombat.size()/3;
        basic += extra;
        //add 2025/6/9
        boolean random = AbstractDungeon.cardRandomRng.random(99)<10;
        if(random) {
            basic += 4;
        }
        return returnSuppressedPrivilege(basic);
    }

    @Override
    public int getSuppressionCount() {
        return 5;
    }

    @Override
    public void upgrade() {

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


