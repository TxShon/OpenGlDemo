//使用外部纹理必须支持此扩展
#extension GL_OES_EGL_image_external : require
precision mediump float;
//外部纹理采样器
uniform samplerExternalOES uTextureSampler;
varying vec2 vTextureCoord;

void main() {
    //获取此纹理（预览图像）对应坐标的颜色值
    gl_FragColor = texture2D(uTextureSampler, vTextureCoord);
}
