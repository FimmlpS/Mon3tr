package Mon3tr.action;

import Mon3tr.power.StartRegeneratePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StartRegenAction extends AbstractGameAction {
    public StartRegenAction(AbstractMonster target, float blv) {
        this.m = target;
        this.blv = blv;
    }

    @Override
    public void update() {
        if(!m.isDeadOrEscaped()) {
            addToTop(new ApplyPowerAction(m,m,new StartRegeneratePower(m,(int)((float)m.maxHealth*blv)),(int)((float)m.maxHealth*blv)));
        }
        this.isDone = true;
    }

    AbstractMonster m;
    float blv;
}
