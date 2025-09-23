package Mon3tr.card;

import Mon3tr.helper.StringHelper;
import Mon3tr.patch.ColorEnum;
import Mon3tr.patch.ExpressPatch;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.ArrayList;

public abstract class AbstractExpressCard extends CustomCard {

    public AbstractExpressCard(String id, String name, int cost, String rawDescription, CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardIMGPatch(id, type), cost, rawDescription, type, ColorEnum.Mon3tr_Express_COLOR, rarity, target);

    }

    //暂时其他卡图
    public AbstractExpressCard(String id, String name,String imgPath, int cost, String rawDescription, CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, new RegionName(imgPath), cost, rawDescription, type, ColorEnum.Mon3tr_Express_COLOR, rarity, target);
    }

    public int lastPrivilegeCalculated = -1;

    protected int getExtraBase(ArrayList<AbstractCard> MALIncapacitated){
        int base = -1;
        int total = 0;
        for(AbstractCard c:MALIncapacitated){
            if(c instanceof AbstractMon3trCard){
                total += ((AbstractMon3trCard) c).iterationCounter;
            }
        }
        base += total/5;
        return base;
    }

    //优先级
    public int getExpressPrivilege(ArrayList<AbstractCard> transcriptCards){
        return 0;
    }

    public int returnSuppressedPrivilege(int base){
        for(AbstractCard c : ExpressPatch.returnedCardsThisCombat){
            if(c.cardID.equals(this.cardID))
                base -= getSuppressionCount();
        }
        return base;
    }

    //抑制 2025/6/7新增 每印出过一张同名牌，优先级会对应下降
    public int getSuppressionCount(){
        return 1;
    }

    public void expressWhenPlayCard(AbstractCard c,AbstractCreature target){

    }

    //表达牌相关trigger
    public void expressAppliedToTarget(AbstractCreature target){

    }

    public void expressAbandonedFromTarget(AbstractCreature target){

    }

    public void expressAtStartOfTurn(AbstractCreature target){

    }

    //此处info.owner为表达牌拥有者
    public void expressWhenAttack(int damage, AbstractCreature attackedCreature, DamageInfo info){

    }

    //此处target为表达牌拥有者
    public void expressWhenAttacked(int damage, AbstractCreature target, DamageInfo info){

    }

    public float gainBlockOrHp(float amount, AbstractCreature target, boolean isBlock){
        return amount;
    }
}
