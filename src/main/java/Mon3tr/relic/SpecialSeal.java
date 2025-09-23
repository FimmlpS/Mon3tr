package Mon3tr.relic;

import Mon3tr.action.IncreaseTechnicalAction;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SpecialSeal extends CustomRelic {

    public static final String ID = "mon3tr:SpecialSeal";

    public SpecialSeal(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.SOLID);
    }

    @Override
    public void atTurnStart() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new IncreaseTechnicalAction(10));
    }

    @Override
    public void onEquip() {
        MeltdownPatch.meltDownCounterMax = 100;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SpecialSeal();
    }
}




