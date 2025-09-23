package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ModuleOverclock extends CustomRelic {

    public static final String ID = "mon3tr:ModuleOverclock";

    public ModuleOverclock(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.UNCOMMON,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public void atTurnStartPostDraw() {
        if(MeltdownPatch.isMeltdownTurn){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            addToBot(new DrawCardAction(2));
        }
    }

    @Override
    public int getPrice() {
        return 200;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}




