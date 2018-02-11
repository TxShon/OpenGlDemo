package com.example.tanxiao.opengldemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by TX on 2018/2/8.
 * Class note:
 * OpenGl常用工具方法
 */

public class GlCommonUtil {

    private static final String TAG = GlCommonUtil.class.getSimpleName();


    /**
     * 创建shader程序
     *
     * @param vertexSource   顶点shader原语
     * @param fragmentSource 片元shader原语
     * @return
     */
    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        int fragShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (fragShader == 0) {
            return 0;
        }

        // Create a program object and store the handle to it.
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            Log.e(TAG, "Could not create program");
        }

        // Bind the vertex shader to the program.
        GLES20.glAttachShader(program, vertexShader);
        checkGlError("glAttachShader");

        // Bind the fragment shader to the program.
        GLES20.glAttachShader(program, fragShader);
        checkGlError("glAttachShader");

        // Link the two shaders together into a program.
        GLES20.glLinkProgram(program);

        // Get the link status.
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
        // If the link failed, delete the program.
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e(TAG, "Could not link program: ");
            Log.e(TAG, GLES20.glGetProgramInfoLog(program));
            GLES20.glDeleteProgram(program);
            program = 0;
        }
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragShader);
        return program;
    }

    /**
     * 创建shader
     *
     * @param shaderType shader类型
     * @param source     shader原语
     * @return
     */
    public static int loadShader(int shaderType, String source) {
        // Load in the vertex shader.
        int shader = GLES20.glCreateShader(shaderType);
        checkGlError("glCreateShader type=" + shaderType);

        // Pass in the shader source.
        GLES20.glShaderSource(shader, source);
        // Compile the shader.
        GLES20.glCompileShader(shader);
        // Get the compilation status.
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        // If the compilation failed, delete the shader.
        if (compiled[0] == 0) {
            Log.e(TAG, "Could not compile shader " + shaderType + ":");
            Log.e(TAG, " " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }
        return shader;
    }

    /**
     * 检查步骤错误
     *
     * @param op 步骤内容
     */
    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    /**
     * 从文件中读取shader原语
     *
     * @param context
     * @param shaderSource
     * @return
     */
    public static String readShaderFromSource(Context context, int shaderSource) {
        StringBuilder body = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            inputStream = context.getResources().openRawResource(shaderSource);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line=reader.readLine()) != null) {
                body.append(line);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("could not open resource: " + shaderSource, e);
        } catch (Resources.NotFoundException rfe) {
            throw new RuntimeException("Resource not found: " + shaderSource, rfe);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return body.toString();
    }

}
