import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL.GL_SRC_ALPHA;

public class Model {
	private double lowerRotateAngle = 45;
	private double upperRotateAngle = 0;
	private double headRotateAngle = 180;
	private double bodyRotateAngle = 0;

    private boolean hairMove = true;
    private double hairRotateAngle = 0;
    private double hairX = 0;

    Texture texture = null;


	GL2 gl;
	
	public void draw(GL2 gl, int textureType)
	{
        //createTexture(gl,1);
        //System.out.println("test: "+ texture);

		drawAxes(gl);
		this.gl = gl;


        //drawQuad(gl);



		Quad body = new Quad(gl,0.5f, textureType);
		TreeNode bodyNode = new TreeNode(body);
		bodyNode.setTranslateVec(new Vec3D(0,0,0));
		bodyNode.setRotateVec(new Vec3D(0,1,0));
		bodyNode.setRotateAngle(bodyRotateAngle);

        Quad head = new Quad(gl,0.25f, textureType+1);
		TreeNode headNode = new TreeNode(head);
		headNode.setRotateVec(new Vec3D(0.0, 0, 1.0));
		headNode.setRotateAngle(headRotateAngle);
		headNode.setTranslateVec(new Vec3D(0,0,-0.75));

		Cylinder hair = new Cylinder(gl, 0.02, 0.25);
		hair.setColor(1,  1,  1);
		TreeNode hairNode = new TreeNode(hair);
		hairNode.setTranslateVec(new Vec3D(hairX,0,-0.25));
        hairNode.setRotateVec(new Vec3D(0,1,0));
        hairNode.setRotateAngle(hairRotateAngle+180);

		Cylinder hair2 = new Cylinder(gl, 0.02, 0.25);
		hair2.setColor(1,  1,  1);
		TreeNode hair2Node = new TreeNode(hair2);
		hair2Node.setTranslateVec(new Vec3D(hairX + 0.1,0,-0.25));
        hair2Node.setRotateVec(new Vec3D(0,1,0));
        hair2Node.setRotateAngle(hairRotateAngle+180);

		Cylinder hair3 = new Cylinder(gl, 0.02, 0.25);
		hair3.setColor(1,  1,  1);
		TreeNode hair3Node = new TreeNode(hair3);
		hair3Node.setTranslateVec(new Vec3D(hairX-0.1,0,-0.25));
        hair3Node.setRotateVec(new Vec3D(0,1,0));
        hair3Node.setRotateAngle(hairRotateAngle+180);



        /*
        Quad head2 = new Quad(gl,0.25f, 10);
        TreeNode headNode2 = new TreeNode(head2);
        headNode2.setRotateVec(new Vec3D(1.5, 0, 0));
        headNode2.setRotateAngle(headRotateAngle);
        headNode2.setTranslateVec(new Vec3D(1,0,-0.75));
        */

		bodyNode.addChild(headNode);

		headNode.addChild(hairNode);
		headNode.addChild(hair2Node);
		headNode.addChild(hair3Node);
		bodyNode.draw(gl);

        //Square2d tri = new Square2d();
        drawTri2d(gl);

        //texture.disable(gl);
	}
	
	public void drawAxes(GL2 gl) {
		// drawing the axes
		float offset = .75f;

			gl.glBegin(GL2.GL_LINES);
			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glVertex3f(-offset, 0, 0);
			gl.glVertex3f(offset, 0, 0);
			gl.glColor3d(0.0, 1.0, 0.0);
			gl.glVertex3f(0, -offset, 0);
			gl.glVertex3f(0, offset, 0);
			gl.glColor3d(0.0, 0, 1.0);
			gl.glVertex3f(0, 0, -offset);
			gl.glVertex3f(0, 0, offset);
		gl.glEnd();


	}

	public void drawQuad(GL2 gl, int[][] quantity){

        Random ran = new Random();

        for(int i=0;i<quantity[0].length;i++){
            gl.glPushMatrix();
            gl.glTranslated(quantity[0][i],quantity[1][i],0);
            Quad building = new Quad(gl,0.5f, 9, quantity[2][i]);
            building.draw();

            gl.glPopMatrix();
        }


	}

    public void drawTri2d(GL2 gl){

        gl.glEnable(GL_BLEND);

        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        gl.glTranslatef(0f,1,0);
        gl.glRotatef(90,1,0,0);
        //gl.glPopMatrix();
        gl.glBegin (GL2.GL_TRIANGLE_STRIP);

        gl.glColor4d(1,1,0,0.7);
        gl.glVertex3f(0f,0.5f,0);
        gl.glVertex3f(-0.50f,-0.75f,0);
        gl.glVertex3f(0.28f,0.06f,0);
        gl.glVertex3f(0.7f,0.5f,0);
        gl.glVertex3f(0.7f,-0.7f,0);

        gl.glEnd();

        //gl.glPushMatrix();
    }


    public void drawSquareGrid(GL2 gl, int width, int length){
		//start at
        gl.glPushMatrix();
		gl.glTranslatef(-10f,-10f,0);

		for(int i=0;i<width;i++){
			gl.glTranslatef(0,1,0);
			for(int j=0;j<length;j++){
				//gl.glTranslatef(-10f,-10f,0);
				gl.glTranslatef(1,0,0);
				drawSquareGridHelper(gl);
			}
			gl.glTranslatef(-length,0,0);

		}

		gl.glPopMatrix();
	}

	public void drawSquareGridHelper(GL2 gl){
		gl.glBegin (GL2.GL_LINE_LOOP);

		gl.glColor3d(1,0,0);
		gl.glVertex3d(0.5,0.5,0);
		gl.glVertex3d(0.5,-0.5,0);
		gl.glVertex3d(-0.5,-0.5,0);
		gl.glVertex3d(-0.5,0.5,0);

		gl.glEnd();
	}

	public void setUpperRotate(double dx, double dy)
	{
		upperRotateAngle += dx;
	}
	
	public void setLowerRotate(double dx, double dy)
	{
		lowerRotateAngle += dx;
	}

	public void setHeadRotate(double dx, double dy)
	{
		headRotateAngle += dx;
	}

	public void setBodyRotate(double dx, double dy)
	{
		bodyRotateAngle += dx;
	}

	public void hairSwipe(){

        if(hairMove==true){
            hairRotateAngle -=4;
            if(hairRotateAngle<-20) hairMove=false;
        }else if(hairMove==false){
            hairRotateAngle+=4;
            if(hairRotateAngle>20) hairMove=true;
        }
    }

}
