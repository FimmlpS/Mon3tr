package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MeshFollowUpAction extends AbstractGameAction {
    public MeshFollowUpAction(int iterationWhenTriggered) {
        this.duration = 0.001F;
        this.amount = iterationWhenTriggered;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone) {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if(c instanceof AbstractMon3trCard){
                    int iteration = ((AbstractMon3trCard) c).iterationCounter;
                    if (iteration<this.amount) {
                        AbstractDungeon.player.hand.moveToDiscardPile(c);
                        c.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                    }
                }
            }
        }

    }
}

