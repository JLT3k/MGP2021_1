package com.sdm.mgp2021_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();

    // Game stuff
    private boolean isPaused = false;
    Ball ball = new Ball();
    ShapeEntity[] Shape = new ShapeEntity[20];
    private int points;


    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
        for (int i = 0; i < 20; ++i){
            Shape[i] = new ShapeEntity();
        }
    }

    public void Update(float _deltaTime)
    {
        ball.Update(_deltaTime);
        for (int i = 0; i < 20; ++i)
            Shape[i].Update(_deltaTime);
    }

    public void Init(SurfaceView _view)
    {
        ball.Init(_view);
        for (int i = 0; i < 20; ++i)
            Shape[i].Init(_view);
        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public int GetPoints() {
        return points;
    }

    public void AddPoint() {
        points++;
    }

    public void ResetPoints() {
        points = 0;
    }
}
