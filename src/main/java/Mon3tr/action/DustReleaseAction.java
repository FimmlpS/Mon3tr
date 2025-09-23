package Mon3tr.action;

import Mon3tr.power.DustReleasePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DustReleaseAction extends AbstractGameAction {
    public DustReleaseAction(DustReleasePower power) {
        this.power = power;
    }

    @Override
    public void update() {
        if(power!=null) {
            power.onSpecificTrigger();
        }
        this.isDone = true;
    }

    DustReleasePower power;
}
