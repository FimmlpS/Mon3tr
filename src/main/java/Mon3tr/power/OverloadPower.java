package Mon3tr.power;

import Mon3tr.patch.MeltdownPatch;
import Mon3tr.ui.CharSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

import javax.smartcardio.ATR;

public class OverloadPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:OverloadPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justApplied = true;

    public OverloadPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/OverloadPower_84.png";
        String path48 = "Mon3trResources/img/powers/OverloadPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
    }

    public void onInitialApplication() {
        AbstractDungeon.player.gameHandSize -= amount;
        MeltdownPatch.decreaseMeltdownCount(40);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if(CharSelectScreen.hardModeEnable(2)){
            DrawReductionPower d = new DrawReductionPower(AbstractDungeon.player,1);
            ReflectionHacks.setPrivate(d,DrawReductionPower.class,"justApplied",false);
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,d,1));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        //回合数重置，防止重复开启
        justApplied = true;
        AbstractDungeon.player.gameHandSize -= stackAmount;
        super.stackPower(stackAmount);
    }

    @Override
    public void reducePower(int reduceAmount) {
        AbstractDungeon.player.gameHandSize += reduceAmount;
        super.reducePower(reduceAmount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(justApplied){
            justApplied = false;
            return;
        }
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
    }

    public void onRemove() {
        AbstractDungeon.player.gameHandSize += amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

