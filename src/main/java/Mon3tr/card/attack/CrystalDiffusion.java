package Mon3tr.card.attack;

import Mon3tr.action.CrystalAllEnemyAction;
import Mon3tr.action.CrystalRandomEnemyAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

public class CrystalDiffusion extends AbstractMon3trCard {
    public static final String ID = "mon3tr:CrystalDiffusion";
    private static final CardStrings cardStrings;

    public CrystalDiffusion(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ALL_ENEMY);
        this.baseDamage = 2;
        this.damage =2;
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(true));
        addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
        for(int i =0;i< 3;i++){
            addToBot(new CrystalRandomEnemyAction(new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT,i==0));
        }
        addToBot(new CrystalAllEnemyAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
