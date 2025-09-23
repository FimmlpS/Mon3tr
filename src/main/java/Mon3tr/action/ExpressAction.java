package Mon3tr.action;

import Mon3tr.card.AbstractExpressCard;
import Mon3tr.effect.ShowCardAndAttachEffect;
import Mon3tr.patch.ExpressPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//表达
public class ExpressAction extends AbstractGameAction {
    public ExpressAction(AbstractExpressCard card, AbstractCreature target){
        this.target = target;
        this.card = card;
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if(startDuration== duration){
            //先只考虑一张表达牌的情况
            ExpressPatch.CreatureExpress express = ExpressPatch.AddExpressPatch.creatureExpress.get(this.target);
            if(express==null){
                this.isDone = true;
                return;
            }
            if(express.isFull()){
                if(express.maxExpresses==1){
                    express.replaceExpressCard(express.expressCards.get(0),card,target);
                }
                else{
                    //todo
                }
            }
            else{
                express.addExpressCard(card,target);
            }
            AbstractDungeon.effectsQueue.add(new ShowCardAndAttachEffect(card,target,true));
        }
        tickDuration();
    }

    AbstractExpressCard card;
}
