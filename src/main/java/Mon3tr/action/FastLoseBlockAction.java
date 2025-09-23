package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class FastLoseBlockAction extends AbstractGameAction {
    public FastLoseBlockAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.duration = 0.1F;
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        if (this.duration == 0.1F) {
            if (this.target.currentBlock == 0) {
                this.isDone = true;
                return;
            }

            this.target.loseBlock(this.amount);
        }

        this.tickDuration();
    }
}

