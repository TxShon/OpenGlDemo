package com.example.tanxiao.opengldemo.objects;

import com.example.tanxiao.opengldemo.data.GenerateData;
import com.example.tanxiao.opengldemo.drawcommand.DrawCommand;
import com.example.tanxiao.opengldemo.geometry.Circle;
import com.example.tanxiao.opengldemo.geometry.Cylinder;
import com.example.tanxiao.opengldemo.geometry.Point;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by TX on 2018/2/26.
 * Class note:
 */

public class ObjectBuilder {

    private static final int FLOATS_PER_VERTEX = 3;
    private final float[] vertexData;
    private int offset = 0;
    private List<DrawCommand> drawCommands = new ArrayList<>();

    public ObjectBuilder(int sizeInVertices) {
        this.vertexData = new float[sizeInVertices * FLOATS_PER_VERTEX];
    }

    /**
     * 返回圆所需要的顶点总数
     * 三角扇需要加上圆心以及重复的第一个顶点
     *
     * @param numPoints
     * @return
     */
    public static int sizeOfCircleInVertices(int numPoints) {
        return numPoints + 2;
    }

    /**
     * 返回圆柱侧面的顶点总数
     * 侧面使用三角带，可以看成由上下两个圆构成，每个圆的第一个顶点需重复使用一次
     *
     * @param numPoints
     * @return
     */
    public static int sizeOfOpenCylinderInVertices(int numPoints) {
        return (numPoints + 1) * 2;
    }

    /**
     * 创建冰球
     *
     * @param puck
     * @param numPoints
     * @return
     */
    public static GenerateData createPuck(Cylinder puck, int numPoints) {
        int size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints);

        ObjectBuilder builder = new ObjectBuilder(size);

        Circle puckTop = new Circle(puck.center.translateY(puck.height / 2), puck.radius);

        builder.appendCircle(puckTop, numPoints);
        builder.appendOpenCylinder(puck, numPoints);

        return builder.build();
    }

    /**
     * 创建木槌
     * @param center
     * @param radius
     * @param height
     * @param numPoints
     * @return
     */
    public static GenerateData createMallet(Point center, float radius,
                                            float height, int numPoints) {
        int size = sizeOfCircleInVertices(numPoints) * 2
                + sizeOfOpenCylinderInVertices(numPoints) * 2;

        ObjectBuilder builder = new ObjectBuilder(size);

        float baseHeight = height * 0.25f;

        //基座
        Circle baseCircle = new Circle(center.translateY(-baseHeight), radius);
        Cylinder baseCylinder = new Cylinder(baseCircle.center.translateY(-baseHeight / 2f),
                radius, baseHeight);

        builder.appendCircle(baseCircle, numPoints);
        builder.appendOpenCylinder(baseCylinder, numPoints);

        //手柄
        float handleHeight = height * 0.75f;
        float handleRadius = radius / 3f;

        Circle handleCircle = new Circle(center.translateY(height * 0.5f), handleRadius);
        Cylinder handleCylinder = new Cylinder(handleCircle.center.translateY(-handleHeight / 2f),
                handleRadius, handleHeight);

        builder.appendCircle(handleCircle, numPoints);
        builder.appendOpenCylinder(handleCylinder,numPoints);

        return builder.build();
    }

    /**
     * 添加圆柱侧面坐标
     *
     * @param cylinder
     * @param numPoints
     */
    private void appendOpenCylinder(Cylinder cylinder, int numPoints) {

        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfOpenCylinderInVertices(numPoints);

        final float yStart = cylinder.center.y - (cylinder.height / 2f);
        final float yEnd = cylinder.center.y + (cylinder.height / 2f);

        for (int i = 0; i <= numPoints; i++) {
            float angle = ((float) i / (float) numPoints) * ((float) Math.PI * 2);
            float xPosition = cylinder.center.x + cylinder.radius * (float) Math.cos(angle);
            float zPosition = cylinder.center.z + cylinder.radius * (float) Math.sin(angle);

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPosition;
        }

        drawCommands.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });
    }

    /**
     * 添加圆坐标
     *
     * @param circle
     * @param numPoints
     */
    private void appendCircle(Circle circle, final int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfCircleInVertices(numPoints);
        //center
        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        for (int i = 0; i <= numPoints; i++) {
            float angle = ((float) i / (float) numPoints) * ((float) Math.PI * 2);
            vertexData[offset++] = circle.center.x + circle.radius * (float) Math.cos(angle);
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = circle.center.z + circle.radius * (float) Math.sin(angle);

        }

        drawCommands.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_FAN, startVertex, numVertices);
            }
        });
    }

    private GenerateData build() {
        return new GenerateData(vertexData, drawCommands);
    }
}
