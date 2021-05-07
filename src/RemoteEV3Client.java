import java.awt.Dimension;

import javax.swing.JFrame;

public class RemoteEV3Client {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Controller for brick");
		frame.setSize(new Dimension(400,400));
		frame.setLocationRelativeTo(null);
		frame.add(new ButtonPanel());
		frame.setVisible(true);
	}

}
