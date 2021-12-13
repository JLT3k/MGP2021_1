package com.sdm.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    ShapeEntity Shape1 = new ShapeEntity();
    ShapeEntity Shape2 = new ShapeEntity();
    ShapeEntity Shape3 = new ShapeEntity();

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        RenderBackground.Create(); // This is da entity
        RenderTextEntity.Create(); // Da text
        Ball.Create();
        Shape1.Create();
        Shape2.Create();
        Shape3.Create();

        //SmurfEntity.Create();
        //StarEntity.Create();
        PausebuttonEntity.Create();
        PausetitleEntity.Create();
        //QuitbuttonEntity.Create();

        // Player.Create();
        // NPC.Create();
        // Example to include another Renderview for Pause Button
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);

    }

    @Override
    public void Update(float _dt) {
        // 3 shapes pos: 120, 540, 960
        EntityManager.Instance.Update(_dt);
        if (TouchManager.Instance.IsDown()) {
            //Example of touch on screen in the main game to trigger back to Main menu
            // StateManager.Instance.ChangeState("Mainmenu");
        }
    }
}



