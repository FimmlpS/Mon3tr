package Mon3tr.card.attack;

import Mon3tr.action.DoubleCrystalAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReverseHalfLife extends AbstractMon3trCard {
    public static final String ID = "mon3tr:ReverseHalfLife";
    private static final CardStrings cardStrings;

    public ReverseHalfLife(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);
        this.baseDamage = 8;
        this.damage = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        addToBot(new DoubleCrystalAction(m));
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(4);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


