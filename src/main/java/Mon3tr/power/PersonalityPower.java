package Mon3tr.power;

import Mon3tr.card.status.PersonFalse;
import Mon3tr.card.status.PersonTrue;
import Mon3tr.patch.MeltdownPatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;


public class PersonalityPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:PersonalityPower";
    private static final PowerStrings powerStrings;

    public PersonalityPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.canGoNegative = true;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/PersonalityPower_84.png";
        String path48 = "Mon3trResources/img/powers/PersonalityPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 999;
    }

    private boolean hasMonster(){
        //return this.owner.hasPower(MonsterPower.POWER_ID);
        return false;
    }

    @Override
    public void updateDescription() {
        boolean hasMonster = this.hasMonster();
        int tmpAmount = this.amount;
        int percent = 0;
//        if(tmpAmount>5)
//            tmpAmount = 5;
//        else if(tmpAmount<-5)
//            tmpAmount = -5;
        if(tmpAmount>0)
            percent = (hasMonster?10:7) *tmpAmount;
        else
            percent = (hasMonster?15:10) *-tmpAmount;

        if(amount>0){
            description = powerStrings.DESCRIPTIONS[0] + percent + powerStrings.DESCRIPTIONS[hasMonster?4:2];
        }
        else {
            description = powerStrings.DESCRIPTIONS[1] + percent + powerStrings.DESCRIPTIONS[hasMonster?5:3];
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
//        if (this.amount >= 5) {
//            this.amount = 5;
//        }
//        if (this.amount <= -5) {
//            this.amount = -5;
//        }
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        float blv = 1F;
//        if(amount<0){
//            float increase = 0.1F * Math.min(-amount, 5);
//            blv += increase;
//        }
//        else {
        boolean hasMonster = this.hasMonster();
        if(amount>0){
            //float decrease = (hasMonster?0.1F:0.07F) * Math.min(amount, 5);
            float decrease = (hasMonster?0.1F:0.07F) * amount;
            blv -= decrease;
            if(blv<0)
                blv = 0;
        }
        if(type == DamageInfo.DamageType.NORMAL){
            return damage * blv;
        }
        return super.atDamageFinalReceive(damage, type);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float blv = 1F;
        boolean hasMonster = this.hasMonster();
        if(amount<0){
            //float increase = (hasMonster?0.15F:0.1F) * Math.min(-amount, 5);
            float increase = (hasMonster?0.15F:0.1F) * -amount;
            blv += increase;
        }
//        else {
//            float decrease = 0.1F * Math.min(amount, 5);
//            blv -= decrease;
//        }
        if(type == DamageInfo.DamageType.NORMAL){
            return damage * blv;
        }
        return super.atDamageFinalGive(damage, type);
    }

    public void setReverse(boolean bigger){
        if((bigger && amount<=0)||(!bigger && amount>0)){
            this.amount = -amount;
            this.flashWithoutSound();
            this.updateDescription();
            AbstractDungeon.onModifyPower();
        }
    }

    @Override
    public void atStartOfTurn() {
        if(this.amount>5){
            this.amount = 5;
            this.fontScale = 8.0F;
        }
        else if(this.amount<-5){
            this.amount = -5;
            this.fontScale = 8.0F;
        }
        updateDescription();
        AbstractDungeon.onModifyPower();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if(MeltdownPatch.isMeltdownTurn){
            ArrayList<AbstractCard> chosen = new ArrayList<>();
            chosen.add(new PersonTrue());
            chosen.add(new PersonFalse());
            addToBot(new ChooseOneAction(chosen));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}




