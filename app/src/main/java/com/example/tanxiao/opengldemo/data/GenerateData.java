package com.example.tanxiao.opengldemo.data;

import com.example.tanxiao.opengldemo.drawcommand.DrawCommand;

import java.util.List;

/**
 * Created by TX on 2018/2/26.
 * Class note:
 */

public class GenerateData {

   private final float[] vertexData;
   private final List<DrawCommand> drawCommands;

    public GenerateData(float[] vertexData, List<DrawCommand> drawCommands) {
        this.vertexData = vertexData;
        this.drawCommands = drawCommands;
    }

    public float[] getVertexData() {
        return vertexData;
    }

    public List<DrawCommand> getDrawCommands() {
        return drawCommands;
    }
}
