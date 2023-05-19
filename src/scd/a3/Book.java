package scd.a3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionListener;

// DAO interfaces
interface BookDAO {
    List<Book> getAllBooks();
    Book getBookById(int id);
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
}

interface BookDAOFactory {
    BookDAO getBookDAO();
}

// Book entity class
class Book {
    private int id;
    private String title;
    private String author;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

// MySQL implementation of BookDAO
class MySQLBookDAO implements BookDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");

                Book book = new Book(id, title, author);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Book getBookById(int id) {
        Book book = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");

                book = new Book(id, title, author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public void addBook(Book book) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (title, author) VALUES (?, ?)")) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBook(Book book) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET title = ?, author = ? WHERE id = ?")) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBook(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Factory for creating MySQLBookDAO instance
class MySQLBookDAOFactory implements BookDAOFactory {
    @Override
    public BookDAO getBookDAO() {
        return new MySQLBookDAO();
    }
}

// GUI for the library management system
public class LibraryManagementSystem extends JFrame {
    private BookDAO bookDAO;
    private DefaultListModel<Book> bookListModel;
    private JList<Book> bookList;
    private JTextField idField, titleField, authorField;
    private JButton addButton, updateButton, deleteButton;

    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Instantiate the DAO
        BookDAOFactory bookDAOFactory = new MySQLBookDAOFactory();
        bookDAO = bookDAOFactory.getBookDAO();

        // Create components
        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);
        JScrollPane bookScrollPane = new JScrollPane(bookList);

        idField = new JTextField();
        titleField = new JTextField();
        authorField = new JTextField();

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // Layout components
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(bookScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(new AddButtonListener());
        updateButton.addActionListener(new UpdateButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
        bookList.addListSelectionListener(new BookListSelectionListener());

        // Load initial book list
        loadBooks();
    }

    private void loadBooks() {
        bookListModel.clear();

        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            bookListModel.addElement(book);
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        bookList.clearSelection();
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String title = titleField.getText();
            String author = authorField.getText();

            Book book = new Book(0, title, author);
            bookDAO.addBook(book);

            loadBooks();
            clearFields();
        }
    }

    private class UpdateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String author = authorField.getText();

            Book book = new Book(id, title, author);
            bookDAO.updateBook(book);

            loadBooks();
            clearFields();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int id = Integer.parseInt(idField.getText());
            bookDAO.deleteBook(id);

            loadBooks();
            clearFields();
        }
    }

    private class BookListSelectionListener implements ListSelectionListener {
        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            if (!evt.getValueIsAdjusting()) {
                Book selectedBook = bookList.getSelectedValue();
                if (selectedBook != null) {
                    idField.setText(String.valueOf(selectedBook.getId()));
                    titleField.setText(selectedBook.getTitle());
                    authorField.setText(selectedBook.getAuthor());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LibraryManagementSystem system = new LibraryManagementSystem();
                system.setVisible(true);
            }
        });
    }
}
