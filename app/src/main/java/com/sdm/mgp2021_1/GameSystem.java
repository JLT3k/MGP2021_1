package com.sdm.mgp2021_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021
// Edited by Muhammad Rifdi

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public final static String SHARED_PREF_ID = "GameSaveFile";
    public final static float inelastic_k = .6f;
    public final static float m_gravity = 2000f;
    public final static float m_terminal_vel = 3000.f;

    // Game stuff
    private boolean isPaused = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    Ball[] ball = new Ball[5];
    ShapeEntity[] Shape = new ShapeEntity[20];
    private int points;
    private int highScore;


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

        // Get our shared preferences (Save file)
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID, 0);
        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
    }

    public void SaveEditBegin()
    {
        // Safety check, only allow if not already editing
        if (editor != null)
            return;

        // Start the editing
        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        // Check if has editor
        if (editor == null)
            return;

        editor.commit();
        editor = null; // Safety to ensure other functions will fail once commit done
    }

    public void SetIntInSave(String _key, int _value)
    {
        // Safety check, only allow if not already editing
        if (editor == null)
            return;

        editor.putInt(_key, _value);
    }
    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 0);
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

    public int GetHighScore() { return highScore; }

    public void AddPoint() {
        points++;
    }

    public void ResetPoints() {
        if (points > GetIntFromSave("highScore"))
        {
            highScore = points;
            SaveEditBegin();
            SetIntInSave("highScore", highScore);
            SaveEditEnd();
        }
        points = 0;
    }
}
