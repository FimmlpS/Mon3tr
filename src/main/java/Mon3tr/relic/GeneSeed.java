package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.CrystalPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class GeneSeed extends CustomRelic {

    public static final String ID = "mon3tr:GeneSeed";

    public GeneSeed(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.SPECIAL,LandingSound.SOLID);
        this.counter = -1;
        CrystalPatch.RelicField.unRefractor.set(this,true);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}





