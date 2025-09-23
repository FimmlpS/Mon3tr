package Mon3tr.save;

import Mon3tr.dungeon.TheSimulation;
import Mon3tr.modcore.Mon3trMod;
import Mon3tr.patch.CrystalPatch;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.ScorePatch;
import Mon3tr.patch.SimulationPatch;

public class Mon3trSave {
    public int meltdownCounterStart;
    public int meltDownCounterMax;
    public int crystalAmount;
    public float simulationRelicChange;
    public int simulationType;
    public boolean enterMNEmperor;
    public boolean enteredMNEmperor;
    public boolean enterMNDsbish;
    public boolean enteredMNDsbish;
    public boolean enterMNSmephi;
    public boolean enteredMNSmephi;
    public boolean killedMNEmperor;
    public boolean killedMNDsbish;
    public boolean killedMNSmephi;

    public Mon3trSave(){}

    @Override
    public String toString() {
        return "Mon3trSave{" +
                "meltdownCounterStart=" + meltdownCounterStart +
                ", meltDownCounterMax=" + meltDownCounterMax +
                ", crystalAmount=" + crystalAmount +
                ", simulationRelicChange=" + simulationRelicChange +
                ", simulationType=" + simulationType +
                ", enterMNEmperor=" + enterMNEmperor +
                ", enteredMNEmperor=" + enteredMNEmperor +
                ", enterMNDsbish=" + enterMNDsbish +
                ", enteredMNDsbish=" + enteredMNDsbish +
                ", killedMNEmperor=" + killedMNEmperor +
                ", killedMNDsbish=" + killedMNDsbish +
                '}';
    }

    public void logSaveData(){
        Mon3trMod.logSomething(this.toString());
    }

    public void onSave(){
        meltdownCounterStart = MeltdownPatch.meltdownCounterStart;
        meltDownCounterMax = MeltdownPatch.meltDownCounterMax;
        crystalAmount = CrystalPatch.crystalAmount;
        simulationType = TheSimulation.currentType;
        simulationRelicChange = SimulationPatch.SimulationRelicChance;
        enterMNEmperor = SimulationPatch.EnterMNEmperor;
        enteredMNEmperor = SimulationPatch.EnteredMNEmperor;
        enterMNDsbish = SimulationPatch.EnterMNDsbish;
        enteredMNDsbish = SimulationPatch.EnteredMNDsbish;
        enterMNSmephi = SimulationPatch.EnterMNSmephi;
        enteredMNSmephi = SimulationPatch.EnteredMNSmephi;
        killedMNEmperor = SimulationPatch.KilledMNEmperor;
        killedMNDsbish = SimulationPatch.KilledMNDsbish;
        killedMNSmephi = SimulationPatch.KilledMNSmephi;

        //logSaveData();
    }

    public void onLoad(){
        MeltdownPatch.meltdownCounterStart = meltdownCounterStart;
        MeltdownPatch.meltDownCounterMax = meltDownCounterMax;
        CrystalPatch.crystalAmount = crystalAmount;
        TheSimulation.currentType = simulationType;
        SimulationPatch.SimulationRelicChance = simulationRelicChange;
        SimulationPatch.EnterMNEmperor = enterMNEmperor;
        SimulationPatch.EnteredMNEmperor = enteredMNEmperor;
        SimulationPatch.EnterMNDsbish = enterMNDsbish;
        SimulationPatch.EnteredMNDsbish = enteredMNDsbish;
        SimulationPatch.EnterMNSmephi = enterMNSmephi;
        SimulationPatch.EnteredMNSmephi = enteredMNSmephi;
        SimulationPatch.KilledMNEmperor = killedMNEmperor;
        SimulationPatch.KilledMNDsbish = killedMNDsbish;
        SimulationPatch.KilledMNSmephi = killedMNSmephi;
    }

    public void onDelete(){
        MeltdownPatch.meltdownCounterStart = 30;
        MeltdownPatch.meltDownCounterMax = 80;
        CrystalPatch.crystalAmount = 0;
        TheSimulation.currentType = 0;
        SimulationPatch.SimulationRelicChance = 0.1F;
        SimulationPatch.EnterMNEmperor = false;
        SimulationPatch.EnteredMNEmperor = false;
        SimulationPatch.EnterMNDsbish = false;
        SimulationPatch.EnteredMNDsbish = false;
        SimulationPatch.EnterMNSmephi = false;
        SimulationPatch.EnteredMNSmephi = false;

        //这两条不重置
        ScorePatch.theShadowEnable = killedMNEmperor;
        ScorePatch.theSophontheatrumEnable = killedMNEmperor;
        ScorePatch.theLightEnable = killedMNSmephi;

        SimulationPatch.KilledMNEmperor = false;
        SimulationPatch.KilledMNDsbish = false;
        SimulationPatch.KilledMNSmephi = false;
    }
}
