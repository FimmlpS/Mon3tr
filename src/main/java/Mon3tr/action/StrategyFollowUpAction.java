package Mon3tr.action;

import Mon3tr.patch.MonsterPatch;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class StrategyFollowUpAction extends AbstractGameAction {
    AbstractPlayer p;
    int[] multiDamage = {};
    boolean firstFrame = false;
    boolean special;

    public StrategyFollowUpAction(boolean special,int[] multiDamage, DamageInfo.DamageType type){
        this.multiDamage = multiDamage;
        this.special = special;
        this.damageType = type;
        this.firstFrame = true;
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if(firstFrame){
            firstFrame =false;
        }
        this.tickDuration();
        if(this.isDone) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.multiDamage);
            }
            Iterator<AbstractMonster> var1 = AbstractDungeon.getMonsters().monsters.iterator();
            ArrayList<AbstractGameAction> actions = new ArrayList<>();
            int index = 0;
            if (AbstractDungeon.getMonsters().monsters.size() > 0) {
                while (var1.hasNext()) {
                    AbstractMonster mm = var1.next();
                    if (!mm.isDeadOrEscaped()) {
                        if(special){
                            if(!MonsterPatch.attackedThisTurn(mm)){
                                actions.add(0,new DamageAction(mm,new DamageInfo(this.p, (int) (this.multiDamage[index]), this.damageType), OtherEnum.MON3TR_ATTACK_EFFECT));
                            }
                        }
                        else{
                            if(MonsterPatch.attackedThisTurn(mm)){
                                actions.add(0,new DamageAction(mm,new DamageInfo(this.p, (int) (this.multiDamage[index]), this.damageType),OtherEnum.MON3TR_ATTACK_EFFECT));
                            }
                        }

                        index++;
                    }
                }
            }
            for (AbstractGameAction action : actions) {
                addToTop(action);
            }
            this.addToTop(new VFXAction(p, new CleaveEffect(), 0.1F));
        }
    }
}
