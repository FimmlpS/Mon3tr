package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IterationAllHandAction extends AbstractGameAction {
    public IterationAllHandAction(int amt){
        this(amt,null);
    }

    public IterationAllHandAction(int amt, String exceptID){
        this.exceptID = exceptID;
        this.amount = amt;
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
    }

    String exceptID;

    @Override
    public void update() {
        if(startDuration == duration){
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(c.cardID.equals(exceptID))
                    continue;
                if(c instanceof AbstractMon3trCard){
                    ((AbstractMon3trCard) c).increaseIteration(this.amount);
                    c.flash();
                }
            }
        }
        this.tickDuration();
    }
}

