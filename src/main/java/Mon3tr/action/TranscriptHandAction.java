package Mon3tr.action;

import Mon3tr.helper.Mon3trHelper;
import Mon3tr.helper.StringHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

//丢弃or留存？
public class TranscriptHandAction extends AbstractGameAction {

    public TranscriptHandAction(int amount, boolean anyAmount, int toward){
        this.amount = amount;
        this.anyAmount = anyAmount;
        this.toward = toward;

        if(toward == 1){
            actionType = ActionType.DISCARD;
        }
        else if(toward ==2){
            actionType = ActionType.EXHAUST;
        }
        else{
            actionType = ActionType.CARD_MANIPULATION;
        }

        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    boolean anyAmount;
    int toward;
    boolean triggered = false;

    private void transcriptAndReset(ArrayList<AbstractCard> list){
        triggered = true;
        ArrayList<AbstractCard> generated = Mon3trHelper.getExpressFromTranscription(list);
        if(toward==1){
            for(AbstractCard c:list){
                AbstractDungeon.player.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
        }
        else if(toward==2){
            for(AbstractCard c:list){
                AbstractDungeon.player.hand.moveToExhaustPile(c);
            }
        }
        else{
            for(AbstractCard c:list){
                if(!AbstractDungeon.player.hand.contains(c)){
                    AbstractDungeon.player.hand.addToTop(c);
                }
            }
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        for(int i =generated.size()-1;i>=0;i--){
            addToTop(new MakeTempCardInHandAction(generated.get(i)));
        }
    }

    @Override
    public void update() {
        if(duration==startDuration){
            AbstractPlayer p = AbstractDungeon.player;
            if(p.hand.size() <= this.amount){
                this.amount = p.hand.size();
                if(this.amount==0){
                    this.isDone = true;
                    return;
                }
                else if(!anyAmount){
                    transcriptAndReset(new ArrayList<>(AbstractDungeon.player.hand.group));
                    this.tickDuration();
                    return;
                }
            }
            AbstractDungeon.handCardSelectScreen.open(StringHelper.actions.TEXT[3+toward], this.amount, anyAmount, anyAmount);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if(!triggered){
            if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
                transcriptAndReset(new ArrayList<>(AbstractDungeon.handCardSelectScreen.selectedCards.group));
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
        }


        tickDuration();
    }
}
