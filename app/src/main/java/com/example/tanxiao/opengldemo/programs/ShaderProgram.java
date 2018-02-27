package com.example.tanxiao.opengldemo.programs;

import android.content.Context;

import com.example.tanxiao.opengldemo.utils.GlCommonUtil;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by TX on 2018/2/24.
 * Class note:
 */

public class ShaderProgram {

    // Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";

    // Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;

    public ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        program = GlCommonUtil.createProgram(
                GlCommonUtil.readShaderFromSource(context, vertexShaderResourceId)
                , GlCommonUtil.readShaderFromSource(context, fragmentShaderResourceId));
    }

    public void useProgram(){
        glUseProgram(program);
    }
}
