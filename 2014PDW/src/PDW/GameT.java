package PDW;

import javax.swing.JFrame;


public class GameT extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameT() {

        add(new BoardT());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Tanks");

        setResizable(false);
 
    	setVisible(true);
    }

    public static void main(String[] args) {
        new GameT();
    }
}
