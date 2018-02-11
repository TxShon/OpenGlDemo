//顶点位置定义及使用
attribute vec4 a_Position;

void main(){

    gl_Position=a_Position;
    gl_PointSize=10.0;
}