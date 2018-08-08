import java.io.*;
import java.util.*;
import java.net.*;

import javax.swing.*;
import java.awt.event.*;

class ServerApp{
	static Socket s;
	static DataInputStream dis;
	static DataOutputStream dos;
	static JTextArea chatArea;
	public static void main(String[] args) {
		try{
			ServerSocket ss = new ServerSocket(6060);
// 			s = ss.accept();

			JFrame frame = new JFrame("Server");

			JLabel l = new JLabel("Enter message : ");
			l.setBounds(50, 50, 120, 30);

			JTextArea msgArea = new JTextArea();
			msgArea.setBounds(180, 50, 250, 80);

			JButton sendBtn = new JButton("Send");
			sendBtn.setBounds(50, 170, 80, 30);
			sendBtn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						
						dos = new DataOutputStream(s.getOutputStream());
						String msgOut = msgArea.getText();
						dos.writeUTF(msgOut);
						dos.flush();

						String chatText = chatArea.getText();
						chatArea.setText(chatText + "\n You : " +msgOut);
						msgArea.setText("");

						dis = new DataInputStream(s.getInputStream());
					} catch(Exception ed){ ed.printStackTrace();}
				}
			});
			JButton closeBtn = new JButton("Close");
			closeBtn.setBounds(300, 170, 80, 30);
			closeBtn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						s.close();
					} catch(Exception ed){ ed.printStackTrace();}
				}
			});

			JLabel header = new JLabel("Chat");
			header.setBounds(235, 200, 80, 30);

			chatArea = new JTextArea();
			chatArea.setBounds(70, 240, 410, 200);

			frame.add(l);
			frame.add(msgArea);
			frame.add(sendBtn);
			frame.add(closeBtn);
			frame.add(header);
			frame.add(chatArea);
			frame.setSize(550,690);
			frame.setLayout(null);
			frame.setVisible(true);

			s = ss.accept();
			
			dis = new DataInputStream(s.getInputStream());
			while(dis != null){
				String msgIn = dis.readUTF();
				String chatText = chatArea.getText();
				chatArea.setText(chatText + "\nServer : "+msgIn);
			}
			

			// s.close();
			ss.close();
		} catch(Exception e ){ e.printStackTrace();}


	}
}
