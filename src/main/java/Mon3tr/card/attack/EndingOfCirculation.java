package Mon3tr.card.attack;

import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.status.SpaceMiracle;
import Mon3tr.patch.OtherEnum;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Iterator;

public class EndingOfCirculation extends AbstractMon3trCard {
    public static final String ID = "mon3tr:EndingOfCirculation";
    private static final CardStrings cardStrings;

    public EndingOfCirculation(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        this.baseDamage = 7;
        this.damage = 7;
        this.baseBlock = 7;
        this.block = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction());
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_HEAVY_EFFECT));
        addToBot(new GainBlockAction(p,block));
    }

    @Override
    protected void applyPowersToBlock() {
        this.isBlockModified = false;
        float tmp = (float)this.baseBlock;

        Iterator<AbstractPower> var2;
        AbstractPower p;
        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); ) {
            p = var2.next();
            tmp = p.modifyBlock(tmp, this);
            //another
            tmp = p.modifyBlock(tmp, this);
        }

        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); ) {
            p = var2.next();
            tmp = p.modifyBlockLast(tmp);
            tmp = p.modifyBlockLast(tmp);
        }

        if (this.baseBlock != MathUtils.floor(tmp)) {
            this.isBlockModified = true;
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.block = MathUtils.floor(tmp);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        if (mo != null) {
            float tmp = (float)this.baseDamage;
            Iterator var9 = player.relics.iterator();

            while(var9.hasNext()) {
                AbstractRelic r = (AbstractRelic)var9.next();
                tmp = r.atDamageModify(tmp, this);
                tmp = r.atDamageModify(tmp, this);
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }
            }

            AbstractPower p;
            for(var9 = player.powers.iterator(); var9.hasNext(); ) {
                p = (AbstractPower)var9.next();
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseDamage != (int)tmp) {
                this.isDamageModified = true;
            }

            for(var9 = mo.powers.iterator(); var9.hasNext();) {
                p = (AbstractPower)var9.next();
                tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
                tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
            }

            for(var9 = player.powers.iterator(); var9.hasNext(); ) {
                p = (AbstractPower)var9.next();
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
            }

            for(var9 = mo.powers.iterator(); var9.hasNext(); ) {
                p = (AbstractPower)var9.next();
                tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
                tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }

            this.damage = MathUtils.floor(tmp);
        }
    }

    @Override
    public void applyPowers() {
        this.applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;

        float tmp = (float) this.baseDamage;
        Iterator var3 = player.relics.iterator();

        while (var3.hasNext()) {
            AbstractRelic r = (AbstractRelic) var3.next();
            tmp = r.atDamageModify(tmp, this);
            tmp = r.atDamageModify(tmp, this);
            if (this.baseDamage != (int) tmp) {
                this.isDamageModified = true;
            }
        }

        AbstractPower p;
        for (var3 = player.powers.iterator(); var3.hasNext(); ) {
            p = (AbstractPower) var3.next();
            tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
            tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
        }

        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseDamage != (int) tmp) {
            this.isDamageModified = true;
        }

        for (var3 = player.powers.iterator(); var3.hasNext(); ) {
            p = (AbstractPower) var3.next();
            tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
            tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        if (this.baseDamage != MathUtils.floor(tmp)) {
            this.isDamageModified = true;
        }

        this.damage = MathUtils.floor(tmp);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(2);
            upgradeBlock(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



