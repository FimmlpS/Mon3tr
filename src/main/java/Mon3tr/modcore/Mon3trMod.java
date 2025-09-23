package Mon3tr.modcore;

import Mon3tr.action.IncreaseIterationAction;
import Mon3tr.action.TutorialAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.attack.WeaveBlock;
import Mon3tr.character.Mon3tr;
import Mon3tr.events.ACloth;
import Mon3tr.events.GarrisonAgreement;
import Mon3tr.helper.ImageHelper;
import Mon3tr.helper.Mon3trHelper;
import Mon3tr.helper.RegisterHelper;
import Mon3tr.patch.*;
import Mon3tr.power.DetectPower;
import Mon3tr.power.DiscordCrystalPower;
import Mon3tr.save.CrystalReward;
import Mon3tr.subscriber.SaveLoadSubscriber;
import Mon3tr.ui.CrystalTopItem;
import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

@SpireInitializer
public class Mon3trMod implements PostPlayerUpdateSubscriber,PreStartGameSubscriber,StartGameSubscriber,OnStartBattleSubscriber,AddAudioSubscriber,PostBattleSubscriber,OnPlayerTurnStartPostDrawSubscriber,OnPlayerTurnStartSubscriber,OnCardUseSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, EditStringsSubscriber, PostInitializeSubscriber {
    private static final Logger logger = LogManager.getLogger(Mon3trMod.class);

    public static Color mColor = new Color(224f/255f,200f/255f,84f/255f,1);

    public static int DefaultCharIndex;
    public static boolean HardModeEnable01;
    public static boolean HardModeEnable02;

    public static boolean TutorialTriggered = false;

    public static final String cardBg512 = "Mon3trResources/img/512/cardbgm.png";
    public static final String cardBgE512 = "Mon3trResources/img/512/cardbge.png";
    public static final String energy512 = "Mon3trResources/img/512/energybg.png";
    public static final String cardBg1024 = "Mon3trResources/img/1024/cardbg.png";
    public static final String cardBgE1024 = "Mon3trResources/img/1024/cardbge.png";
    public static final String energy1024 = "Mon3trResources/img/1024/energybg.png";

    public static void initialize(){
        new Mon3trMod();

        try {
            Properties defaults = new Properties();
            defaults.setProperty("defaultChar","0");
            defaults.setProperty("hardModeEnable01","false");
            defaults.setProperty("hardModeEnable02","false");
            defaults.setProperty("tutorialTriggered","false");
            SpireConfig config = new SpireConfig("Mon3tr_FimmlpS","Common",defaults);
            DefaultCharIndex = config.getInt("defaultChar");
            HardModeEnable01 = config.getBool("hardModeEnable01");
            HardModeEnable02 = config.getBool("hardModeEnable02");
            TutorialTriggered = config.getBool("tutorialTriggered");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void logSomething(String mes){
        logger.info(mes);
    }

    public Mon3trMod(){
        BaseMod.subscribe(this);
        BaseMod.addColor(ColorEnum.Mon3tr_COLOR,mColor.cpy(),mColor.cpy(),mColor.cpy(),mColor.cpy(),mColor.cpy(),mColor.cpy(),mColor.cpy(),cardBg512,cardBg512,cardBg512,energy512,cardBg1024,cardBg1024,cardBg1024,energy1024,"Mon3trResources/img/orbs/EnergyOrb.png");
        BaseMod.addColor(ColorEnum.Mon3tr_Express_COLOR,mColor.cpy(),mColor.cpy(),mColor.cpy(),mColor.cpy(),mColor.cpy(),mColor.cpy(),mColor.cpy(),cardBgE512,cardBgE512,cardBgE512,energy512,cardBgE1024,cardBgE1024,cardBgE1024,energy1024,"Mon3trResources/img/orbs/EnergyOrb.png");
        ModConfig.initModSettings();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Mon3tr("Mon3tr"),
                "Mon3trResources/img/charSelect/Mon3trButton.png",
                ImageHelper.getCharSelectBg(),
                ClassEnum.Mon3tr_CLASS);
    }

    @Override
    public void receiveEditStrings() {
        String relic = "relics.json",
                card = "cards.json",
                power= "powers.json",
                potion="potions.json",
                event="events.json",
                character="characters.json",
                ui="uis.json",
                monster="monsters.json",
                score ="scores.json";
        String fore = "Mon3trResources/localizations/";
        String lang;
        if(Settings.language == Settings.GameLanguage.ZHS||Settings.language == Settings.GameLanguage.ZHT){
            lang = "zh";
        }
        else if(Settings.language == Settings.GameLanguage.KOR){
            lang = "kr";
        }
        else{
            lang = "en";
        }
        relic = fore + lang + "/" + relic;
        card = fore + lang + "/" + card;
        power = fore + lang + "/" + power;
        potion = fore + lang + "/" + potion;
        event = fore + lang + "/" + event;
        character = fore + lang + "/" + character;
        ui = fore + lang + "/" + ui;
        monster = fore + lang + "/" + monster;
        score = fore + lang + "/" + score;

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class,eventStrings);
        String characterStrings = Gdx.files.internal(character).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class,characterStrings);
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class,uiStrings);
        String monsterStrings = Gdx.files.internal(monster).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(MonsterStrings.class,monsterStrings);
        String scoreStrings = Gdx.files.internal(score).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(ScoreBonusStrings.class,scoreStrings);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "en";
        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            lang = "zh";
        }
        else if(Settings.language == Settings.GameLanguage.KOR){
            lang = "kr";
        }
        else {
            lang = "en";
        }

        String json = Gdx.files.internal("Mon3trResources/localizations/"+lang+"/keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            Keyword[] var5 = keywords;
            int var6 = keywords.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Keyword keyword = var5[var7];
                BaseMod.addKeyword("mon3tr", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditRelics() {
        for(AbstractRelic r: RegisterHelper.getRelicsToAdd(true)){
            BaseMod.addRelicToCustomPool(r,ColorEnum.Mon3tr_COLOR);
        }

        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(false)){
            BaseMod.addRelic(r, RelicType.SHARED);
        }
    }

    @Override
    public void receiveEditCards() {
        for (AbstractCard c : RegisterHelper.getCardsToAdd()) {
            BaseMod.addCard(c);
        }

        for (AbstractCard c : RegisterHelper.getExpressCardsToAdd()) {
            BaseMod.addCard(c);
        }
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio("MON3TR_UNBREAKABLE","Mon3trResources/audio/p_skill_Mon3trburst.ogg");
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        //迭代
        if(abstractCard instanceof AbstractMon3trCard){
            AbstractDungeon.actionManager.addToBottom(new IncreaseIterationAction((AbstractMon3trCard) abstractCard));
        }
    }

    private void initializeEvents(){
        BaseMod.addEvent((new AddEventParams.Builder(GarrisonAgreement.ID,GarrisonAgreement.class))
                .eventType(EventUtils.EventType.ONE_TIME)
                .dungeonIDs(Exordium.ID, TheCity.ID, TheBeyond.ID,"samirg:TheSami")
                .endsWithRewardsUI(false)
                .spawnCondition(()->AbstractDungeon.player instanceof Mon3tr)
                .create());

        BaseMod.addEvent((new AddEventParams.Builder(ACloth.ID,ACloth.class))
                .eventType(EventUtils.EventType.ONE_TIME)
                .dungeonIDs(Exordium.ID, TheCity.ID, TheBeyond.ID,"samirg:TheSami")
                .endsWithRewardsUI(false)
                .spawnCondition(()->AbstractDungeon.player instanceof Mon3tr)
                .create());
    }

    @Override
    public void receivePostInitialize() {
        unlockCards();
        unlockRelics();
        ModConfig.initModConfigMenu();
        RegisterHelper.registerMonster();
        initializeEvents();
        BaseMod.registerCustomReward(
                OtherEnum.MON3TR_CRYSTAL,
                (rewardSave)->{
                    return new CrystalReward(rewardSave.amount);
                },
                (customReward)->{
                    return new RewardSave(customReward.type.toString(),null,((CrystalReward)customReward).amount,0);
                });
    }

    private static void unlockCards(){
        Iterator<AbstractCard> var1 = RegisterHelper.getCardsToAdd().iterator();
        while (var1.hasNext()){
            AbstractCard c = var1.next();
            String key = c.cardID;
            AbstractCard tmp = CardLibrary.getCard(key);
            if (tmp != null && !CardLibrary.getCard(key).isSeen) {
                tmp.isSeen = true;
                tmp.unlock();
                UnlockTracker.seenPref.putInteger(key, 1);
            }
        }
        var1 = new ArrayList<AbstractCard>(RegisterHelper.getExpressCardsToAdd()).iterator();
        while (var1.hasNext()){
            AbstractCard c = var1.next();
            String key = c.cardID;
            AbstractCard tmp = CardLibrary.getCard(key);
            if (tmp != null && !CardLibrary.getCard(key).isSeen) {
                tmp.isSeen = true;
                tmp.unlock();
                UnlockTracker.seenPref.putInteger(key, 1);
            }
        }
        UnlockTracker.seenPref.flush();
    }

    private static void unlockRelics(){
        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(true)){
            UnlockTracker.markRelicAsSeen(r.relicId);
        }

        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(false)){
            UnlockTracker.markRelicAsSeen(r.relicId);
        }
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        MonsterPatch.resetMonsterRecord();
        MeltdownPatch.addMeltdown();
        MeltdownPatch.setEndTurn();
        Mon3trHelper.onEndingMod();
        DiscordCrystalPower.tryTimes = 0;
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        if(AbstractDungeon.player instanceof Mon3tr){
            if(!TutorialTriggered){
                AbstractDungeon.actionManager.addToBottom(new TutorialAction());
            }
        }
    }

    @Override
    public void receivePreStartGame() {
        ProstsPatch.clear();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        ExpressPatch.clearPlayerExpress();
        MeltdownPatch.clearMeltdown();
        ProstsPatch.clear();
        CrystalPatch.setTopPanelItem();
        DetectPower.someoneOwned = false;
        MeltdownPatch.battleStart();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        ExpressPatch.clearPlayerExpress();
        MeltdownPatch.clearMeltdown();
        MeltdownPatch.resetMon3tr();
    }

    @Override
    public void receivePostPlayerUpdate() {
        CrystalPatch.updateCrystal();
    }

    @Override
    public void receiveStartGame() {
        CrystalPatch.setTopPanelItem();
    }

}
