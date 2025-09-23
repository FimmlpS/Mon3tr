package Mon3tr.card.power;

import Mon3tr.card.AbstractMon3trCard;
import Mon3tr.card.status.SpaceMiracle;
import Mon3tr.power.ProtocolOfPsychePower;
import Mon3tr.power.ResonancePower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

public class ProtocolOfPsyche extends AbstractMon3trCard {
    public static final String ID = "mon3tr:ProtocolOfPsyche";
    private static final CardStrings cardStrings;

    public ProtocolOfPsyche(){
        super(ID, cardStrings.NAME,1, cardStrings.DESCRIPTION, CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        this.cardsToPreview = new SpaceMiracle();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p != null) {
            this.addToBot(new VFXAction(new BorderFlashEffect(Color.VIOLET, true)));
            this.addToBot(new VFXAction(new GiantEyeEffect(p.hb.cX, p.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.8F, 1.0F, 0.0F))));
        }
        addToBot(new ApplyPowerAction(p,p,new ProtocolOfPsychePower(p,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



