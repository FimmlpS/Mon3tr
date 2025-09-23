package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.OtherEnum;
import Mon3tr.power.FocusPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class NoLongerSung extends AbstractMon3trCard {
    public static final String ID = "mon3tr:NoLongerSung";
    private static final CardStrings cardStrings;

    public NoLongerSung(){
        super(ID, cardStrings.NAME, 2, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);
        this.baseDamage = 44;
        this.damage = 44;
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        if (m != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }

        this.addToBot(new WaitAction(0.8F));
        addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn), OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseDamage;
        if(!upgraded)
            baseDamage -= magicNumber * iterationCounter;
        else
            baseDamage += magicNumber * iterationCounter;
        if(baseDamage<0)
            baseDamage = 0;
        super.calculateCardDamage(mo);
        baseDamage = tmp;
        isDamageModified = baseDamage!=damage;
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        if(!upgraded)
            baseDamage -= magicNumber * iterationCounter;
        else
            baseDamage += magicNumber * iterationCounter;
        if(baseDamage<0)
            baseDamage = 0;
        super.applyPowers();
        baseDamage = tmp;
        isDamageModified = baseDamage!=damage;
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return true;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        return 1;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.name = cardStrings.EXTENDED_DESCRIPTION[0];
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            loadCardImage(StringHelper.getCardStrategyIMGPath(ID,CardType.ATTACK));
            upgradeDamage(-40);
            upgradeMagicNumber(-1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


