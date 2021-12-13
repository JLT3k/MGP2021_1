package com.sdm.mgp2021_1;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

//Create a new entity name the class name as RenderTextEntity.
//Buttons, text, player, obstacles, NPC == Entity.
public class RenderTextEntity implements EntityBase{
    private boolean isDone = false;

    // Render text using android loading of a font type.
    // Paint
    // Assign color, strokewidth, font size with number.

    Paint paint = new Paint(); // Under android graphic library.
    private  int red = 0, green = 0, blue = 0;  // 0 - 255
    // Paint takes Red, Green, Blue and also there is an alpha.

    Typeface myfont;  // USe for loading font

    // Use for loading FPS so need da more parameters!
    int frameCount; // Framecount
    long lastTime = 0; // Time
    long lastFPSTime = 0; // last frame time
    float fps; // use to store by FPS
    float xPos, yPos;


    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }

    @Override
    // For us to intialize or load resource eg: images
    public void Init(SurfaceView _view){
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void Update(float _dt){
        // get actual fps
        frameCount++;

        long currentTime = System.currentTimeMillis();

        lastTime = currentTime; // Last time = current time

        if (currentTime - lastFPSTime > 1000)
        {
            fps = (frameCount * 1000.f) / (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }
    }

    @Override
    public void Render(Canvas _canvas){
        // we using PAINT which is part of graphic library in android
        paint.setARGB(255, 0,255,0);  // alpha = 255 which meant it is not transparent, Opacity is full 100%
        // font color is black
        paint.setStrokeWidth(200);  // How thick the font is
        paint.setTypeface(myfont); // Use the font type that I loaded
        paint.setTextSize(70); // Font size.
        _canvas.drawText("FPS: " + Math.round(fps), 30, 80, paint);  // For now, default number but u can use _view.getWidth/ ?
        _canvas.drawText("Paused?: " + (GameSystem.Instance.GetIsPaused() ? "paused" : "not paused"), 30, 160, paint);
    }

    @Override
    public boolean IsInit(){
        return true;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.RENDERTEXT_LAYER; // Check from Layer constants
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static RenderTextEntity Create(){
        RenderTextEntity result = new RenderTextEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

}
