package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class Ball implements EntityBase, Collidable, PhysicsObject {
    private boolean isDone = false, shot = false, move = false, shotRight = false, flipped = false, turn = false;
    int ScreenWidth, ScreenHeight;
    private float xPos, yPos, xTouchPos, yTouchPos, xPosPrev, yPosPrev, acceleration, imgRadius;
    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;
    // In any entity class, under public void Init(SurfaceView _view) {}
    private Bitmap ball = null;

    private float xVel, yVel;

    //private Vector3 pos, vel;

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
        xPosPrev = 488.f;
        yPosPrev = 144.f;
        imgRadius = ball.getHeight() * 0.5f;
    }

    @Override
    public void Update(float _dt) {
        if (!GameSystem.Instance.GetIsPaused()) {
            for (int i = 0; i < 20; ++i) {
                if (TouchManager.Instance.HasTouch() && !shot && !move && !GameSystem.Instance.Shape[i].GetAnimation()) {
                    xTouchPos = TouchManager.Instance.GetPosX();
                    yTouchPos = TouchManager.Instance.GetPosY();
                    shot = true;
                }
            }
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
            yPos -= _dt * acceleration;
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

        if (xPos > 1006 || xPos < 0) {
            flipped = !flipped;
            //System.out.println(flipped);
        }
        if (yPos > 2000 || yPos < -100) {
            flipped = false;
            xPos = 488.f;
            yPos = 144.f;
            acceleration = 0;
            move = false;
            turn = true;
        }


    }

    public void Reset () {
        flipped = false;
        xPos = 488.f;
        yPos = 144.f;
        acceleration = 500;
        move = false;
        turn = true;
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
    public Vector3 GetPos() { return null; }

    @Override
    public Vector3 GetVel() { return null; }

    public boolean GetTurn(){ return turn; }

    public void SetTurn(boolean turn){
        this.turn = turn;
    }

    @Override
    public float GetRadius() {
        return ball.getHeight() * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {

    }

    @Override
    public void OnHit(PhysicsObject _other) {

    }

    @Override
    public String GetPhysicsType() { return "Ball"; }

}
