package com.sdm.mgp2021_1;

// author: Ho Junliang

public class Vector3 {
    float x, y, z;
    Vector3() { x = y = z = 0.0f; }
    Vector3(float x, float y) { this.x = x; this.y = y; z = 0.0f; }
    Vector3(float x, float y, float z) { this.x = x; this.y = y; this.z = z; }

    Vector3 AddVector (Vector3 v){
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    Vector3 SubtractVector (Vector3 v){
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    Vector3 MultiplyVector(float var){
        return new Vector3(x * var, y * var, z * var);
    }

    float Dot(Vector3 rhs){
        return x * rhs.x + y * rhs.y + z * rhs.z;
    }

    Vector3 Normalised()
    {
        float d = Length();
        if (d <= 0.00001 && -d <= 0.00001)
            return new Vector3();
        return new Vector3(x / d, y / d, z / d);
    }

    float LengthSquared(){
        return x * x + y * y + z * z;
    }

    float Length(){
        return (float)Math.sqrt((LengthSquared()));
    }

}
