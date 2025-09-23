package Mon3tr.card.express;

import Mon3tr.action.ExpressAction;
import Mon3tr.card.AbstractExpressCard;
import Mon3tr.patch.ExpressPatch;
import Mon3tr.power.CrystalPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class SYSLupusErythematosus extends AbstractExpressCard {
    public static final String ID = "mon3tr:SYSLupusErythematosus";
    private static final CardStrings cardStrings;

    public SYSLupusErythematosus(){
        super(ID,cardStrings.NAME,"green/skill/nightmare",0,cardStrings.DESCRIPTION,CardType.STATUS,CardRarity.RARE,CardTarget.ENEMY);
        isEthereal = true;
        purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ExpressAction((AbstractExpressCard) this.makeCopy(),abstractMonster));
    }

    @Override
    public void expressWhenAttacked(int damage, AbstractCreature target, DamageInfo info) {
        if(info.type!= DamageInfo.DamageType.NORMAL)
            return;
        int random = AbstractDungeon.cardRandomRng.random(5);
        switch(random) {
            case 0:
                addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new StrengthPower(target,-1),-1));
                if(!target.hasPower(ArtifactPower.POWER_ID)){
                    addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new GainStrengthPower(target,1),1));
                }
                break;
            case 1:
                addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new WeakPower(target,1,false),1));
                break;
            case 2:
                addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new VulnerablePower(target,1,false),1));
                break;
            case 3:
                addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new CrystalPower(target,1),1));
                break;
            case 4:
                addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new ChokePower(target,1),1));
                break;
            case 5:
                addToBot(new ApplyPowerAction(target,AbstractDungeon.player,new PoisonPower(target,AbstractDungeon.player,1),1));
                break;
        }
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

