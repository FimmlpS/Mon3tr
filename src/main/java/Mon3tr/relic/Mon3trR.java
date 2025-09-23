package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Mon3trR extends CustomRelic {

    public static final String ID = "mon3tr:Mon3trR";

    public Mon3trR(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(Mon2tr.ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(Mon2tr.ID,true)),RelicTier.STARTER,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EquilibriumPower(AbstractDungeon.player,1),1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Mon3trR();
    }
}




