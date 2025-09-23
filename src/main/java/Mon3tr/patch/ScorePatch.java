package Mon3tr.patch;

import Mon3tr.character.Mon3tr;
import Mon3tr.relic.BadOrganization;
import Mon3tr.relic.GeneSeed;
import Mon3tr.relic.LiveDust;
import Mon3tr.ui.CharSelectScreen;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.ScoreBonusStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ScorePatch {
    public static final ScoreBonusStrings theShadow;
    public static final ScoreBonusStrings theSophontheatrum;
    public static final ScoreBonusStrings theLight;
    public static final ScoreBonusStrings hardSelections;

    public static boolean theShadowEnable = false;
    public static boolean theSophontheatrumEnable = false;
    public static boolean theLightEnable = false;

    @SpirePatch(clz = GameOverScreen.class,method = "checkScoreBonus")
    public static class CheckScorePatch{
        @SpireInsertPatch(locator = Locator.class, localvars = {"points"})
        public static void Insert(boolean victory, @ByRef int[] points) {
            if(AbstractDungeon.player instanceof Mon3tr){
                if(CharSelectScreen.hardModeEnable(1)){
                    points[0] += 50;
                }
                if(CharSelectScreen.hardModeEnable(2)){
                    points[0] += 50;
                }
            }
            AbstractRelic r1 = AbstractDungeon.player.getRelic(BadOrganization.ID);
            if(r1 != null && r1.counter == 1) {
                points[0] += 300;
            }
            AbstractRelic r2 = AbstractDungeon.player.getRelic(GeneSeed.ID);
            if(r2 != null && r2.counter == 1) {
                points[0] += 300;
            }
            AbstractRelic r3 = AbstractDungeon.player.getRelic(LiveDust.ID);
            if(r3 != null && r3.counter == 1) {
                points[0] += 300;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
            }
        }
    }

    @SpirePatch(clz = VictoryScreen.class, method = "createGameOverStats")
    public static class VictoryStatsPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(VictoryScreen _inst) {
            ScorePatch.addStats((GameOverScreen)_inst);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(VictoryScreen.class, "IS_POOPY");
                return LineFinder.findInOrder(ctBehavior, (Matcher)fieldAccessMatcher);
            }
        }
    }

    @SpirePatch(clz = DeathScreen.class, method = "createGameOverStats")
    public static class DeathStatsPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(DeathScreen _inst) {
            ScorePatch.addStats((GameOverScreen)_inst);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(DeathScreen.class, "IS_POOPY");
                return LineFinder.findInOrder(ctBehavior, (Matcher)fieldAccessMatcher);
            }
        }
    }

    private static void addStats(GameOverScreen _inst) {
        try {
            Field stats = GameOverScreen.class.getDeclaredField("stats");
            stats.setAccessible(true);
            if(AbstractDungeon.player instanceof Mon3tr){
                if(CharSelectScreen.hardModeEnable(1)){
                    ((ArrayList<GameOverStat>)stats.get(_inst)).add(new GameOverStat(hardSelections.DESCRIPTIONS[0], hardSelections.DESCRIPTIONS[1],Integer.toString(50)));
                }
                if(CharSelectScreen.hardModeEnable(2)){
                    ((ArrayList<GameOverStat>)stats.get(_inst)).add(new GameOverStat(hardSelections.DESCRIPTIONS[2], hardSelections.DESCRIPTIONS[3],Integer.toString(50)));
                }
            }
            AbstractRelic r1 = AbstractDungeon.player.getRelic(BadOrganization.ID);
            if(r1 != null && r1.counter == 1) {
                ((ArrayList<GameOverStat>)stats.get(_inst)).add(new GameOverStat(theShadow.NAME, theShadow.DESCRIPTIONS[0],Integer.toString(300)));
            }
            AbstractRelic r2 = AbstractDungeon.player.getRelic(GeneSeed.ID);
            if(r2 != null && r2.counter == 1) {
                ((ArrayList<GameOverStat>)stats.get(_inst)).add(new GameOverStat(theSophontheatrum.NAME, theSophontheatrum.DESCRIPTIONS[0],Integer.toString(300)));
            }
            AbstractRelic r3 = AbstractDungeon.player.getRelic(LiveDust.ID);
            if(r3 != null && r3.counter == 1) {
                ((ArrayList<GameOverStat>)stats.get(_inst)).add(new GameOverStat(theLight.NAME, theLight.DESCRIPTIONS[0],Integer.toString(300)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to set game over stats.", e);
        }
    }

    static {
        theShadow = CardCrawlGame.languagePack.getScoreString("mon3tr:TheShadow");
        theSophontheatrum = CardCrawlGame.languagePack.getScoreString("mon3tr:TheSophontheatrum");
        theLight = CardCrawlGame.languagePack.getScoreString("mon3tr:TheLight");
        hardSelections = CardCrawlGame.languagePack.getScoreString("mon3tr:HardSelections");
    }
}
