package Mon3tr.patch;

import Mon3tr.character.Mon3tr;
import Mon3tr.modcore.ModConfig;
import Mon3tr.ui.CrystalTopItem;
import Mon3tr.ui.RebuildOption;
import Mon3tr.ui.StarGroup;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.TopPanelGroup;
import basemod.TopPanelItem;
import basemod.patches.com.megacrit.cardcrawl.helpers.TopPanel.TopPanelHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

public class CrystalPatch {
    public static int crystalAmount;
    public static CrystalTopItem crystalTopItem = new CrystalTopItem();

    public static StarGroup starGroup = new StarGroup();

    public static void obtainCrystal(int amt){
        if(amt>0){
            crystalAmount += amt;
        }
    }

    public static void loseCrystal(int amt){
        crystalAmount -= amt;
        if(crystalAmount <= 0){
            crystalAmount = 0;
        }
    }

    public static void setTopPanelItem(){
        if(AbstractDungeon.player instanceof Mon3tr || ModConfig.allUseRefractor){
            if(!addedItem())
                BaseMod.addTopPanelItem(crystalTopItem);
        }
        else{
            BaseMod.removeTopPanelItem(crystalTopItem);
        }
    }

    private static boolean addedItem(){
        ArrayList<TopPanelItem> items = ReflectionHacks.getPrivate(TopPanelHelper.topPanelGroup, TopPanelGroup.class,"topPanelItems");
        return items.contains(crystalTopItem);
    }

    public static void updateCrystal(){
        starGroup.update();
    }

    @SpirePatch(clz = AbstractRelic.class, method = SpirePatch.CLASS)
    public static class RelicField {
        public static SpireField<Boolean> unRefractor = new SpireField<Boolean>(() -> false);
    }

    @SpirePatch(clz = AbstractMonster.class,method = "die",paramtypez = {boolean.class})
    public static class MonsterDropCrystalPatch{
        @SpirePrefixPatch
        public static void Prefix(AbstractMonster _inst, boolean triggerRelics){
            if(!(AbstractDungeon.player instanceof Mon3tr) && !ModConfig.allUseRefractor)
                return;
            if(triggerRelics && !_inst.isDying){
                if(_inst.hasPower(MinionPower.POWER_ID)){
                    return;
                }
                int c = 1;
                if(_inst.type == AbstractMonster.EnemyType.ELITE){
                    c = 2;
                }
                else if(_inst.type == AbstractMonster.EnemyType.BOSS){
                    c = 5;
                }
                obtainCrystal(c);
                for(int i =0;i<c;i++){
                    starGroup.onDropCrystal(_inst);
                }
            }
        }
    }

    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class AddTearChargePatch {
        @SpireInsertPatch(rloc = 33,localvars = {"buttons"})
        public static void Insert(CampfireUI _inst, ArrayList<AbstractCampfireOption> buttons) {
            if(!(AbstractDungeon.player instanceof Mon3tr) && !ModConfig.allUseRefractor)
                return;
            boolean valid = false;
            if(CrystalPatch.crystalAmount>=8)
                valid = true;
            buttons.add(new RebuildOption(valid));
        }
    }
}
