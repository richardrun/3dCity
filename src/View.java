//import java.awt.FlowLayout;
import java.awt.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class View extends JFrame implements GLEventListener, ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GLCanvas glcanvas, glcanvasRight;
	Model model;
	private int WIDTH = 500;
	private int HEIGHT = 500;
	private double camX = 0, camY = 0, camZ = 1.5;
	private double upX = 0, upY = 1, upZ = 0;
	private double lookX = 0, lookY = 0, lookZ = 0;
	private GLU glu;
	private float rColor = 0, gColor = 0, bColor = 0;
	private double near = -10, far = 20.0;
	private double cameraAngleX = 0, cameraAngleY = 110;

    private double modelSize = 15;
    private int randomArray [][];

    int textureType=1;
    boolean startAnimation = false;
    boolean light0Open = false;

	public View(String title, Model model) {
		super(title);
		this.model = model;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		glcanvas.repaint();

	}

	public void display() {
		display(glcanvas);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, WIDTH, HEIGHT);

		// Set projection, view volume
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		//gl.glFrustum(-1.0, 1.0, -1.0, 1.0, near, far);
		gl.glOrtho(-modelSize, modelSize, -modelSize, modelSize, near, far );

		// Enable depth testing
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glClearColor(rColor, gColor, bColor, 0.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();

		// set camera

		// eye, center, up
		glu.gluLookAt(camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
		gl.glRotated(cameraAngleX, 0, 1, 0);
		gl.glRotated(cameraAngleY, 1, 0, 0);

        //lighting


        if(light0Open==true){
            gl.glEnable(GL2.GL_LIGHTING);
            gl.glEnable(GL2.GL_LIGHT0);
            gl.glEnable(GL2.GL_NORMALIZE);

            float[] ambientLight = { 0.2f, 0.2f, 0.2f,0f };
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);

            float[] diffuseLight = { 1f,1f,0f,0f };  // multicolor diffuse
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);

            float light_specular[] = { 0.8f, 0.8f, 0.8f, 1.0f };

            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_specular, 0);

            float light_position[] = { 1.0f, 1.0f, 1.0f, 1.0f };
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);

        }else {
            gl.glDisable(GL2.GL_LIGHTING);
            gl.glDisable(GL2.GL_LIGHT0);
            gl.glDisable(GL2.GL_NORMALIZE);
        }


		/*hw3,4
		//model.draw(gl,textureType);
        if(startAnimation==true){
            textureType++;
            if(textureType>8) textureType=1;
        }
		*/

		//hw5
		model.drawAxes(gl);
		model.drawSquareGrid(gl,20,20);
		model.drawQuad(gl, randomArray);
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		glu = GLU.createGLU(gl);

	}

	public void init() {
		// Initialize the canvas
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(this);
		glcanvas.setSize(WIDTH, HEIGHT);
        /*
        final FPSAnimator animator = new FPSAnimator(glcanvas, 30,true);

        if(startAnimation==true){
            animator.start();
        }else {
            animator.stop();
        }
        */

        glcanvasRight = new GLCanvas(capabilities);
		glcanvasRight.addGLEventListener(this);
		glcanvasRight.setSize(WIDTH, HEIGHT);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH+100, HEIGHT+150);
		//setLayout(new GridLayout(1,2));

		add(glcanvas,BorderLayout.CENTER);

		JPanel bottomPane = new JPanel(new FlowLayout());
		bottomPane.setPreferredSize(new Dimension(WIDTH,150));
		JLabel description = new JLabel(
				"<html> interactive listener:" +
						"<br> left click mouse drag: move the camera " +
						"<br> roll the wheel: zoom the camera" +
                        "<br> press Q to switch on/off light" +
                        "<br> press R to re-draw bulding with random height");
		bottomPane.add(description);
		add(bottomPane,BorderLayout.SOUTH);

		setVisible(true);
        buildRandomArray();

	}

	//hw5
    public void buildRandomArray(){
        randomArray = new int[3][50];
        Random ran = new Random();

        for(int i=0;i<3;i++){
            for(int j=0;j<50;j++){

                int x = ran.nextInt()%10;
                if(i==2){
                    x = Math.abs(x);
                }
                randomArray[i][j]=x;
            }
        }
    }



    //hw5 end

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		glcanvas.repaint();

	}

	public void cameraPan(double dx, double dy) {
		camX -= (dx/WIDTH) *2.0;
		lookX -= (dx/WIDTH) *2.0;
		
		camY += (dy/WIDTH) *2.0;
		lookY += (dy/WIDTH) *2.0;
		glcanvas.repaint();
		
	}

	public void cameraZoom(int clicks) {
        modelSize += clicks;
        System.out.println(camZ);
		glcanvas.repaint();
	}
	
	
	// Note, this is not trackball action, just quick and dirty rotation
	public void cameraRotate(double dx, double dy)
	{
		cameraAngleX += dx * 0.3;		
		cameraAngleY += dy * 0.3;
		glcanvas.repaint();
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

    public void switchLight(){
        light0Open = !light0Open;
    }

    public void setStartAnimation(){
        startAnimation = !startAnimation;
        final FPSAnimator animator = new FPSAnimator(glcanvas, 10,true);

        if(startAnimation==true){
            animator.start();
        }else {
            animator.stop();
        }
    }
}
