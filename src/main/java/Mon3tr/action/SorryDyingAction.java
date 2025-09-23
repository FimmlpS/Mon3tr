package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SorryDyingAction extends AbstractGameAction {
    public SorryDyingAction(AbstractMonster m){
        this.m = m;
    }

    @Override
    public void update() {
        if(m!=null && m.isDeadOrEscaped()){
            if(EnergyPanel.getCurrentEnergy() < AbstractDungeon.player.energy.energy)
            EnergyPanel.setEnergy(AbstractDungeon.player.energy.energy);
        }
        this.isDone = true;
    }

    AbstractMonster m ;
}
