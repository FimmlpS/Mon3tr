package Mon3tr.power;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LockPower extends AbstractPower {

    public static final String POWER_ID = "mon3tr:LockPower";
    private static final PowerStrings powerStrings;

    public LockPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("lockon");
//        String path128 = "Mon3trResources/img/powers/MonsterPower_84.png";
//        String path48 = "Mon3trResources/img/powers/MonsterPower_32.png";
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.DEBUFF;
        this.priority = 99;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.name!=POWER_ID && info.type== DamageInfo.DamageType.NORMAL){
            this.flashWithoutSound();
            DamageInfo extraInfo = new DamageInfo(AbstractDungeon.player,amount, DamageInfo.DamageType.NORMAL);
            extraInfo.name = POWER_ID;
            addToTop(new DamageAction(this.owner,extraInfo));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+ this.amount + powerStrings.DESCRIPTIONS[1] ;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}





