//顶点坐标
attribute vec4 aPosition;
// MVP 的变换矩阵（整体变形）
uniform mat4 uMVPMatrix;
//纹理矩阵
uniform mat4 uTextureMatrix;
//自己定义的纹理坐标
attribute vec4 aTextureCoordinate;
//传给片段着色器的纹理坐标
varying vec2 vTextureCoord;

void main() {
    //根据自己定义的纹理坐标和纹理矩阵求取传给片段着色器的纹理坐标
    vTextureCoord = (uTextureMatrix * aTextureCoordinate).xy;
    gl_Position = uMVPMatrix*aPosition;
}
