package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DelayActionAction extends AbstractGameAction {
    public DelayActionAction(AbstractGameAction actionToDelay) {
        this.actionToDelay = actionToDelay;
    }

    @Override
    public void update() {
        addToBot(actionToDelay);
        this.isDone = true;
    }

    private AbstractGameAction actionToDelay;
}
