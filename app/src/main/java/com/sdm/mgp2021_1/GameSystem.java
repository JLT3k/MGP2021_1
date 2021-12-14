package com.sdm.mgp2021_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public final static float inelastic_k = .7f;

    // Game stuff
    private boolean isPaused = false;
    Ball[] ball = new Ball[5];
    ShapeEntity[] Shape = new ShapeEntity[20];
    private int points;


    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
        for (int i = 0; i < 20; ++i){
            Shape[i] = new ShapeEntity();
        }
        for (int i = 0; i < 5; ++i){
            ball[i] = new Ball();
        }
    }

    public void Update(float _deltaTime)
    {
        for (int i = 0; i < 5; ++i) {
            ball[i].Update(_deltaTime);
        }
        for (int i = 0; i < 20; ++i)
            Shape[i].Update(_deltaTime);
    }

    public void Init(SurfaceView _view)
    {
        for (int i = 0; i < 5; ++i) {
            ball[i].Init(_view);
        }
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
