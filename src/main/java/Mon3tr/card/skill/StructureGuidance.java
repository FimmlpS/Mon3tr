package Mon3tr.card.skill;

import Mon3tr.action.MeshFollowUpAction;
import Mon3tr.action.StructureAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class StructureGuidance extends AbstractMon3trCard {
    public static final String ID = "mon3tr:StructureGuidance";
    private static final CardStrings cardStrings;
    int lastTriggeredCounter = 0;

    public StructureGuidance(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        this.iterationCounter = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new StructureAction(this.upgraded));
    }


    @Override
    public int increaseIteration(int amt, boolean canTechnic) {
        amt = super.increaseIteration(amt,canTechnic);
        while (this.iterationCounter>=lastTriggeredCounter+2){
            lastTriggeredCounter+=2;
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new MetallicizePower(AbstractDungeon.player,1),1));
        }
        return amt;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}






