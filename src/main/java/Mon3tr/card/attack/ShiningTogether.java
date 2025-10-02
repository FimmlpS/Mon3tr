package Mon3tr.card.attack;

import Mon3tr.action.IncreaseDamageAction;
import Mon3tr.action.IncreaseIterationAction;
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

    public ShiningTogether(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        this.baseDamage = 6;
        this.damage = 6;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction());
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT));
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return card.costForTurn != 0;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        addToBot(new IncreaseDamageAction(this,magicNumber));
        if(card instanceof AbstractMon3trCard){
            addToBot(new IncreaseIterationAction((AbstractMon3trCard) card,magicNumber));
        }
        //addToBot(new IncreaseDamageAction(card,magicNumber));
        return magicNumber;
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


