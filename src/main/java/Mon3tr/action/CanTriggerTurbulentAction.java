package Mon3tr.action;

import Mon3tr.power.TurbulentFlowPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class CanTriggerTurbulentAction extends AbstractGameAction {
    public CanTriggerTurbulentAction(TurbulentFlowPower power){
        this.power = power;
    }

    @Override
    public void update() {
        this.power.triggered = false;
        this.isDone = true;
    }

    TurbulentFlowPower power;
}
