package com.sdm.mgp2021_1;

// Created by TanSiewLan2021

public class Collision {

    public static boolean SphereToSphere(float x1, float y1, float radius1, float x2, float y2, float radius2)
    {
        float xVec = x2 - x1;
        float yVec = y2 - y1;

        float distSquared = xVec * xVec + yVec * yVec;

        float rSquared = radius1 + radius2;
        rSquared *= rSquared;

        if (distSquared > rSquared)
            return false;

        return true;
    }

    public static boolean SphereToSphere(PhysicsObject v1, PhysicsObject v2)
    {
        float distSquared = v2.GetPos().SubtractVector(v1.GetPos()).LengthSquared();
        float rSquared = v1.GetRadius() + v2.GetRadius();
        rSquared *= rSquared;

        if (distSquared > rSquared)
            return false;
        Vector3 u = v1.GetVel();
        Vector3 p_b = v2.GetPos().SubtractVector(v1.GetPos());
        float r1 = v1.GetRadius(), r2 = v2.GetRadius();
        return ((p_b.Length() < r1 + r2) && (p_b.Dot(u) > 0));  // prevent internal Collision
    }
}
