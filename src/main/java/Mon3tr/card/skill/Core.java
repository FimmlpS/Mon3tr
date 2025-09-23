package Mon3tr.card.skill;

import Mon3tr.action.RandomIterationAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.CrystalPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Core extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Core";
    private static final CardStrings cardStrings;

    public Core(){
        super(ID, cardStrings.NAME, 0, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.BASIC,CardTarget.SELF);
        this.baseBlock = 3;
        this.block =3;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
    }

    @Override
    public int increaseIteration(int amt, boolean canTechnic) {
        amt = super.increaseIteration(amt,canTechnic);
        if(amt>0){
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new CrystalPower(AbstractDungeon.player,magicNumber*amt),magicNumber*amt));
        }
        return amt;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(1);
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


