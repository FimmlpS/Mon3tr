package Mon3tr.action;

import Mon3tr.card.attack.StarWeb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class StarWebAction extends AbstractGameAction {
    public StarWebAction(StarWeb sw){
        this.wb = sw;
        this.duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    StarWeb wb;

    @Override
    public void update() {
        if(startDuration==duration){
            if(AbstractDungeon.player.drawPile.size()==0){
                this.isDone = true;
            }
            ArrayList<AbstractCard> starWebs = new ArrayList<>();
            CardGroup drawGroup = AbstractDungeon.player.drawPile;
            for(AbstractCard c: drawGroup.group){
                if(c instanceof StarWeb){
                    starWebs.add(c);
                }
            }
            AbstractCard cardPlayed = null;
            if(starWebs.size()==0){
                //addToTop(new MakeTempCardInDrawPileAction(wb,1,true,true));
                this.isDone = true;
                return;
            }
            else if(starWebs.size()==1){
                cardPlayed = starWebs.get(0);
            }
            else{
                cardPlayed = starWebs.get(AbstractDungeon.cardRandomRng.random(starWebs.size()-1));
            }
            if(cardPlayed!=null){
                AbstractDungeon.player.drawPile.group.remove(cardPlayed);
                AbstractDungeon.getCurrRoom().souls.remove(cardPlayed);
                AbstractDungeon.player.limbo.group.remove(cardPlayed);
                //add exhaust
                //cardPlayed.exhaustOnUseOnce = true;
                cardPlayed.current_y = -200.0F * Settings.scale;
                cardPlayed.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                cardPlayed.target_y = (float)Settings.HEIGHT / 2.0F;
                cardPlayed.targetAngle = 0.0F;
                cardPlayed.lighten(false);
                cardPlayed.drawScale = 0.12F;
                cardPlayed.targetDrawScale = 0.75F;
                cardPlayed.applyPowers();
                this.addToTop(new NewQueueCardAction(cardPlayed, true, false, true));
                this.addToTop(new UnlimboAction(cardPlayed));
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
        }

        this.tickDuration();
    }
}
