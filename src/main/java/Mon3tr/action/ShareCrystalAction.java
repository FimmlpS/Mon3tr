package Mon3tr.action;

import Mon3tr.character.Mon3tr;
import Mon3tr.character.Prosts;
import Mon3tr.patch.ProstsPatch;
import Mon3tr.power.CrystalPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class ShareCrystalAction extends AbstractGameAction {
    public ShareCrystalAction(int amt){
        this.amount = amt;
        this.duration = startDuration = Settings.ACTION_DUR_FASTER;
        actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        if(startDuration == duration){
            int total = 0;
            AbstractPlayer p = AbstractDungeon.player;
            int playerCounter = 1;
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
                addToTop(new ApplyPowerAction(p,p,new CrystalPower(p,playerCounter * single + remain),playerCounter * single + remain));
            }
            for(AbstractMonster m:ms){
                if(!m.isDeadOrEscaped()){
                    addToTop(new ApplyPowerAction(m,p,new CrystalPower(m,single),single));
                }
            }
        }
        tickDuration();
    }
}

