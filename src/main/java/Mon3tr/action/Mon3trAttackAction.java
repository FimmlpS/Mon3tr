package Mon3tr.action;

import Mon3tr.character.Mon3tr;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class Mon3trAttackAction extends AbstractGameAction {
    public Mon3trAttackAction(){
        this(false);
    }

    public Mon3trAttackAction(boolean skilled){
        this.skilled = skilled;
        if(skilled&&!Settings.FAST_MODE){
            startDuration = duration = Settings.ACTION_DUR_XFAST;
        }
    }

    boolean skilled;

    @Override
    public void update() {
        if(!skilled||Settings.FAST_MODE){
            Mon3tr.onAttack(false);
            this.isDone = true;
        }
        else if (startDuration==duration){
            Mon3tr.onAttack(true);
        }
        this.tickDuration();
    }
}
