package Mon3tr.card.skill;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.PersonalityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class PrepareMedicine extends AbstractMon3trCard {
    public static final String ID = "mon3tr:PrepareMedicine";
    private static final CardStrings cardStrings;

    public PrepareMedicine(){
        super(ID, cardStrings.NAME,0, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        this.baseBlock = 3;
        this.block = 3;
        this.baseMagicNumber = 4;
        this.magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new PersonalityPower(p,1),1));
        addToBot(new ApplyPowerAction(p,p,new NextTurnBlockPower(p,magicNumber),magicNumber));
    }

    @Override
    public void applyPowers() {
        this.baseBlock += 1 + this.timesUpgraded * 3;
        this.baseMagicNumber = this.baseBlock;
        super.applyPowers();
        this.magicNumber = this.block;
        this.isMagicNumberModified = this.isBlockModified;
        this.baseBlock -= 1 + this.timesUpgraded * 3;
        super.applyPowers();
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(1);
            this.baseMagicNumber = this.baseBlock + 1 + this.timesUpgraded * 3;
            this.upgradedMagicNumber = this.upgradedBlock;
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



