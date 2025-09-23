package Mon3tr.card.attack;

import Mon3tr.action.ExhaustTechnicalAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.action.StarWebAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IntenseCrystalCracking extends AbstractMon3trCard {
    public static final String ID = "mon3tr:IntenseCrystalCracking";
    private static final CardStrings cardStrings;

    public IntenseCrystalCracking(){
        super(ID, cardStrings.NAME, 2, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        this.baseDamage = 5;
        this.damage = 5;
        this.baseMagicNumber = 3;
        this.magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!isInAutoplay)
            addToBot(new ExhaustTechnicalAction(technicalNeeded));
        addToBot(new Mon3trAttackAction(true));
        for(int i =0;i<magicNumber;i++){
            addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int trueBase = baseMagicNumber;
        baseMagicNumber += getTechnical()/3;
        super.calculateCardDamage(mo);
        magicNumber = baseMagicNumber;
        baseMagicNumber = trueBase;
        isMagicNumberModified = baseMagicNumber!=magicNumber;
        this.technicalNeeded = getTechnical();
    }

    @Override
    public void applyPowers() {
        int trueBase = baseMagicNumber;
        baseMagicNumber += getTechnical()/3;
        super.applyPowers();
        magicNumber = baseMagicNumber;
        baseMagicNumber = trueBase;
        isMagicNumberModified = baseMagicNumber!=magicNumber;
        this.technicalNeeded = getTechnical();
    }

    private int getTechnical(){
        if(AbstractDungeon.player==null)
            return 0;
        return Math.abs(AbstractDungeon.player.drawPile.size()-AbstractDungeon.player.discardPile.size());
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!MeltdownPatch.isMeltdownTurn() && !isInAutoplay && MeltdownPatch.meltDownCounter<getTechnical()){
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0]+getTechnical()+cardStrings.EXTENDED_DESCRIPTION[1];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




