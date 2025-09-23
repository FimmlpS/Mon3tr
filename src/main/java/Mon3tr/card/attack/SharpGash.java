package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.action.StarWebAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.OtherEnum;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

public class SharpGash extends AbstractMon3trCard {
    public static final String ID = "mon3tr:SharpGash";
    private static final CardStrings cardStrings;

    public SharpGash(){
        super(ID, cardStrings.NAME, 0, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        this.baseDamage = 3;
        this.damage = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(false));
        if (m != null) {
            this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(2);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseDamage;
        baseDamage += magicNumber * iterationCounter;
        super.calculateCardDamage(mo);
        baseDamage = tmp;
        isDamageModified = baseDamage!=damage;
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        baseDamage += magicNumber * iterationCounter;
        super.applyPowers();
        baseDamage = tmp;
        isDamageModified = baseDamage!=damage;
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return card.type == CardType.ATTACK && card.costForTurn == 0;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        return 1;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




