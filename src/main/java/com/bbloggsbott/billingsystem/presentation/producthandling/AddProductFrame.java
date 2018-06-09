package com.bbloggsbott.billingsystem.presentation.producthandling;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;
import com.bbloggsbott.billingsystem.presentation.springutils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

public class AddProductFrame extends JFrame implements ActionListener, ItemListener {

    JPanel header, content, footer;
    JLabel title, idLabel, nameLabel, buyLabel, sellLabel, typeLabel, stockLabel;
    JTextField id, name, buy, sell, stock;
    JCheckBox type;
    JButton addButton, clearButton;
    Product product;
    boolean typeValue;

    public AddProductFrame(){
        title = new JLabel("Add Product");
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
        type = new JCheckBox("No.s");       //If unchecked, Considers as Kgs
        typeValue = false;
        addButton = new JButton("Add");
        clearButton = new JButton("Clear");
        header = new JPanel( new FlowLayout());
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
        SpringUtilities.makeGrid(content,2,6,10,20,10,10);
        header.add(title);
        footer.add(addButton);
        footer.add(clearButton);
        content.setSize(800,300);
        add(header,BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        addButton.addActionListener(this);
        clearButton.addActionListener(this);
        type.addItemListener(this);

        setVisible(true);
        setSize(800,170);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == clearButton){
            name.setText("");
            buy.setText("");
            sell.setText("");
            stock.setText("");
        }
        else if(e.getSource() == addButton){
            product = new Product(Integer.parseInt(id.getText()),name.getText(),new BigDecimal(buy.getText()),new BigDecimal(sell.getText()),Boolean.toString(typeValue),new BigDecimal(stock.getText()));
            if(product.insertProduct()){
                id.setText(Integer.parseInt(id.getText())+"");
                name.setText("");
                buy.setText("");
                sell.setText("");
                stock.setText("");
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == type){
            typeValue = !typeValue;
        }
    }
}
