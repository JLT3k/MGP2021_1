package com.sdm.mgp2021_1;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

// Created by Muhammad Rifdi

public class LoseScreen extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_return;
    private Button btn_exit;
    Paint paint = new Paint(); // Under android graphic library.
    Typeface myfont;  // USe for loading font

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.losescreen);

        btn_return = (Button)findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this); //Set Listener to this button --> Back Button

        StateManager.Instance.AddState(new LoseScreen());

    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent
        Intent intent = new Intent();

        if (v == btn_return)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page
            for (int i = 0; i < 5; ++i)
                GameSystem.Instance.ball[i].Reset();

        }
        else if (v == btn_exit)
        {
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page
        }
        startActivity(intent);

    }

    @Override
    public void Render(Canvas _canvas) {
        // we using PAINT which is part of graphic library in android
        paint.setARGB(255, 255,255,255);  // alpha = 255 which meant it is not transparent, Opacity is full 100%
        // font color is black
        paint.setStrokeWidth(200);  // How thick the font is
        paint.setTypeface(myfont); // Use the font type that I loaded
        paint.setTextSize(70); // Font size.
        _canvas.drawText("Your Points:", 500, 350, paint);
        _canvas.drawText("" + GameSystem.Instance.GetPoints(), 520, 400, paint);
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Roboto-Black.ttf");
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "LoseScreen";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
