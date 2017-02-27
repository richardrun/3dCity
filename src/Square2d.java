import com.jogamp.opengl.GL2;

/**
 * Created by richa on 2/8/2017.
 */
public class Square2d{
    //GL2 gl;

    public Square2d(){

        //this.gl = gl;
    }

    public void draw(GL2 gl){

        gl.glTranslatef(5f,0,0);
        gl.glPopMatrix();
        gl.glBegin (GL2.GL_TRIANGLE_STRIP);

        gl.glColor3d(1,0,0);
        gl.glVertex3f(0f,0.5f,0);
        gl.glVertex3f(-0.50f,-0.75f,0);
        gl.glVertex3f(0.28f,0.06f,0);
        gl.glVertex3f(0.7f,0.5f,0);
        gl.glVertex3f(0.7f,-0.7f,0);

        gl.glEnd();

        gl.glPushMatrix();
    }

}
