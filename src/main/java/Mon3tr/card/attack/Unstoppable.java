package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Unstoppable extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Unstoppable";
    private static final CardStrings cardStrings;

    public Unstoppable(){
        super(ID, cardStrings.NAME, 1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.BASIC,CardTarget.ENEMY);
        this.baseDamage = 2;
        this.damage =2;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction());
        addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmpBase = baseDamage;
        if(AbstractDungeon.player.hand.size()<10){
            baseDamage += magicNumber*(10-AbstractDungeon.player.hand.size());
        }
        super.calculateCardDamage(mo);
        baseDamage = tmpBase;
        isDamageModified = baseDamage!=damage;
    }

    @Override
    public void applyPowers() {
        int tmpBase = baseDamage;
        if(AbstractDungeon.player.hand.size()<10){
            baseDamage += magicNumber*(10-AbstractDungeon.player.hand.size());
        }
        super.applyPowers();
        baseDamage = tmpBase;
        isDamageModified = baseDamage!=damage;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(-2);
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
