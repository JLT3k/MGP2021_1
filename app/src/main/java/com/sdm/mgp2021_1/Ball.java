package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class Ball implements EntityBase, Collidable {
    private boolean isDone = false, shot = false, move = false, shotRight = false, flipped = false;
    int ScreenWidth, ScreenHeight;
    private float xPos, yPos, xTouchPos, yTouchPos, xPosPrev, yPosPrev, acceleration, gravity;
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
        acceleration = 1;
        xPos = 488.f;
        yPos = 144.f;
        xPosPrev = 488.f;
        yPosPrev = 144.f;
    }

    @Override
    public void Update(float _dt) {

        if (TouchManager.Instance.HasTouch() && !shot && !move){
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
            yPos += _dt * (yTouchPos - yPosPrev);
            if (shotRight) {
                if (!flipped)
                    xPos += _dt * 500;
                else
                    xPos -= _dt * 500;
            } else {
                if (!flipped)
                    xPos -= _dt * 500;
                else
                    xPos += _dt * 500;
            }
        }

        if (xPos >= 1006 || xPos <= 0) {
            flipped = !flipped;
            //System.out.println(flipped);
        }
        if (yPos > 2000 || yPos < -100) {
            flipped = false;
            xPos = 488.f;
            yPos = 144.f;
            acceleration = 0;
            move = false;
        }

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

    @Override
    public String GetType() {
        return "BallEntity";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return ball.getWidth();
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType()
                && _other.GetType() !=  "SmurfEntity") {  // Another entity
            System.out.print(true);
            flipped = false;
            xPos = 488.f;
            yPos = 144.f;
            acceleration = 0;
            move = false;
        }
    }

}
