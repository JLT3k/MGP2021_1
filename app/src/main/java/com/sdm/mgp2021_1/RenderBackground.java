package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase{

    private boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap scaledbmp = null; // To scale bmp according to width and height
    private SurfaceView view = null;
    private float xPos, yPos, xPos2, yPos2;
    private int screenWidth, screenHeight;
    MotionEvent event;

    private Bitmap ball = null;

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
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamepage);

        // Finding the screen width & height to allow the images to scale according to it.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        scaledbmp = Bitmap.createScaledBitmap(bmp, screenWidth, screenHeight, true);

        ball = BitmapFactory.decodeResource(_view.getResources(),R.drawable.whitecircle);
    }

    @Override
    public void Update(float _dt){
        //xPos -= _dt * 500; // How fast you want to move the screen

        //if (xPos < -screenWidth){
            //xPos = 0;
        //}
        xPos2 = TouchManager.Instance.GetPosX();
        yPos2 = TouchManager.Instance.GetPosY();
    }

    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(scaledbmp, xPos, yPos, null); //1st image
        //_canvas.drawBitmap(scaledbmp, xPos + screenWidth, yPos, null); // 2nd image

        Matrix transform = new Matrix();
        transform.preTranslate(488.f, 144.f);
//        if (TouchManager.Instance.HasTouch()){
//            transform.setTranslate(xPos2, yPos2);
//            System.out.println(xPos2);
//            System.out.println(yPos2);
//        }
        _canvas.drawBitmap(ball,transform,null);
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
