package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ModuleTarget extends CustomRelic {

    public static final String ID = "mon3tr:ModuleTarget";

    public ModuleTarget(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.COMMON,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public int getPrice() {
        return 120;
    }


    @Override
    public void atTurnStart() {
        if(MeltdownPatch.isMeltdownTurn){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            addToBot(new GainBlockAction(AbstractDungeon.player,8));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}






