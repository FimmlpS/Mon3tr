package Mon3tr.relic;

import Mon3tr.character.Mon3tr;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class M3HJ extends CustomRelic {

    public static final String ID = "mon3tr:M3HJ";

    public M3HJ(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.UNCOMMON,LandingSound.CLINK);
        this.counter = -1;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        this.flash();
        addToTop(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,2,true));
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public int getPrice() {
        if(AbstractDungeon.player!= null && AbstractDungeon.player.hasRelic(M3LZ.ID))
            return 50;
        return 200;
    }

    //小patch一下
    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 128)
        public static SpireReturn<Void> Insert(AbstractPlayer _inst, DamageInfo info){
            if(AbstractDungeon.player instanceof Mon3tr){
                AbstractRelic hj = AbstractDungeon.player.getRelic(M3HJ.ID);
                if(hj==null){
                    return SpireReturn.Continue();
                }
                if(MeltdownPatch.meltDownCounter>=40){
                    MeltdownPatch.meltDownCounter -= 40;
                    _inst.currentHealth = 0;
                    _inst.heal(20);
                    hj.flash();
                    AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(_inst,hj));
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}



