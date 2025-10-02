package Mon3tr.patch;

import Mon3tr.dungeon.TheSimulation;
import Mon3tr.monster.Dsbish;
import Mon3tr.monster.Empgrd;
import Mon3tr.relic.BadOrganization;
import Mon3tr.relic.GeneSeed;
import Mon3tr.relic.LiveDust;
import Mon3tr.room.SimulationTreasureRoom;
import basemod.ReflectionHacks;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

import java.util.Iterator;

public class SimulationPatch {
    public static float SimulationRelicChance = 0.3F;
    public static boolean EnterMNEmperor = false;
    public static boolean EnteredMNEmperor = false;
    public static boolean EnterMNDsbish = false;
    public static boolean EnteredMNDsbish = false;
    public static boolean EnterMNSmephi = false;
    public static boolean EnteredMNSmephi = false;
    public static boolean KilledMNEmperor = false;
    public static boolean KilledMNDsbish = false;
    public static boolean KilledMNSmephi = false;

    public static String BOSS_KEY = "";

    @SpirePatch(
            clz = TempMusic.class,
            method = "getSong"
    )
    public static class TempMusicPatch{
        @SpirePostfixPatch
        public static Music Postfix(Music _result, TempMusic _inst,String key){
            if(AbstractDungeon.id!=null && AbstractDungeon.id.equals(TheSimulation.ID) && key.equals("BOSS_BEYOND")){
                if(TheSimulation.currentType == 0){
                    BOSS_KEY = "EMPGRD";
                }
                else if(TheSimulation.currentType == 1){
                    BOSS_KEY = "DSBISH";
                }
                else if(TheSimulation.currentType == 2){
                    if(AbstractDungeon.getCurrRoom().cannotLose)
                        BOSS_KEY = "SMEPHI1";
                    else{
                        BOSS_KEY = "SMEPHI2";
                    }
                }
                if(BOSS_KEY.equals("EMPGRD")){
                    return MainMusic.newMusic("Mon3trResources/audio/empgrd.ogg");
                }
                else if(BOSS_KEY.equals("DSBISH")){
                    return MainMusic.newMusic("Mon3trResources/audio/dsbish.ogg");
                }
                else if(BOSS_KEY.equals("SMEPHI1")){
                    return MainMusic.newMusic("Mon3trResources/audio/smephi1.ogg");
                }
                else if(BOSS_KEY.equals("SMEPHI2")){
                    return MainMusic.newMusic("Mon3trResources/audio/smephi2.ogg");
                }
            }
            BOSS_KEY = "";
            return _result;
        }
    }

    @SpirePatch(clz = CardCrawlGame.class,method = "getDungeon",paramtypez = {String.class, AbstractPlayer.class})
    public static class GetDungeonPatch{
        @SpirePostfixPatch
        public static AbstractDungeon Postfix(AbstractDungeon _ret, CardCrawlGame _inst, String key, AbstractPlayer p){

            if(key.equals(TheSimulation.ID))
                return new TheSimulation(p,AbstractDungeon.specialOneTimeEventList);

            return _ret;
        }
    }

    @SpirePatch(clz = CardCrawlGame.class,method = "getDungeon",paramtypez = {String.class,AbstractPlayer.class, SaveFile.class})
    public static class GetDungeonOnSavePatch{
        @SpirePostfixPatch
        public static AbstractDungeon Postfix(AbstractDungeon _ret, CardCrawlGame _inst, String key, AbstractPlayer p,SaveFile file){

            if(key.equals(TheSimulation.ID))
                return new TheSimulation(p,file);

            return _ret;
        }
    }

    @SpirePatch(clz = DungeonMap.class,method = "calculateMapSize")
    public static class MapSizePatch{
        @SpirePostfixPatch
        public static float Postfix(float _ret,DungeonMap _inst){
            if(AbstractDungeon.id.equals(TheSimulation.ID))
                return Settings.MAP_DST_Y * 4F - 1080F*Settings.scale;
            return _ret;
        }
    }

    @SpirePatch(clz = DungeonMap.class,method = "update")
    public static class UpdatePatch {
        @SpireInsertPatch(rloc = 40)
        public static void Insert(DungeonMap _inst) {
            if (_inst.bossHb.hovered && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())) {
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMPLETE && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP) {
                    if ((Settings.isDebug || AbstractDungeon.id.equals(TheSimulation.ID) && AbstractDungeon.getCurrMapNode().y == 0)) {
                        AbstractDungeon.getCurrMapNode().taken = true;
                        MapRoomNode node2 = AbstractDungeon.getCurrMapNode();
                        Iterator var2 = node2.getEdges().iterator();

                        while (var2.hasNext()) {
                            MapEdge e = (MapEdge) var2.next();
                            if (e != null) {
                                e.markAsTaken();
                            }
                        }

                        InputHelper.justClickedLeft = false;
                        CardCrawlGame.music.fadeOutTempBGM();
                        MapRoomNode node = new MapRoomNode(-1, 15);
                        node.room = new MonsterRoomBoss();
                        AbstractDungeon.nextRoom = node;
                        if (AbstractDungeon.pathY.size() > 1) {
                            AbstractDungeon.pathX.add(AbstractDungeon.pathX.get(AbstractDungeon.pathX.size() - 1));
                            AbstractDungeon.pathY.add((Integer) AbstractDungeon.pathY.get(AbstractDungeon.pathY.size() - 1) + 1);
                        } else {
                            AbstractDungeon.pathX.add(1);
                            AbstractDungeon.pathY.add(15);
                        }

                        AbstractDungeon.nextRoomTransitionStart();
                        _inst.bossHb.hovered = false;
                    }
                }
            }
        }
    }

    @SpirePatch(clz = DungeonMap.class,method = "render")
    public static class RenderPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(DungeonMap _inst, SpriteBatch sb){
            if(AbstractDungeon.id.equals(TheSimulation.ID)){
                ReflectionHacks.privateMethod(DungeonMap.class,"renderFinalActMap", SpriteBatch.class).invoke(_inst, sb);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = DungeonMap.class,method = "renderMapBlender")
    public static class RenderBlenderPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(DungeonMap _inst, SpriteBatch sb){
            if(AbstractDungeon.id.equals(TheSimulation.ID))
                return SpireReturn.Return();
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = DungeonMapScreen.class,method = "open")
    public static class OpenMapPatch{
        @SpireInsertPatch(rloc = 8)
        public static void Insert(DungeonMapScreen _inst,boolean doScrollingAnimation){
            if(AbstractDungeon.id.equals(TheSimulation.ID)){
                ReflectionHacks.setPrivate(_inst,DungeonMapScreen.class,"mapScrollUpperLimit",-720.0F * Settings.scale);
            }
        }
    }

    @SpirePatch(clz = ProceedButton.class,method = "update")
    public static class EndSimulationPatch{
        @SpireInsertPatch(rloc = 40)
        public static void Insert(ProceedButton _inst){
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
                if (AbstractDungeon.id.equals(TheSimulation.ID)) {
                    CardCrawlGame.music.fadeOutBGM();
                    MapRoomNode node = new MapRoomNode(3, 6);
                    node.room = new TrueVictoryRoom();
                    AbstractDungeon.nextRoom = node;
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.nextRoomTransitionStart();
                    _inst.hide();
                }
            }
        }
    }

    //change true victory
    @SpirePatch(clz = TrueVictoryRoom.class,method = "onPlayerEntry")
    public static class TrueVictoryPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TrueVictoryRoom _inst){
            boolean continueIt = false;
            for(AbstractRelic r : AbstractDungeon.player.relics){
                if(r.relicId.equals(BadOrganization.ID)){
                    if(!EnteredMNEmperor&&!EnterMNEmperor){
                        EnterMNEmperor = true;
                        continueIt = true;
                        break;
                    }
                }
                else if(r.relicId.equals(GeneSeed.ID)){
                    if(!EnteredMNDsbish&&!EnterMNDsbish){
                        EnterMNDsbish = true;
                        continueIt = true;
                        break;
                    }
                }
                else if(r.relicId.equals(LiveDust.ID)){
                    if(!EnteredMNSmephi&&!EnterMNSmephi){
                        EnterMNSmephi = true;
                        continueIt = true;
                        break;
                    }
                }
            }
            if(continueIt){
                CardCrawlGame.stopClock = false;
                CardCrawlGame.music.fadeOutTempBGM();
                MapRoomNode node = new MapRoomNode(-1, 15);
                node.room = new SimulationTreasureRoom();
                AbstractDungeon.nextRoom = node;
                AbstractDungeon.closeCurrentScreen();
                CardCrawlGame.dungeon.nextRoomTransition();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    //BOSS宝箱房修正
    @SpirePatch(clz = AbstractDungeon.class,method = "populatePathTaken")
    public static class PopulatePathTakenPatch{
        @SpireInsertPatch(rloc = 25,localvars = {"node"})
        public static void Insert(AbstractDungeon _inst,SaveFile saveFile, MapRoomNode node){
            if (saveFile.current_room.equals(SimulationTreasureRoom.class.getName())) {
                node = new MapRoomNode(-1, 15);
                node.room = new SimulationTreasureRoom();
                AbstractDungeon.nextRoom = node;
            }
        }
    }

    //兼容更新：仅当进入过BOSS房，才标记已经进入过
    @SpirePatch(clz = AbstractDungeon.class, method = "nextRoomTransition",paramtypez = {SaveFile.class})
    public static class EnteredPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon _inst, SaveFile saveFile){
            if(CardCrawlGame.dungeon instanceof TheSimulation && AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
                if(TheSimulation.currentType == 0)
                    EnteredMNEmperor = true;
                else if(TheSimulation.currentType == 1)
                    EnteredMNDsbish = true;
                else if(TheSimulation.currentType == 2)
                    EnteredMNSmephi = true;
            }
        }
    }

    //dungeon
    @SpirePatch(clz = TreasureRoomBoss.class,method = "getNextDungeonName")
    public static class TreasureBossPatch{
        @SpirePostfixPatch
        public static String Postfix(String _ret,TreasureRoomBoss _inst){
            if(!EnteredMNEmperor&&EnterMNEmperor){
                TheSimulation.currentType = 0;
                return TheSimulation.ID;
            }
            if(!EnteredMNDsbish&&EnterMNDsbish){
                TheSimulation.currentType = 1;
                return TheSimulation.ID;
            }
            if(!EnteredMNSmephi&&EnterMNSmephi){
                TheSimulation.currentType = 2;
                return TheSimulation.ID;
            }
            return _ret;
        }
    }

    //dungeon actlikeit
    @SpirePatch(clz = ProceedButton.class,method = "goToNextDungeon")
    public static class ProceedButtonTPatch{
        @SpirePostfixPatch
        public static void Postfix(ProceedButton _inst){
            if(!EnteredMNEmperor && EnterMNEmperor){
                TheSimulation.currentType = 0;
                CardCrawlGame.nextDungeon = TheSimulation.ID;
            }
            else if(!EnteredMNDsbish && EnterMNDsbish){
                TheSimulation.currentType = 1;
                CardCrawlGame.nextDungeon = TheSimulation.ID;
            }
            else if(!EnteredMNSmephi && EnterMNSmephi){
                TheSimulation.currentType = 2;
                CardCrawlGame.nextDungeon = TheSimulation.ID;
            }
        }
    }
}
