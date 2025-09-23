package Mon3tr.action;

import Mon3tr.card.skill.StrategyOverpressureLink;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TryChangeStrategyAction extends AbstractGameAction {
    public TryChangeStrategyAction(StrategyOverpressureLink link){
        this.link = link;
    }

    @Override
    public void update() {
        if(!link.inBattleAndInHand()){
            this.isDone = true;
            return;
        }
        if(!link.specialType){
            addToTop(new DrawCardAction(link.magicNumber));
            link.changeStrategy(true,true);
            link.applyPowers();
        }
        else if(AbstractDungeon.player.hand.size()>=link.magicNumber){
            addToTop(new DiscardAction(AbstractDungeon.player,AbstractDungeon.player,link.magicNumber,false));
            link.changeStrategy(true,true);
            link.applyPowers();
        }
        this.isDone = true;
    }

    StrategyOverpressureLink link;
}
