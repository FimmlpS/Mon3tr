package Mon3tr.power;

import Mon3tr.action.DustReleaseAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Iterator;

public class DustReleasePower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:DustReleasePower";
    private static final PowerStrings powerStrings;

    private boolean stronger = false;
    private ArrayList<Float> triggerLines = new ArrayList<>();

    public DustReleasePower(AbstractCreature owner, boolean stronger) {
        this.name = stronger?powerStrings.DESCRIPTIONS[2]:powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.stronger = stronger;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/DustReleasePower_84.png";
        String path48 = "Mon3trResources/img/powers/DustReleasePower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
        this.priority = 1;

        if(this.owner.maxHealth>0){
            float blv = (float) owner.currentHealth /(float)owner.maxHealth;
            if(blv>0.8F)
                this.triggerLines.add(0.8F);
            if(blv>0.6F)
                this.triggerLines.add(0.6F);
            if(blv>0.4F)
                this.triggerLines.add(0.4F);
            if(blv>0.2F)
                this.triggerLines.add(0.2F);
            if(blv>0)
                this.triggerLines.add(0.0F);
        }
    }

    @Override
    public void onSpecificTrigger() {
        float blv = (float) owner.currentHealth /(float)owner.maxHealth;
        Iterator<Float> it = triggerLines.iterator();
        while (it.hasNext()) {
            float f = it.next();
            if(blv<=f){
                this.flash();
                addToTop(new GainBlockAction(this.owner,this.owner,AbstractDungeon.ascensionLevel>=19?30:20));
                addToTop(new ApplyPowerAction(this.owner,this.owner,new ToxicPower(this.owner,AbstractDungeon.ascensionLevel>=19?3:2,stronger,false)));
                it.remove();
            }
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        addToTop(new DustReleaseAction(this));
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        boolean asc = AbstractDungeon.ascensionLevel>=19;
        if(asc){
            this.description = stronger?powerStrings.DESCRIPTIONS[4]:powerStrings.DESCRIPTIONS[1];
        }
        else {
            this.description = stronger?powerStrings.DESCRIPTIONS[3]:powerStrings.DESCRIPTIONS[0];
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}

