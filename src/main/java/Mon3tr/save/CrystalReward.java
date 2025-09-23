package Mon3tr.save;

import Mon3tr.helper.ImageHelper;
import Mon3tr.patch.CrystalPatch;
import Mon3tr.patch.OtherEnum;
import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class CrystalReward extends CustomReward {
    private static final UIStrings uiStrings;

    public int amount;

    public CrystalReward(int amount){
        super(ImageHelper.crystalItem.getTexture(),uiStrings.TEXT[0]+amount+uiStrings.TEXT[1], OtherEnum.MON3TR_CRYSTAL);
        this.amount = amount;
    }

    @Override
    public boolean claimReward() {
        CrystalPatch.obtainCrystal(this.amount);
        return true;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("mon3tr:CrystalReward");
    }
}
