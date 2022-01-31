package com.sdm.mgp2021_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021
// Edited by Muhammad Rifdi
// Edited by Junliang (points and Leaderboard)

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public final static String SHARED_PREF_ID = "GameSaveFile";
    public final static float inelastic_k = .5f;
    public final static float m_gravity = 2000f;
    public final static float m_terminal_vel = 3000.f;

    public final static int m_leaderboard_size = 6;

    // Game stuff
    private boolean isPaused = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    Ball[] ball = new Ball[5];
    ShapeEntity[] Shape = new ShapeEntity[20];

    // points and leaderboard
    private int points;
    //private String[] keyScore = new String[m_leaderboard_size];
    //private String[] keyName = new String[m_leaderboard_size];
    //private LeaderboardData highScore;
    //private LeaderboardData[] leaderboards = new LeaderboardData[10];


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

        UpdateLeaderboardInstance();
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

    public void SetStringInSave(String _key, String _value)
    {
        // Safety check, only allow if not already editing
        if (editor == null)
            return;

        editor.putString(_key, _value);
    }
    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 0);
    }

    public String GetStringFromSave(String _key)
    {
        return sharedPref.getString(_key, "Empty");
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

    //public int GetHighScore() { return GetIntFromSave("highScore"); }

    public void AddPoint() {
        points++;
    }

    public void ResetPoints() {
        points = 0;
    }

    public void UpdateLeaderboardSave()
    {
        SaveEditBegin();
        for (int i = 0; i < m_leaderboard_size; i++)
        {
            SetIntInSave("lbScore" + i, Leaderboard.Instance.GetLeaderboardData(i).score);
            SetStringInSave("lbName" + i, Leaderboard.Instance.GetLeaderboardData(i).name);
        }
        SaveEditEnd();
    }

    public void UpdateLeaderboardInstance()
    {
        for (int i = 0; i < m_leaderboard_size; i++)
        {
            Leaderboard.Instance.ForceAddToLeaderboard(i, GetStringFromSave("lbName" + i), GetIntFromSave("lbScore" + i));
        }
    }
}
