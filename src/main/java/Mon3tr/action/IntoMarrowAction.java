package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IntoMarrowAction extends AbstractGameAction {
    public IntoMarrowAction(AbstractMonster target,float percent){
        this.t = target;
        this.percent = percent;
    }

    @Override
    public void update() {
        if(t!=null&&!t.isDeadOrEscaped()){
            int lose = (int)(t.maxHealth * percent);
            if(lose>0){
                this.addToTop(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(lose, true), DamageInfo.DamageType.HP_LOSS, AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }

    float percent;
    AbstractMonster t;
}
