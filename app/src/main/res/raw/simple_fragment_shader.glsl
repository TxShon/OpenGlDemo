//uniform 定义及使用
precision mediump float;

varying vec4 v_Color;

void main()
{
    gl_FragColor = v_Color;
}