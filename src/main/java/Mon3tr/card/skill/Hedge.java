package Mon3tr.card.skill;

import Mon3tr.action.LoseAllStrengthAction;
import Mon3tr.action.ShareBlockAction;
import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.DoubleYourBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Hedge extends AbstractMon3trCard {
    public static final String ID = "mon3tr:Hedge";
    private static final CardStrings cardStrings;

    public Hedge(){
        super(ID, cardStrings.NAME, "red/skill/entrench",1, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 24;
        this.magicNumber = 24;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DoubleYourBlockAction(p));
    }

    @Override
    public void triggerWhenDrawn() {
        this.flash();
        addToBot(new ShareBlockAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(8);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}




