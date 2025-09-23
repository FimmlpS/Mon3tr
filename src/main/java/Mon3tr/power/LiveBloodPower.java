package Mon3tr.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class LiveBloodPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:LiveBloodPower";
    private static final PowerStrings powerStrings;

    boolean special;
    boolean stronger;

    public LiveBloodPower(AbstractCreature owner, boolean special){
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.special = special;
        this.stronger = AbstractDungeon.ascensionLevel >= 18;
        this.loadRegion("rupture");
        this.updateDescription();
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.type == DamageInfo.DamageType.NORMAL){
            int lost = (int)((special?0.15F:0.1F) * (float) this.owner.maxHealth);
            this.flash();
            addToBot(new LoseHPAction(this.owner,this.owner,lost));
            if(special){
                int block = (int)(0.05F * (float) this.owner.maxHealth);
                addToBot(new GainBlockAction(this.owner, block));
            }
            if(stronger){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,2)));
            }
        }
    }

    @Override
    public void updateDescription() {
        if(special){
            description = powerStrings.DESCRIPTIONS[stronger?3:2];
        }
        else {
            description = powerStrings.DESCRIPTIONS[stronger?1:0];
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}




