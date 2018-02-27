package com.example.tanxiao.opengldemo.geometry;

/**
 * Created by TX on 2018/2/26.
 * Class note:
 */

public class Cylinder {
    public final Point center;
    public final float radius;
    public final float height;

    public Cylinder(Point point, float radius, float height) {
        this.center = point;
        this.radius = radius;
        this.height = height;
    }
}
