package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase{

    private boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap scaledbmp = null; // To scale bmp according to width and height
    private SurfaceView view = null;
    private float xPos, yPos;
    private int screenWidth, screenHeight;

    private Bitmap ship = null;

    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view){
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamescene);

        // Finding the screen width & height to allow the images to scale according to it.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        scaledbmp = Bitmap.createScaledBitmap(bmp, screenWidth, screenHeight, true);

        ship = BitmapFactory.decodeResource(_view.getResources(),R.drawable.ship2_1);
    }

    @Override
    public void Update(float _dt){
        xPos -= _dt * 500; // How fast you want to move the screen

        if (xPos < -screenWidth){
            xPos = 0;
        }
    }

    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(scaledbmp, xPos, yPos, null); //1st image
        _canvas.drawBitmap(scaledbmp, xPos + screenWidth, yPos, null); // 2nd image

        Matrix transform = new Matrix();
        transform.postRotate(30);
        _canvas.drawBitmap(ship,transform,null);
        _canvas.drawBitmap(ship,transform,null);
    }

    @Override
    public boolean IsInit(){
        return bmp != null;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.BACKGROUND_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer){
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static RenderBackground Create(){
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
