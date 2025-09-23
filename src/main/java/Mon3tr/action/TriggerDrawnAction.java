package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TriggerDrawnAction extends AbstractGameAction {
    public TriggerDrawnAction(AbstractCard c){
        this.c = c;
    }

    @Override
    public void update() {
        if(c!=null){
            if(AbstractDungeon.player.hand.contains(c)){
                c.triggerWhenDrawn();
            }
        }
        this.isDone = true;
    }

    AbstractCard c;
}
