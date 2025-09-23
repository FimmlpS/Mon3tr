package Mon3tr.power;

import Mon3tr.card.status.CageOfPerson;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;

public class DetectPower extends AbstractPower {
    public static final String POWER_ID = "mon3tr:DetectPower";
    private static final PowerStrings powerStrings;
    public static boolean someoneOwned = false;

    public boolean enable = true;

    public DetectPower(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        String path128 = "Mon3trResources/img/powers/DetectPower_84.png";
        String path48 = "Mon3trResources/img/powers/DetectPower_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
        this.priority = 99;
        enable = true;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(enable){
            AbstractPower p = this.owner.getPower(InvinciblePower.POWER_ID);
            if(p != null){
                if(p.amount==0){
                    enable = false;
                    this.flash();
                    addToBot(new MakeTempCardInHandAction(new CageOfPerson(),1));
                }
            }
        }
        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        this.enable = true;
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        someoneOwned = true;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    //小Patch一下
    @SpirePatch(clz = AbstractMonster.class,method = "damage")
    public static class DetectPowerPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractMonster _inst, DamageInfo info) {
            if (!AbstractDungeon.getMonsters().monsters.contains(_inst)) {
                return;
            }
            if (!_inst.isDeadOrEscaped() && !_inst.hasPower(DetectPower.POWER_ID)) {
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDeadOrEscaped() && m.hasPower(DetectPower.POWER_ID)) {
                        if (((DetectPower) m.getPower(DetectPower.POWER_ID)).enable) {
                            int shared = (int) (info.output * 0.8F);
                            info.output -= shared;
                            AbstractPower p = m.getPower(DetectPower.POWER_ID);
                            p.flash();
                            AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(null, shared, DamageInfo.DamageType.THORNS)));
                            return;
                        }
                    }
                }
            }
        }
    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    }
}






