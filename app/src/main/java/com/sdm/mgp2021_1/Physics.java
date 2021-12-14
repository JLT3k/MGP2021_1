package com.sdm.mgp2021_1;

// Created by Ho Junliang

public class Physics {
    float normalAngle;

    /** @Brief
     * for ball colliding with a circle object
     * @param ball object affected by physics
     * @param pillar stationary object
     * @return a Vector3 which is the new velocity of the ball
     */
    public static Vector3 BallToPillar(PhysicsObject ball, PhysicsObject pillar)
    {
        Vector3 normal = (ball.GetPos().SubtractVector(pillar.GetPos())).Normalised();
        Vector3 u = ball.GetVel();
        Vector3 newVel = ball.GetVel();
        newVel = u.SubtractVector(normal.MultiplyVector(u.MultiplyVector(2).Dot(normal)));
        newVel = newVel.MultiplyVector(GameSystem.inelastic_k);
        return newVel;
    }
}
