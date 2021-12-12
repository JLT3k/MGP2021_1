package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class PausebuttonEntity implements EntityBase{

    private boolean isDone = false;

    private Bitmap bmpP = null;
    private Bitmap scaledbmpP =null;

    private Bitmap bmpUP = null;
    private Bitmap scaledbmpUP =null;

    private boolean isInit = false;
    private boolean Paused = false;

    int ScreenWidth,ScreenHeight;
    private float xPos,yPos;

    private float buttonDelay = 0;


    //check if anything to do with entity (use for pause)
    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone=_isDone;

    }

    @Override
    public void Init(SurfaceView _view) {

        bmpP = BitmapFactory.decodeResource(_view.getResources(),R.drawable.pauseicon);
        bmpUP = BitmapFactory.decodeResource(_view.getResources(),R.drawable.pauseicon1);

        DisplayMetrics metrics=_view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        scaledbmpP = Bitmap.createScaledBitmap(bmpP,ScreenWidth/10,ScreenHeight/20,true);
        scaledbmpUP = Bitmap.createScaledBitmap(bmpUP,ScreenWidth/10,ScreenHeight/20,true);

        xPos = ScreenWidth - 100;
        yPos = 150;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {
        buttonDelay += _dt;

        if (TouchManager.Instance.HasTouch())
        {
            if (TouchManager.Instance.IsDown() && !Paused) {  // Check for collision
                float imgRadius1 = scaledbmpP.getHeight() * 0.5f;
                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos,yPos, imgRadius1)  && buttonDelay >= 0.25)
                {
                    Paused = true;

                }
                buttonDelay = 0;
                GameSystem.Instance.SetIsPaused((!GameSystem.Instance.GetIsPaused()));
            }
        }
        else
            Paused = false;
    }

    @Override
    public void Render(Canvas _canvas) {
        if (Paused == false)
            _canvas.drawBitmap(scaledbmpP, xPos - scaledbmpP.getWidth() * 0.5f , yPos - scaledbmpP.getHeight() *0.5f, null );

        else
            _canvas.drawBitmap(scaledbmpUP, xPos - scaledbmpUP.getWidth() * 0.5f , yPos - scaledbmpUP.getHeight() *0.5f, null );
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.PAUSEBUTTON_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_PAUSE;
    }
    public static PausebuttonEntity Create(){
        PausebuttonEntity result=new PausebuttonEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_PAUSE);
        return result;
    }

}
