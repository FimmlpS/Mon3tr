package Mon3tr.card.attack;

import Mon3tr.action.IncreaseDamageAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShiningTogether extends AbstractMon3trCard {
    public static final String ID = "mon3tr:ShiningTogether";
    private static final CardStrings cardStrings;
    boolean evolutionThisTurn = false;

    public ShiningTogether(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        this.baseDamage = 5;
        this.damage = 5;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction());
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return !evolutionThisTurn;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        this.evolutionThisTurn = true;
        addToBot(new IncreaseDamageAction(this,magicNumber));
        addToBot(new IncreaseDamageAction(card,magicNumber));
        return magicNumber;
    }

    @Override
    public void atTurnStart() {
        this.evolutionThisTurn = false;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


