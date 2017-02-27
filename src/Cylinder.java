import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Cylinder implements Shape {
	private int slices = 20, stacks = 20;
	private double r = 1, g = 1, b = 1;
	private GL2 gl;
	private double radius = 1;
	private double height = 1;
	

	@Override
	public void draw() {
		GLU glu = GLU.createGLU(gl);
		gl.glColor3d(r, g, b);
		
		GLUquadric quadric = glu.gluNewQuadric();
		
		// Cylinder (quadric, baseRadius, topRadius, height, slices, stacks)
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_LINE);
		
		glu.gluCylinder(quadric, radius, radius, height, slices, stacks);
		
	}
	
	public Cylinder(GL2 gl, double radius, double height)
	{
		this.radius = radius;
		this.height = height;
		this.gl = gl;
	}

	@Override
	public void setColor(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;		
	}

}
