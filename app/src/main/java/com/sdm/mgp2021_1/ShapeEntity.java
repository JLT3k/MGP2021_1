package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class ShapeEntity implements EntityBase, Collidable, PhysicsObject {
    private boolean isDone = false, animation = true;
    private Bitmap bmp = null, scaledbmp = null;
    public int ScreenWidth, ScreenHeight;
    private int shape_type;
    //private float xPos = new Random().nextInt(840) + 120;
    private float /*yPos = 1920,*/ yPosPrev = 1785;
    private float offset, imgRadius, rotation;
    private SurfaceView view = null;
    private int health;

    private Vector3 pos = new Vector3(new Random().nextInt(840) + 120, 1920);
    private Vector3 vel;

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
        shape_type = new Random().nextInt(3);
        health = new Random().nextInt(30) + 1;
        //Find the surfaceview size or screensize
      /*  metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels / 5;
        ScreenWidth = metrics.widthPixels / 5;
        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);*/
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.whitecircle);
        InitShapes(_view);
    }

    @Override
    public void Update(float _dt) {
        imgRadius = yellowCircle.getHeight() * 0.5f;
        if (animation){
            if (pos.y > (yPosPrev)){
                pos.y -= _dt * 100;
            }
            else{
                animation = false;
            }
        }
        else{
            yPosPrev = pos.y - 150;
        }

        if (health <= 0){
            SetIsDone(true);
        }
        rotation += _dt * 10;

        if (Collision.SphereToSphere(pos.x, pos.y, imgRadius, GameSystem.Instance.ball.GetPosX(), GameSystem.Instance.ball.GetPosY(), GameSystem.Instance.ball.GetRadius())){
            health--;
        }

    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.setTranslate(pos.x, pos.y);
//        if (shape_type == 1 || shape_type == 2) {
//            transform.setRotate(rotation);
//        }
        renderShapes(_canvas, transform);
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

    @Override
    public Vector3 GetPos() { return pos; }

    @Override
    public Vector3 GetVel() { return vel; }

    @Override
    public float GetRadius() {
        return yellowCircle.getWidth();
    }

    public void SetAnimation(boolean animation) {
        this.animation = animation;
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