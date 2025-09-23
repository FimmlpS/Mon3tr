package Mon3tr.action;

import Mon3tr.helper.StringHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class DrawDiscardAttackHandAction extends AbstractGameAction {
    public DrawDiscardAttackHandAction(int showAmt, int takeAmt){
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.sAmt = showAmt;
        this.tAmt = takeAmt;
    }

    int sAmt;
    int tAmt;

    private boolean selected = false;

    private void toHand(AbstractCard card){
        if(AbstractDungeon.player.hand.size()>=10)
            return;
        AbstractDungeon.player.hand.addToHand(card);
        card.unhover();
        card.setAngle(0.0F, true);
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        if (AbstractDungeon.player.drawPile.contains(card)) {
            AbstractDungeon.player.drawPile.removeCard(card);
        }
        else{
            AbstractDungeon.player.discardPile.removeCard(card);
        }
        AbstractDungeon.player.onCardDrawOrDiscard();
    }

    @Override
    public void update() {
        if(duration==startDuration){
            ArrayList<AbstractCard> tmp = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.player.drawPile.group){
                if(c.type == AbstractCard.CardType.ATTACK){
                    tmp.add(c);
                }
            }
            for(AbstractCard c : AbstractDungeon.player.discardPile.group){
                if(c.type == AbstractCard.CardType.ATTACK){
                    tmp.add(c);
                }
            }
            if(tmp.size()<tAmt){
                for(AbstractCard c : tmp){
                    toHand(c);
                }
                this.isDone = true;
                return;
            }
            ArrayList<AbstractCard> showTmp = new ArrayList<>();
            Collections.shuffle(tmp,AbstractDungeon.cardRandomRng.random);
            sAmt = Math.min(sAmt,tmp.size());
            for(int i =0;i<sAmt;i++){
                showTmp.add(tmp.get(i));
            }
            tAmt = Math.min(tAmt,sAmt);
            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmpGroup.group = showTmp;
            AbstractDungeon.gridSelectScreen.open(tmpGroup,tAmt,true, StringHelper.actions.TEXT[8]+tAmt+StringHelper.actions.TEXT[9]);
        }
        else if (!selected){
            selected = true;
            if(AbstractDungeon.gridSelectScreen.selectedCards.size()>0){
                for(AbstractCard c:AbstractDungeon.gridSelectScreen.selectedCards){
                    toHand(c);
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }
}
