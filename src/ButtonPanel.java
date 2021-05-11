import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	private JLabel txtInfo;

	private String currentAction = "stop";
	private String messageFromRoberto = "[Insert info from Roberto]";

	private Socket s;
	private DataOutputStream dos;

	public ButtonPanel() {

		try 
		{
			btnForward = new JButton("🡅");
			btnLeft = new JButton("🡄");
			btnRight = new JButton("🡆");
			btnBackward = new JButton("🡇");
			btnShutDown = new JButton("Shutdown");
			txtInfo = new JLabel(messageFromRoberto, JLabel.CENTER);
			
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0,0,15,0);
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 3;
			add(txtInfo, c);
			
			c.fill = GridBagConstraints.NONE;
			c.insets = new Insets(0,0,0,0);
			c.gridwidth = 1;
			c.ipadx = 30;
			c.ipady = 30;
			c.gridx = 1;
			c.gridy = 1;
			add(btnForward, c);

			c.gridx = 0;
			c.gridy = 2;
			c.anchor = GridBagConstraints.LINE_END;
			add(btnLeft, c);
			
			c.gridx = 1;
			c.gridy = 2;
			c.anchor = GridBagConstraints.CENTER;
			add(btnBackward, c);
			
			c.gridx = 2;
			c.gridy = 2;
			c.anchor = GridBagConstraints.LAST_LINE_START;
			add(btnRight, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.PAGE_END;
			c.ipady = 0;
			c.insets = new Insets(15,0,0,0);
			c.gridwidth = 2;
			c.gridx = 1;
			c.gridy = 3;
			add(btnShutDown, c);
			
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
