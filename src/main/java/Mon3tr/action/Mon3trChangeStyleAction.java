package Mon3tr.action;

import Mon3tr.character.Mon3tr;
import Mon3tr.patch.MeltdownPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Mon3trChangeStyleAction extends AbstractGameAction {
    public Mon3trChangeStyleAction(int state){
        AbstractPlayer p = AbstractDungeon.player;
        if(p instanceof Mon3tr){
            this.mon3tr = (Mon3tr) p;
        }
        actionType = ActionType.WAIT;
        if(mon3tr!=null){
            int from = mon3tr.currentState;
            this.state = state;
            if(from == state){
                this.startDuration = duration = 0.01F;
            }
            else if(state ==3){
                startDuration = duration = 1.7F - 1.0F;
            }
            else if(state ==2){
                startDuration = duration = 0.27F;
            }
            else if(from==3){
                startDuration = duration = 1.4F - 0.9F;
            }
            else if(from==2){
                startDuration = duration = 0.21F;
            }
        }
        else{
            this.startDuration = duration = 0.01F;
        }
    }

    Mon3tr mon3tr;
    int state;

    @Override
    public void update() {
        if(startDuration==duration){
            if(mon3tr!=null){
                int from = mon3tr.currentState;
                int target = state;
                if(from == target){
                    //NOTHING HAPPENED - NO, BUG HAPPENED!!
                }
                else if(target==3){
                    mon3tr.enterMeltdownAnimation();
                    //此时已经进入回合
                    MeltdownPatch.startMeltdown(MeltdownPatch.MeltdownType.Meltdown);
                }
                else if(target==2){
                    mon3tr.enterOverloadAnimation();
                    //此时已经进入回合
                    MeltdownPatch.startMeltdown(MeltdownPatch.MeltdownType.Overload);
                }
                else if(from==3){
                    mon3tr.outerMeltdownAnimation();
                    //此时已经退出回合
                    MeltdownPatch.endMeltdown();
                }
                else if(from==2){
                    mon3tr.outerOverloadAnimation();
                    //此时已经退出回合
                    MeltdownPatch.endMeltdown();
                }
            }

        }

        tickDuration();
    }
}
