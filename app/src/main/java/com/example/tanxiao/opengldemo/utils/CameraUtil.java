package com.example.tanxiao.opengldemo.utils;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import java.io.IOException;

/**
 * Created by TX on 2018/2/8.
 * Class note:
 */

public class CameraUtil {

    public static Camera openCamera(SurfaceTexture surfaceTexture) {

        if (surfaceTexture == null) {
            return null;
        }

        Camera camera = null;
        try {
            camera = Camera.open();
            int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            camera = Camera.open(mCameraId);
            Camera.Parameters parameters = camera.getParameters();
            parameters.set("orientation", "portrait");
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            parameters.setPreviewSize(1280, 720);
            camera.setDisplayOrientation(0);
            camera.setParameters(parameters);
            camera.setPreviewTexture(surfaceTexture);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return camera;

    }
}
