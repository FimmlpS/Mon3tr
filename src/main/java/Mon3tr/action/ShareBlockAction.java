package Mon3tr.action;

import Mon3tr.character.Mon3tr;
import Mon3tr.character.Prosts;
import Mon3tr.patch.ProstsPatch;
import Mon3tr.power.NeverTherePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class ShareBlockAction extends AbstractGameAction {
    public ShareBlockAction(int amt){
        this.amount = amt;
        this.duration = startDuration = Settings.ACTION_DUR_FASTER;
        actionType = ActionType.BLOCK;
    }

    @Override
    public void update() {
        if(startDuration == duration){
            int total = 0;
            AbstractPlayer p = AbstractDungeon.player;
            int playerCounter = 1;
            //重构体额外计作一个均分单位
            if(p instanceof Mon3tr){
                Prosts prosts = ProstsPatch.prosts;
                if(prosts!=null && !prosts.isDeadOrEscaped())
                    playerCounter++;
            }
            total+=playerCounter;
            ArrayList<AbstractMonster> ms = AbstractDungeon.getMonsters().monsters;
            for(AbstractMonster m:ms){
                if(!m.isDeadOrEscaped()){
                    total++;
                }
            }
            int single = this.amount/total;
            int remain = this.amount - single * total;
            if(p!=null){
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(p.hb.cX,p.hb.cY,AttackEffect.SHIELD));
                p.addBlock(playerCounter * single + remain);
            }
            for(AbstractMonster m:ms){
                if(!m.isDeadOrEscaped()){
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX,m.hb.cY,AttackEffect.SHIELD,true));
                    m.addBlock(single);
                }
            }
        }
        tickDuration();
    }
}
