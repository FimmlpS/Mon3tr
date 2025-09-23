package Mon3tr.action;

import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HealNumberEffect;

public class IncreaseTechnicalAction extends AbstractGameAction {
    public IncreaseTechnicalAction(int amt){
        this(amt,false);
    }

    public IncreaseTechnicalAction(int amt,boolean showEffect){
        this.amount = amt;
        this.show = showEffect;
    }

    boolean show;

    @Override
    public void update() {
//        if(MeltdownPatch.isMeltdownTurn()){
//            this.isDone = true;
//            return;
//        }
        MeltdownPatch.increaseMeltdownCounter(this.amount);
        if(show){
            AbstractDungeon.effectList.add(new HealNumberEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY,this.amount));
        }
        this.isDone = true;
    }
}

