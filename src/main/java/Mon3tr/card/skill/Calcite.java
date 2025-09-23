package Mon3tr.card.skill;

import Mon3tr.action.ExhaustTechnicalAction;
import Mon3tr.action.IncreaseCalciteAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Calcite extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Calcite";
    private static final CardStrings cardStrings;

    public Calcite(){
        super(ID, cardStrings.NAME,0, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        this.baseBlock = 8;
        this.block =8;
        this.baseMagicNumber = 4;
        this.magicNumber = 4;
        this.technicalNeeded = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!isInAutoplay)
            addToBot(new ExhaustTechnicalAction(magicNumber));
        addToBot(new GainBlockAction(p,block));
        addToBot(new IncreaseCalciteAction(this));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(2);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!MeltdownPatch.isMeltdownTurn() && !isInAutoplay && MeltdownPatch.meltDownCounter<magicNumber){
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0]+magicNumber+cardStrings.EXTENDED_DESCRIPTION[1];
            return false;
        }
        return super.canUse(p, m);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



