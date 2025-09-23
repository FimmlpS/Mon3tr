package Mon3tr.patch;

import Mon3tr.action.TakeTurnAction;
import Mon3tr.character.Mon3tr;
import Mon3tr.character.Prosts;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import java.util.ArrayList;
import java.util.Iterator;

public class ProstsPatch {
    public static Prosts prosts = null;

    public static void dying(){
        if(prosts!=null&&!prosts.isDeadOrEscaped()){
            prosts.die(false);
        }
    }

    public static void clear(){
        prosts = null;
    }

    public static void render(SpriteBatch sb){
        if(prosts!=null){
            prosts.render(sb);
        }
    }

    public static void update(){
        if(prosts!=null){
            prosts.update();
            if(prosts!=null&&prosts.hb!=null&&prosts.hb.hovered){
                prosts.canShowTips = true;
            }
        }
    }

    public static void loseBlock(){
        if(prosts!=null){
            prosts.loseBlock();
        }
    }

    public static void startOfTurn(){
        if (prosts!=null){
            prosts.applyStartOfTurnPowers();
        }
    }

    public static void endOfTurn(){
        if(prosts!=null){
            applyEndTurnPower(prosts);
            AbstractDungeon.actionManager.addToBottom(new TakeTurnAction(prosts));
        }
    }

    public static void spawnProsts(){
        if(AbstractDungeon.player instanceof Mon3tr){
            prosts = new Prosts();
            prosts.spawn();
        }
    }

    public static void absorbProsts(boolean healSelf){
        if(prosts!=null && !prosts.isDeadOrEscaped() && prosts.currentHealth>0){
            int block = prosts.currentHealth;
            if(!healSelf){
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,block));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new NextTurnBlockPower(AbstractDungeon.player,block),block));
                AbstractDungeon.player.heal(5,true);
            }
            prosts.absorb();
        }
    }

    private static void applyEndTurnPower(Prosts m){
        if (!m.isDying && !m.isEscaping) {
            m.applyEndOfTurnTriggers();
        }
        for (AbstractPower p : m.powers) {
            p.atEndOfRound();
        }
    }

    //目标选择修改
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "renderHoverReticle"
    )
    public static class ReticlePatch{
        public ReticlePatch(){}

        public static void Postfix(AbstractPlayer _inst, SpriteBatch sb){
            if(prosts!=null){
                prosts.reticleRendered = false;
            }
        }
    }

    @SpirePatch(clz = AbstractMonster.class,method = "die",paramtypez = {boolean.class})
    public static class DieRenderPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster _inst,boolean triggerRelics){
            if(!(_inst instanceof Prosts)){
                if(!AbstractDungeon.getCurrRoom().cannotLose&&AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                    dying();
            }
        }
    }

    @SpirePatch(clz = AbstractMonster.class,method = "escape")
    public static class DieRender2Patch{
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster _inst){
            if(!(_inst instanceof Prosts)){
                if(!AbstractDungeon.getCurrRoom().cannotLose&&AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                    dying();
            }
        }
    }

    //配置applyPowers
    @SpirePatch(clz = AbstractDungeon.class,method = "onModifyPower")
    public static class ApplyPowerPatch{
        @SpirePostfixPatch
        public static void Postfix(){
            if(prosts!=null){
                if(!prosts.isDeadOrEscaped()){
                    prosts.applyPowers();
                }
            }
        }
    }
}
