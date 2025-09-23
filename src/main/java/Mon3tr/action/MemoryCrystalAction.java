package Mon3tr.action;

import Mon3tr.card.skill.MemoryCrystal;
import Mon3tr.card.status.StrategyMeltdown;
import Mon3tr.card.status.StrategyOverload;
import Mon3tr.helper.StringHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class MemoryCrystalAction extends AbstractGameAction {
    public MemoryCrystalAction(MemoryCrystal memoryCrystal){
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.memoryCrystal = memoryCrystal;
    }

    boolean selected = false;

    MemoryCrystal memoryCrystal;

    private void removeC(){
        if(memoryCrystal!=null){
            AbstractCard removed = null;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.cardID == this.memoryCrystal.cardID && c.uuid == this.memoryCrystal.uuid) {
                    removed = c;
                    break;
                }
            }
            if(removed!=null){
                AbstractDungeon.player.masterDeck.removeCard(removed);
            }
        }
    }

    @Override
    public void update() {
       if(duration==startDuration){
           ArrayList<AbstractCard> cardPlayed = new ArrayList<>();
           ArrayList<String> cardNames = new ArrayList<>();
           for(AbstractCard c:AbstractDungeon.actionManager.cardsPlayedThisCombat){
               if(c instanceof MemoryCrystal)
                   continue;
               if(c instanceof StrategyOverload || c instanceof StrategyMeltdown)
                   continue;
               if(!cardNames.contains(c.name)){
                   cardNames.add(c.name);
                   cardPlayed.add(c);
               }
           }
           if(cardPlayed.isEmpty()){
               //removeC();
               this.isDone = true;
               return;
           }
           CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
           tmp.group = cardPlayed;
           AbstractDungeon.gridSelectScreen.open(tmp,1,false, StringHelper.actions.EXTRA_TEXT[1]);
       }
       else if(!selected){
           selected = true;
           if(AbstractDungeon.gridSelectScreen.selectedCards.size()>0){
               AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
               AbstractCard copy = c.makeCopy();
               for(int i =0;i<c.timesUpgraded;i++){
                   copy.upgrade();
               }
               //removeC();
               AbstractDungeon.actionManager.addToTop(new AddCardToDeckAction(copy));
           }
           AbstractDungeon.gridSelectScreen.selectedCards.clear();
       }

       this.tickDuration();
    }
}
