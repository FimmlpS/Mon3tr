package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.skill.Calcite;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class IncreaseCalciteAction extends AbstractGameAction {
    public IncreaseCalciteAction(Calcite c){
        this.c = c;
    }

    @Override
    public void update() {
        if(c!=null){
            c.technicalNeeded *= 2;
            c.baseMagicNumber *= 2;
            c.magicNumber = c.baseMagicNumber;
            c.baseBlock *= 2;
            c.applyPowers();
        }
        this.isDone = true;
    }

    Calcite c;
}
