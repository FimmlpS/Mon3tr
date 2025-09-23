package Mon3tr.helper;

import Mon3tr.action.DrawToHandAction;
import Mon3tr.action.TriggerDrawnAction;
import Mon3tr.card.AbstractExpressCard;
import Mon3tr.character.Mon3tr;
import Mon3tr.patch.ExpressPatch;
import Mon3tr.patch.Mon3trTag;
import Mon3tr.ui.StarGroup;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Mon3trHelper {
    public static final ArrayList<AbstractExpressCard> expressCards;

    public static ArrayList<AbstractCard> getExpressFromTranscription(ArrayList<AbstractCard> transcriptCards){
        ArrayList<AbstractCard> returnCards = new ArrayList<>();
        ArrayList<AbstractExpressCard> tmpList = new ArrayList<>(expressCards);
        //只计算一次优先级
        for(AbstractExpressCard c : tmpList){
            c.lastPrivilegeCalculated = c.getExpressPrivilege(transcriptCards);
        }
        //总优先级排序
        Comparator<AbstractExpressCard> privilegeCom = new Comparator<AbstractExpressCard>() {
            @Override
            public int compare(AbstractExpressCard o1, AbstractExpressCard o2) {
                return Integer.compare(o2.lastPrivilegeCalculated,o1.lastPrivilegeCalculated);
            }
        };
        tmpList.sort(privilegeCom);
        int totalReturn = transcriptCards.size();
        if(totalReturn>=tmpList.size()){
            return new ArrayList<>(tmpList);
        }
        //过随机数
        AbstractDungeon.cardRandomRng.random();
        //同优先级打乱
        Random duplicated = new Random(Settings.seed, AbstractDungeon.cardRandomRng.counter);
        int start = 0;
        while (start<tmpList.size()){
            int currentPrivilege = tmpList.get(start).lastPrivilegeCalculated;
            int end = start;
            while (end < tmpList.size() && tmpList.get(end).lastPrivilegeCalculated == currentPrivilege){
                end++;
            }
            //打乱
            if(end - start > 1){
                Collections.shuffle(tmpList.subList(start,end),duplicated.random);
            }
            start = end+1;
        }
        //返回
        for(int i =0;i<totalReturn;i++){
            returnCards.add(tmpList.get(i).makeCopy());
        }
        ExpressPatch.returnedCardsThisCombat.addAll(returnCards);
        return returnCards;
    }

    public static ArrayList<AbstractRelic> getRebuildRelics(AbstractRelic relic,int size){
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        AbstractRelic.RelicTier tier = relic.tier;
        if(tier == AbstractRelic.RelicTier.STARTER)
            tier = AbstractRelic.RelicTier.COMMON;
        else if(tier != AbstractRelic.RelicTier.COMMON && tier != AbstractRelic.RelicTier.UNCOMMON && tier != AbstractRelic.RelicTier.RARE)
            tier = AbstractRelic.RelicTier.UNCOMMON;
        for(int i =0;i<size;i++){
            AbstractRelic.RelicTier tmpRelicTier = tier;
            boolean upgraded = AbstractDungeon.relicRng.randomBoolean(0.1F);
            if(upgraded && tier== AbstractRelic.RelicTier.COMMON)
                tmpRelicTier = AbstractRelic.RelicTier.UNCOMMON;
            else if(upgraded && tier== AbstractRelic.RelicTier.UNCOMMON)
                tmpRelicTier = AbstractRelic.RelicTier.RARE;
            relics.add(AbstractDungeon.returnRandomScreenlessRelic(tmpRelicTier));
        }
        return relics;
    }

    public static ArrayList<AbstractCard> getRebuildCards(AbstractCard card,int size){
        //update remove sameID 2025/6/7
        ArrayList<AbstractCard> common = new ArrayList<>(AbstractDungeon.commonCardPool.group);
        common.removeIf(c -> c.cardID.equals(card.cardID));
        Collections.shuffle(common,AbstractDungeon.cardRandomRng.random);
        ArrayList<AbstractCard> uncommon = new ArrayList<>(AbstractDungeon.uncommonCardPool.group);
        uncommon.removeIf(c -> c.cardID.equals(card.cardID));
        Collections.shuffle(uncommon,AbstractDungeon.cardRandomRng.random);
        ArrayList<AbstractCard> rare = new ArrayList<>(AbstractDungeon.rareCardPool.group);
        rare.removeIf(c -> c.cardID.equals(card.cardID));
        Collections.shuffle(rare,AbstractDungeon.cardRandomRng.random);

        ArrayList<AbstractCard> returnCards = new ArrayList<>();
        AbstractCard.CardRarity rarity = card.rarity;
        if(rarity == AbstractCard.CardRarity.BASIC)
            rarity = AbstractCard.CardRarity.COMMON;
        else if(rarity != AbstractCard.CardRarity.COMMON && rarity!= AbstractCard.CardRarity.UNCOMMON && rarity != AbstractCard.CardRarity.RARE)
            rarity = AbstractCard.CardRarity.UNCOMMON;
        for(int i =0;i<size;i++){
            AbstractCard.CardRarity tmpRarity = rarity;
            boolean upgraded = AbstractDungeon.relicRng.randomBoolean(0.1F);
            if(upgraded && rarity== AbstractCard.CardRarity.COMMON)
                tmpRarity = AbstractCard.CardRarity.UNCOMMON;
            else if(upgraded && rarity== AbstractCard.CardRarity.UNCOMMON)
                tmpRarity = AbstractCard.CardRarity.RARE;
            if(tmpRarity == AbstractCard.CardRarity.COMMON){
                returnCards.add(common.remove(0));
            }
            else if(tmpRarity == AbstractCard.CardRarity.UNCOMMON){
                returnCards.add(uncommon.remove(0));
            }
            else {
                returnCards.add(rare.remove(0));
            }
        }
        return returnCards;
    }



    //过去与未来的终点
    public static void onEndingMod(){
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            if(c.hasTag(Mon3trTag.ENDING_MON3TR)){
                AbstractDungeon.actionManager.addToBottom(new DrawToHandAction(c));
                AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(c));
                AbstractDungeon.actionManager.addToBottom(new TriggerDrawnAction(c));
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.hasTag(Mon3trTag.ENDING_MON3TR)){
                AbstractDungeon.actionManager.addToBottom(new DrawToHandAction(c));
                AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(c));
                AbstractDungeon.actionManager.addToBottom(new TriggerDrawnAction(c));
            }
        }
    }

    static {
        expressCards = RegisterHelper.getExpressCardsToAdd();
    }
}
