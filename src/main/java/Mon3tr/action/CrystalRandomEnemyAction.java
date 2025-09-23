package Mon3tr.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class CrystalRandomEnemyAction extends AbstractGameAction {
    private DamageInfo info;
    public static ArrayList<AbstractCreature> monsterDamaged = new ArrayList<>();

    public CrystalRandomEnemyAction(DamageInfo info, AttackEffect effect,boolean cleanHistory) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        if(cleanHistory){
            monsterDamaged.clear();
        }
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            if(!monsterDamaged.contains(target))
                monsterDamaged.add(target);
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
        }

        this.isDone = true;
    }
}

