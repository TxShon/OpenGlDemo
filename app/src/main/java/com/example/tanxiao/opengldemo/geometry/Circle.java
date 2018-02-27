package com.example.tanxiao.opengldemo.geometry;

/**
 * Created by TX on 2018/2/26.
 * Class note:
 */

public class Circle {

    public final Point center;
    public final float radius;

    public Circle(Point point, float radius) {
        this.center = point;
        this.radius = radius;
    }


    public Circle scale(float scale) {
        return new Circle(center, radius * scale);
    }

}
