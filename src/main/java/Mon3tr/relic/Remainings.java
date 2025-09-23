package Mon3tr.relic;

import Mon3tr.action.DelayActionAction;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class Remainings extends CustomRelic {

    public static final String ID = "mon3tr:Remainings";

    public Remainings(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.SOLID);
    }

    boolean triggered = false;

    @Override
    public void atTurnStart() {
        triggered = false;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(!triggered){
            triggered = true;
            this.flash();
            if(MeltdownPatch.isMeltdownTurn){
                addToBot(new DelayActionAction(new RelicAboveCreatureAction(AbstractDungeon.player,this)));
                addToBot(new DelayActionAction(new ArmamentsAction(true)));
            }
            else {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
                addToBot(new GainEnergyAction(1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}




