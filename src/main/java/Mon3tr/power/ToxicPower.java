package Mon3tr.power;

import Mon3tr.action.FastLoseBlockAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ToxicPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:ToxicPower";
    private static final PowerStrings powerStrings;

    private boolean stronger = false;
    private boolean justApplied = false;
    private static int offset = 0;

    int damageIncreased = 0;

    public ToxicPower(AbstractCreature owner,int amount, boolean stronger,boolean justApplied) {
        this.name = stronger?powerStrings.DESCRIPTIONS[1]:powerStrings.NAME;
        this.ID = POWER_ID + offset;
        offset++;
        this.owner = owner;
        this.amount = amount;
        this.stronger = stronger;
        this.justApplied = justApplied;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/ToxicPower_84.png";
        String path48 = "Mon3trResources/img/powers/ToxicPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
        this.priority = 99;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL){
            return damage+damageIncreased;
        }
        return damage;
    }

    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        this.flashWithoutSound();
        damageIncreased++;
        boolean added = true;
        for(AbstractGameAction action: AbstractDungeon.actionManager.actions){
            if(action instanceof FastLoseBlockAction){
                action.amount += stronger?2:1;
                added = false;
                break;
            }
        }
        if(added)
            addToTop(new FastLoseBlockAction(AbstractDungeon.player,this.owner,stronger?2:1));
        if(this.owner instanceof AbstractMonster){
            ((AbstractMonster)this.owner).applyPowers();
        }
        return super.onPlayerGainedBlock(blockAmount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        damageIncreased = 0;
//        if(this.owner instanceof AbstractMonster){
//            ((AbstractMonster)this.owner).applyPowers();
//        }
    }

    @Override
    public void atEndOfRound() {
        if(justApplied){
            justApplied = false;
        }
        else {
            if(this.amount>1){
                addToBot(new ReducePowerAction(this.owner,this.owner,this,1));
            }
            else {
                addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = stronger?powerStrings.DESCRIPTIONS[2]:powerStrings.DESCRIPTIONS[0];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}
