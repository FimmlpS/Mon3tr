package Mon3tr.monster;

import Mon3tr.action.LongWaitAction;
import Mon3tr.action.ResetMoveAction;
import Mon3tr.card.status.BlackSnow;
import Mon3tr.effect.RedEyeParticle;
import Mon3tr.orbs.CountryOrb;
import Mon3tr.patch.SimulationPatch;
import Mon3tr.power.AltCountryPower;
import Mon3tr.power.CountryPower;
import Mon3tr.power.MoreCollapsePower;
import Mon3tr.power.ScarePower;
import Mon3tr.relic.BadOrganization;
import Mon3tr.relic.LiveDust;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.spine.Bone;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Empgrd extends CustomMonster {
    public static final String ID = "mon3tr:Empgrd";
    private static final MonsterStrings monsterStrings;
    private static final EnemyType TYPE = EnemyType.BOSS;
    private static final String ATTACK_1_NAME = "Attack01";
    private static final String ATTACK_2_NAME = "Attack02";

    private static final String IDLE_NAME = "Idle";
    private static final String DIE_NAME = "Die";
    private static final String SKILL_1_NAME = "Skill_01";
    private static final String SKILL_2_BEGIN_NAME = "Skill_02_Begin";
    private static final String SKILL_2_LOOP_NAME = "Skill_02_Loop";
    private static final String SKILL_2_END_NAME = "Skill_02_End";

    //没有美术😭
    public static final String ICON = "Mon3trResources/img/monsters/empgrd/Empgrd_Icon.png";
    public static final String ICON_O = "Mon3trResources/img/monsters/empgrd/Empgrd_Icon_O.png";

    //战前
    //战斗开始时，获得1层国度，并向弃牌堆放入2张黑雪

    //行动意图
    //远程攻击：造成6点伤害2次(A19：额外1次；复活后：额外1次)。 - 35％概率
    //近战攻击：造成14点伤害。给予1层易伤(A19：和脆弱；复活后：和虚弱)。 - 35％概率
    //技能：造成20点伤害。给予1层 恐惧 。(复活后：向抽牌堆洗入一张黑雪；A19：改为黑雪+) - 每行动6次进行1次
    //强化：获得2点力量，获得20点格挡(A19：再获得2层再生)。 - 30％概率
    //复活（参考觉醒者）-复活机制：复活后，清空DEBUFF，每有一张未被消耗的黑雪，复活时提升20点最大生命。国度放入的黑雪会升级。
    //复活后获得逆向坍缩。

    int attack01;
    int attack01times;
    int attack02;
    int skill01;
    int move_times;
    int skill_times;
    int strength;
    int reborn;
    int block;
    int recover_max_health;

    float attack01wait = 0.35f;
    float attack02wait = 0.7f;
    float skill01wait = 0.95f;

    private Bone eye = null;
    private float fireTimer = 0F;
    private boolean animateParticles = false;
    ArrayList<CountryOrb> orbs = new ArrayList<>();

    public Empgrd(float x, float y) {
        super(monsterStrings.NAME,ID,500,0F,0F,320F,360F,null,x,y);
        this.type = EnemyType.BOSS;
        this.loadAnimation("Mon3trResources/img/monsters/empgrd/enemy_1520_empgrd.atlas","Mon3trResources/img/monsters/empgrd/enemy_1520_empgrd.json",1.6F);
        this.flipHorizontal = true;

        if(AbstractDungeon.ascensionLevel >= 9){
            setHp(500);
        }
        else {
            setHp(480);
        }

        if(AbstractDungeon.ascensionLevel>=4){
            attack01 = 7;
            attack02 = 16;
            skill01 = 24;
        }
        else  {
            attack01 = 6;
            attack02 = 14;
            skill01 = 20;
        }

        if(AbstractDungeon.ascensionLevel>=19){
            attack01times = 2;
            block = 25;
            strength = 3;
            reborn = 3;
            recover_max_health = 30;
        }
        else {
            attack01times = 2;
            block = 20;
            strength = 2;
            reborn = 2;
            recover_max_health = 20;
        }

        this.eye = skeleton.findBone("head_1");
        setFastMode();
        this.state.setAnimation(0,IDLE_NAME,true);
        float xSize = 80F;
        float ySize = 80F;
        for(int i =0;i<3;i++){
            if(i==0)
                this.orbs.add(new CountryOrb(0,xSize,0));
            else{
                this.orbs.add(new CountryOrb(-xSize*i,ySize,i));
                this.orbs.add(new CountryOrb(xSize*i,ySize,i));
            }
        }
        for(CountryOrb orb : orbs){
            orb.activate(hb.cX+animX,hb.cY-hb.height/2F+animY);
        }

        //初始值 0
        move_times = 0;
        skill_times = 0;
        this.damage.add(new DamageInfo(this,this.attack01, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this,this.attack02, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this,this.skill01, DamageInfo.DamageType.NORMAL));
    }

    public void setFastMode(){
        if(false){
            state.setTimeScale(2);
            this.attack01wait = 0.175F;
            this.attack02wait = 0.35F;
            this.skill01wait = 0.475F;
        }
        else{
            state.setTimeScale(1);
            this.attack01wait = 0.35F;
            this.attack02wait = 0.7F;
            this.skill01wait = 0.95f;
        }
    }

    @Override
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        AbstractDungeon.getCurrRoom().cannotLose = true;
        addToBot(new ApplyPowerAction(this,this,new CountryPower(this,1),1));
        addToBot(new ApplyPowerAction(this,this, new MoreCollapsePower(this)));

        addToBot(new MakeTempCardInDiscardAction(new BlackSnow(),2));
        super.usePreBattleAction();
    }

    @Override
    protected void getMove(int i) {
        move_times++;
        if(move_times<6*(skill_times+1)){
            if(i<35&&!lastMove((byte) 1)){
                setMove((byte) 1,Intent.ATTACK,damage.get(0).base,attack01times,true);
            }
            else if(i<70&&!lastMove((byte) 2)){
                setMove((byte)2,Intent.ATTACK_DEBUFF,damage.get(1).base);
            }
            else {
                setMove((byte) 3,Intent.DEFEND_BUFF);
            }
        }
        else {
            setMove((byte) 4,Intent.ATTACK_DEBUFF,damage.get(2).base);
        }
    }

    public void takeTurn(boolean triggerByBlackSnow){
        ArrayList<AbstractGameAction> actionsToAdd = new ArrayList<>();
        switch (nextMove) {
            case 1:
                actionsToAdd.add(new ChangeStateAction(this, "ATTACK_1"));
                for (int i = 0; i < attack01times; i++) {
                    actionsToAdd.add(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
                break;
            case 2:
                actionsToAdd.add(new ChangeStateAction(this,"ATTACK_2"));
                actionsToAdd.add(new DamageAction(AbstractDungeon.player,damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                actionsToAdd.add(new ApplyPowerAction(AbstractDungeon.player,this,new VulnerablePower(AbstractDungeon.player,1,true),1));
                if(AbstractDungeon.ascensionLevel>=19){
                    actionsToAdd.add(new ApplyPowerAction(AbstractDungeon.player,this,new FrailPower(AbstractDungeon.player,1,true),1));
                }
                if(!AbstractDungeon.getCurrRoom().cannotLose){
                    actionsToAdd.add(new ApplyPowerAction(AbstractDungeon.player,this,new WeakPower(AbstractDungeon.player,1,true),1));
                }
                break;
            case 3:
                actionsToAdd.add(new GainBlockAction(this,block));
                actionsToAdd.add(new ApplyPowerAction(this,this,new StrengthPower(this,strength),strength));
                if(AbstractDungeon.ascensionLevel>=19){
                    actionsToAdd.add(new ApplyPowerAction(this,this,new RegenerateMonsterPower(this,reborn),reborn));
                }
                break;
            case 4:
                skill_times++;
                actionsToAdd.add(new ChangeStateAction(this,"SKILL"));
                actionsToAdd.add(new DamageAction(AbstractDungeon.player,damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                actionsToAdd.add(new ApplyPowerAction(AbstractDungeon.player,this,new ScarePower(AbstractDungeon.player,1),1));
                if(!AbstractDungeon.getCurrRoom().cannotLose){
                    AbstractCard c = new BlackSnow();
                    if(AbstractDungeon.ascensionLevel>=19){
                        c.upgrade();
                    }
                    actionsToAdd.add(new MakeTempCardInDrawPileAction(c,1,true,true));
                }
                break;
            case 5:
                actionsToAdd.add(new ChangeStateAction(this,"REBIRTH"));
                break;
        }
        if(!hasPower(CountryPower.POWER_ID)){
            actionsToAdd.add(new ApplyPowerAction(this,this,new CountryPower(this,1)));
        }
        actionsToAdd.add(new RollMoveAction(this));
        if(triggerByBlackSnow){
            actionsToAdd.add(new ResetMoveAction(this));
            actionsToAdd.add(new LongWaitAction(0.8F));
            Collections.reverse(actionsToAdd);
            for(AbstractGameAction action : actionsToAdd){
                addToTop(action);
            }
        }
        else {
            for(AbstractGameAction action : actionsToAdd){
                addToBot(action);
            }
        }
    }

    @Override
    public void takeTurn() {
        takeTurn(false);
    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "REBIRTH":
                setFastMode();
                int count = 0;
                for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if(c.cardID.equals(BlackSnow.ID))
                        count++;
                }
                for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if(c.cardID.equals(BlackSnow.ID))
                        count++;
                }
                for(AbstractCard c : AbstractDungeon.player.limbo.group) {
                    if(c.cardID.equals(BlackSnow.ID))
                        count++;
                }
                for(AbstractCard c : AbstractDungeon.player.hand.group) {
                    if(c.cardID.equals(BlackSnow.ID))
                        count++;
                }
                maxHealth += count * recover_max_health;
                this.state.setAnimation(0,SKILL_2_END_NAME,false);
                this.state.addAnimation(0,IDLE_NAME,true,0F);
                this.halfDead = false;
                //animate
                this.animateParticles = true;
                orbs.clear();
                float xSize = 80F;
                float ySize = 80F;
                for(int i =0;i<4;i++){
                    if(i==0)
                        this.orbs.add(new CountryOrb(0,xSize,0));
                    else{
                        this.orbs.add(new CountryOrb(-xSize*i,ySize,i));
                        this.orbs.add(new CountryOrb(xSize*i,ySize,i));
                    }
                }
                for(CountryOrb orb : orbs){
                    orb.activate(hb.cX+animX,hb.cY-hb.height/2F+animY);
                }
                attack01times++;
                addToBot(new HealAction(this,this,maxHealth));
                addToBot(new ApplyPowerAction(this,this,new AltCountryPower(this,50),20));
                addToBot(new CanLoseAction());
                break;
            case "ATTACK_1":
                setFastMode();
                this.state.setAnimation(0,ATTACK_1_NAME,false);
                this.state.addAnimation(0,IDLE_NAME,true,0F);
                addToTop(new LongWaitAction(attack01wait));
                break;
            case "ATTACK_2":
                setFastMode();
                this.state.setAnimation(0,ATTACK_2_NAME,false);
                this.state.addAnimation(0,IDLE_NAME,true,0F);
                addToTop(new LongWaitAction(attack02wait));
                break;
            case "SKILL":
                setFastMode();
                this.state.setAnimation(0,SKILL_1_NAME,false);
                this.state.addAnimation(0,IDLE_NAME,true,0F);
                addToTop(new LongWaitAction(skill01wait));
                break;
        }
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.currentHealth <= 0 && !this.halfDead) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                this.halfDead = true;
                this.state.setAnimation(0,SKILL_2_BEGIN_NAME,false);
                this.state.addAnimation(0,SKILL_2_LOOP_NAME,true,0F);
                addToBot(new MakeTempCardInDrawPileAction(new BlackSnow(),2,true,true));
            }

            for(AbstractPower p : this.powers) {
                p.onDeath();
            }

            for(AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }

            this.addToTop(new ClearCardQueueAction());
            Iterator<AbstractPower> s = this.powers.iterator();

            while(s.hasNext()) {
                AbstractPower p = (AbstractPower)s.next();
                if (p.type == AbstractPower.PowerType.DEBUFF || p.ID.equals(MoreCollapsePower.POWER_ID)) {
                    s.remove();
                }
            }

            this.setMove((byte)5, Intent.UNKNOWN);
            this.createIntent();
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)5, Intent.UNKNOWN));
            this.applyPowers();
            if (GameActionManager.turn <= 1) {
                UnlockTracker.unlockAchievement("YOU_ARE_NOTHING");
            }
        }
    }

    //小Patch一下
    @SpirePatch(clz = AbstractMonster.class,method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 93)
        public static void Insert(AbstractMonster _inst, DamageInfo info) {
            if (_inst instanceof Empgrd) {
                if (_inst.currentHealth <= 0) {
                    AbstractPower alt = _inst.getPower(AltCountryPower.POWER_ID);
                    if (alt != null) {
                        ArrayList<AbstractCard> blackSnows = new ArrayList<>();
                        blackSnows.addAll(AbstractDungeon.player.drawPile.group);
                        blackSnows.addAll(AbstractDungeon.player.discardPile.group);
                        blackSnows.addAll(AbstractDungeon.player.hand.group);
                        blackSnows.removeIf(c -> !c.cardID.equals(BlackSnow.ID));
                        if (!blackSnows.isEmpty()) {
                            alt.onSpecificTrigger();
                            AbstractCard c = blackSnows.get(0);
                            if(AbstractDungeon.player.drawPile.contains(c))
                                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.player.drawPile));
                            else if (AbstractDungeon.player.discardPile.contains(c))
                                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.player.discardPile));
                            else if (AbstractDungeon.player.hand.contains(c))
                                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if(!isDead && animateParticles){
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if(this.fireTimer < 0F){
                this.fireTimer = 0.1F;
                AbstractDungeon.effectList.add(new RedEyeParticle(this.skeleton.getX() + this.eye.getWorldX(), this.skeleton.getY() + this.eye.getWorldY()-6F*Settings.scale));
            }
        }
        if(!this.isDead){
            for(CountryOrb orb:orbs){
                orb.update(hb.cX + animX,hb.cY - hb.height/2F + animY);
            }
        }
    }

    @Override
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            this.state.setTimeScale(1F);
            (AbstractDungeon.getCurrRoom()).rewardAllowed = false;
            this.state.setAnimation(0, DIE_NAME, false);
            this.onBossVictoryLogic();
            CardCrawlGame.stopClock = true;
            super.die();
            SimulationPatch.KilledMNEmperor = true;
            AbstractRelic r = AbstractDungeon.player.getRelic(BadOrganization.ID);
            if(r != null) {
                r.counter = 1;
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    }
}
