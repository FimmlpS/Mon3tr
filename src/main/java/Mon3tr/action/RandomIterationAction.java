package Mon3tr.action;

import Mon3tr.card.AbstractMon3trCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RandomIterationAction extends AbstractGameAction {
    boolean onlySkill;

    public RandomIterationAction(int amt){
        this(amt,false);
    }

    public RandomIterationAction(int amt,boolean onlySkill){
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
        this.amount = amt;
        this.onlySkill = onlySkill;
    }

    @Override
    public void update() {
        if(startDuration==duration){
            ArrayList<AbstractMon3trCard> canIterate = new ArrayList<>();
            ArrayList<AbstractMon3trCard> increased = new ArrayList<>();
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(onlySkill && c.type != AbstractCard.CardType.SKILL)
                    continue;
                if(c instanceof AbstractMon3trCard) {
                    canIterate.add((AbstractMon3trCard) c);
                }
            }
            if(canIterate.size()==1){
                for(int i =0;i<this.amount;i++){
                    canIterate.get(0).increaseIteration(1);
                }
                canIterate.get(0).flash();
            }
            else if(canIterate.size()>1){
                for(int i =0;i<this.amount;i++){
                    AbstractMon3trCard c = canIterate.get(AbstractDungeon.cardRandomRng.random(canIterate.size()-1));
                    c.increaseIteration(1);
                    if(!increased.contains(c))
                        increased.add(c);
                }
                for(AbstractMon3trCard c: increased){
                    c.flash();
                }
            }
        }

        this.tickDuration();
    }
}
