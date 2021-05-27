import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnForward;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnBackward;
	private JButton btnShutDown;
	private JLabel txtInfoTitel;
	private JLabel txtInfo;
	private JLabel batteryInfo;

	private String currentAction = "stop";

	private String messageTitel = "Roberto siger:";
	private String messageFromRoberto = "Klar til eventyr!";
	private String stringCheckBatteryinfo = "Batteri";
	private String batteryLevel = "Batteri: ?▮▮▯?";

	private String FORWARD = "forward";
	private String BACKWARD = "backward";
	private String TURNLEFT = "turnleft";
	private String TURNRIGHT = "turnright";
	
	private Socket s;
	private DataOutputStream dos;
	private DataInputStream dis;

	public ButtonPanel() {

		try 
		{
			socket_singleton socket = socket_singleton.getSocketInstance();
			s = socket.socket;
			dos = socket.dataOut;
			dis = socket.dataIn;
		
			btnForward = new JButton("🡅");
			btnLeft = new JButton("🡄");
			btnRight = new JButton("🡆");
			btnBackward = new JButton("🡇");
			btnShutDown = new JButton("Shutdown");
			batteryInfo = new JLabel(batteryLevel, JLabel.LEFT);
			txtInfoTitel = new JLabel(messageTitel, JLabel.LEFT);
			txtInfo = new JLabel(messageFromRoberto, JLabel.CENTER);
			txtInfo.setFocusable(isFocusable());
			
	
			txtInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed UP"), FORWARD);
			txtInfo.getActionMap().put(FORWARD, new Command(FORWARD));
			txtInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "stop");
			txtInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed DOWN"), BACKWARD);
			txtInfo.getActionMap().put(BACKWARD, new Command(BACKWARD));
			txtInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"), "stop");
			txtInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed LEFT"), TURNLEFT);
			txtInfo.getActionMap().put(TURNLEFT, new Command(TURNLEFT));
			txtInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "stop");
			txtInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed RIGHT"), TURNRIGHT);
			txtInfo.getActionMap().put(TURNRIGHT, new Command(TURNRIGHT));
			txtInfo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "stop");
			
			txtInfo.getActionMap().put("stop", new Stop());
			
			
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 3;
			add(txtInfoTitel, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0,0,15,0);
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 3;
			add(txtInfo, c);
			
			c.fill = GridBagConstraints.NONE;
			c.insets = new Insets(0,0,0,0);
			c.gridwidth = 1;
			c.ipadx = 30;
			c.ipady = 30;
			c.gridx = 1;
			c.gridy = 2;
			add(btnForward, c);

			c.gridx = 0;
			c.gridy = 3;
			c.anchor = GridBagConstraints.LINE_END;
			add(btnLeft, c);
			
			c.gridx = 1;
			c.gridy = 3;
			c.anchor = GridBagConstraints.CENTER;
			add(btnBackward, c);
			
			c.gridx = 2;
			c.gridy = 3;
			c.anchor = GridBagConstraints.LAST_LINE_START;
			add(btnRight, c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 4;
			c.gridwidth = 3;
			add(batteryInfo, c);

			c.ipady = 0;
			c.insets = new Insets(15,0,0,0);
			c.gridwidth = 2;
			c.gridx = 1;
			c.gridy = 5;
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

			//get info from brick/Datainputstream in separate thread
            Thread readInfo = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String info = dis.readUTF();
                            if(info.contentEquals("SHUTDOWN"))
                            {
                            	System.exit(0);
                            }
                            if (info.toLowerCase().contains(stringCheckBatteryinfo.toLowerCase()))
                            {
                            	batteryInfo.setText(info);
                            } 
                            else {
                            	txtInfo.setText(info); 
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }) ;

            readInfo.start();
		
		
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

	private class Command extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String command; 
		
		Command(String command) {
			this.command = command;
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			currentAction = this.command;
			sendCommand(this.command);
		}
	}
	
	private class Stop extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentAction = "stop";
			sendCommand("stop");
			
		}
		
	}


}
