package com.sdm.mgp2021_1;

// Created by Muhammad Rifdi

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;

import java.util.Random;

public class ShapeEntity implements EntityBase, Collidable, PhysicsObject {
    private boolean isDone = false, animation = true;
    private Bitmap bmp = null, scaledbmp = null;
    public int ScreenWidth, ScreenHeight;
    private int shape_type;
    private float yPosPrev = 9999;
    private float imgRadius, rotation;
    private SurfaceView view = null;
    private Vibrator _vibrator;
    private int health;
    Paint paint = new Paint(); // Under android graphic library.
    Typeface myfont;  // USe for loading font

    private Vector3 pos, vel;

    Matrix tfx = new Matrix();
    DisplayMetrics metrics;
    // In any entity class, under public void Init(SurfaceView _view) {}

    private Bitmap yellowCircle = null;
    private Bitmap orangeCircle = null;
    private Bitmap redCircle = null;
    private Bitmap greenCircle = null;
    private Bitmap cyanCircle = null;
    private Bitmap blueCircle = null;
    private Bitmap purpleCircle = null;

    private Bitmap yellowSquare = null;
    private Bitmap orangeSquare = null;
    private Bitmap redSquare = null;
    private Bitmap greenSquare = null;
    private Bitmap cyanSquare = null;
    private Bitmap blueSquare = null;
    private Bitmap purpleSquare = null;

    private Bitmap yellowTriangle = null;
    private Bitmap orangeTriangle = null;
    private Bitmap redTriangle = null;
    private Bitmap greenTriangle = null;
    private Bitmap cyanTriangle = null;
    private Bitmap blueTriangle = null;
    private Bitmap purpleTriangle = null;

    //check if anything to do with entity (use for pause)
    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void InitShapes(SurfaceView _view)
    {
        // Initialize every single shape to be used
        yellowCircle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.yellowcircle);
        orangeCircle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.orangecircle);
        redCircle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.redcircle);
        greenCircle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.greencircle);
        cyanCircle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.cyancircle);
        blueCircle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.bluecircle);
        purpleCircle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.purplecircle);

        yellowSquare = BitmapFactory.decodeResource(_view.getResources(), R.drawable.yellowsquare);
        orangeSquare = BitmapFactory.decodeResource(_view.getResources(), R.drawable.orangesquare);
        redSquare = BitmapFactory.decodeResource(_view.getResources(), R.drawable.redsquare);
        greenSquare = BitmapFactory.decodeResource(_view.getResources(), R.drawable.greensquare);
        cyanSquare = BitmapFactory.decodeResource(_view.getResources(), R.drawable.cyansquare);
        blueSquare = BitmapFactory.decodeResource(_view.getResources(), R.drawable.bluesquare);
        purpleSquare = BitmapFactory.decodeResource(_view.getResources(), R.drawable.purplesquare);

        yellowTriangle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.yellowtriangle);
        orangeTriangle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.orangetriangle);
        redTriangle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.redtriangle);
        greenTriangle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.greentriangle);
        cyanTriangle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.cyantriangle);
        blueTriangle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.bluetriangle);
        purpleTriangle = BitmapFactory.decodeResource(_view.getResources(), R.drawable.purpletriangle);
    }

    @Override
    public void Init(SurfaceView _view) {
        // Shape type: 0 = circle, 1 = square, 2 - triangle
        shape_type = new Random().nextInt(3);
        // health is RNG on player points
        health = new Random().nextInt(1) + 1;
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.whitecircle);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        InitShapes(_view);
        // Initialize outside of screen first before setting
        pos = new Vector3(9999, 9999);
        vel = new Vector3();
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);
    }

    public void startVibrate(){
        if (Build.VERSION.SDK_INT >= 26)
        {
            _vibrator.vibrate(VibrationEffect.createOneShot(150,10));
        }
        else{
            long pattern[] = {0,50,0};
            _vibrator.vibrate(pattern, -1);
        }
    }

    public void stopVibrate(){
        _vibrator.cancel();
    }

    @Override
    public void Update(float _dt) {
        // Check if game is paused
        if (GameSystem.Instance.GetIsPaused()) {
            return;
        }
        imgRadius = yellowCircle.getHeight() * 0.5f;
        // Animation for when shape moves up from original pos
        if (animation){
            if (pos.y > (yPosPrev)){
                pos.y -= _dt * 100;
            }
            else{
                animation = false;
            }
        }
        else{
            yPosPrev = pos.y - 200;
        }

        // Check collision of each ball with shape
        for (int i = 0; i < 5; ++i) {
            if (Collision.SphereToSphere(GameSystem.Instance.ball[i], this)) {
                health--;
                int random_audio = new Random().nextInt(2);
                if (random_audio == 0)
                    AudioManager.Instance.PlayAudio(R.raw.hit1, 1.0f);
                else
                    AudioManager.Instance.PlayAudio(R.raw.hit2, 1.0f);
                startVibrate();
                GameSystem.Instance.ball[i].OnHit((PhysicsObject)this);
                Log.v("/","Hit");
            }
            else {
                stopVibrate();
            }
        }

        // Add point when shape is destroyed
        if (health <= 0 && !IsDone()){
            GameSystem.Instance.AddPoint();
            SetIsDone(true);
        }

        // Set shape pos far away if destroyed
        if (IsDone()) {
            pos.x = 9999;
            pos.y = 9999;
        }

        // Lose if shape exceed ball height
        if (pos.y < 200) {
            Intent intent = new Intent();
            intent.setClass(GamePage.Instance, LoseScreen.class);
            StateManager.Instance.ChangeState("LoseScreen"); // Default is like a loading page
            GamePage.Instance.startActivity(intent);
        }

        // Animation purposes
        rotation +=_dt * 25;
    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.reset();
        transform.setTranslate(pos.x, pos.y);
        // Rotate constantly if shape is square or triangle
        if (shape_type > 0)
            transform.postRotate(rotation, pos.x + imgRadius, pos.y + imgRadius);
        renderShapes(_canvas, transform);
        RenderHealth(_canvas);
    }

    public void RenderHealth(Canvas _canvas) {
        // Render health text on shape
        paint.setARGB(255, 0,0,0);
        paint.setStrokeWidth(200);
        paint.setTypeface(myfont);
        paint.setTextSize(70);
        if (health < 10)
            _canvas.drawText("" + Math.round(health), pos.x + 60, pos.y + 100 , paint);
        else
            _canvas.drawText("" + Math.round(health), pos.x + 50, pos.y + 100 , paint);
    }

    public void Respawn() {
        // Respawn and reset shape variables
        SetIsDone(false);
        pos.x = new Random().nextInt(781) + 130;
        pos.y = 2180;
        yPosPrev = 1950;
        shape_type = new Random().nextInt(3);
        health = new Random().nextInt(GameSystem.Instance.GetPoints() + 1) + 1;
    }

    public void renderShapes(Canvas _canvas, Matrix transform)
    {
        if (shape_type == 0) {
            if (health >= 1 && health < 5)
                _canvas.drawBitmap(yellowCircle, transform, null);
            else if (health >= 5 && health < 10)
                _canvas.drawBitmap(orangeCircle, transform, null);
            else if (health >= 10 && health < 15)
                _canvas.drawBitmap(redCircle, transform, null);
            else if (health >= 15 && health < 20)
                _canvas.drawBitmap(greenCircle, transform, null);
            else if (health >= 20 && health < 25)
                _canvas.drawBitmap(cyanCircle, transform, null);
            else if (health >= 25 && health < 30)
                _canvas.drawBitmap(blueCircle, transform, null);
            else if (health >= 30)
                _canvas.drawBitmap(purpleCircle, transform, null);
        }
        else if (shape_type == 1) {
            if (health >= 1 && health < 5)
                _canvas.drawBitmap(yellowSquare, transform, null);
            else if (health >= 5 && health < 10)
                _canvas.drawBitmap(orangeSquare, transform, null);
            else if (health >= 10 && health < 15)
                _canvas.drawBitmap(redSquare, transform, null);
            else if (health >= 15 && health < 20)
                _canvas.drawBitmap(greenSquare, transform, null);
            else if (health >= 20 && health < 25)
                _canvas.drawBitmap(cyanSquare, transform, null);
            else if (health >= 25 && health < 30)
                _canvas.drawBitmap(blueSquare, transform, null);
            else if (health >= 30)
                _canvas.drawBitmap(purpleSquare, transform, null);
        }
        else if (shape_type == 2) {
            if (health >= 1 && health < 5)
                _canvas.drawBitmap(yellowTriangle, transform, null);
            else if (health >= 5 && health < 10)
                _canvas.drawBitmap(orangeTriangle, transform, null);
            else if (health >= 10 && health < 15)
                _canvas.drawBitmap(redTriangle, transform, null);
            else if (health >= 15 && health < 20)
                _canvas.drawBitmap(greenTriangle, transform, null);
            else if (health >= 20 && health < 25)
                _canvas.drawBitmap(cyanTriangle, transform, null);
            else if (health >= 25 && health < 30)
                _canvas.drawBitmap(blueTriangle, transform, null);
            else if (health >= 30)
                _canvas.drawBitmap(purpleTriangle, transform, null);
        }
    }

    @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.SHAPE_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static ShapeEntity Create() {
        ShapeEntity result = new ShapeEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public String GetType() {
        return "ShapeEntity";
    }

    @Override
    public float GetPosX() {
        return pos.x;
    }

    @Override
    public float GetPosY() {
        return pos.y;
    }

    public void SetPosX(float xPos) {
        pos.x = xPos;
    }

    @Override
    public Vector3 GetPos() { return pos; }

    @Override
    public Vector3 GetVel() { return vel; }

    @Override
    public float GetRadius() {
        return yellowCircle.getHeight() * 0.5f;
    }

    public void SetAnimation(boolean animation) {
        this.animation = animation;
    }

    public boolean GetAnimation() {
        return animation;
    }

    @Override
    public void OnHit(Collidable _other) {

    }

    @Override
    public void OnHit(PhysicsObject _other) {

    }

    @Override
    public String GetPhysicsType() { return ""; }

}