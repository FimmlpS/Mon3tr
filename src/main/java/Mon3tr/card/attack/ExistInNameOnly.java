package Mon3tr.card.attack;

import Mon3tr.action.ExistInNameOnlyAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ExistInNameOnly extends AbstractMon3trCard {
    public static final String ID = "mon3tr:ExistInNameOnly";
    private static final CardStrings cardStrings;

    public ExistInNameOnly(){
        super(ID, cardStrings.NAME, 2, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        this.baseDamage = 10;
        this.damage =10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn), OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT));
        addToBot(new ExistInNameOnlyAction(-1,m,new DamageInfo(p,this.damage,this.damageTypeForTurn), OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}

