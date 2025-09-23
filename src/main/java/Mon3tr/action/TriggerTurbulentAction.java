package Mon3tr.action;

import Mon3tr.power.TurbulentFlowPower;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TriggerTurbulentAction extends AbstractGameAction {
    public TriggerTurbulentAction(TurbulentFlowPower power) {
        this.power = power;
    }

    @Override
    public void update() {
        if (!power.triggered && !AbstractDungeon.actionManager.turnHasEnded) {
            if (AbstractDungeon.player.hand.size() < 4) {
                power.flash();
                for (int i = 0; i < power.amount; i++) {
                    AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
                    CardModifierManager.addModifier(c, new ExhaustMod());
                    if(c.cost>0)
                        c.updateCost(-1);
                    addToTop(new MakeTempCardInHandAction(c));
                }
                power.triggered = true;
            }
        }
        this.isDone = true;
    }

    TurbulentFlowPower power;
}
