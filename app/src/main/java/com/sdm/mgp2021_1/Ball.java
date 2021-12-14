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
    private float /*xPos, yPos,*/ xTouchPos, yTouchPos, xPosPrev, yPosPrev, acceleration, imgRadius;
    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;
    // In any entity class, under public void Init(SurfaceView _view) {}
    private Bitmap ball = null;

    //private float xVel, yVel;

    private Vector3 pos, vel;

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
        pos.x = 488.f;
        pos.y = 144.f;
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
                    System.out.print("Shot!");
                }
            }
        }
        if (shot){
            if (pos.x < xTouchPos)
                shotRight = true;

            if (pos.x > xTouchPos)
                shotRight = false;

            shot = false;
            move = true;
        }
        if (move) {
            pos.y += _dt * (yTouchPos - yPosPrev);
            pos.y -= _dt * acceleration;
            if (shotRight) {
                if (!flipped)
                    pos.x += _dt * 500;
                else
                    pos.x -= _dt * 500;
            } else {
                if (!flipped)
                    pos.x -= _dt * 500;
                else
                    pos.x += _dt * 500;
            }
        }

        if (pos.x >= 1006 || pos.x <= 0) {
            flipped = !flipped;
            //System.out.println(flipped);
        }
        if (pos.y > 2000 || pos.y < -100) {
            flipped = false;
            pos.x = 488.f;
            pos.y = 144.f;
            acceleration = 0;
            move = false;
            turn = true;
        }


    }

    public void Reset () {
        flipped = false;
        pos.x = 488.f;
        pos.y = 144.f;
        acceleration = 500;
        move = false;
        turn = true;
    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.setTranslate(pos.x, pos.y);
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
        return pos.x;
    }

    @Override
    public float GetPosY() {
        return pos.y;
    }

    @Override
    public Vector3 GetPos() { return pos; }

    @Override
    public Vector3 GetVel() { return vel; }

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
