package Mon3tr.card.attack;

import Mon3tr.action.DrawToHandAction;
import Mon3tr.action.Mon3trAttackAction;
import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.helper.RightHitbox;
import Mon3tr.helper.StringHelper;
import Mon3tr.patch.MeltdownPatch;
import Mon3tr.patch.OtherEnum;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class WeaveBlock extends AbstractMon3trCard implements HitboxListener {
    public static final String ID = "mon3tr:WeaveBlock";
    private static final CardStrings cardStrings;

    public WeaveBlock(){
        this(true);
    }

    public WeaveBlock(boolean preview){
        super(ID, cardStrings.NAME ,0, cardStrings.DESCRIPTION, CardType.ATTACK,CardRarity.COMMON,CardTarget.ALL_ENEMY);
        this.baseDamage = 5;
        this.damage = 5;
        this.isMultiDamage = true;
        RightHitbox rhb = new RightHitbox(-10000F,-10000F, IMG_WIDTH_S, IMG_HEIGHT_S);
        rhb.setListener(this);
        this.hb = rhb;
        if(preview){
            WeaveBlock preWeave = new WeaveBlock(false);
            preWeave.changeStrategy(false,true);
            this.cardsToPreview = preWeave;
        }
        isStrategyCard = true;
        canChangeStrategy = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new Mon3trAttackAction(false));
        if(!specialType){
            this.addToBot(new SFXAction("ATTACK_HEAVY"));
            this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, OtherEnum.MON3TR_ATTACK_EFFECT));
        }
        else{
            for(int i =0;i<2;i++){
                addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn), OtherEnum.MON3TR_ATTACK_EFFECT));
            }
        }
    }

    @Override
    public void atTurnStart() {
        if(MeltdownPatch.isMeltdownTurn){
            addToBot(new DrawToHandAction(this));
            addToBot(new DiscardToHandAction(this));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeDamage(2);
            if(cardsToPreview!=null){
                cardsToPreview.upgrade();
            }
        }
    }

    public void changeStrategy(boolean flash,boolean changeNumber){

        if(specialType){
            specialType = false;
            target = CardTarget.ALL_ENEMY;
            isMultiDamage = true;
            if(changeNumber)
                this.baseDamage+=2;
            if(flash){
                this.flash(Color.BLUE.cpy());
            }
            loadCardImage(StringHelper.getCardIMGPatch(ID,CardType.ATTACK));
            name = cardStrings.NAME + (upgraded?"+":"");
            rawDescription = cardStrings.DESCRIPTION;
        }
        else{
            specialType = true;
            target = CardTarget.ENEMY;
            isMultiDamage = false;
            if(changeNumber)
                this.baseDamage-=2;
            if(flash){
                this.flash(Color.RED.cpy());
            }
            loadCardImage(StringHelper.getCardStrategyIMGPath(ID,CardType.ATTACK));
            name = cardStrings.EXTENDED_DESCRIPTION[0] + (upgraded?"+":"");
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        }
        initializeTitle();
        initializeDescription();
        if(cardsToPreview instanceof WeaveBlock){
            ((WeaveBlock) cardsToPreview).changeStrategy(false,true);
        }
        if(!inBattleAndInHand())
            resetAttributes();
    }

    @Override
    public void hoverStarted(Hitbox hitbox) {

    }

    @Override
    public void startClicking(Hitbox hitbox) {

    }

    @Override
    public void clicked(Hitbox hitbox) {
        if(hitbox instanceof RightHitbox){
            RightHitbox rhb = (RightHitbox) hitbox;
            if(rhb.rightClicked){
                if(!inBattleAndInHand())
                    return;
                changeStrategy(true,true);
                applyPowers();
            }
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


