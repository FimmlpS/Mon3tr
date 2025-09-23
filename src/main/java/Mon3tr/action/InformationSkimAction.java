package Mon3tr.action;

import Mon3tr.card.status.ClockInsight;
import Mon3tr.card.status.SpaceMiracle;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.CollectPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class InformationSkimAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private boolean upgraded;
    private AbstractPlayer p;
    private int energyOnUse;

    public InformationSkimAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            ++effect;
        }

        if (effect > 0) {
            ArrayList<AbstractGameAction> actions = new ArrayList<>();
            AbstractCard c1 = new ClockInsight();
            AbstractCard c2 = new SpaceMiracle();
            for(int i =0;i<effect;i++){
                if(i%2 == 0){
                    actions.add(0,new MakeTempCardInHandAction(c1));
                }
                else{
                    actions.add(0,new MakeTempCardInHandAction(c2));
                }
            }
            for(AbstractGameAction action:actions){
                addToTop(action);
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

