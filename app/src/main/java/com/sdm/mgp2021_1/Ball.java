package com.sdm.mgp2021_1;

// Created by Muhammad Rifdi

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

public class Ball implements EntityBase, Collidable, PhysicsObject {
    private boolean isDone = false, shot = false, move = false, timerStart = false;
    int ScreenWidth, ScreenHeight;
    private float xTouchPos, yTouchPos, imgRadius, timer;
    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;
    // In any entity class, under public void Init(SurfaceView _view) {}
    private Bitmap ball = null;

    private float xVel, yVel;

    private Vector3 pos, vel;
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
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        pos = new Vector3(ScreenWidth/2.23f, ScreenHeight/13.6f);
        vel = new Vector3();
        imgRadius = ball.getHeight() * 0.5f;
    }

    @Override
    public void Update(float _dt) {
        // Pause ball if game paused
        if (GameSystem.Instance.GetIsPaused()) {
            timerStart = false;
            shot = false;
            return;
        }
        // Move ball when shape moves finished
        for (int i = 0; i < 20; ++i) {
            if (TouchManager.Instance.HasTouch() && !shot && !move && !GameSystem.Instance.Shape[i].GetAnimation()) {
                if (TouchManager.Instance.GetPosY() > 200) {
                    xTouchPos = TouchManager.Instance.GetPosX();
                    yTouchPos = TouchManager.Instance.GetPosY();
                    timerStart = true;
                }
            }
        }
        // Timer for spawning of ball
        if (timerStart)
            timer -= _dt;
        if (timer < 0) {
            timerStart = false;
            shot = true;
        }
        // Set velocity upon being shot
        if (shot) {
            vel.x = xTouchPos - pos.x;
            vel.y = yTouchPos - pos.y;
            vel = vel.Normalised().MultiplyVector(1000.f);

            shot = false;
            move = true;
        }
        // Update ball pos with velocity
        if (move) {
            vel = Physics.UpdateGravity(vel, _dt);
            pos = Physics.UpdatePosition(this, _dt);
            for (int i = 0; i < 5; ++i) {
                if (Collision.SphereToSphere(GameSystem.Instance.ball[i], this)) {
                    GameSystem.Instance.ball[i].OnHit((PhysicsObject)this);
                }
            }
        }

        // Flip ball velocity if hit sides
        if ((pos.x > (ScreenWidth/1.074f) && vel.x > 0) || (pos.x < 0 && vel.x < 0)) {
            vel.x *= -1;
        }

    }

    public void Reset () {
        // Reset ball variables
        pos.Set(ScreenWidth/2.23f, ScreenHeight/13.6f);
        vel.Set(0, 0);
        shot = false;
        move = false;
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

    public boolean GetTimerStart(){ return timerStart;}

    public void SetTimer(float timer) {
        this.timer = timer;
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
        vel = Physics.BallToPillar(this, _other);
    }

    @Override
    public String GetPhysicsType() { return "Ball"; }

}
