package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.action.ShatteredEchoAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.status.StrategyMeltdown;
import Mon3tr.card.status.StrategyOverload;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShatteredEcho extends AbstractMon3trCard {
    public static final String ID = "mon3tr:ShatteredEcho";
    private static final CardStrings cardStrings;

    public ShatteredEcho(){
        super(ID, cardStrings.NAME, 1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);
        this.baseDamage = 6;
        this.damage =6;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        this.iterationCounter = 1;
        this.cardsToPreview = new MemoryFragment();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(false));
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
        addToBot(new ShatteredEchoAction(m,this.iterationCounter,upgraded));
        this.iterationCounter = 0;
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return card.type == CardType.STATUS;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        return magicNumber;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            cardsToPreview.upgrade();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




