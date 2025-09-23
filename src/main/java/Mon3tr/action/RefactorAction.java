package Mon3tr.action;

import Mon3tr.card.attack.UnbreakableRefactoring;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class RefactorAction extends AbstractGameAction {
    public RefactorAction(UnbreakableRefactoring unbreakableRefactoring){
        this.c = unbreakableRefactoring;
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if(startDuration==duration){
            AbstractPlayer p = AbstractDungeon.player;
            if(c==null){
                this.isDone = true;
                return;
            }
            if(!MeltdownPatch.isMeltdownTurn()&&MeltdownPatch.meltDownCounter<c.technicalNeeded){
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, StringHelper.actions.TEXT[10] + c.technicalNeeded + StringHelper.actions.TEXT[11], true));
                this.isDone = true;
                return;
            }
            if(c.inBattleAndInDiscard(true)){
                c.baseDamage += c.magicNumber;
                c.damage = c.baseDamage;
                if(!MeltdownPatch.isMeltdownTurn())
                    MeltdownPatch.decreaseMeltdownCount(c.technicalNeeded);
                p.hand.addToHand(c);
                c.unhover();
                c.setAngle(0F,true);
                c.lighten(false);
                c.drawScale = 0.12F;
                c.targetDrawScale = 0.75F;
                c.applyPowers();
                p.discardPile.removeCard(c);
                p.onCardDrawOrDiscard();
            }
            else{
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, StringHelper.actions.TEXT[12], true));
                this.isDone = true;
                return;
            }
        }
        tickDuration();
    }

    UnbreakableRefactoring c;
}
