package Mon3tr.action;

import Mon3tr.helper.StringHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ExistInNameOnlyAction extends AbstractGameAction {
    public static int chasedTimes = 1;

    public ExistInNameOnlyAction(int lastDamage, AbstractMonster m, DamageInfo info, AttackEffect effect){
        this.attackEffect = effect;
        this.info = info;
        this.m = m;
        this.amount = lastDamage;
        if(this.amount == -1){
            chasedTimes = 1;
        }
        else{
            chasedTimes++;
        }
    }

    @Override
    public void update() {
        if(m!=null&&!m.isDeadOrEscaped()){
            //第一次传入的amount为-1以100％触发第二次伤害
            if(this.amount == m.lastDamageTaken){
                this.isDone = true;
                return;
            }
            //此时的lastDamage记录值为第二次伤害处理前所记录
            addToBot(new TextAboveCreatureAction(m, StringHelper.actions.TEXT[0] + chasedTimes + StringHelper.actions.TEXT[1]));
            addToBot(new DamageAction(m,info,attackEffect));
            addToBot(new ExistInNameOnlyAction(m.lastDamageTaken,m,info,attackEffect));
        }
        this.isDone = true;
    }

    AbstractMonster m;
    DamageInfo info;
}
