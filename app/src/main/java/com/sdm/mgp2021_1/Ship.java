package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class Ship implements EntityBase {
    private boolean isDone = false;
    private Bitmap bmp = null, scaledbmp = null;
    int ScreenWidth, ScreenHeight;
    private float xPos, yPos, offset;
    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;
    // In any entity class, under public void Init(SurfaceView _view) {}

    // Use for allowing entity to have a lifespan
    private float lifetime;



    //check if anything to do with entity (use for pause)
    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_1);
        //Find the surfaceview size or screensize
        metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels / 5;
        ScreenWidth = metrics.widthPixels / 5;
        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);

        // We can define how we want the player to react or if it is enemy or obstacles, how it is going to appear as.
        Random ranGen = new Random();
        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = ranGen.nextFloat() * _view.getHeight();

        lifetime = 30.0f;
    }

    @Override
    public void Update(float _dt) {
        // tfx.preRotate(20 * _dt, metrics.widthPixels / 10, metrics.heightPixels / 10);
        // tfx.postTranslate(10 * _dt, 10 * _dt);

        lifetime -= _dt;
        if (lifetime < 0.0f ) {
            SetIsDone(true);
        }

        if(TouchManager.Instance.IsDown()){

            // Check collision
            float imgRadius = bmp.getHeight() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos,yPos, imgRadius))
            {
                SetIsDone(true);
            }

        }
    }

    @Override
    public void Render(Canvas _canvas) {
       /* _canvas.drawBitmap(bmp, xPos, yPos, null);
        Matrix transform = new Matrix();
        transform.postScale((0.5f + Math.abs((float)Math.sin(lifetime))),(0.5f + Math.abs((float)Math.sin(lifetime))) );
        _canvas.drawBitmap(bmp, transform, null);*/
    }

    @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.SHIP_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static Ship Create() {
        Ship result = new Ship();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
