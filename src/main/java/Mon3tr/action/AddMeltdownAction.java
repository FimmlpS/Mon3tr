package Mon3tr.action;

import Mon3tr.effect.ShowCardAndAddToMeltdownEffect;
import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddMeltdownAction extends AbstractGameAction {
    public AddMeltdownAction(AbstractCard card){
        actionType = ActionType.CARD_MANIPULATION;
        amount = 1;
        this.c = card;
    }

    AbstractCard c;


    @Override
    public void update() {
        if(MeltdownPatch.meltdownGroup.group.size()==0){
            AbstractDungeon.effectList.add(new ShowCardAndAddToMeltdownEffect(c.makeCopy(),(float) Settings.WIDTH / 2.0F - (400F*Settings.scale + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
            addToTop(new WaitAction(0.8F));
        }

        this.isDone = true;
    }
}
