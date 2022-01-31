package com.sdm.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

// Created by TanSiewLan2021
// Updated by Muhammad Rifdi

public class MainGameSceneState implements StateBase {
    private int Index = 0, prevIndex = 0, numberOfShapes = 0, shapeIncrement = 0, ballIncrement = 0, Spawned = 1, NoOfBalls = 1;
    private boolean spawnExisting = false, turn = true;

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
        Spawned = 1;
        turn = true;
        for (int i = 0; i < 5; ++i)
            GameSystem.Instance.ball[i].Reset();
        for (int i = 0; i < 20; ++i)
            GameSystem.Instance.Shape[i].SetIsDone(true);
        GameSystem.Instance.SetIsPaused(false);
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
        Leaderboard.Instance.AddToLeaderboard("Default", GameSystem.Instance.GetPoints());
        GameSystem.Instance.UpdateLeaderboardSave();
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
        // Update balls timer for shooting of balls
        for (int i = 0; i < NoOfBalls; ++i) {
            if (!GameSystem.Instance.ball[i].GetTimerStart()) {
                GameSystem.Instance.ball[i].SetTimer((i * 0.5f));
            }
            GameSystem.Instance.ball[i].Update(_dt);
        }
        // Update all spawned shapes
        for (int i = 0; i < Spawned; ++i) {
            GameSystem.Instance.Shape[i].Update(_dt);
        }
        EntityManager.Instance.Update(_dt);
        spawnShapes();

        if (Spawned >= 20)
            Spawned = 20;

        // Reset all balls after last ball goes off screen
        if (GameSystem.Instance.ball[ballIncrement].GetPosY() > 2200) {
            ballIncrement++;
        }
        if (ballIncrement == NoOfBalls) {
            for (int i = 0; i < NoOfBalls; ++i)
                GameSystem.Instance.ball[i].Reset();
            ballIncrement = 0;
            turn = true;
        }

        // Respawn and reuse destroyed shapes
        for (int i = 0; i <= Index; ++i) {
            if (GameSystem.Instance.Shape[i].IsDone()) {
                Index = i;
                spawnExisting = true;
            }
        }

        if (turn) {
            // Increase number of balls based on points
            if (GameSystem.Instance.GetPoints() >= 40) {
                NoOfBalls = 5;
            } else if (GameSystem.Instance.GetPoints() >= 30) {
                NoOfBalls = 4;
            } else if (GameSystem.Instance.GetPoints() >= 20) {
                NoOfBalls = 3;
            } else if (GameSystem.Instance.GetPoints() >= 10) {
                NoOfBalls = 2;
            }
        }
    }

    private void spawnShapes()
    {
        // Check if ball reached bottom of screen before spawning and moving new shape
        if (turn){
            // Set shape number spawn random
            if (shapeIncrement == 0)
                numberOfShapes = new Random().nextInt(3) + 1;
            // Spawn 1 shape
            if (numberOfShapes == 1) {
                GameSystem.Instance.Shape[Index].Respawn();
                if (!spawnExisting)
                    Spawned++;
                Index = Spawned;
                spawnExisting = false;
                shapeIncrement = 3;
            }
            // Spawn 2 shapes
            else if (numberOfShapes == 2){
                GameSystem.Instance.Shape[Index].Respawn();
                if (shapeIncrement == 1) {
                    int shape_dist = new Random().nextInt(231) + 170;
                    if (GameSystem.Instance.Shape[prevIndex].GetPosX() + shape_dist > 920)
                        GameSystem.Instance.Shape[Index].SetPosX(GameSystem.Instance.Shape[prevIndex].GetPosX() - shape_dist);
                    else /*if (GameSystem.Instance.Shape[prevIndex].GetPosX() - 70 < 130)*/
                        GameSystem.Instance.Shape[Index].SetPosX(GameSystem.Instance.Shape[prevIndex].GetPosX() + shape_dist);
                }
                if (!spawnExisting)
                    Spawned++;
                if (shapeIncrement == 0) {
                    prevIndex = Index;
                    shapeIncrement++;
                }
                else
                    shapeIncrement = 3;
                Index = Spawned;
                spawnExisting = false;
            }
            // Spawn 3 shapes
            else if (numberOfShapes == 3){
                // Center shape range = 450 --- 600
                // Left shape range = 130 --- 300
                // Right shape range = 750 --- 950
                GameSystem.Instance.Shape[Index].Respawn();
                if (shapeIncrement == 0) {
                    int shape_dist = new Random().nextInt(151) + 450;
                    GameSystem.Instance.Shape[Index].SetPosX(shape_dist);
                }
                else if (shapeIncrement == 1) {
                    int shape_dist = new Random().nextInt(171) + 130;
                    GameSystem.Instance.Shape[Index].SetPosX(shape_dist);
                }
                else if (shapeIncrement == 2) {
                    int shape_dist = new Random().nextInt(201) + 750;
                    GameSystem.Instance.Shape[Index].SetPosX(shape_dist);
                }
                if (!spawnExisting)
                    Spawned++;
                Index = Spawned;
                spawnExisting = false;
                shapeIncrement++;
            }
            // Run shape go up animation and ball set turn to false
            if (shapeIncrement == 3) {
                for (int i = 0; i < Spawned; ++i) {
                    GameSystem.Instance.Shape[i].SetAnimation(true);
                }
                turn = false;
                shapeIncrement = 0;
            }
        }
    }
}





