package Mon3tr.action;

import Mon3tr.character.Prosts;
import Mon3tr.monster.Empgrd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TakeTurnAction extends AbstractGameAction {
    public TakeTurnAction(AbstractMonster m){
        this.m = m;
    }

    @Override
    public void update() {
        if(m!=null){
            if(m instanceof Empgrd){
                ((Empgrd) m).takeTurn(true);
            }
            else
                m.takeTurn();
        }
        this.isDone = true;
    }

    AbstractMonster m;
}

