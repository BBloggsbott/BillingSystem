package com.bbloggsbott.billingsystem.presentation.producthandling;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;
import com.bbloggsbott.billingsystem.presentation.springutils.*;
import com.bbloggsbott.billingsystem.service.productservice.ProductLookup;

/**
 * The JFrame for the GUI of Updating a product in the database
 */
public class UpdateProductFrame extends JFrame implements ActionListener, ItemListener {
    JPanel header, content, footer;
    JLabel title, idLabel, nameLabel, buyLabel, sellLabel, typeLabel, stockLabel;
    JTextField id, name, buy, sell, stock;
    JCheckBox type;
    JButton updateButton, clearButton, getProduct;
    Product product;
    boolean typeValue;

    public UpdateProductFrame() {
        setTitle("Update Product");
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        title = new JLabel("Update Product");
        idLabel = new JLabel("ID");
        nameLabel = new JLabel("Name");
        buyLabel = new JLabel("Buy Price");
        sellLabel = new JLabel("Sell Price");
        typeLabel = new JLabel("Type");
        stockLabel = new JLabel("Stock");
        id = new JTextField();
        name = new JTextField();
        buy = new JTextField();
        sell = new JTextField();
        stock = new JTextField();
        type = new JCheckBox("No.s"); // If unchecked, Considers as Kgs
        typeValue = false;
        updateButton = new JButton("Update");
        clearButton = new JButton("Clear");
        getProduct = new JButton("Get Product");
        header = new JPanel(new FlowLayout());
        content = new JPanel(new SpringLayout());
        footer = new JPanel(new FlowLayout());

        content.add(idLabel);
        content.add(nameLabel);
        content.add(buyLabel);
        content.add(sellLabel);
        content.add(typeLabel);
        content.add(stockLabel);
        content.add(id);
        content.add(name);
        content.add(buy);
        content.add(sell);
        content.add(type);
        content.add(stock);
        SpringUtilities.makeGrid(content, 2, 6, 10, 20, 10, 10);
        header.add(title);
        footer.add(getProduct);
        footer.add(updateButton);
        footer.add(clearButton);
        content.setSize(800, 300);
        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        updateButton.addActionListener(this);
        clearButton.addActionListener(this);
        getProduct.addActionListener(this);
        type.addItemListener(this);

        setVisible(true);
        setSize(800, 170);

    }

    /**
     * Event Handler for the Action Event
     * 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            name.setText("");
            buy.setText("");
            sell.setText("");
            stock.setText("");
        } else if (e.getSource() == updateButton && !id.getText().isEmpty()) {
            product = new Product(Integer.parseInt(id.getText()), name.getText(), new BigDecimal(buy.getText()),
                    new BigDecimal(sell.getText()), Boolean.toString(typeValue), new BigDecimal(stock.getText()));
            if (product.updateProduct()) {
                id.setText(Integer.parseInt(id.getText()) + "");
                name.setText("");
                buy.setText("");
                sell.setText("");
                stock.setText("");
            }
        } else if (e.getSource() == getProduct) {
            product = ProductLookup.lookupByID(Integer.parseInt(id.getText())).get(0);
            name.setText(product.getName());
            sell.setText(Double.toString(product.getSellPrice().doubleValue()));
            buy.setText(Double.toString(product.getBuyPrice().doubleValue()));
            stock.setText(Double.toString((product.getStock().doubleValue())));
            typeValue = Boolean.parseBoolean(product.getType());
            type.setSelected(typeValue);
        }
    }

    /**
     * Event Handler for the Item Event
     * 
     * @param e The Item Event
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == type) {
            typeValue = !typeValue;
        }
    }
}