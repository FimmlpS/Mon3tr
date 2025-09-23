package Mon3tr.patch;

import Mon3tr.relic.DeviceHardness;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RelicPatch {
    @SpirePatch(clz = AbstractCreature.class, method = "brokeBlock")
    public static class HardnessPatch{
        public static void Postfix(AbstractCreature _inst){
            if(!_inst.isPlayer)
                return;
            AbstractRelic hardness = AbstractDungeon.player.getRelic(DeviceHardness.ID);
            if(hardness!=null){
                hardness.onTrigger();
            }
        }
    }
}
