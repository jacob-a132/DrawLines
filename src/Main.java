import java.awt.*;
import javax.swing.*;

public class Main {
	
	public Main(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		int p = 768;
		frame.setPreferredSize(new Dimension(p*16/9, p));
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String args[]){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				Main game = new Main();
			}
		});
	}
}
