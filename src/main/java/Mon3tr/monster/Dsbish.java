package Mon3tr.monster;

import Mon3tr.action.InstantIncreaseMaxHpAction;
import Mon3tr.card.status.CageOfPerson;
import Mon3tr.patch.SimulationPatch;
import Mon3tr.power.DetectPower;
import Mon3tr.relic.GeneSeed;
import Mon3tr.relic.LiveDust;
import basemod.abstracts.CustomMonster;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class Dsbish extends CustomMonster {
    public static final String ID = "mon3tr:Dsbish";
    private static final MonsterStrings monsterStrings;
    private static final EnemyType TYPE = EnemyType.BOSS;

    private static final String IDLE_NAME = "B_Idle";
    private static final String DIE_NAME = "B_Die";
    private static final String SKILL_NAME = "B_Skill_2";

    public static final String ICON = "Mon3trResources/img/monsters/dsbish/Dsbish_Icon.png";
    public static final String ICON_O = "Mon3trResources/img/monsters/dsbish/Dsbish_Icon_O.png";

    public AbstractMonster[] dsbasies = new AbstractMonster[2];
    public static float[] xList = {-590F,-298F};
    public static float[] yList = {10F,-10F};

    //初始生命1500(A9：1600)

    //战前
    //战斗开始时，获得400的坚不可摧；并获得引导演化。
    //引导演化：承受其他敌人受到伤害的80％，自身坚不可摧变为0时向手牌放入1张【人性的囚笼】，并令此效果失效至回合结束。

    //行动意图
    //召唤：召唤2只异光体掠食者 - 仅当异光体掠食者数量为0时必定触发（优先级最高）
    //哺育生机1：失去10％最大生命的生命，使所有其他敌人提升等量生命上限并获得2(A19：4)点力量 - 场上有异光体掠食者时必定触发
    //哺育生机2：哺育生机1行动开始时如果没有异光体掠食者，则改为令自身提升200点最大生命并获得200点格挡，然后
    //群体进化：使所有敌人获得2(A19:3)层锋利外甲和2(A19:3)层仪式 - 哺育生机1后必定触发

    //【人性的囚笼】状态牌 1C 抽1张牌。在手中时，使相邻1(升级后：2)张手牌不能被打出。

    int strength;
    int sharp;
    int heal = 200;
    int block = 200;
    boolean mustEvolute = false;
    AbstractCard cage;

    public Dsbish(float x, float y) {
        super(monsterStrings.NAME,ID,2000,0F,0F,320F,360F,null,x,y);
        this.type = EnemyType.BOSS;
        this.loadAnimation("Mon3trResources/img/monsters/dsbish/enemy_1556_dsbish.atlas","Mon3trResources/img/monsters/dsbish/enemy_1556_dsbish.json",1.6F);
        this.flipHorizontal = true;

        if(AbstractDungeon.ascensionLevel >= 9){
            setHp(1600);
        }
        else {
            setHp(1500);
        }

        cage = new CageOfPerson();

        if(AbstractDungeon.ascensionLevel>=19){
            strength = 3;
            sharp = 3;
            //cage.upgrade();
        }
        else {
            strength = 2;
            sharp = 2;
        }
        this.state.setAnimation(0,IDLE_NAME,true);
        this.damage.add(new DamageInfo(this,0, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvinciblePower(this, 400), 400));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this, new DetectPower(this)));
        super.usePreBattleAction();
    }

    @Override
    protected void getMove(int i) {
        int dsbasiCount = 0;
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                if(m instanceof Dsbasi)
                    dsbasiCount++;
            }
        }
        if(dsbasiCount == 0){
            this.setMove((byte) 1,Intent.UNKNOWN);
        }
        else {
            if(!mustEvolute){
                this.setMove((byte) 2, Intent.DEFEND_BUFF);
            }
            else {
                this.setMove((byte) 3, Intent.BUFF);
            }
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 1:
                for(int i =0;i<2;i++){
                    if(dsbasies[i] == null || dsbasies[i].isDeadOrEscaped()){
                        dsbasies[i] = new Dsbasi(xList[i],yList[i]);
                        addToBot(new SpawnMonsterAction(dsbasies[i],false));
                    }
                }
                break;
            case 2:
                addToBot(new ChangeStateAction(this,"SKILL"));
                int dsbasiCount = 0;
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                    if(!m.isDeadOrEscaped()){
                        if(m instanceof Dsbasi)
                            dsbasiCount++;
                    }
                }
                if(dsbasiCount == 0){
                    addToBot(new InstantIncreaseMaxHpAction(this,200,true));
                    addToBot(new GainBlockAction(this,200));
                }
                else {
                    mustEvolute = true;
                    int lose = (int)(0.1F * maxHealth);
                    addToBot(new LoseHPAction(this,this,lose));
                    for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                        if(m == this)
                            continue;
                        if(!m.isDeadOrEscaped()){
                            addToBot(new InstantIncreaseMaxHpAction(m,lose,true));
                            addToBot(new ApplyPowerAction(m,this,new StrengthPower(m,strength),strength));
                        }
                    }
                }
                break;
            case 3:
                mustEvolute = false;
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                    if(!m.isDeadOrEscaped()){
                        addToBot(new ApplyPowerAction(m,this,new SharpHidePower(this,sharp),sharp));
                        addToBot(new ApplyPowerAction(m,this,new RitualPower(m,strength,false),strength));
                    }
                }
                break;
        }
        addToBot(new RollMoveAction(this));
    }

    @Override
    public void changeState(String stateName) {
        if(stateName.equals("SKILL")){
            this.state.setAnimation(0,SKILL_NAME,false);
            this.state.addAnimation(0,IDLE_NAME,true,0F);
        }
    }

    @Override
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.state.setTimeScale(1F);
            (AbstractDungeon.getCurrRoom()).rewardAllowed = false;
            this.state.setAnimation(0, DIE_NAME, false);
            super.die();
            boolean theEnd = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped()) {
                    theEnd = false;
                    break;
                }
            }
            if (theEnd) {
                this.useFastShakeAnimation(5.0F);
                CardCrawlGame.screenShake.rumble(4.0F);
                this.onBossVictoryLogic();
                CardCrawlGame.stopClock = true;
                SimulationPatch.KilledMNDsbish = true;
                AbstractRelic r = AbstractDungeon.player.getRelic(GeneSeed.ID);
                if(r != null) {
                    r.counter = 1;
                }
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    }
}
