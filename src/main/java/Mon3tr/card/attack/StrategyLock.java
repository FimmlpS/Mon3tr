package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import Mon3tr.power.LockPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StrategyLock extends AbstractMon3trCard {
    public static final String ID = "mon3tr:StrategyLock";
    private static final CardStrings cardStrings;

    public StrategyLock(){
        super(ID, cardStrings.NAME ,0, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        this.baseDamage = 4;
        this.damage =4;
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(false));
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
        addToBot(new ApplyPowerAction(m,p,new LockPower(m,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}

