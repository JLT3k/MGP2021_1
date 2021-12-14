package com.sdm.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021
// Updated by Muhammad Rifdi

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private int Index = 0;
    private int Spawned = 1;
    private int NoOfBalls = 1;
    private boolean spawnExisting = false;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        RenderBackground.Create(); // This is da entity
        RenderTextEntity.Create(); // Da text

        PausebuttonEntity.Create();
        PausetitleEntity.Create();
        QuitbuttonEntity.Create();

        // Example to include another Renderview for Pause Button

        GameSystem.Instance.ResetPoints();
        NoOfBalls = 1;
        GameSystem.Instance.ball[0].Reset();
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);
        for (int i = 0; i < NoOfBalls; ++i) {
            GameSystem.Instance.ball[i].Render(_canvas);
        }
        for (int i = 0; i < Spawned; ++i) {
            GameSystem.Instance.Shape[i].Render(_canvas);
        }
    }

    @Override
    public void Update(float _dt) {
        for (int i = 0; i < NoOfBalls; ++i) {
            if (!GameSystem.Instance.ball[i].GetTimerStart()) {
                GameSystem.Instance.ball[i].SetTimer((i * 0.5f));
            }
            GameSystem.Instance.ball[i].Update(_dt);
        }

        if (GameSystem.Instance.ball[NoOfBalls - 1].GetPosY() > 2000) {
            for (int i = 0; i < NoOfBalls; ++i) {
                GameSystem.Instance.ball[i].Reset();
            }
        }

        for (int i = 0; i < Spawned; ++i) {
            GameSystem.Instance.Shape[i].Update(_dt);
        }
        EntityManager.Instance.Update(_dt);

        if (GameSystem.Instance.ball[NoOfBalls - 1].GetTurn()){
            GameSystem.Instance.Shape[Index].Respawn();
            for (int i = 0; i < Spawned; ++i) {
                GameSystem.Instance.Shape[i].SetAnimation(true);
            }
            for (int i = 0; i < NoOfBalls; ++i) {
                GameSystem.Instance.ball[i].SetTurn(false);
            }
            if (!spawnExisting)
                Spawned++;
            Index = Spawned;
            spawnExisting = false;
        }

        if (Spawned >= 20) {
            Spawned = 20;
        }

        for (int i = 0; i <= Index; ++i) {
            if (GameSystem.Instance.Shape[i].IsDone()) {
                Index = i;
                spawnExisting = true;
            }
        }


        if (GameSystem.Instance.GetPoints() > 40){
            NoOfBalls = 5;
        }
        else if (GameSystem.Instance.GetPoints() > 30){
            NoOfBalls = 4;
        }
        else if (GameSystem.Instance.GetPoints() > 20){
            NoOfBalls = 3;
        }
        else if (GameSystem.Instance.GetPoints() > 10){
            NoOfBalls = 2;
        }

    }
}



