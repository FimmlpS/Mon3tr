package Mon3tr.relic;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ModuleFast extends CustomRelic {
    public static final String ID = "mon3tr:ModuleFast";
    private boolean triggered = false;

    public ModuleFast(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.COMMON,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public int getPrice() {
        return 120;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(triggered)
            return;
        if(targetCard instanceof AbstractMon3trCard){
            AbstractMon3trCard m = (AbstractMon3trCard) targetCard;
            triggered = true;
            this.flash();
            m.increaseIteration(2);
        }
    }

    @Override
    public void atTurnStart() {
        triggered = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}



