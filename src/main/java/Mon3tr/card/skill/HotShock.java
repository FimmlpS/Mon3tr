package Mon3tr.card.skill;

import Mon3tr.action.NewLiveAction;
import Mon3tr.action.ObtainExpressCardAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HotShock extends AbstractMon3trCard {
    public static final String ID = "mon3tr:HotShock";
    private static final CardStrings cardStrings;

    public HotShock(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseBlock = 10;
        this.block = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new LoseEnergyAction(1));
        addToBot(new ObtainExpressCardAction(this));
        if(MeltdownPatch.isMeltdownTurn){
            addToBot(new GainBlockAction(p,block));
            addToBot(new LoseEnergyAction(1));
            addToBot(new ObtainExpressCardAction(this));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(MeltdownPatch.isMeltdownTurn){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBlock(4);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



