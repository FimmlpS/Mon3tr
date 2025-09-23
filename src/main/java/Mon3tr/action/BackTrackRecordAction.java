package Mon3tr.action;

import Mon3tr.power.BackTrackPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class BackTrackRecordAction extends AbstractGameAction {
    public BackTrackRecordAction(BackTrackPower p, AbstractCard card) {
        this.power = p;
        this.card = card;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> record = power.record;
        if(!record.contains(card)){
            record.add(card);
        }
        if(record.size() == 4){
            int maxCost = -3;
            Iterator<AbstractCard> it = record.iterator();
            while(it.hasNext()){
                AbstractCard c = it.next();
                if(!p.drawPile.contains(c) && !p.discardPile.contains(c)){
                    it.remove();
                }
            }
            for(AbstractCard c : record){
                if(c.cost > maxCost){
                    maxCost = c.cost;
                }
            }
            ArrayList<AbstractCard> returnCards = new ArrayList<>();
            for(AbstractCard c : record){
                if(c.cost == maxCost){
                    returnCards.add(c);
                }
            }
            //仅首张
            for(AbstractCard c : returnCards){
                addToTop(new DiscardToHandAction(c));
                addToTop(new DrawToHandAction(c));
                break;
            }
            record.clear();
        }
        power.amount = record.size();
        power.updateDescription();
        this.isDone = true;
    }

    BackTrackPower power;
    AbstractCard card;
}
