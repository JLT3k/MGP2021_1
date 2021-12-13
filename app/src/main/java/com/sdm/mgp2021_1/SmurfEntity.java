package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class SmurfEntity implements EntityBase {
    private boolean isDone = false;
    private Sprite spritesmurf = null;
    private boolean hasTouched = false;
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
        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_1);
        bmp = ResourceManager.Instance.GetBitmap( R.drawable.ship2_1);

        // New to Week 8
        // Using Sprite animation class to load our sprite sheet
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4, 16 );

        // Define how we want the player to react or if it is enemy or obstacles, how it is going to appear as.
        // You should have this part!
        Random ranGen = new Random();
        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = ranGen.nextFloat() * _view.getHeight();

        lifetime = 30.0f;
    }

    @Override
    public void Update(float _dt) {
        spritesmurf.Update(_dt);

        lifetime -= _dt;
        if (lifetime < 0.0f) {
            SetIsDone(true);   // <--- This part here or this code, meant when time is up, kill the items.
        }

       /* if (TouchManager.Instance.IsDown()) {   // Previous and it is for just a touch - useful for collision with a image (example button)

            // Check collision
            float imgRadius = bmp.getHeight() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)) {
                SetIsDone(true); // // <--- This part here or this code, meant when time is up, kill the items.
                // If it is a button --> Going to another state
            }
        }*/
    }

    @Override
    public void Render(Canvas _canvas) {

        // New to Week 8
        spritesmurf.Render(_canvas, (int)xPos, (int)yPos);  // Location can be changed!

        // New Week 8
        if (TouchManager.Instance.HasTouch())  // Touch and drag
        {
            // Check collision with the smurf sprite
            float imgRadius1 = spritesmurf.GetWidth() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius1) || hasTouched) {
                hasTouched = true;
                xPos = TouchManager.Instance.GetPosX();
                yPos = TouchManager.Instance.GetPosY();

            }
        }
    }


        @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.SMURF_LAYER;
    }


    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static SmurfEntity Create() {
        SmurfEntity result = new SmurfEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

}
