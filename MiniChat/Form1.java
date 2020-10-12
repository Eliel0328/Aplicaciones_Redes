package MiniChat;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;  

public class Form1 implements Observer{
    JFrame formulario;
    JButton sendButton = new JButton("Enviar");
    JTextArea areaText = new JTextArea();
    JTextField inputBox = new JTextField("");

    Form1(){
        formulario = new JFrame();

        sendButton = new JButton("Enviar");
        sendButton.setBounds(305, 420, 80, 35);

        areaText = new JTextArea();
        areaText.setBounds(10,10, 375, 400); 

        inputBox = new JTextField("");
        inputBox.setBounds(10, 420, 285, 35);
        
        formulario.add(areaText);
        formulario.add(sendButton);
        formulario.add(inputBox);
        formulario.setSize(400, 500);
        formulario.setLayout(null);
        formulario.setTitle("1 MiniChat");
        formulario.setVisible(true);

        inputBox.requestFocusInWindow();
        sendButton.addActionListener(new CustomActionListener());

        Servidor s = new Servidor(5000);
        s.addObserver(this);
        Thread t = new Thread(s);
        t.start();
    }

    class CustomActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String mensaje = "1-: " + inputBox.getText() + "\n";
            areaText.append(mensaje);
            inputBox.setText("");
            Cliente cliente = new Cliente(6000, mensaje);
            Thread t = new Thread(cliente);
            t.start();
        }
    }

    public void update(Observable o, Object arg){
        this.areaText.append((String) arg);
    }

    public static void main(String[] args) {
        new Form1();
    }
}