package Mon3tr.helper;

import Mon3tr.modcore.Mon3trMod;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class RightHitbox extends Hitbox {
    //右键点击，后覆盖左键
    public boolean rightClicked;
    private HitboxListener listener;

    public void setListener(HitboxListener l){
        this.listener = l;
    }

    public RightHitbox(float x,float y, float width, float height){
        super(x, y, width, height);
    }

    @Override
    public void update() {
        this.update(this.x, this.y);

        if (this.clickStarted && (InputHelper.justReleasedClickLeft||InputHelper.justReleasedClickRight)) {
            if (this.hovered) {
                if(InputHelper.justReleasedClickRight){
                    this.rightClicked = true;
                    Mon3trMod.logSomething(" === RightHitbox 点击了右键 === ");
                }
                else {
                    this.clicked = true;
                }
            }

            this.clickStarted = false;
        }

        if (this.hovered && (InputHelper.justClickedLeft||InputHelper.justClickedRight)) {
            this.clickStarted = true;
        } else if ((this.clicked||this.rightClicked) || this.hovered && CInputActionSet.select.isJustPressed()) {
            CInputActionSet.select.unpress();
            if(listener!=null){
                listener.clicked(this);
            }
            this.rightClicked = false;
        }
    }
}
