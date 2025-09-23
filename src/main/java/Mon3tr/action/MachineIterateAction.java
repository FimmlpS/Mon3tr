package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MachineIterateAction extends AbstractGameAction {
    public MachineIterateAction(String cardID,int amount) {
        this.cardID = cardID;
        this.amount = amount;
    }

    @Override
    public void update() {
        if(cardID!=null){
            for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
                if(c.cardID.equals(cardID) && (c instanceof AbstractMon3trCard)){
                    ((AbstractMon3trCard) c).iterationCounter++;
                }
            }
        }

        this.isDone = true;
    }

    String cardID;
}
