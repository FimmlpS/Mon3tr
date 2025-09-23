package Mon3tr.action;

import Mon3tr.power.MeltdownPower;
import Mon3tr.power.OverloadPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class StructureAction extends AbstractGameAction {
    public StructureAction(boolean upgrade) {
        this.upgrade = upgrade;
    }

    boolean upgrade;

    @Override
    public void update() {
        if(!upgrade){
            AbstractPower p = AbstractDungeon.player.getPower(MetallicizePower.POWER_ID);
            if(p != null){
                p.atEndOfTurnPreEndTurnCards(true);
            }
        }
        else{
            try{
                AbstractDungeon.getCurrRoom().applyEndOfTurnPreCardPowers();
                for(AbstractPower p : AbstractDungeon.player.powers) {
                    if(!p.ID.equals(MeltdownPower.POWER_ID) && !p.ID.equals(OverloadPower.POWER_ID))
                        p.atEndOfTurn(true);
                }

            }
            catch (Exception e){
                //nothing happened
            }
        }
        this.isDone = true;
    }
}
