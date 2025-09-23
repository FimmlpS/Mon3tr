package Mon3tr.action;

import Mon3tr.power.CrystalPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrystalAllEnemyAction extends AbstractGameAction {
    public CrystalAllEnemyAction(int damage) {
        this.startDuration = this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = damage;
    }

    @Override
    public void update() {
        this.tickDuration();
        if (isDone) {
            if (CrystalRandomEnemyAction.monsterDamaged.size() > 0) {
                int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
                for (int i = 0; i < temp; ++i) {
                    AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                    if (CrystalRandomEnemyAction.monsterDamaged.contains(m) && !m.isDeadOrEscaped()) {
                        addToTop(new ApplyPowerAction(m,AbstractDungeon.player,new CrystalPower(m,amount),amount));
                    }
                }
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(0.1F));
                }
            }
        }
    }
}
