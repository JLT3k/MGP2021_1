package com.sdm.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private boolean next = false;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        RenderBackground.Create(); // This is da entity
        RenderTextEntity.Create(); // Da text
        GameSystem.Instance.Shape1.Create();
        GameSystem.Instance.ball.Create();

        //SmurfEntity.Create();
        //StarEntity.Create();
        PausebuttonEntity.Create();
        PausetitleEntity.Create();
        //QuitbuttonEntity.Create();

        // Player.Create();
        // NPC.Create();
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
        GameSystem.Instance.ball.Render(_canvas);
        GameSystem.Instance.Shape1.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {
        // 3 shapes pos: 120, 540, 960
        GameSystem.Instance.Shape1.Update(_dt);
        GameSystem.Instance.ball.Update(_dt);
        EntityManager.Instance.Update(_dt);

        if (GameSystem.Instance.ball.GetTurn()){
            //ShapeEntity.Create();
            GameSystem.Instance.Shape1.SetAnimation(true);
            GameSystem.Instance.ball.SetTurn(false);
        }

        if (TouchManager.Instance.IsDown()) {
            //Example of touch on screen in the main game to trigger back to Main menu
            // StateManager.Instance.ChangeState("Mainmenu");
        }
    }
}



