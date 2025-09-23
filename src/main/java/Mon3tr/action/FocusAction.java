package Mon3tr.action;

import Mon3tr.helper.StringHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class FocusAction extends AbstractGameAction {

    public FocusAction(boolean special,int extra){
        this.setValues(AbstractDungeon.player,AbstractDungeon.player);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.amount = extra;
        this.special = special;
    }

    boolean special;

    @Override
    public void update() {

        if(this.duration==Settings.ACTION_DUR_MED){
            AbstractDungeon.handCardSelectScreen.open(StringHelper.actions.TEXT[2],10,true,true,false,false,true);
            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        }
        else{
            if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
                AbstractCard c;
                int count = this.amount;
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    c = card;
                    count += 1;
                }

                if(count>0){
                    if(special){
                        addToTop(new DrawCardAction(count));
                    }
                    else
                        addToTop(new IterationAllHandAction(count));
                }

                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    c = card;
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                this.isDone = true;
            }
            this.tickDuration();
        }
    }

}

