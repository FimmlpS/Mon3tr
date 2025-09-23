package Mon3tr.action;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InstantIncreaseMaxHpAction extends AbstractGameAction {
    private boolean showEffect;

    public InstantIncreaseMaxHpAction(AbstractMonster m, int amt, boolean showEffect) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.showEffect = showEffect;
        this.amount = amt;
        this.target = m;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.increaseMaxHp(MathUtils.round((float)this.amount), this.showEffect);
        }

        this.tickDuration();
    }
}

