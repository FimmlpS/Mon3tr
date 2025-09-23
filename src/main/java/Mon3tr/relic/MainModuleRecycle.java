package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MainModuleRecycle extends CustomRelic {

    public static final String ID = "mon3tr:MainModuleRecycle";

    public MainModuleRecycle(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.RARE,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public int getPrice() {
        return 240;
    }

    @Override
    public void atTurnStart() {
        if (MeltdownPatch.isMeltdownTurn) {
            int amt = Math.min(AbstractDungeon.player.energy.energy, EnergyPanel.getCurrentEnergy());
            if (amt > 0) {
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new DrawCardAction(amt));
                addToBot(new GainEnergyAction(amt));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}






