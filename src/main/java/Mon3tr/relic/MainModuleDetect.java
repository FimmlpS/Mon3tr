package Mon3tr.relic;

import Mon3tr.action.RandomIterationAction;
import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class MainModuleDetect extends CustomRelic {

    public static final String ID = "mon3tr:MainModuleDetect";

    public MainModuleDetect(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.RARE,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public int getPrice() {
        return 240;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(targetCard.type == AbstractCard.CardType.ATTACK){
            this.flash();
            addToBot(new RandomIterationAction(1,true));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}







