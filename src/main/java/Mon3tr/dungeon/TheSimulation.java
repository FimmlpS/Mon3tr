package Mon3tr.dungeon;

import Mon3tr.helper.DungeonHelper;
import Mon3tr.monster.Dsbish;
import Mon3tr.monster.Empgrd;
import Mon3tr.monster.Smephi;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class TheSimulation extends AbstractDungeon {
    private static final Logger logger = LogManager.getLogger(TheSimulation.class.getName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public static final String[] EXTRA_TEXT;
    public static String ID = "mon3tr:Simulation";

    //0-Emperor 1-todo
    public static int currentType = 0;

    public TheSimulation(AbstractPlayer p, ArrayList<String> theList) {
        super(getName(),ID,p,theList);
        if(scene!=null){
            scene.dispose();
        }
        initializeScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        initializeLevelSpecificChances();
        mapRng = new Random(Settings.seed);
        generateSpecialMap();
        CardCrawlGame.music.changeBGM(id);
    }

    public TheSimulation(AbstractPlayer p, SaveFile saveFile){
        super(getName(),p,saveFile);
        CardCrawlGame.dungeon = this;
        if(scene!=null){
            scene.dispose();
        }
        initializeScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        initializeLevelSpecificChances();
        miscRng = new Random(Settings.seed + (long)saveFile.floor_num);
        CardCrawlGame.music.changeBGM(id);
        mapRng = new Random(Settings.seed);
        this.generateSpecialMap();
        firstRoomChosen = true;
        this.populatePathTaken(saveFile);
    }

    public static String getName(){
        return EXTRA_TEXT[currentType];
    }

    private void initializeScene(){
        switch (currentType){
            case 0:
                scene = new SimulationEmperorScene();
                break;
            case 1:
                scene = new SimulationDsbishScene();
                break;
            case 2:
                scene = new SimulationSmephiScene();
                break;
        }
    }

    private void generateSpecialMap(){
        long startTime = System.currentTimeMillis();
        map = new ArrayList();
        MapRoomNode rest = new MapRoomNode(3,0);
        rest.room =  new RestRoom();
        MapRoomNode boss = new MapRoomNode(3,5);
        boss.room = new MonsterRoomBoss();
        MapRoomNode victory = new MapRoomNode(3,6);
        victory.room = new TrueVictoryRoom();

        map.add(DungeonHelper.createRow(0,rest));
        map.add(DungeonHelper.createRow(1));
        map.add(DungeonHelper.createRow(2));
        map.add(DungeonHelper.createRow(3));
        map.add(DungeonHelper.createRow(4));
        map.add(DungeonHelper.createRow(5,boss));
        map.add(DungeonHelper.createRow(6,victory));

        DungeonHelper.connectNode(rest,boss);


        logger.info("Generated the following dungeon map:");
        logger.info(MapGenerator.toString(map, true));
        logger.info("Game Seed: " + Settings.seed);
        logger.info("Map generation time: " + (System.currentTimeMillis() - startTime) + "ms");
        firstRoomChosen = false;
        fadeIn();
    }

    @Override
    protected void generateMonsters() {
        monsterList = new ArrayList<>();
        monsterList.add("Shield and Spear");
        monsterList.add("Shield and Spear");
        monsterList.add("Shield and Spear");
        eliteMonsterList = new ArrayList<>();
        eliteMonsterList.add("Shield and Spear");
        eliteMonsterList.add("Shield and Spear");
        eliteMonsterList.add("Shield and Spear");
    }

    @Override
    protected void generateWeakEnemies(int i) {

    }

    @Override
    protected void generateStrongEnemies(int i) {

    }

    @Override
    protected void generateElites(int i) {

    }

    @Override
    protected void initializeBoss() {
        bossList = new ArrayList<>();
        switch (currentType){
            case 0:
                bossList.add(Empgrd.ID);
                bossList.add(Empgrd.ID);
                bossList.add(Empgrd.ID);
                break;
            case 1:
                bossList.add(Dsbish.ID);
                bossList.add(Dsbish.ID);
                bossList.add(Dsbish.ID);
                break;
            case 2:
                bossList.add(Smephi.ID);
                bossList.add(Smephi.ID);
                bossList.add(Smephi.ID);
                break;
        }
    }

    @Override
    protected void initializeEventList() {

    }

    @Override
    protected void initializeEventImg() {
        if (eventBackgroundImg != null) {
            eventBackgroundImg.dispose();
            eventBackgroundImg = null;
        }

        eventBackgroundImg = ImageMaster.loadImage("images/ui/event/panel.png");
    }

    @Override
    protected void initializeShrineList() {

    }

    @Override
    protected void initializeLevelSpecificChances() {

    }

    @Override
    protected ArrayList<String> generateExclusions() {
        return new ArrayList<>();
    }



    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
        EXTRA_TEXT = uiStrings.EXTRA_TEXT;
    }
}
