package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class Ball implements EntityBase {
    private boolean isDone = false, shot = false, move = false, shotRight = false, flipped = false;
    int ScreenWidth, ScreenHeight;
    private float xPos, yPos, xTouchPos, yTouchPos, acceleration;
    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;
    // In any entity class, under public void Init(SurfaceView _view) {}
    private Bitmap ball = null;

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
        ball = BitmapFactory.decodeResource(_view.getResources(), R.drawable.whitecircle);
        acceleration = 0;
        xPos = 488.f;
        yPos = 144.f;
    }

    @Override
    public void Update(float _dt) {

        if (TouchManager.Instance.HasTouch()){
            xTouchPos = TouchManager.Instance.GetPosX();
            yTouchPos = TouchManager.Instance.GetPosY();
            shot = true;
        }
        if (shot){
            if (xPos < xTouchPos)
                shotRight = true;

            if (xPos > xTouchPos)
                shotRight = false;

            shot = false;
            move = true;
        }
        if (move) {
            yPos += _dt * acceleration;
            if (shotRight) {
                if (!flipped)
                    xPos += _dt * acceleration;
                else
                    xPos -= _dt * acceleration;
            } else {
                if (!flipped)
                    xPos -= _dt * acceleration;
                else
                    xPos += _dt * acceleration;
            }
        }

        if (acceleration >= 1000) {
            acceleration = 1000;
        }
        else {
            acceleration += _dt * 200;
        }
        if (xPos >= 1006 || xPos <= 0) {
            flipped = !flipped;
            System.out.println(flipped);
        }
        if (yPos > 2000 || yPos < -100) {
            SetIsDone(true);
        }

 /*       if(TouchManager.Instance.IsDown()){
            // Check collision
            float imgRadius = bmp.getHeight() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos,yPos, imgRadius))
            {
                SetIsDone(true);
            }
        }*/
    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.setTranslate(xPos, yPos);
        _canvas.drawBitmap(ball,transform,null);
    }

    @Override
    public boolean IsInit() {
        return ball != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.BALL_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static Ball Create() {
        Ball result = new Ball();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

}
