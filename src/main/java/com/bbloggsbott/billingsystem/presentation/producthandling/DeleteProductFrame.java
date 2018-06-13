package com.bbloggsbott.billingsystem.presentation.producthandling;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;
import com.bbloggsbott.billingsystem.service.productservice.ProductLookup;
import com.bbloggsbott.billingsystem.presentation.springutils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The JFrame for the GUI of Deleting a product from the database
 */
public class DeleteProductFrame extends JFrame implements ActionListener {

    JLabel idLabel, nameLabel, title;
    JTextField id, name;
    JButton deleteButton, getProduct;
    JPanel header, content, footer;
    Product product;

    /**
     * Constructor of the Delete Product Interface
     */
    public DeleteProductFrame() {
        setTitle("Delete Product");
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        header = new JPanel(new FlowLayout());
        footer = new JPanel(new FlowLayout());
        content = new JPanel(new SpringLayout());

        idLabel = new JLabel("ID");
        nameLabel = new JLabel("Name");
        title = new JLabel("Delete Product");
        id = new JTextField();
        name = new JTextField();
        name.setEditable(false);
        deleteButton = new JButton("Delete");
        getProduct = new JButton("Get Product");

        header.add(title);
        footer.add(getProduct);
        footer.add(deleteButton);

        content.add(idLabel);
        content.add(id);
        content.add(nameLabel);
        content.add(name);
        SpringUtilities.makeGrid(content, 2, 2, 10, 20, 10, 40);

        add(header, BorderLayout.NORTH);
        add(footer, BorderLayout.SOUTH);
        add(content, BorderLayout.CENTER);

        deleteButton.addActionListener(this);
        getProduct.addActionListener(this);

        setVisible(true);
        setSize(300, 300);
    }

    /**
     * Event Handler for the Action Event
     * 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getProduct) {
            product = ProductLookup.lookupByID(Integer.parseInt(id.getText())).get(0);
            name.setText(product.getName());
        } else if (e.getSource() == deleteButton) {
            product = new Product(Integer.parseInt(id.getText()));
            if (product.deleteProduct()) {
                name.setText("");
                id.setText("");
            }
        }
    }
}
