package Mon3tr.action;

import Mon3tr.card.status.StrategyMeltdown;
import Mon3tr.card.status.StrategyOverload;
import Mon3tr.helper.StringHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class MemoryAction extends AbstractGameAction {
    public MemoryAction(){
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    private boolean selected = false;

    @Override
    public void update() {
        if(duration==startDuration){
            if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0){
                this.isDone = true;
                return;
            }

            ArrayList<AbstractCard> cards = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn){
                if(!cards.contains(c))
                    cards.add(c);
            }
            Iterator<AbstractCard> var1 = cards.iterator();
            while (var1.hasNext()){
                AbstractCard c = var1.next();
                if(c instanceof StrategyMeltdown || c instanceof StrategyOverload)
                    var1.remove();
            }
            if(cards.isEmpty()){
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmp.group = cards;
            AbstractDungeon.gridSelectScreen.open(tmp,1,false, StringHelper.actions.EXTRA_TEXT[0]);
        }
        else if(!selected){
            selected = true;
            if(AbstractDungeon.gridSelectScreen.selectedCards.size()>0){
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeSameInstanceOf();
                c.dontTriggerOnUseCard = true;
                addToTop(new PlayCardAction(c,AbstractDungeon.getRandomMonster(),true));
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }

        this.tickDuration();
    }
}
