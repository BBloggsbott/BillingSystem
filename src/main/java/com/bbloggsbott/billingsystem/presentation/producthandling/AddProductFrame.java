package com.bbloggsbott.billingsystem.presentation.producthandling;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;
import com.bbloggsbott.billingsystem.presentation.springutils.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;

/**
 * The JFrame for the GUI of Adding a product to the database
 */
public class AddProductFrame extends JFrame implements ActionListener, ItemListener {

    JPanel header, content, footer;
    JLabel title, idLabel, nameLabel, buyLabel, sellLabel, typeLabel, stockLabel;
    JTextField id, name, buy, sell, stock;
    JCheckBox type;
    JButton addButton, clearButton;
    Product product;
    boolean typeValue;
    JSONObject jsonObject;

    /**
     * Constructor of the Add Product Interface
     */
    public AddProductFrame() {
        setTitle("Add Product");
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        title = new JLabel("Add Product");
        idLabel = new JLabel("ID");
        nameLabel = new JLabel("Name");
        buyLabel = new JLabel("Buy Price");
        sellLabel = new JLabel("Sell Price");
        typeLabel = new JLabel("Type");
        stockLabel = new JLabel("Stock");
        id = new JTextField();
        id.setEditable(false);
        name = new JTextField();
        buy = new JTextField();
        sell = new JTextField();
        stock = new JTextField();
        type = new JCheckBox("No.s"); // If unchecked, Considers as Kgs
        typeValue = false;
        addButton = new JButton("Add");
        clearButton = new JButton("Clear");
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
        footer.add(addButton);
        footer.add(clearButton);
        content.setSize(800, 300);
        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader("billing.json");
            Object obj = parser.parse(fr);
            jsonObject = (JSONObject) obj;
            fr.close();
            Long ID = (Long) jsonObject.get("productID");
            id.setText(Long.toString(ID));
        } catch (Exception e) {
            e.printStackTrace();
        }

        addButton.addActionListener(this);
        clearButton.addActionListener(this);
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
        } else if (e.getSource() == addButton) {
            product = new Product(Integer.parseInt(id.getText()), name.getText(), new BigDecimal(buy.getText()),
                    new BigDecimal(sell.getText()), Boolean.toString(typeValue), new BigDecimal(stock.getText()));
            if (product.insertProduct()) {
                int newID = Integer.parseInt(id.getText()) + 1;
                id.setText(newID + "");
                try {
                    FileWriter fw = new FileWriter("billing.json");
                    jsonObject.put("productID", new Long(newID));
                    fw.write(jsonObject.toJSONString());
                    fw.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    name.setText("");
                    buy.setText("");
                    sell.setText("");
                    stock.setText("");
                }
            }
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
