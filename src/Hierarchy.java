import javax.swing.JFrame;

public class Hierarchy {
	
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View("Interactive Camera", model);
		view.init();

		Controller controller = new Controller(view);

	}
}
