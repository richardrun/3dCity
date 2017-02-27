import java.awt.event.*;

import javax.swing.SwingUtilities;

public class Controller {
	View view;
	Model model;
	MouseListener mouseListener;
	int mouseStartX = 0;
	int mouseStartY = 0;
	private boolean hairMove = false;

	public Controller(View view)
	{
		this.view = view;
		mouseListener = new GLCanvasMouseAdapter();   
		model = view.getModel();

		view.glcanvas.addMouseListener(mouseListener); 	
		view.glcanvas.addMouseMotionListener((MouseMotionListener) mouseListener);
		view.glcanvas.addMouseWheelListener((MouseWheelListener)mouseListener);
        view.glcanvas.addKeyListener((KeyListener) mouseListener);
	}
	
	public class GLCanvasMouseAdapter implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent event) {
			mouseStartX = event.getX();
			mouseStartY = event.getY();

		}

		@Override
		public void mouseReleased(MouseEvent event) {
//			int diff = event.getX() - mouseStartX;
//			double dx = ((double)diff)/ view.getWIDTH();
//			view.cameraPan(dx, 0);
		}

		@Override
		public void mouseMoved(MouseEvent event) {			
		}


		@Override
		public void mouseDragged(MouseEvent event) {
			double dx = event.getX() - mouseStartX;
			double dy = event.getY() - mouseStartY;
			
			if (SwingUtilities.isRightMouseButton(event))
			 {
				if (!event.isControlDown())
					model.setHeadRotate(dx, dy);
				else 
					model.setUpperRotate(dx, dy);
				view.glcanvas.repaint();
			
			}
			else if (SwingUtilities.isLeftMouseButton(event))
			{
				view.cameraRotate(dx, dy);
				
			}			
			mouseStartX = event.getX();
			mouseStartY = event.getY();
		}


		@Override
		public void mouseWheelMoved(MouseWheelEvent event) {
			int clicks = event.getWheelRotation();

			/* hw3,4
			if(clicks>0){
				model.setBodyRotate(5,0);
			}else model.setBodyRotate(-5,0);

			view.glcanvas.repaint();
			*/
			System.out.println(clicks);
			view.cameraZoom(clicks);
		}

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
			//System.out.println(key);
            switch( key ) {
                case KeyEvent.VK_UP:
                    // handle up
                    model.hairSwipe();
                    view.glcanvas.repaint();

                    break;
                case KeyEvent.VK_DOWN:
                    // handle down
                    break;
                case KeyEvent.VK_LEFT:
                    // handle left
                    break;
                case KeyEvent.VK_RIGHT :
                    // handle right
                    break;
				case KeyEvent.VK_Q:
					view.switchLight();
					view.glcanvas.repaint();
					break;
				//case KeyEvent.VK_W:
				//	view.setStartAnimation();
				//	view.init();
				//	break;
                case KeyEvent.VK_R:
                    view.buildRandomArray();
                    view.glcanvas.repaint();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


}
