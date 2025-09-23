package Mon3tr.action;

import Mon3tr.patch.ProstsPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class SpawnProstsAction extends AbstractGameAction {
    public SpawnProstsAction(){
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if(duration==startDuration){
            ProstsPatch.spawnProsts();
        }

        tickDuration();
    }
}
