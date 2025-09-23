package Mon3tr.power;

import Mon3tr.action.ChangeMeltdownCounterAction;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.ui.CharSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

public class MeltdownPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:MeltdownPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justApplied = true;

    public MeltdownPower(AbstractCreature owner){
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/MeltdownPower_84.png";
        String path48 = "Mon3trResources/img/powers/MeltdownPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(MeltdownPatch.meltDownCounter>=20){
            this.flashWithoutSound();
            MeltdownPatch.energyToStrength(1);
            MeltdownPatch.drawToDexterity(1);
            addToBot(new DrawCardAction(1));
            addToBot(new GainEnergyAction(1));
            addToBot(new ChangeMeltdownCounterAction(false,20));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(justApplied){
            justApplied = false;
            return;
        }
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
