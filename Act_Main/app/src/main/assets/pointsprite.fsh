#ifdef GL_ES
precision highp float;
#endif 
uniform sampler2D tex0;

varying vec4 vColor;

void main() 
{
    //vColor vient du vertexShader
    //dans le vertexShader, vColor est alimenté avec aColor
    //aColor reçoit des valeurs de la part su programme Java 
    gl_FragColor = texture2D(tex0, gl_PointCoord) * vColor;
}

