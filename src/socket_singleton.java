import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class socket_singleton {
	
	
	
	private static socket_singleton socket_instance = null;
	

	public Socket socket = null; 
	public DataInputStream dataIn = null; 
	public DataOutputStream dataOut = null;
	
	
	private socket_singleton() throws Exception {
	
		socket =  new Socket("192.168.0.12", 5000);
		dataIn = new DataInputStream(socket.getInputStream());	
		dataOut = new DataOutputStream(socket.getOutputStream());
		

	}
	
	public static socket_singleton getSocketInstance() throws Exception {
		if( socket_instance == null) 
		socket_instance = new socket_singleton();
		
	 return socket_instance;
	}




}
