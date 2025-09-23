package Mon3tr.card.skill;

import Mon3tr.action.RandomDiscardToHandAction;
import Mon3tr.action.RandomIterationAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.power.PersonalityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.MeditateAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CanGetBack extends AbstractMon3trCard {
    public static final String ID = "mon3tr:CanGetBack";
    private static final CardStrings cardStrings;

    public CanGetBack(){
        super(ID, cardStrings.NAME,0, cardStrings.DESCRIPTION, CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(dontTriggerOnUseCard){
            addToBot(new ApplyPowerAction(p,p,new PersonalityPower(p,magicNumber),magicNumber));
        }
        else {
            addToBot(new ApplyPowerAction(p,p,new PersonalityPower(p,-magicNumber),-magicNumber));
        }
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



