package com.sdm.mgp2021_1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.TextView;

import java.util.Arrays;

// Created by TanSiewLan2021

public class LeaderboardPage extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_return;

    private  TextView[] text_name = new TextView[6];
    private  TextView[] text_score = new TextView[6];
//    private TextView text_name1, text_score1
//            , text_name2, text_score2
//            , text_name3, text_score3
//            , text_name4, text_score4
//            , text_name5, text_score5
//            , text_name6, text_score6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.leaderboard);

        btn_return = (Button)findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this); //Set Listener to this button --> retrun Button

        text_name[0] = (TextView)findViewById(R.id.text_name1);
        text_score[0] = (TextView)findViewById(R.id.text_score1);
        text_name[1] = (TextView)findViewById(R.id.text_name2);
        text_score[1] = (TextView)findViewById(R.id.text_score2);
        text_name[2] = (TextView)findViewById(R.id.text_name3);
        text_score[2] = (TextView)findViewById(R.id.text_score3);
        text_name[3] = (TextView)findViewById(R.id.text_name4);
        text_score[3] = (TextView)findViewById(R.id.text_score4);
        text_name[4] = (TextView)findViewById(R.id.text_name5);
        text_score[4] = (TextView)findViewById(R.id.text_score5);
        text_name[5] = (TextView)findViewById(R.id.text_name6);
        text_score[5] = (TextView)findViewById(R.id.text_score6);

        UpdateScreenText();

        StateManager.Instance.AddState(new LeaderboardPage());
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
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page
        }
        startActivity(intent);

    }

    void UpdateScreenText(){
        for(int i = 0; i < GameSystem.m_leaderboard_size; i++)
        {
            text_name[i].setText(Leaderboard.Instance.GetLeaderboardData(i).name);
            text_score[i].setText("" + Leaderboard.Instance.GetLeaderboardData(i).score);
        }
    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        UpdateScreenText();
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "Leaderboard";
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
