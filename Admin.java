/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scdassignment;

/**
 *
 * @author Lenovo
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Admin extends JFrame implements ActionListener
{
    //Elements of Admin Class
    private JButton View_Books_Button;
    private JButton View_Users_Button;
    private JButton View_IssuedBooks_Button;
    private JButton Issue_Book_Button;
    private JButton Add_User_Button;
    private JButton Add_Book_Button;
    private JButton Return_Book_Button;
    private JButton Create_Reset_Button;

    public Admin() 
    {
        //Properties 
        setTitle("Library Management System");
        setSize(430,700 );
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        // View Book Components
        View_Books_Button = new JButton("View Books");
        View_Books_Button.addActionListener(this);
        add(View_Books_Button);
        
       // View UsersComponents
        View_Users_Button = new JButton("View Users");
        View_Users_Button.addActionListener(this);
        add(View_Users_Button);
        
       //View Users Components
        View_IssuedBooks_Button = new JButton("View Issued Books");
        View_IssuedBooks_Button.addActionListener(this);
        add(View_IssuedBooks_Button);
        
      // View Issued Book Components
        Issue_Book_Button = new JButton("Issue Book");
        Issue_Book_Button.addActionListener(this);
        add(Issue_Book_Button);
        
      // Add User Components
        Add_User_Button = new JButton("Add User");
        Add_User_Button.addActionListener(this);
        add(Add_User_Button);

        Add_Book_Button = new JButton("Add Book");
        Add_Book_Button.addActionListener(this);
        add(Add_Book_Button);

        // Return Book Components
        Return_Book_Button = new JButton("Return Book");
        Return_Book_Button.addActionListener(this);
        add(Return_Book_Button);
        
       //Components of Reset 
        Create_Reset_Button = new JButton("Create/Reset");
        Create_Reset_Button.addActionListener(this);
        add(Create_Reset_Button);

        
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == View_Books_Button) 
        {
            // Handle view books button action
           // JOptionPane.showMessageDialog(this, "View Books");
            new ViewBooks().setVisible(true);
        } 
        else if (e.getSource() == View_Users_Button) // Controls view users button action
        {
    
            //JOptionPane.showMessageDialog(this, "View Users");
            new UsersList().setVisible(true);
        }
        else if (e.getSource() == View_IssuedBooks_Button)  // Controls view issued books button action
        {
            //JOptionPane.showMessageDialog(this, "View Issued Books");
            new IssueBook().setVisible(true);
        }
        else if (e.getSource() == Issue_Book_Button) // Controls issue book button action
        {
            JOptionPane.showMessageDialog(this, "Issue Book");
        } 
        else if (e.getSource() == Add_User_Button)// Controls add user button action
        {
            JOptionPane.showMessageDialog(this, "Add User");
        }
        else if (e.getSource() == Add_Book_Button) // Controls add book button action
        {
           // JOptionPane.showMessageDialog(this, "Add Book");
           new AddBook().setVisible(true);
        } 
        else if (e.getSource() == Return_Book_Button) // Controls return book button action
        {
            JOptionPane.showMessageDialog(this, "Return Book");
        } 
        else if (e.getSource() == Create_Reset_Button) // Controls create/reset button action
        {
            JOptionPane.showMessageDialog(this, " Database Create/Reset");
          
        }
    }

    public static void main(String[] args) {
        new Admin();
    }
}
