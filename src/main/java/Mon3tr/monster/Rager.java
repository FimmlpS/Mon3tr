package Mon3tr.monster;

import Mon3tr.action.LongWaitAction;
import Mon3tr.power.LiveBloodPower;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

public class Rager extends CustomMonster {
    public static final String ID = "mon3tr:Rager";
    private static final MonsterStrings monsterStrings;
    private static final EnemyType TYPE = EnemyType.NORMAL;

    private static final String IDLE_NAME = "Idle";
    private static final String DIE_NAME = "Die";
    private static final String ATTACK_NAME = "Attack";

    int attack;

    //初始生命 480(500)

    //战前
    //获得终死者之血

    //行动意图
    //攻击：造成20点伤害

    public Rager(float x, float y) {
        super(monsterStrings.NAME,ID,500,0F,0F,240F,250F,null,x,y);
        this.type = EnemyType.NORMAL;
        this.loadAnimation("Mon3trResources/img/monsters/rager/enemy_1062_rager_2.atlas","Mon3trResources/img/monsters/rager/enemy_1062_rager_2.json",1.6F);
        this.flipHorizontal = true;

        if(AbstractDungeon.ascensionLevel >= 7){
            setHp(500);
        }
        else {
            setHp(480);
        }

        if(AbstractDungeon.ascensionLevel >= 2){
            attack = 20;
        }
        else {
            attack = 18;
        }

        this.powers.add(new LiveBloodPower(this,true));

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
        addToBot(new DamageAction(AbstractDungeon.player,damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
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
        this.state.setTimeScale(1F);
        this.state.setAnimation(0, DIE_NAME, false);
        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    }
}


