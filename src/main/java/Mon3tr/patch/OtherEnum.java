package Mon3tr.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class OtherEnum {
    @SpireEnum
    public static CardGroup.CardGroupType HAND_MELTDOWN;

    @SpireEnum
    public static RewardItem.RewardType MON3TR_CRYSTAL;

    @SpireEnum
    public static AbstractGameAction.AttackEffect MON3TR_ATTACK_EFFECT;

    @SpireEnum
    public static AbstractGameAction.AttackEffect MON3TR_ATTACK_HEAVY_EFFECT;
}
