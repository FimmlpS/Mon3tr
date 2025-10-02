package Mon3tr.card.skill;

import Mon3tr.action.NewLiveAction;
import Mon3tr.action.ObtainExpressCardAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.OtherEnum;
import Mon3tr.power.PersonalityPower;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HotShock extends AbstractMon3trCard {
    public static final String ID = "mon3tr:HotShock";
    private static final CardStrings cardStrings;

    public HotShock(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseBlock = 10;
        this.block = 10;
    }

    private boolean shouldDouble(){
        AbstractPower per = AbstractDungeon.player.getPower(PersonalityPower.POWER_ID);
        return per!=null && per.amount > 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new LoseEnergyAction(1));
        addToBot(new ObtainExpressCardAction(this));
        if(shouldDouble()){
            addToBot(new GainBlockAction(p,block));
            addToBot(new LoseEnergyAction(1));
            addToBot(new ObtainExpressCardAction(this));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(shouldDouble()){
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



