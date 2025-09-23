package Mon3tr.card.attack;

import Mon3tr.action.ExistInNameOnlyAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.action.TakeStrengthAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InverseQuantization extends AbstractMon3trCard {
    public static final String ID = "mon3tr:InverseQuantization";
    private static final CardStrings cardStrings;

    public InverseQuantization(){
        super(ID, cardStrings.NAME, 2, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        this.baseDamage = 4;
        this.damage = 4;
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        for(int i =0;i<3;i++){
            addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
        }
        for(int i =0;i<this.magicNumber;i++){
            addToBot(new TakeStrengthAction(m,1));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


