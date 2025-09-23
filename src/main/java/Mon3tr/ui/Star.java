package Mon3tr.ui;

import Mon3tr.effect.StarTrailEffect;
import Mon3tr.patch.CrystalPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Star {

    private CatmullRomSpline<Vector2> crs = new CatmullRomSpline<>();
    private ArrayList<Vector2> controlPoints = new ArrayList<>();
    private Vector2[] points = new Vector2[20];

    private static final float DURATION = 1.75F;
    private static final float DURATION_DROP = 0.8F;
    private Vector2 start;
    private Vector2 pos;
    private Vector2 target;
    private boolean half;
    private static final float VFX_INTERVAL = 0.015F;
    private float vfxTimer = 0.015F;
    public float duration;
    public boolean isReadyForReuse;

    public static final Pool<StarTrailEffect> trailEffectPool = new Pool<StarTrailEffect>() {
        @Override
        protected StarTrailEffect newObject() {
            return new StarTrailEffect();
        }
    };

    public boolean isDrop;
    public boolean isDone;
    private float rotation;
    private float xSpeed;
    private float ySpeed;
    private float yAcceleration;
    private Vector2 tmp = new Vector2();

    private static final float VELOCITY_RAMP_RATE;
    private static final float DST_THRESHOLD;
    private static final float HOME_IN_THRESHOLD;
    private static final float MIDDLE_LINE_Y;

    public Star(){
        crs.controlPoints = new Vector2[1];
        start = new Vector2();
        target = new Vector2();
        this.isReadyForReuse = true;
    }

    public void startMove(){
        duration = DURATION;
        half = false;
        //中轴线
        float startY = MathUtils.random(-100F*Settings.scale, Settings.HEIGHT + 100F*Settings.scale);
        start.x = -100F * Settings.scale;
        start.y = startY;
        target.x = Settings.WIDTH + 50F * Settings.scale;
        target.y = startY;
        pos = start.cpy();
        xSpeed = (100F * Settings.scale + Settings.WIDTH)/duration;
        float halfTime = duration/2F;
        yAcceleration = 2F * 0.65F * (startY-MIDDLE_LINE_Y) / halfTime / halfTime;
        ySpeed = -halfTime * yAcceleration;
        vfxTimer = 0.015F;
        controlPoints.clear();
        this.isDrop = false;
        this.isReadyForReuse = false;
        this.isDone = false;
    }

    public void startDropMove(AbstractMonster m){
        this.startDropMove(m.hb.cX,m.hb.cY,m.hb.width * 0.4F,m.hb.height * 0.4F);
    }

    public void startDropMove(float startX,float startY,float rangeX,float rangeY){
        duration = DURATION_DROP;
        half = false;
        start.x = startX + MathUtils.random(-rangeX,rangeX);
        start.y = startY + MathUtils.random(-rangeY,rangeY);
        target.x = CrystalPatch.crystalTopItem.getX() + 32F *Settings.scale;
        target.y = CrystalPatch.crystalTopItem.getY() + 32F *Settings.scale;
        pos = start.cpy();
        xSpeed = (target.x - start.x)/duration;
        ySpeed = (target.y - start.y)/duration;
        yAcceleration = 0F;
        vfxTimer = 0.015F;
        controlPoints.clear();
        this.isDrop = true;
        this.isReadyForReuse = false;
        this.isDone = false;
    }

    public void update(){
        updateMovement();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration<0F){
            this.isReadyForReuse = true;
            this.isDone = true;
        }
    }

    private void updateMovement(){
        //当前角度设置（帧变前）
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 beforePos = pos.cpy();
        pos.x += xSpeed * deltaTime;
        pos.y += ySpeed * deltaTime;
        ySpeed += yAcceleration * deltaTime;

        this.tmp.x = beforePos.x - pos.x;
        this.tmp.y = beforePos.y - pos.y;
        this.tmp.nor();
        this.rotation = this.tmp.angle();

        //粒子效果
        this.vfxTimer -= deltaTime;
        if(!this.isDone && vfxTimer<0.0F){
            vfxTimer = 0.015F;
            if(!controlPoints.isEmpty()){
                if(!controlPoints.get(0).equals(this.pos)){
                    controlPoints.add(pos.cpy());
                }
            }
            else{
                controlPoints.add(pos.cpy());
            }

            if(controlPoints.size()>10){
                controlPoints.remove(0);
            }

            if(controlPoints.size()>3){
                Vector2[] vec2Array = new Vector2[0];
                crs.set(controlPoints.toArray(vec2Array),false);
                for(int i =0;i<20;i++){
                    if(points[i] == null){
                        points[i] = new Vector2();
                    }

                    Vector2 derp = crs.valueAt(points[i], i/19F);
                    StarTrailEffect effect = trailEffectPool.obtain();
                    effect.init(derp.x,derp.y,this.isDrop);
                    if(!isDrop)
                        AbstractDungeon.effectList.add(effect);
                    else
                        AbstractDungeon.topLevelEffects.add(effect);
                }
            }
        }


        //改变传向
        if(!half){
            if(pos.x >= Settings.WIDTH/2F){
                half = true;
            }
        }
    }


    static {
        VELOCITY_RAMP_RATE = 3000.0F * Settings.scale;
        DST_THRESHOLD = 36.0F * Settings.scale;
        HOME_IN_THRESHOLD = 72.0F * Settings.scale;
        MIDDLE_LINE_Y = 0.5F * Settings.HEIGHT;
    }
}
