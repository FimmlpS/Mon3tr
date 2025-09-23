package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.power.CrystalPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ModuleRecover extends CustomRelic {

    public static final String ID = "mon3tr:ModuleRecover";

    public ModuleRecover(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.COMMON,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public int getPrice() {
        return 120;
    }


    @Override
    public void wasHPLost(int damageAmount) {
        if(AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
            return;
        }
        if(damageAmount>0){
            this.flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new CrystalPower(AbstractDungeon.player,2),2,true));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}







