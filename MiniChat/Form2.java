package MiniChat;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;  

public class Form2 implements Observer{
    JFrame formulario;
    JButton sendButton = new JButton("Enviar");
    JTextArea areaText = new JTextArea();
    JTextField inputBox = new JTextField("");

    Form2(){
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
        formulario.setTitle("2 MiniChat");
        formulario.setVisible(true);

        inputBox.requestFocusInWindow();
        sendButton.addActionListener(new CustomActionListener());

        Servidor s = new Servidor(6000);
        s.addObserver(this);
        Thread t = new Thread(s);
        t.start();
    }

    class CustomActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String mensaje = "2-: " + inputBox.getText() + "\n";
            areaText.append(mensaje);
            Cliente cliente = new Cliente(5000, mensaje);
            inputBox.setText("");
            Thread t = new Thread(cliente);
            t.start();
        }
    }

    public void update(Observable o, Object arg){
        this.areaText.append((String) arg);
    }

    public static void main(String[] args) {
        new Form2();
    }    
}
