package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Mon2tr extends CustomRelic {

    public static final String ID = "mon3tr:Mon2tr";

    public Mon2tr(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.STARTER,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new DrawCardAction(AbstractDungeon.player, 2));
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void atTurnStartPostDraw() {
        //第2、3回合抽牌数减少1张
        if(counter<0){
            this.counter = 2;
            AbstractDungeon.player.gameHandSize--;
        }
        else if(counter>0){
            this.flash();
            this.counter--;
            if(this.counter == 0){
                AbstractDungeon.player.gameHandSize++;
            }
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Mon2tr();
    }
}



