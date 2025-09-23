package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IterationAccelerationAction extends AbstractGameAction {
    public IterationAccelerationAction(int amt){
        this(amt,true);
    }

    public IterationAccelerationAction(int amt,boolean technic){
        this.amount = amt;
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
        this.technic = technic;
    }

    boolean technic;

    @Override
    public void update() {
        if(startDuration == duration){
            for(AbstractCard c: AbstractDungeon.player.drawPile.group){
                if(c instanceof AbstractMon3trCard)
                    ((AbstractMon3trCard) c).increaseIteration(this.amount, technic);
            }
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(c instanceof AbstractMon3trCard)
                    ((AbstractMon3trCard) c).increaseIteration(this.amount, technic);
            }
            for(AbstractCard c: AbstractDungeon.player.discardPile.group){
                if(c instanceof AbstractMon3trCard)
                    ((AbstractMon3trCard) c).increaseIteration(this.amount, technic);
            }
        }
        this.tickDuration();
    }
}
