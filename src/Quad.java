import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by richa on 1/31/2017.
 */
public class Quad implements Shape{
    private float size;
    private float tall;
    private double r = 1, g = 1, b = 1;
    private GL2 gl;

    Texture texture;
    int textureType;

    public Quad(GL2 gl, float size, int type){
        this.size = size;
        this.tall = size;
        this.gl = gl;
        this.textureType = type;

    }

    public Quad(GL2 gl, float size, int type, int tall){
        this.size = size;
        this.tall = tall;
        this.gl = gl;
        this.textureType = type;

    }

    @Override
    public void draw() {

        createTexture(gl,textureType);
        texture.enable(gl);
        //texture.bind(gl);
        //texture.setTexParameteri(gl, GL2.GL_TEXTURE_2D, arg2);

        gl.glColor3d(1,1,1);
        //gl.glPushMatrix();
        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glNormal3f(0.0f, 0.0f, size);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-size, -size, 0);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(size, -size, 0);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(size, size, 0);
        gl.glTexCoord2f(0.0f, 1f);
        gl.glVertex3f(-size, size, 0);
        // Back Face
        gl.glNormal3f(0.0f, 0.0f, -size);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(-size, -size, -tall);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(-size, size, -tall);
        gl.glTexCoord2f(0.0f, 1f);
        gl.glVertex3f(size, size, -tall);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(size, -size, -tall);
        // Top Face
        gl.glNormal3f(0.0f, size, 0.0f);
        gl.glTexCoord2f(0.0f, 1f);
        gl.glVertex3f(-size, size, -tall);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-size, size, 0);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(size, size, 0);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(size, size, -tall);
        // Bottom Face
        gl.glNormal3f(0.0f, -size, 0.0f);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(-size, -size, -tall);
        gl.glTexCoord2f(0.0f, 1f);
        gl.glVertex3f(size, -size, -tall);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(size, -size, 0);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(-size, -size, 0);
        // Right face
        gl.glNormal3f(size, 0.0f, 0.0f);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(size, -size, 0);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(size, -size, -tall);  //top corner
        gl.glTexCoord2f(0.0f, 1f);
        gl.glVertex3f(size, size, -tall);   //top corner
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(size, size, 0);
        // Left Face
        gl.glNormal3f(-size, 0.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-size, -size, -tall);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(-size, -size, 0);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(-size, size, 0);
        gl.glTexCoord2f(0.0f, 1f);
        gl.glVertex3f(-size, size, -tall);
        gl.glEnd();
        //gl.glPopMatrix();

        texture.disable(gl);
    }

    @Override
    public void setColor(double r, double g, double b) {

    }

    public void createTexture(GL2 gl, int img)
    {

        String imgPath = "res/chess.jpg";

        switch (img){
            case 1: imgPath = "res/chess.jpg";
                break;
            case 2: imgPath = "res/chrome.jpg";
                break;
            case 3: imgPath = "res/glass.jpg";
                break;
            case 4: imgPath = "res/gold.jpg";
                break;
            case 5: imgPath = "res/marble.jpg";
                break;
            case 6: imgPath = "res/mercedes.jpg";
                break;
            case 7: imgPath = "res/outline.jpg";
                break;
            case 8: imgPath = "res/satin.jpg";
                break;
            case 9: imgPath = "res/building1.jpg";
                break;
            default:
                break;
        }



        GLU glu = GLU.createGLU(gl);

        try {
            String filename = imgPath;
            FileInputStream stream = new FileInputStream(new File(filename));
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream,  false,  "jpg");

            texture = TextureIO.newTexture(data);

            //System.out.println("texture width, height: " + texture.getWidth() + " " + texture.getHeight());

            gl.glEnable(GL2.GL_NORMALIZE);
            gl.glEnable(GL2.GL_COLOR_MATERIAL);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D,  GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D,  GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
            gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

            //glu.gluBuild2DMipmaps(GL2.GL_TEXTURE_2D, GL2.GL_RGB, texture.getWidth(), texture.getHeight(),GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, texture);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}
