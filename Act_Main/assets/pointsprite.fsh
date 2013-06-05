#ifdef GL_ES
precision highp float;
#endif 
uniform sampler2D tex0;

varying vec4 vColor;

void main() 
{
    gl_FragColor = texture2D(tex0, gl_PointCoord) * vColor;
}

