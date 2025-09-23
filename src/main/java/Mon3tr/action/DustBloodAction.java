package Mon3tr.action;

import Mon3tr.card.attack.MemoryFragment;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DustBloodAction extends AbstractGameAction {
    public DustBloodAction(AbstractMonster target){
        this.t = target;
    }

    @Override
    public void update() {
        if(t!=null){
            float cur = Math.max(0,t.currentHealth);
            float max = t.maxHealth;
            if(max>0F){
                int amt = (int) (5F*(max-cur)/max);
                if(amt>0){
                    addToTop(new MakeTempCardInHandAction(new MemoryFragment(),amt));
                }
            }
        }
        this.isDone = true;
    }

    AbstractMonster t;
}
