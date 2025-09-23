package Mon3tr.power;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StartRegeneratePower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:StartRegeneratePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public StartRegeneratePower(AbstractMonster owner, int regenAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = regenAmt;
        this.updateDescription();
        this.loadRegion("regen");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        if (!this.owner.halfDead && !this.owner.isDying && !this.owner.isDead) {
            this.addToBot(new HealAction(this.owner, this.owner, this.amount));
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Regenerate");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

