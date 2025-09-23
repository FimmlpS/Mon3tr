package Mon3tr.action;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class SVFXAction extends AbstractGameAction {
    private String key;
    private float pitchVar;
    float volume;

    public SVFXAction(String key) {
        this(key, 0.0F, 1.0F);
    }

    public SVFXAction(String key, float pitchVar) {
        this(key, pitchVar, 1.0F);
    }

    public SVFXAction(String key, float pitchVar, float volume) {
        this.key = key;
        this.pitchVar = MathUtils.random(-pitchVar,pitchVar);
        this.actionType = ActionType.WAIT;
        this.volume = volume;
    }

    public void update() {
        CardCrawlGame.sound.playAV(key,pitchVar,volume);

        this.isDone = true;
    }
}

