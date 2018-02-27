package com.example.tanxiao.opengldemo.objects;

import com.example.tanxiao.opengldemo.data.GenerateData;
import com.example.tanxiao.opengldemo.data.VertexArray;
import com.example.tanxiao.opengldemo.drawcommand.DrawCommand;
import com.example.tanxiao.opengldemo.geometry.Point;
import com.example.tanxiao.opengldemo.programs.ColorShaderProgram;

import java.util.List;

/**
 * Created by TX on 2018/2/27.
 * Class note:
 * 实物mallet
 */

public class NewMallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius, height;

    private final VertexArray vertexArray;

    private final List<DrawCommand> drawList;

    public NewMallet(float radius, float height, int puckPoints) {
        this.radius = radius;
        this.height = height;

        GenerateData generateData = ObjectBuilder.createMallet(
                new Point(0f, 0f, 0f), radius, height, puckPoints);

        this.vertexArray = new VertexArray(generateData.getVertexData());
        this.drawList = generateData.getDrawCommands();
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttributePointer(
                0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                0);
        /*vertexArray.setVertexAttributePointer(
                POSITION_COMPONENT_COUNT,
                colorProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE);*/
    }

    public void draw() {
        for (DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }

}
