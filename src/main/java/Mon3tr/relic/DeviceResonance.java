package Mon3tr.relic;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class DeviceResonance extends CustomRelic {

    public static final String ID = "mon3tr:DeviceResonance";
    ArrayList<AbstractMonster> attacked = new ArrayList<>();

    public DeviceResonance(){
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(StringHelper.moduleRelicID,true)),RelicTier.COMMON,LandingSound.SOLID);
        this.counter = -1;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        attacked.clear();
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
        attacked.clear();
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
        attacked.clear();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(target instanceof AbstractMonster && info.type == DamageInfo.DamageType.NORMAL){
            if(!attacked.contains(target)){
                attacked.add((AbstractMonster) target);
                this.counter = attacked.size();
            }
        }
    }

    @Override
    public void onPlayerEndTurn() {
        if(counter>0)
            this.flash();
        for(int i =0;i<this.counter;i++){
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,DamageInfo.createDamageMatrix(3,true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}





