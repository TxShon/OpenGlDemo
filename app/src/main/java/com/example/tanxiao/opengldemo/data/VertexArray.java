package com.example.tanxiao.opengldemo.data;

import android.opengl.GLES20;

import com.example.tanxiao.opengldemo.utils.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by TX on 2018/2/24.
 * Class note:
 * 顶点数据
 */

public class VertexArray {

    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData) {
        floatBuffer = ByteBuffer
                .allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    /**
     * 设置顶点数据
     *
     * @param offset
     * @param attributeLocation
     * @param componentCount
     * @param stride
     */
    public void setVertexAttributePointer(int offset, int attributeLocation,
                                          int componentCount, int stride) {
        floatBuffer.position(offset);
        glVertexAttribPointer(attributeLocation, componentCount,
                GLES20.GL_FLOAT, false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);

    }

}
