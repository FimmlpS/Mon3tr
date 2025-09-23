package Mon3tr.relic;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class MainModuleHelp extends CustomRelic {

    public static final String ID = "mon3tr:MainModuleHelp";

    public MainModuleHelp(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.RARE,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public int getPrice() {
        return 240;
    }

    @Override
    public void atTurnStart() {
        if (MeltdownPatch.isMeltdownTurn) {
            this.flash();
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    addToBot(new RelicAboveCreatureAction(m,this));
                    addToBot(new ApplyPowerAction(m,AbstractDungeon.player,new WeakPower(m,1,false),1));
                    addToBot(new ApplyPowerAction(m,AbstractDungeon.player,new VulnerablePower(m,1,false),1));
                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}






