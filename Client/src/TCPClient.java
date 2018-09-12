import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
class Chat {
    boolean flag = true;
    Scanner sc = new Scanner(System.in);
    public synchronized void Question(String question) {
        if (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server writes " + question);
        flag = false;
        notify();
    }

    public synchronized void Answer() {
        if (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Client writes : " + sc.nextLine());
        flag = true;
        notify();
    }
}
class T1 implements Runnable {
    Chat m;

    public T1(Chat m1) {
        this.m = m1;
        new Thread(this, "Question").start();
    }

    public void run() {
        // Socker initialisation
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(),9898);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while(true) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                m.Question(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

class T2 implements Runnable {
    Chat m;

    public T2(Chat m2) {
        this.m = m2;
        new Thread(this, "Answer").start();
    }

    public void run() {
        while(true){
            m.Answer();
        }
    }
}
public class TCPClient extends Thread {


    public static void main(String[] args) throws Exception {
        Chat m = new Chat();
        new T1(m);
        new T2(m);
    }
}