package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ResetMoveAction extends AbstractGameAction {
    public ResetMoveAction(AbstractMonster m) {
        this.m = m;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        this.tickDuration();
        if(this.isDone){
            m.createIntent();
        }
    }

    AbstractMonster m;
}
