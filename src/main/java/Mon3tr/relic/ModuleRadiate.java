package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ModuleRadiate extends CustomRelic {

    public static final String ID = "mon3tr:ModuleRadiate";

    public ModuleRadiate(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.UNCOMMON,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public void onTrigger() {
        this.flash();
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






