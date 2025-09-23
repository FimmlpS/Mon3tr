package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ModuleReceptor extends CustomRelic {

    public static final String ID = "mon3tr:ModuleReceptor";

    public ModuleReceptor(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.UNCOMMON,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public void atTurnStart() {
        if(MeltdownPatch.isMeltdownTurn){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            MeltdownPatch.energyToStrength(3);
            MeltdownPatch.drawToDexterity(3);
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





