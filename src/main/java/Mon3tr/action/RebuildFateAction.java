package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class RebuildFateAction extends AbstractGameAction {
    public RebuildFateAction(int amt){
        this.amount = amt;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(p.drawPile.group);
        cards.addAll(p.discardPile.group);
        cards.removeIf(c -> !(c instanceof AbstractMon3trCard));
        cards.sort((c1, c2) -> ((AbstractMon3trCard)c2).iterationCounter > ((AbstractMon3trCard)c1).iterationCounter ? 1 : -1);
        boolean added = false;
        for(int i =0;i<cards.size() && i<this.amount;){
            boolean doAdd = false;
            if(p.hand.size()<10){
                if(p.drawPile.group.contains(cards.get(i))){
                    p.drawPile.moveToHand(cards.get(i));
                    doAdd = true;
                }
                else if(p.discardPile.group.contains(cards.get(i))){
                    p.discardPile.moveToHand(cards.get(i));
                    doAdd = true;
                }
            }
            else {
                break;
            }
            if(doAdd){
                i++;
                added = true;
            }
        }
        if(added)
            addToTop(new WaitAction(0.1F));
        this.isDone = true;
    }
}
