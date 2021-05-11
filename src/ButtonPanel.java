import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnForward;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnBackward;
	private JButton btnShutDown;

	private String currentAction = "stop";

	private Socket s;
	private DataOutputStream dos;

	public ButtonPanel() {

		try 
		{	
			socket_singleton socket = socket_singleton.getSocketInstance();
			s = socket.socket;
			dos = socket.dataOut;

			setLayout(new GridLayout(3, 3));
			btnForward = new JButton("^");
			btnLeft = new JButton("<");
			btnRight = new JButton(">");
			btnBackward = new JButton("v");
			btnShutDown = new JButton("ShutDown");

			add(new JLabel(""));
			add(btnForward);
			add(new JLabel(""));

			add(btnLeft);
			add(btnShutDown);
			add(btnRight);

			add(new JLabel(""));
			add(btnBackward);
			add(new JLabel(""));

			btnForward.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (currentAction == "forward")
						return;
					sendCommand("forward");
					currentAction = "forward";
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					currentAction = "stop";
					sendCommand("stop");
					currentAction = "stop";
				}

			});

			btnLeft.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (currentAction == "turnleft")
						return;
					sendCommand("turnleft");
					currentAction = "turnleft";
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					currentAction = "stop";
					sendCommand("stop");
				}
			});

			btnRight.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (currentAction == "turnright")
						return;
					sendCommand("turnright");
					currentAction = "turnright";
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					currentAction = "stop";
					sendCommand("stop");
					currentAction = "stop";
				}
			});

			btnBackward.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (currentAction == "backward")
						return;
					sendCommand("backward");
					currentAction = "backward";
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					currentAction = "stop";
					sendCommand("stop");
					currentAction = "stop";
				}
			});

			btnShutDown.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					sendCommand("shutdown");
					System.exit(0);
				}

			});
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "RemoveEV3Client - ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void sendCommand(String command) {
		try {
			dos.writeUTF(command);
			dos.flush();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "RemoteEV3Client - ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
