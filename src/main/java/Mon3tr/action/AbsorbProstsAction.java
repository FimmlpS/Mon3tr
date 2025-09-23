package Mon3tr.action;

import Mon3tr.patch.ProstsPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class AbsorbProstsAction extends AbstractGameAction {
    public AbsorbProstsAction(boolean heal){
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.heal = heal;
    }

    boolean heal;

    @Override
    public void update() {
        if(duration==startDuration){
            if(ProstsPatch.prosts==null || ProstsPatch.prosts.isDeadOrEscaped() || ProstsPatch.prosts.currentHealth<=0){
                this.isDone = true;
                return;
            }
            ProstsPatch.absorbProsts(heal);
        }

        tickDuration();
        if(this.isDone){
            ProstsPatch.prosts = null;
        }
    }
}

