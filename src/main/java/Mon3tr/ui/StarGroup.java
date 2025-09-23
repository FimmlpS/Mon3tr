package Mon3tr.ui;

import Mon3tr.helper.Mon3trHelper;
import Mon3tr.patch.MeltdownPatch;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class StarGroup {
    private ArrayList<Star> stars = new ArrayList<>();
    private static final int DEFAULT_SOUL_CACHE = 20;
    private float effectTimer = 1.8F / DEFAULT_SOUL_CACHE;
    private float effectTimerMark = 1.8F / DEFAULT_SOUL_CACHE;

    public StarGroup(){
        for(int i = 0; i < DEFAULT_SOUL_CACHE; ++i) {
            this.stars.add(new Star());
        }
    }

    public void onMove(){
        boolean needMore = true;
        for(Star star:stars){
            if(star.isReadyForReuse){
                star.startMove();
                needMore = false;
                break;
            }
        }

        if(needMore){
            Star s = new Star();
            s.startMove();
            this.stars.add(s);
        }
    }

    public void onDropCrystal(AbstractMonster m){
        boolean needMore = true;
        for(Star star:stars){
            if(star.isReadyForReuse){
                star.startDropMove(m);
                needMore = false;
                break;
            }
        }

        if(needMore){
            Star s = new Star();
            s.startDropMove(m);
            this.stars.add(s);
        }
    }

    public void onDropCrystal(float startX,float startY,float rangeX,float rangeY){
        boolean needMore = true;
        for(Star star:stars){
            if(star.isReadyForReuse){
                star.startDropMove(startX, startY, rangeX, rangeY);
                needMore = false;
                break;
            }
        }

        if(needMore){
            Star s = new Star();
            s.startDropMove(startX, startY, rangeX, rangeY);
            this.stars.add(s);
        }
    }

    public void update(){
        for(Star star:stars){
            if(!star.isReadyForReuse){
                star.update();
            }
        }

        //add
        if(MeltdownPatch.isMeltdownTurn){
            effectTimer -= Gdx.graphics.getDeltaTime();
            if(effectTimer<0F){
                effectTimer = effectTimerMark;
                onMove();
            }
        }
    }

    public static boolean isActive(){
        return false;
    }
}
