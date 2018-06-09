package com.bbloggsbott.billingsystem.presentation.producthandling;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;
import com.bbloggsbott.billingsystem.service.productservice.ProductLookup;
import com.bbloggsbott.billingsystem.presentation.springutils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteProductFrame extends JFrame implements ActionListener {

    JLabel idLabel, nameLabel, title;
    JTextField id, name;
    JButton deleteButton;
    JPanel header, content, footer;
    Product product;

    public DeleteProductFrame() {
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

        header.add(title);
        footer.add(deleteButton);

        content.add(idLabel);
        content.add(id);
        content.add(nameLabel);
        content.add(name);
        SpringUtilities.makeGrid(content, 2, 2, 10, 20, 10, 40);

        add(header, BorderLayout.NORTH);
        add(footer, BorderLayout.SOUTH);
        add(content, BorderLayout.CENTER);

        id.addActionListener(this);
        deleteButton.addActionListener(this);

        setVisible(true);
        setSize(300, 300);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == id){
            try {
                product = ProductLookup.lookupByID(Integer.parseInt(id.getText())).get(0);
                name.setText(product.getName());
            }
            catch(IndexOutOfBoundsException ie){
                name.setText("Product not Found");
            }
        }
        else if(e.getSource() == deleteButton){
            product = new Product(Integer.parseInt(id.getText()));
            product.deleteProduct();
        }
    }
}
