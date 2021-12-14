package com.sdm.mgp2021_1;

// Created by Ho Junliang

public interface PhysicsObject {
    String GetPhysicsType();

    Vector3 GetPos();
    Vector3 GetVel();

    float GetRadius();

    void OnHit(PhysicsObject _other);
}

