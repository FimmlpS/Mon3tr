package Mon3tr.relic;

import Mon3tr.action.SpawnDiscordAction;
import Mon3tr.helper.StringHelper;
import Mon3tr.power.DiscordCrystalPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MainModuleColdness extends CustomRelic {

    public static final String ID = "mon3tr:MainModuleColdness";

    public MainModuleColdness(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.RARE,LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public int getPrice() {
        return 240;
    }

    @Override
    public void onTrigger(AbstractCreature target) {
        //addToBot(new SpawnDiscordAction(target,1));
        AbstractPower p = new DiscordCrystalPower(target,1);
        target.powers.add(p);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}








