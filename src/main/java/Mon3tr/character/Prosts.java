package Mon3tr.character;

import Mon3tr.patch.ProstsPatch;
import Mon3tr.power.CellActivationPower;
import Mon3tr.power.DetectPower;
import Mon3tr.relic.ModuleRecover;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.*;


public class Prosts extends AbstractMonster {
    public static final String ID = "mon3tr:Prosts";
    private static final MonsterStrings monsterStrings;
    private static final String NAME;
    private static final String[] MOVES;
    private static final String[] DIALOG;

    private static final int ACT_HP = 10;

    public boolean canShowTips = false;

    public Prosts(){
        super(NAME,ID, getStartHp(), 0.0F,0.0F,160F,160F,null,0F,0F);
        this.refreshSkin();
    }

    private static int getStartHp(){
        int hp = ACT_HP * AbstractDungeon.actNum;
        if(hp<=0){
            hp = ACT_HP;
        }
        return hp;
    }

    public void refreshSkin(){
        int type = 0;
        String pathName = "Mon3trResources/img/characters/";
        String fileName = "";
        if(type==0){
            fileName = "prosts/token_10050_monstr_prosts";
        }
        this.atlas = new TextureAtlas(Gdx.files.internal(pathName+fileName+".atlas"));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale / 1.75F);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(pathName+fileName+".json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);

        this.state.setAnimation(0,"Idle",true);
    }

    public void spawn(){
        init();
        useUniversalPreBattleAction();
        showHealthBar();
        createIntent();
        movePosition(AbstractDungeon.player.drawX+230.0F*Settings.scale,AbstractDungeon.player.drawY-40F*Settings.scale);

        this.state.setAnimation(0,"Start",false);
        this.state.addAnimation(0,"Idle",true,0F);
    }

    public void absorb(){
        hideHealthBar();
        flashIntent();
        this.state.setAnimation(0,"Skill_3_Begin",false);
        this.state.addAnimation(0,"Skill_3",true,0F);
    }

    public void addTips(){
        this.tips.add(new PowerTip(DIALOG[0],DIALOG[1]));
        this.tips.add(new PowerTip(DIALOG[2],DIALOG[3]));
    }

    private void movePosition(float x, float y){
        drawX = x;
        drawY = y;
        animX = 0.0F;
        animY = 0.0F;
        hb.move(drawX+hb_x,drawY+hb_y+hb_h/2.0F);
        healthHb.move(hb.cX, hb.cY - hb_h / 2.0F - healthHb.height / 2.0F);
        refreshIntentHbLocation();
    }

    @Override
    protected void getMove(int i) {
        this.setMove((byte) 0,Intent.DEFEND);
    }

    @Override
    public void takeTurn() {

    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if(lastDamageTaken>0){
//            AbstractPower cell = AbstractDungeon.player.getPower(CellActivationPower.POWER_ID);
//            if(cell!=null){
//                cell.wasHPLost(info,lastDamageTaken);
//            }
            AbstractRelic recover = AbstractDungeon.player.getRelic(ModuleRecover.ID);
            if(recover!=null){
                recover.wasHPLost(lastDamageTaken);
            }
        }
        if(this.currentHealth<=0&&!this.halfDead){
            this.state.setAnimation(0,"Die",false);
        }
    }

    @Override
    public void heal(int healAmount, boolean showEffect) {
        super.heal(healAmount, showEffect);
        if (!this.isDying&&healAmount>0&&showEffect) {
            AbstractDungeon.topPanel.panelHealEffect();
            AbstractDungeon.effectsQueue.add(new HealEffect(this.hb.cX - this.animX, this.hb.cY, healAmount));
        }
    }

    @Override
    public void updateAnimations() {
        super.updateAnimations();
        updatePowers();
    }

    @Override
    public void update() {
        this.canShowTips = false;
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.DEATH) {
            hb.update();
            intentHb.update();
            healthHb.update();
        }
        super.update();
        if(isDead){
            ProstsPatch.prosts = null;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(!AbstractDungeon.isScreenUp&&!AbstractDungeon.player.isDraggingCard && canShowTips)
            this.renderTip(sb);
        super.render(sb);
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        this.tips.clear();
        this.addTips();

        for (AbstractPower p : this.powers) {
            if (p.region48 != null) {
                this.tips.add(new PowerTip(p.name, p.description, p.region48));
            } else {
                this.tips.add(new PowerTip(p.name, p.description, p.img));
            }
        }

        if (!this.tips.isEmpty()) {
            if (this.hb.cX + this.hb.width / 2.0F < TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + TIP_OFFSET_R_X, this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            } else {
                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0F + TIP_OFFSET_L_X, this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            }
        }
    }

    @SpireOverride
    protected void brokeBlock() {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            r.onBlockBroken(this);
        }
        AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
    }

    @Override
    protected int decrementBlock(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.HP_LOSS && this.currentBlock > 0) {
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
            if (damageAmount > this.currentBlock) {
                damageAmount -= this.currentBlock;
                if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(this.hb.cX, this.hb.cY + this.hb.height / 2.0F, String.valueOf(this.currentBlock)));
                }

                this.loseBlock();
                this.brokeBlock();
            } else if (damageAmount == this.currentBlock) {
                damageAmount = 0;
                this.loseBlock();
                this.brokeBlock();
                AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, DIALOG[2]));
            } else {
                this.loseBlock(damageAmount);

                for(int i = 0; i < 18; ++i) {
                    AbstractDungeon.effectList.add(new BlockImpactLineEffect(this.hb.cX, this.hb.cY));
                }

                if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(this.hb.cX, this.hb.cY + this.hb.height / 2.0F, String.valueOf(damageAmount)));
                }

                damageAmount = 0;
            }
        }

        return damageAmount;
    }

    @SpireOverride
    protected boolean applyBackAttack(){
        return false;
    }

    //小Patch一下
    @SpirePatch(clz = AbstractMonster.class,method = "damage")
    public static class DetectPowerPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractMonster _inst, DamageInfo info) {
            if (!AbstractDungeon.getMonsters().monsters.contains(_inst)) {
                return;
            }
            if (ProstsPatch.prosts == null || ProstsPatch.prosts.currentHealth<=0)
                return;
            if (!_inst.isDeadOrEscaped()) {
                info.output += 1;
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
