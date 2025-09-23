package Mon3tr.relic;

import Mon3tr.action.IterationAccelerationAction;
import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ModulePrepare extends CustomRelic {

    public static final String ID = "mon3tr:ModulePrepare";

    public ModulePrepare(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.UNCOMMON,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        this.flash();
        addToBot(new IterationAccelerationAction(1,false));
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






