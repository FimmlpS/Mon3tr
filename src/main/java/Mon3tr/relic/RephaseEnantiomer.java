package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class RephaseEnantiomer extends CustomRelic {

    public static final String ID = "mon3tr:RephaseEnantiomer";

    public RephaseEnantiomer(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.SOLID);
        this.counter = 0;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.type== DamageInfo.DamageType.NORMAL && damageAmount>0){
            this.counter += damageAmount;
            int blockAmt = 0;
            while (this.counter>=10){
                this.counter -= 10;
                blockAmt += MeltdownPatch.isMeltdownTurn?4:2;
            }
            if(blockAmt>0){
                this.flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
                addToBot(new GainBlockAction(AbstractDungeon.player,blockAmt,true));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}




