package com.example.tanxiao.opengldemo.objects;

import com.example.tanxiao.opengldemo.data.VertexArray;
import com.example.tanxiao.opengldemo.programs.TextureShaderProgram;
import com.example.tanxiao.opengldemo.utils.Constants;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by TX on 2018/2/24.
 * Class note:
 * 桌子
 */

public class Table {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT +
            TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, S, T
            // Triangle Fan
            0f, 0f,
            0.5f, 0.5f,

            -0.5f, -0.8f,
            0f, 0.9f,

            0.5f, -0.8f,
            1f, 0.9f,

            0.5f, 0.8f,
            1f, 0.1f,

            -0.5f, 0.8f,
            0f, 0.1f,

            -0.5f, -0.8f,
            0f, 0.9f
    };

    private final VertexArray vertexArray;

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);

    }

    public void bindData(TextureShaderProgram textureShaderProgram) {
        vertexArray.setVertexAttributePointer(
                0,
                textureShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        vertexArray.setVertexAttributePointer(
                POSITION_COMPONENT_COUNT,
                textureShaderProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
