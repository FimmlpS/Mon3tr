package Mon3tr.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ToDustPower extends AbstractPower {

    public static final String POWER_ID = "mon3tr:ToDustPower";
    private static final PowerStrings powerStrings;
    private static int dustIdOffset;
    private int damageTimes;
    private int damage;

    public ToDustPower(AbstractCreature owner, int damage, int turns) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID + dustIdOffset;
        dustIdOffset++;
        this.owner = owner;
        this.damage = damage;
        this.amount = turns;
        this.damageTimes = turns;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/ToDustPower_84.png";
        String path48 = "Mon3trResources/img/powers/ToDustPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            if(amount==1){
                for (int i =0;i<damageTimes;i++) {
                    this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = String.format(powerStrings.DESCRIPTIONS[1], this.damage, damageTimes);
        } else {
            this.description = String.format(powerStrings.DESCRIPTIONS[0], this.amount, this.damage, damageTimes);
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}







