package Mon3tr.power;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CrystalOverloadPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:CrystalOverloadPower";
    private static final PowerStrings powerStrings;

    public CrystalOverloadPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("end_turn_death");
//        String path128 = "Mon3trResources/img/powers/MonsterPower_84.png";
//        String path48 = "Mon3trResources/img/powers/MonsterPower_32.png";
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 50;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+ this.amount+ powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfRound() {
        this.flash();
        addToBot(new LoseHPAction(this.owner,this.owner,amount, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}




