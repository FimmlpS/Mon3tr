package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.skill.ThePastOne;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheNowOne extends AbstractMon3trCard {
    public static final String ID = "mon3tr:TheNowOne";
    private static final CardStrings cardStrings;

    public TheNowOne(){
        super(ID, cardStrings.NAME, StringHelper.getCardIMGPatch(ID,CardType.ATTACK),0, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.ENEMY);
        this.isEthereal = true;
        this.baseDamage = 8;
        this.damage = 8;
        this.baseMagicNumber = 8;
        this.magicNumber = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction());
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return card instanceof ThePastOne;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        return 2;
    }

    @Override
    public void triggerOnExhaust() {
        applyPowers();
        for(int i =0;i<this.iterationCounter;i++){
            addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




