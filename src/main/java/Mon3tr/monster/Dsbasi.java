package Mon3tr.monster;

import Mon3tr.action.LongWaitAction;
import Mon3tr.patch.SimulationPatch;
import Mon3tr.relic.GeneSeed;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.SharpHidePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Dsbasi extends CustomMonster {
    public static final String ID = "mon3tr:Dsbasi";
    private static final MonsterStrings monsterStrings;
    private static final EnemyType TYPE = EnemyType.NORMAL;

    private static final String IDLE_NAME = "Idle";
    private static final String DIE_NAME = "Die";
    private static final String ATTACK_NAME = "Attack";

    int attack;

    //初始生命 216(240)

    //战前
    //获得2层锋利外甲 (A17:和1层仪式)

    //行动意图
    //攻击：造成9点伤害

    public Dsbasi(float x, float y) {
        super(monsterStrings.NAME,ID,240,0F,0F,240F,250F,null,x,y);
        this.type = EnemyType.NORMAL;
        this.loadAnimation("Mon3trResources/img/monsters/dsbasi/enemy_1433_dsbasi_2.atlas","Mon3trResources/img/monsters/dsbasi/enemy_1433_dsbasi_2.json",1.6F);
        this.flipHorizontal = true;

        if(AbstractDungeon.ascensionLevel >= 7){
            setHp(240);
        }
        else {
            setHp(216);
        }

        if(AbstractDungeon.ascensionLevel >= 2){
            attack = 11;
        }
        else {
            attack = 9;
        }


        if(AbstractDungeon.ascensionLevel>=17){

        }
        else {

        }
        this.powers.add(new SharpHidePower(this,2));
        if(AbstractDungeon.ascensionLevel>=17){
            RitualPower r = new RitualPower(this,1,false);
            ReflectionHacks.setPrivate(r,RitualPower.class,"skipFirst",false);
            this.powers.add(r);
        }

        this.state.setAnimation(0,IDLE_NAME,true);
        this.damage.add(new DamageInfo(this,attack, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
    }

    @Override
    protected void getMove(int i) {
        this.setMove((byte) 1,Intent.ATTACK,damage.get(0).base);
    }

    @Override
    public void takeTurn() {
        addToBot(new ChangeStateAction(this,"ATTACK"));
        addToBot(new DamageAction(AbstractDungeon.player,damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new RollMoveAction(this));
    }

    @Override
    public void changeState(String stateName) {
        if(stateName.equals("ATTACK")){
            this.state.setAnimation(0,ATTACK_NAME,false);
            this.state.addAnimation(0,IDLE_NAME,true,0F);
            this.addToTop(new LongWaitAction(0.5F));
        }
    }

    @Override
    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.state.setTimeScale(1F);
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

