package Mon3tr.action;

import Mon3tr.card.skill.CoverMeInDebris;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class DebrisAction extends AbstractGameAction {

    public DebrisAction(CoverMeInDebris coverMeInDebris) {
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
        this.c = coverMeInDebris;
    }

    CoverMeInDebris c;

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
            if(c.inBattleAndInExhaust(true)){
                //寻找原本的c
                CoverMeInDebris fore = null;
                for(AbstractCard c1 : p.exhaustPile.group){
                    if(c1 instanceof CoverMeInDebris){
                        if(((CoverMeInDebris) c1).debrisID == c.debrisID){
                            fore = (CoverMeInDebris) c1;
                            break;
                        }
                    }
                }
                if(fore==null){
                    this.isDone = true;
                    return;
                }
                if(!MeltdownPatch.isMeltdownTurn())
                    MeltdownPatch.decreaseMeltdownCount(c.technicalNeeded);
                fore.unfadeOut();
                fore.drawScale = 0.1F;
                p.hand.addToHand(fore);
                if (AbstractDungeon.player.hasPower("Corruption") && fore.type == AbstractCard.CardType.SKILL) {
                    fore.setCostForTurn(-9);
                }
                p.exhaustPile.removeCard(fore);
                fore.unhover();
                fore.fadingOut = false;
                fore.current_x = fore.target_x = (float) CardGroup.DISCARD_PILE_X;
                fore.target_y = 0.0F;
            }
            else{
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, StringHelper.actions.TEXT[12], true));
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}

