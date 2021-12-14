package com.sdm.mgp2021_1;

// Created by Ho Junliang

public class Physics {
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
//        Vector3 p_b = pillar.GetPos().SubtractVector(ball.GetPos());
//        float r1 = ball.GetRadius(), r2 = pillar.GetRadius();
//        if ((p_b.Length() < r1 + r2) && (p_b.Dot(u) > 0))  // prevent internal Collision
//        {
            Vector3 newVel = u.SubtractVector(normal.MultiplyVector(u.MultiplyVector(2).Dot(normal)));
            newVel = newVel.MultiplyVector(GameSystem.inelastic_k);
            return newVel;
//        }
//        return ball.GetVel();
    }

    public static Vector3 UpdateGravity(Vector3 vel, float dt)
    {
        return new Vector3(vel.x, vel.y += GameSystem.m_gravity * dt);
    }

    public static Vector3 UpdatePosition(PhysicsObject obj, float dt)
    {
        Vector3 pos = obj.GetPos(), vel = obj.GetVel();
        if (vel.Length() > GameSystem.m_terminal_vel)
        {
            vel = vel.Normalised().MultiplyVector(GameSystem.m_terminal_vel);
        }
        return new Vector3(pos.x + vel.x * dt, pos.y + vel.y * dt, pos.z + vel.z * dt);
    }
}
