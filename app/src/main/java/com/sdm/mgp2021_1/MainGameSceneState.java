package com.sdm.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private int Index = 1;
    private int Spawned = 1;
    private int NoOfBalls = 1;

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
        // 3 shapes pos: 120, 540, 960
        for (int i = 0; i < NoOfBalls; ++i) {
            GameSystem.Instance.ball[i].Update(_dt);
        }
        for (int i = 0; i < Spawned; ++i) {
            GameSystem.Instance.Shape[i].Update(_dt);
        }
        EntityManager.Instance.Update(_dt);

        if (GameSystem.Instance.ball[NoOfBalls - 1].GetTurn()){
            GameSystem.Instance.Shape[Index].Create();
            for (int i = 0; i < Spawned; ++i) {
                GameSystem.Instance.Shape[i].SetAnimation(true);
            }
            for (int i = 0; i < NoOfBalls; ++i) {
                GameSystem.Instance.ball[i].SetTurn(false);
            }
            Spawned++;
            Index = Spawned;
        }

        if (Spawned >= 20) {
            Spawned = 20;
        }

        for (int i = 0; i < Spawned; ++i) {
            if (GameSystem.Instance.Shape[i].IsDone()) {
                Index = i;
            }
        }
    }
}



