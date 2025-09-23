package Mon3tr.monster;

import Mon3tr.action.CreateIntentAction;
import Mon3tr.action.LongWaitAction;
import Mon3tr.action.StartRegenAction;
import Mon3tr.patch.SimulationPatch;
import Mon3tr.power.*;
import Mon3tr.relic.LiveDust;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Iterator;

public class Smephi extends AbstractMonster {
    public static final String ID = "mon3tr:Smephi";
    private static final MonsterStrings monsterStrings;
    private static final AbstractMonster.EnemyType TYPE = AbstractMonster.EnemyType.BOSS;

    private static final String IDLE_NAME = "Idle";
    private static final String DIE_NAME = "Die";
    private static final String ATTACK_NAME = "Attack";
    private static final String SKILL_1_NAME = "Skill";
    private static final String SKILL_2_NAME = "Skill_2";
    private static final String REBORN_BEGIN_NAME = "Idle_2_begin";
    private static final String REBORN_LOOP_NAME = "Idle_2_loop";
    private static final String REBORN_END_NAME = "Idle_2_end";

    public static final String ICON = "Mon3trResources/img/monsters/smephi/Smephi_Icon.png";
    public static final String ICON_O = "Mon3trResources/img/monsters/smephi/Smephi_Icon_O.png";

    public static float[] xList = {-590F,-298F};
    public static float[] yList = {10F,-10F};
    public ArrayList<AbstractMonster> monsterSummoned = new ArrayList<>();

    //战前
    //战斗开始时，获得[粉尘释放]
    //[粉尘释放(+)] 每失去20％生命值，立即获得20点格挡，并获得独立的持续3回合的[毒性(+)]（类似于炸弹-相互独立）
    //[毒性(+)] 每当对方获得格挡时，使其失去2点格挡，并对其造成1(2)点伤害

    //行动意图
    //攻击-一阶段：造成24点伤害 -与粉尘释放交替
    //攻击-二阶段：造成30点伤害 -与粉尘释放交替
    //粉尘释放-一阶段：给予1层虚弱，获得独立的持续2回合的[毒性] 首个回合不会消失    -执行2次后与攻击交替
    //粉尘释放-二阶段：给予1层虚弱，获得独立的持续2回合的[毒性(+)] 首个回合不会消失 -执行2次后与攻击交替
    //技能：获得12点格挡，治疗其他敌人24点生命 - 仅当场上有其他敌人时 以50％概率释放
    //复活-1：生命值首次≤0时，失去[粉尘释放]，将生命恢复至25％，获得3层[无实体]和相当于最大生命25％的[再生]，召唤[狂暴宿主组长]和[狂暴宿主掷骨手]，然后将意图锁定为“？”
    //复活-2：若自身[无实体]仅剩1层 或者 场上不存在[狂暴宿主组长]和[狂暴宿主掷骨手]中任何一个，清除自身[无实体]和[再生]，获得[粉尘释放+]

    int attack01 = 24;
    int attack02 = 30;
    int block = 12;
    int heal = 24;
    int weak = 1;
    boolean slept = false;
    int firstTurn = 0;
    float attackWait = 1.4f;

    public Smephi(float x,float y ){
        super(monsterStrings.NAME,ID,600,0F,0F,320F,360F,null,x,y);
        this.type = AbstractMonster.EnemyType.BOSS;
        this.loadAnimation("Mon3trResources/img/monsters/smephi/enemy_1514_smephi.atlas","Mon3trResources/img/monsters/smephi/enemy_1514_smephi.json",1.6F);
        this.flipHorizontal = true;

        if(AbstractDungeon.ascensionLevel >= 9){
            setHp(640);
        }
        else {
            setHp(600);
        }

        if(AbstractDungeon.ascensionLevel>=4){
            attack01 = 30;
            attack02 = 36;
        }
        else  {
            attack01 = 24;
            attack02 = 30;
        }

        if(AbstractDungeon.ascensionLevel>=19){
            block = 15;
            heal = 30;
        }
        else {
            block = 12;
            heal = 24;
        }
        firstTurn = 0;
        this.state.setAnimation(0,IDLE_NAME,true);
        this.damage.add(new DamageInfo(this,this.attack01, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this,this.attack02, DamageInfo.DamageType.NORMAL));
    }

    public boolean summonedAlive(){
        for(AbstractMonster m : monsterSummoned){
            if(!m.isDeadOrEscaped()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().cannotLose = true;
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");

        addToBot(new ApplyPowerAction(this,this,new DustReleasePower(this,false)));
        addToBot(new ApplyPowerAction(this,this,new SleepPower(this)));

        super.usePreBattleAction();
    }

    @Override
    protected void getMove(int i) {
        if (firstTurn!=0 && summonedAlive() && i < 50) {
            this.setMove((byte) 3, Intent.DEFEND_BUFF);
        } else {
            if(firstTurn == 0){
                this.setMove((byte) 2,Intent.STRONG_DEBUFF);
            }
            else {
                this.setMove((byte) 1,Intent.ATTACK,damage.get(slept?1:0).base);
            }
        }
        firstTurn++;
        if(firstTurn==3)
            firstTurn = 0;
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 1:
                addToBot(new ChangeStateAction(this,"ATTACK"));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(slept?1:0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                break;
            case 2:
                addToBot(new ChangeStateAction(this,"SKILL_2"));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new WeakPower(AbstractDungeon.player,1,true),1));
                addToBot(new ApplyPowerAction(this,this,new ToxicPower(this,2,slept,true)));
                break;
            case 3:
                addToBot(new ChangeStateAction(this,"SKILL_1"));
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                    if(!m.isDeadOrEscaped()){
                        addToBot(new HealAction(m,this,monsterSummoned.contains(m)?heal*3:heal));
                    }
                }
                addToBot(new GainBlockAction(this,this,block));
                break;
            case 4:
                AbstractPower intan = this.getPower(IntangiblePower.POWER_ID);
                if(intan == null || intan.amount == 1 || !summonedAlive()){
                    addToBot(new ChangeStateAction(this,"REBIRTH"));
                    addToBot(new RollMoveAction(this));
                }
                else {
                    addToBot(new SetMoveAction(this, (byte)4, Intent.UNKNOWN));
                }
                break;
        }
        if(nextMove != (byte) 4)
            addToBot(new RollMoveAction(this));
    }

    @Override
    public void changeState(String stateName) {
        switch (stateName){
            case "REBIRTH":
                this.firstTurn = 0;
                this.state.setAnimation(0,REBORN_END_NAME,false);
                this.state.addAnimation(0,IDLE_NAME,true,0F);
                addToTop(new ApplyPowerAction(this,this,new DustReleasePower(this,true)));
                addToTop(new RemoveSpecificPowerAction(this,this,StartRegeneratePower.POWER_ID));
                addToTop(new RemoveSpecificPowerAction(this,this,IntangiblePower.POWER_ID));
                addToTop(new RemoveSpecificPowerAction(this,this,SleepPower.POWER_ID));
                for(AbstractMonster m :monsterSummoned){
                    addToTop(new RemoveSpecificPowerAction(m,m,StartRegeneratePower.POWER_ID));
                }
                break;
            case "ATTACK":
                this.state.setAnimation(0,ATTACK_NAME,false);
                this.state.addAnimation(0,IDLE_NAME,true,0F);
                addToTop(new LongWaitAction(attackWait));
                break;
            case "SKILL_1":
                this.state.setAnimation(0,SKILL_1_NAME,false);
                this.state.addAnimation(0,IDLE_NAME,true,0F);
                break;
            case "SKILL_2":
                this.state.setAnimation(0,SKILL_2_NAME,false);
                this.state.addAnimation(0,IDLE_NAME,true,0F);
                break;
        }
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.currentHealth <= 0 && !slept) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                slept = true;
                this.currentHealth = 0;
                this.heal((int)(0.25F*this.maxHealth));
                this.state.setAnimation(0,REBORN_BEGIN_NAME,false);
                this.state.addAnimation(0,REBORN_LOOP_NAME,true,0F);

                CardCrawlGame.music.fadeOutTempBGM();
                AbstractDungeon.getCurrRoom().cannotLose = false;
                AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");

                Iterator<AbstractPower> s = this.powers.iterator();
                while(s.hasNext()) {
                    AbstractPower p = (AbstractPower)s.next();
                    if (p.type == AbstractPower.PowerType.DEBUFF || p instanceof IntangiblePower || p instanceof DustReleasePower) {
                        s.remove();
                    }
                }

                IntangiblePower intangiblePower = new IntangiblePower(this,3);
                ReflectionHacks.setPrivate(intangiblePower,IntangiblePower.class,"justApplied",false);
                this.powers.add(intangiblePower);
                this.powers.add(new StartRegeneratePower(this,(int)(0.25F*this.maxHealth)));

                try {
                    for(AbstractPower p : this.powers){
                        p.flash();
                    }
                }
                catch (Exception e) {
                    //nothing happened
                }

                //debuff
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new WeakPower(AbstractDungeon.player,99,true),99));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new VulnerablePower(AbstractDungeon.player,99,true),99));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new FrailPower(AbstractDungeon.player,99,true),99));

                //summon
                AbstractMonster m = new Rager(xList[0],yList[0]);
                monsterSummoned.add(m);
                addToBot(new SpawnMonsterAction(m,true));
                addToBot(new CreateIntentAction(m));
                addToBot(new StartRegenAction(m,0.25F));
                AbstractMonster m2 = new Rageth(xList[1],yList[1]);
                monsterSummoned.add(m2);
                addToBot(new SpawnMonsterAction(m2,true));
                addToBot(new CreateIntentAction(m2));
                addToBot(new StartRegenAction(m2,0.25F));
                this.setMove((byte)4, Intent.UNKNOWN);
                this.createIntent();
                addToBot(new SetMoveAction(this, (byte)4, Intent.UNKNOWN));
                this.applyPowers();
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
            SimulationPatch.KilledMNSmephi = true;
            AbstractRelic r = AbstractDungeon.player.getRelic(LiveDust.ID);
            if(r != null) {
                r.counter = 1;
            }

            for(AbstractMonster m : monsterSummoned) {
                if (!m.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new InstantKillAction(m));
                }
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    }
}
