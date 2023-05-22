/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.scdassignment;

/**
 *
 * @author Lenovo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame implements ActionListener
{
    
    private JTextField Username_Field;
    private JPasswordField Pass_Field;
    private UserDao User_Dao; //dataacessobject

    public LoginForm()
    {
        //Properties of Form
        setTitle("Login Form");
        setLayout(new FlowLayout());
        setSize(1000,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        //Username Components
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);
        Username_Field = new JTextField(20);
        add(Username_Field);
        
         //Password Components
        JLabel PasswordLabel = new JLabel("Password:");
        add(PasswordLabel);
        Pass_Field = new JPasswordField(20);
        add(Pass_Field);
         
        //Button Components
        JButton login_Button = new JButton("Login");
        login_Button.addActionListener(this);
        login_Button.setBackground(Color.BLUE);
        add(login_Button);

        User_Dao = new UserDao(); 

    }

    public void actionPerformed(ActionEvent e) 
    {
        String username = Username_Field.getText();
        char[] passwordChars = Pass_Field.getPassword();
        String password = new String(passwordChars);

        // Here the appropriate or the authentication of the user is donw for getting loged into 
        boolean isAuthenticated = User_Dao.authenticate(username, password);

        if (isAuthenticated) 
        {
            //JOptionPane.showMessageDialog(this, "Login successful!");
            new Admin().setVisible(true); // using this parameter the admin class will be linked with the logged in form when the user will click loged in
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }

       // Here the Input feilds will be cleared
        Username_Field.setText("");
        Pass_Field.setText("");
    }

    public static void main(String[] args)
    {
        // thus library is used to runn the LOGIN FORM 
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new LoginForm();
            }
        });
    }
}

class UserDao
{
    public boolean authenticate(String username, String password) 
    {
       
        if (username.equals("BSE") && password.equals("1234")) {
            return true;
        } else {
            return false;
        }
    }
}

