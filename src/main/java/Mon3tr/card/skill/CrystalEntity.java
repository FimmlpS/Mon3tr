package Mon3tr.card.skill;

import Mon3tr.action.MachineIterateAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.LibraryEnum;
import Mon3tr.patch.Mon3trTag;
import Mon3tr.patch.OtherEnum;
import Mon3tr.power.CrystalPower;
import basemod.abstracts.CustomSavable;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrystalEntity extends AbstractMon3trCard implements CustomSavable<Integer> {
    public static final String ID = "mon3tr:CrystalEntity";
    private static final CardStrings cardStrings;
    int lastTriggeredCounter = 0;
    boolean battleStartTrigger = false;

    public CrystalEntity(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //nothing happened
    }

    @Override
    public boolean shouldEvolution(AbstractCard card) {
        return card.type == CardType.POWER;
    }

    @Override
    public int getEvolutionCount(AbstractCard card) {
        return 1;
    }

    @Override
    public void atTurnStart() {
        if(!battleStartTrigger){
            battleStartTrigger = true;
            increaseIteration(0,false);
        }
    }

    @Override
    public int increaseIteration(int amt, boolean canTechnic) {
        amt = super.increaseIteration(amt,canTechnic);
        if(AbstractDungeon.player.masterDeck.contains(this))
            return amt;
        if(amt>0){
            addToBot(new MachineIterateAction(ID,1));
        }
        int singleDamage = 0;
        while (this.iterationCounter>=lastTriggeredCounter+3){
            lastTriggeredCounter+=3;
            singleDamage += magicNumber;
        }
        if(singleDamage>0)
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(singleDamage,true), DamageInfo.DamageType.THORNS, OtherEnum.MON3TR_ATTACK_EFFECT,true));
        return amt;
    }

    private void makeRandom(){
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        CardModifierManager.addModifier(c,new ExhaustMod());
        if(c.cost>=0){
            c.updateCost(-c.cost);
        }
        addToBot(new MakeTempCardInHandAction(c,1));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void onLoad(Integer integer) {
        this.iterationCounter = integer;
    }

    @Override
    public Integer onSave() {
        return this.iterationCounter;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}





