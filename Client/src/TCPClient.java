import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TCPClient {

    private BufferedReader in;
    private PrintWriter out;


    public TCPClient() {
        String response;
        try{
            response = in.readLine();
            if (response == null || response.equals("")) {
                System.exit(0);
            }
        } catch (IOException ex) {
            response = "Error: " + ex;
        }
        System.out.println(response);
    }


    public void connectToServer() throws IOException {

        Socket socket = new Socket(InetAddress.getLocalHost(), 9898);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        String message = in.readLine();
        System.out.println(message);
    }

    public static void main(String[] args) throws Exception {
        TCPClient client = new TCPClient();
        client.connectToServer();
    }
}