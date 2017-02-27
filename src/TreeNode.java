import java.util.ArrayList;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.texture.Texture;


/*
 * Node for a SceneGraph tree.
 * Simplified, in that transformations are included with geometry.
 * Assumes rotation before translation.
 * Appearance and geometry are within draw method.
 */
public class TreeNode {
	private ArrayList<TreeNode> children = new ArrayList<TreeNode>();
	private TreeNode sibling = null;
	private Vec3D translateVec = new Vec3D();
	private Vec3D rotateVec = new Vec3D();
	private double rotateAngle = 0;
	private Shape shape;
	private Texture texture = null;
	
	public TreeNode(Shape shape)
	{
		this.shape = shape;
	}
	
	public Vec3D getTranslateVec() {
		return translateVec;
	}

	public void setTranslateVec(Vec3D translateVec) {
		this.translateVec = translateVec;
	}

	public Vec3D getRotateVec() {
		return rotateVec;
	}

	public void setRotateVec(Vec3D rotateVec) {
		this.rotateVec = rotateVec;
	}

	public void setRotateAngle(double angle)
	{
		rotateAngle = angle;
	}
	
	
	/*
	 * Pre-order traversal, drawing children last
	 */
	public void draw(GL2 gl)
	{
		//this.texture = texture;
		//this.texture.enable(gl);

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW); 
		gl.glPushMatrix();
		gl.glTranslated(translateVec.x, translateVec.y, translateVec.z);
		gl.glRotated(rotateAngle, rotateVec.x, rotateVec.y, rotateVec.z);
		
		shape.draw();
		
		for (TreeNode node: children)
		{
			node.draw(gl);
		}
		
		gl.glPopMatrix();
		
	}
	
	
	public void addChild(TreeNode node)
	{
		if (!children.isEmpty())
			children.get(children.size() - 1).sibling = node;
		children.add(node);
	}

}
