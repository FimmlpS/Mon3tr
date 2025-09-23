package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.BufferPower;


public class DeviceHardness extends CustomRelic {

    public static final String ID = "mon3tr:DeviceHardness";
    boolean triggered = false;

    public DeviceHardness(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.UNCOMMON,LandingSound.SOLID);
        this.counter = -1;
    }

    @Override
    public void atTurnStart() {
        triggered = false;
    }

    @Override
    public void onTrigger() {
        if(!triggered){
            triggered = true;
            this.flash();
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 12));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}





