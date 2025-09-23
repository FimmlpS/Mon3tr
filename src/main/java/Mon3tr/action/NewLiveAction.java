package Mon3tr.action;

import Mon3tr.power.PersonalityPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public class NewLiveAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public NewLiveAction(int amt) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.amount = amt;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    private void newLiveCard(AbstractCard c){
//        AbstractCard copy = c.makeCopy();
//        for(int i =0;i<c.timesUpgraded;i++){
//            copy.upgrade();
//        }
//        if(c instanceof AbstractMon3trCard){
//            AbstractMon3trCard m = (AbstractMon3trCard) c;
//            int amt = this.amount * m.iterationCounter;
//            if(amt>0){
//                MeltdownPatch.increaseMeltdownCounter(amt);
//            }
//        }
        //addToTop(new MakeTempCardInDrawPileAction(copy,1,false,true,false));
        int energy = c.costForTurn;
        if(energy == -1)
            energy = EnergyPanel.totalCount;
        if(energy>0)
            addToTop(new ApplyPowerAction(p,p,new PersonalityPower(p,energy),energy));
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                newLiveCard(p.hand.getBottomCard());

                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
                this.tickDuration();
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for(Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToExhaustPile(c)) {
                    c = (AbstractCard)var1.next();
                    newLiveCard(c);
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
        TEXT = uiStrings.TEXT;
    }
}

