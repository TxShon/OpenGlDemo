package com.example.tanxiao.opengldemo.programs;

import android.content.Context;

import com.example.tanxiao.opengldemo.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by TX on 2018/2/24.
 * Class note:
 */

public class ColorShaderProgram extends ShaderProgram {

    // Uniform locations
    private final int uMatrixLocation;
    private final int uColorLocation;

    // Attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context, int vertexShaderResource, int fragmentShaderResource) {
        super(context, vertexShaderResource, fragmentShaderResource);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
    }

    public ColorShaderProgram(Context context) {
        this(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);
    }

    public void setUniforms(float[] matrix) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public void setUniforms(float[] matrix, float r, float g, float b) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glUniform4f(uColorLocation, r, g, b, 1f);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getColorAttributeLocation() {
        return aColorLocation;
    }
}
