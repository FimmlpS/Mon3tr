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
import java.util.HashSet;
import java.util.Set;

public class DegreeChangeAction extends AbstractGameAction {
    public DegreeChangeAction(boolean allDiff, boolean allSame){
        diff = allDiff;
        same = allSame;
        actionType = ActionType.DISCARD;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    private void transcriptAndReset(ArrayList<AbstractCard> list) {
        ArrayList<AbstractCard> doubleList = new ArrayList<>(list);
        ArrayList<AbstractCard> generated = Mon3trHelper.getExpressFromTranscription(doubleList);
        for (AbstractCard c : list) {
            AbstractDungeon.player.hand.moveToDiscardPile(c);
            c.triggerOnManualDiscard();
            GameActionManager.incrementDiscard(false);
        }
        AbstractDungeon.player.hand.refreshHandLayout();

        //diff
        boolean tmpDiff = true;
        Set<Integer> costSet = new HashSet<>();
        for(AbstractCard c:list){
            if(costSet.contains(c.costForTurn)){
                tmpDiff = false;
                break;
            }
            costSet.add(c.costForTurn);
        }

        //same
        boolean tmpSame = true;
        int sameCost = -99;
        for(AbstractCard c:list){
            if(sameCost==-99){
                sameCost = c.costForTurn;
            }
            else{
                if(c.costForTurn!=sameCost){
                    tmpSame = false;
                    break;
                }
            }
        }

        boolean continueTag1 = diff&&tmpDiff;
        boolean continueTag2 = same&&tmpSame;
        if(!continueTag1&&!continueTag2){
            return;
        }

        for (int i = generated.size() - 1; i >= 0; i--) {
            addToTop(new MakeTempCardInHandAction(generated.get(i)));
        }
    }

    @Override
    public void update() {
        if(startDuration==duration){
            AbstractPlayer p = AbstractDungeon.player;
            if(p.hand.size()==0){
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(StringHelper.actions.TEXT[4], 99, true, true);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            transcriptAndReset(new ArrayList<>(AbstractDungeon.handCardSelectScreen.selectedCards.group));
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }

    boolean diff;
    boolean same;
}
