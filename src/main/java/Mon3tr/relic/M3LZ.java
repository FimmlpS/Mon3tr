package Mon3tr.relic;

import Mon3tr.action.IncreaseTechnicalAction;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class M3LZ extends CustomRelic {

    public static final String ID = "mon3tr:M3LZ";

    public M3LZ(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.UNCOMMON,LandingSound.CLINK);
        this.counter = -1;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            MeltdownPatch.increaseMeltdownCounter(1);
        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if(!AbstractDungeon.player.hasRelic(M3HJ.ID))
            return;
        this.flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new IncreaseTechnicalAction(20,true));
    }

    @Override
    public int getPrice() {
        if(AbstractDungeon.player!= null && AbstractDungeon.player.hasRelic(M3HJ.ID))
            return 50;
        return 200;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}



