package Mon3tr.helper;

import Mon3tr.card.AbstractExpressCard;
import Mon3tr.card.attack.*;
import Mon3tr.card.express.*;
import Mon3tr.card.power.*;
import Mon3tr.card.skill.*;
import Mon3tr.card.status.*;
import Mon3tr.monster.Dsbish;
import Mon3tr.monster.Empgrd;
import Mon3tr.monster.Smephi;
import Mon3tr.relic.*;
import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class RegisterHelper {

    public static ArrayList<String> startDeck = new ArrayList<>();
    public static ArrayList<String> startRelic = new ArrayList<>();
    public static ArrayList<String> startRelic2 = new ArrayList<>();
    public static ArrayList<String> strategyCards = new ArrayList<>();

    public static ArrayList<AbstractCard> getCardsToAdd(){
        ArrayList<AbstractCard> list = new ArrayList<>();

        //BASIC - 4
        list.add(new Strike());
        list.add(new Defend());
        list.add(new Core());
        list.add(new Unstoppable());

        //COMMON - 21
        list.add(new CrystalDiffusion());
        list.add(new RebuildFate());
        list.add(new PrepareMedicine());
        list.add(new Redirect());
        list.add(new LullabyOfDeath());
        list.add(new StrategyLock());
        list.add(new IntoTheMarrow());
        list.add(new Mesh());
        list.add(new OrganizeSample());
        list.add(new CrackStrike());
        list.add(new WeaveBlock());
        list.add(new StrategyTree());
        list.add(new DustToDust());
        list.add(new SharpGash());
        list.add(new CrippleStrike());
        list.add(new Calcite());
        list.add(new ShiningTogether());
        list.add(new StructureGuidance());
        list.add(new FadeAndGrow());
        list.add(new EndingOfMemory());
        list.add(new Subspace());

        //UNCOMMON - 35
        list.add(new BattleFocus());
        list.add(new ExistInNameOnly());
        list.add(new DensificationBody());
        list.add(new NewLive());
        list.add(new DeconstructToChaos());
        list.add(new ProgressiveApproach());
        list.add(new IterationAcceleration());
        list.add(new InverseQuantization());
        list.add(new StrategyFocus());
        list.add(new TimeInsight());
        list.add(new DimensionMiracle());
        list.add(new StrategyFollowUp());
        list.add(new InDustOrBlood());
        list.add(new CriticalPoint());
        list.add(new InformationSkim());
        list.add(new TimeSand());
        list.add(new Resonance());
        list.add(new MedicineShield());
        list.add(new StarWeb());
        list.add(new CanGetBack());
        list.add(new HotShock());
        list.add(new PolishClaws());
        list.add(new IsolationClearance());
        list.add(new ProtocolOfPsyche());
        list.add(new IntenseCrystalCracking());
        list.add(new CellActivation());
        list.add(new TurbulentFlow());
        list.add(new EndingOfCirculation());
        list.add(new MakeNoAllowance());
        //list.add(new Hedge());
        list.add(new FateReflow());
        list.add(new AnotherHand());
        list.add(new SorryForYou());
        list.add(new RuinAndCure());
        list.add(new CoverMeInDebris());
        list.add(new CrystalEntity());

        //RARE - 15
        list.add(new NamedMonster());
        list.add(new NoLongerSung());
        list.add(new StrategyOverpressureLink());
        list.add(new CircularTwins());
        list.add(new ShatteredEcho());
        list.add(new ChangesByDegree());
        list.add(new SelfMagnetism());
        //list.add(new DimensionalDeduction());
        list.add(new BackTrack());
        list.add(new MutualExclusion());
        //list.add(new PastEndsFuture());
        list.add(new ReverseHalfLife());
        list.add(new EternityMissesPresent());
        list.add(new AssaultCommand());
        list.add(new UnbreakableRefactoring());
        list.add(new MemoryCrystal());
        list.add(new NeverThere());

        //OTHER - 12
        list.add(new StrategyOverload());
        list.add(new StrategyMeltdown());
        list.add(new ClockInsight());
        list.add(new SpaceMiracle());
        list.add(new ThePastOne());
        list.add(new TheNowOne());
        list.add(new MemoryFragment());
        list.add(new SelfFix());
        list.add(new BlackSnow());
        list.add(new CageOfPerson());
        list.add(new PersonTrue());
        list.add(new PersonFalse());

        return list;
    }

    public static ArrayList<AbstractExpressCard> getExpressCardsToAdd(){
        ArrayList<AbstractExpressCard> list = new ArrayList<>();

        //COMMON - 8
        list.add(new MALMuscleAmyotrophy());
        list.add(new MALEmotionDisorder());
        list.add(new MALNeuralRegression());
        list.add(new MALImmuneDisorder());
        list.add(new BENMuscleStrengthen());
        list.add(new BENEmotionStable());
        list.add(new BENNeuralEvolution());
        list.add(new BENImmuneDevelop());

        //UNCOMMON - 8
        list.add(new MALIncapacitated());
        list.add(new MALInjudicious());
        list.add(new MALUnconcentrated());
        list.add(new MALCureless());
        list.add(new BENCapacitated());
        list.add(new BENJudicious());
        list.add(new BENConcentrated());
        list.add(new BENCurable());

        //RARE - 4
        list.add(new SYSDrugResistance());
        list.add(new SYSLupusErythematosus());
        list.add(new SYSLCystineRestore());
        list.add(new SYSReperfusionInjury());

        return list;
    }

    public static ArrayList<AbstractRelic> getRelicsToAdd(boolean onlyMon3tr){
        ArrayList<AbstractRelic> list = new ArrayList<>();
        if(onlyMon3tr){
            //STARTER
            list.add(new Mon2tr());
            list.add(new Mon3trR());

            //COMMON
            list.add(new ModuleRecycle());
            list.add(new ModuleTarget());
            list.add(new ModuleFast());
            list.add(new ModuleRecover());

            //UNCOMMON
            list.add(new ModuleOverclock());
            list.add(new ModuleReceptor());
            list.add(new ModulePrepare());
            list.add(new ModuleRadiate());
            list.add(new M3LZ());
            list.add(new M3HJ());

            //RARE
            list.add(new MainModuleRecycle());
            list.add(new MainModuleHelp());
            list.add(new MainModuleDetect());
            list.add(new MainModuleColdness());

            //BOSS
            list.add(new SpecialSeal());
            list.add(new RephaseEnantiomer());
            list.add(new Remainings());
        }
        else{
            //COMMON
            list.add(new DeviceResonance());

            //UNCOMMON
            list.add(new DeviceHardness());

            //SPECIAL
            list.add(new BadOrganization());
            list.add(new GeneSeed());
            list.add(new LiveDust());

        }

        return list;
    }

    public static void registerMonster(){
        UIStrings ui = CardCrawlGame.languagePack.getUIString("mon3tr:Monsters");
        String[] names = ui.TEXT;
        //BOSS
        BaseMod.addMonster(Empgrd.ID,names[0],()->{
            return new MonsterGroup(new AbstractMonster[]{
                    new Empgrd(0F,0F)
            });
        });
        BaseMod.addBoss("mon3tr:Utawarerumono",Empgrd.ID,Empgrd.ICON,Empgrd.ICON_O);

        BaseMod.addMonster(Dsbish.ID,names[1],()->{
            return new MonsterGroup(new AbstractMonster[]{
                    new Dsbish(100.0F,5F)
            });
        });
        BaseMod.addBoss("mon3tr:Utawarerumono",Dsbish.ID,Dsbish.ICON,Dsbish.ICON_O);

        BaseMod.addMonster(Smephi.ID,names[2],()->{
            return new MonsterGroup(new AbstractMonster[]{
                    new Smephi(100.0F,5F)
            });
        });
        BaseMod.addBoss("mon3tr:Utawarerumono",Smephi.ID,Smephi.ICON,Smephi.ICON_O);
    }

    private static void initializeStrategyCards(){
        strategyCards.add(WeaveBlock.ID);
        strategyCards.add(StrategyTree.ID);
        strategyCards.add(StrategyFocus.ID);
        strategyCards.add(StrategyFollowUp.ID);
        strategyCards.add(StrategyOverpressureLink.ID);
    }

    static {
        for(int i =0;i<5;i++){
            startDeck.add(Strike.ID);
        }
        for (int i =0;i<5;i++){
            startDeck.add(Defend.ID);
        }
        //NO MORE QWQ
        //startDeck.add(StrategyOverload.ID);
        startDeck.add(Core.ID);
        startDeck.add(Unstoppable.ID);

        startRelic.add(Mon2tr.ID);

        startRelic2.add(Mon3trR.ID);

        initializeStrategyCards();
    }
}
