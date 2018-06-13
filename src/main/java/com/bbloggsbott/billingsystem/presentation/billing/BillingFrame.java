package com.bbloggsbott.billingsystem.presentation.billing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;
import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.service.billingservice.BillCreator;
import com.bbloggsbott.billingsystem.service.productservice.ProductLookup;
import com.bbloggsbott.billingsystem.service.productservice.StockUpdater;

/**
 * The JFrame for the GUI of Billing
 */
public class BillingFrame extends JFrame implements ActionListener {
    JLabel title, idLabel, nameLabel, priceLabel, qtyLabel, rateLabel, totalLabel, customerNameLabel,
            customerEmailLabel, billNoLabel;
    JTextField id, name, rate, qty, price, total, customerName, customerEmail, billNo;
    JTable billTable;
    DefaultTableModel billTableModel;
    JButton addButton, deleteButton, generateBillButton, getProduct;
    JPanel header, content, footer;
    User user;
    Product product;
    boolean newBill;
    List<String[]> items, billProducts;
    Object[][] itemsData = { { "Empty", "0", "0", "0" } };
    ArrayList<String> toAdd;
    double sellPrice, totalPrice;
    Long billNoValue;

    /**
     * Constructor of the Billing Interface
     * 
     * @param user The user performing the billing task
     */
    public BillingFrame(User user) {
        setTitle("Billing");
        setLayout(new BorderLayout(10, 5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.user = user;
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("billLogo.png")));
            setIconImage(img.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        totalPrice = 0;
        billNoValue = BillCreator.getBillNo();
        title = new JLabel("Billing");
        idLabel = new JLabel("ID");
        nameLabel = new JLabel("Name");
        priceLabel = new JLabel("Price");
        qtyLabel = new JLabel("Qty");
        rateLabel = new JLabel("Rate");
        totalLabel = new JLabel("Total");
        id = new JTextField();
        name = new JTextField();
        name.setEditable(false);
        rate = new JTextField();
        rate.setEditable(false);
        qty = new JTextField();
        price = new JTextField();
        price.setEditable(false);
        total = new JTextField(Double.toString(totalPrice), 10);
        total.setEditable(false);

        customerNameLabel = new JLabel("Customer Name");
        customerName = new JTextField(10);
        customerEmailLabel = new JLabel("Customer Email");
        customerEmail = new JTextField(10);

        getProduct = new JButton("Get Product");
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        generateBillButton = new JButton("Generate");

        header = new JPanel(new GridLayout(2, 1));
        footer = new JPanel(new GridLayout(3, 1));
        content = new JPanel(new GridLayout(2, 1));

        JPanel billNoPanel = new JPanel(new FlowLayout());
        billNoLabel = new JLabel("Bill No");
        billNo = new JTextField(Long.toString(billNoValue), 5);
        billNo.setEditable(false);
        billNoPanel.add(billNoLabel);
        billNoPanel.add(billNo);

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);

        header.add(titlePanel);
        header.add(billNoPanel);
        JPanel footer1 = new JPanel(new FlowLayout());
        JPanel footer2 = new JPanel(new FlowLayout());
        footer2.add(customerNameLabel);
        footer2.add(customerName);
        footer2.add(customerEmailLabel);
        footer2.add(customerEmail);
        footer1.add(totalLabel);
        footer1.add(total);
        footer.add(footer1);
        footer.add(footer2);
        JPanel footer3 = new JPanel(new FlowLayout());
        footer3.add(getProduct);
        footer3.add(addButton);
        footer3.add(deleteButton);
        footer3.add(generateBillButton);
        footer.add(footer3);

        JPanel content1 = new JPanel(new GridLayout(2, 5, 10, 10));
        content1.add(idLabel);
        content1.add(nameLabel);
        content1.add(rateLabel);
        content1.add(qtyLabel);
        content1.add(priceLabel);
        content1.add(id);
        content1.add(name);
        content1.add(rate);
        content1.add(qty);
        content1.add(price);

        content.add(content1);

        String[] columnNames = { "Name", "Rate", "Qty", "Price" };
        items = new ArrayList<String[]>();
        billProducts = new ArrayList<String[]>();
        billTableModel = new DefaultTableModel();
        for (int i = 0; i < 4; i++)
            billTableModel.addColumn(columnNames[i]);
        billTable = new JTable(billTableModel);
        JScrollPane scrollPane = new JScrollPane(billTable);
        billTable.setFillsViewportHeight(true);
        content.add(scrollPane);
        toAdd = new ArrayList<String>();

        add(header, BorderLayout.NORTH);
        add(footer, BorderLayout.SOUTH);
        add(content, BorderLayout.CENTER);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        getProduct.addActionListener(this);
        generateBillButton.addActionListener(this);

        setSize(900, 400);
        setVisible(true);
    }

    /**
     * Event Handler for the Action Event
     * 
     * @param e The Action Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            if (!id.getText().isEmpty() && !qty.getText().isEmpty()) {
                double sellRate = product.getSellPrice().doubleValue();
                rate.setText(Double.toString(sellRate));
                sellPrice = sellRate * Double.parseDouble(qty.getText());
                price.setText(Double.toString(sellPrice));
                toAdd.clear();
                toAdd.add(name.getText());
                toAdd.add(rate.getText());
                toAdd.add(qty.getText());
                toAdd.add(price.getText());
                billProducts.add(getIdStock());
                totalPrice = totalPrice + sellPrice;
                total.setText(Double.toString(totalPrice));
                items.add(toStringArray(toAdd));
                billTableModel.addRow(items.get(items.size() - 1));
            }
        } else if (e.getSource() == deleteButton) {
            items.remove(billTable.getSelectedRow());
            billProducts.remove(billTable.getSelectedRow());
            Double value = Double.parseDouble((String) billTableModel.getValueAt(billTable.getSelectedRow(), 3));
            total.setText(Double.toString(Double.parseDouble(total.getText()) - value));
            billTableModel.removeRow(billTable.getSelectedRow());
        } else if (e.getSource() == qty) {

        } else if (e.getSource() == generateBillButton) {
            if (!customerName.getText().isEmpty() && !customerEmail.getText().isEmpty()) {
                if (!StockUpdater.reduceStock(billProducts)) {
                    return;
                }
                BillCreator bc = new BillCreator(user, customerName.getText(), customerEmail.getText(),
                        Long.toString(billNoValue), BillCreator.processForPrinting(items), totalPrice);
                if (bc.gernerateAndMailBill()) {
                    BillCreator.incrementBillNo();
                    dispose();
                }
            }
        } else if (e.getSource() == getProduct) {
            if (qty.getText().isEmpty()) {
                qty.setText("1");
            }
            product = ProductLookup.lookupByID(Integer.parseInt(id.getText())).get(0);
            double sellRate = product.getSellPrice().doubleValue();
            rate.setText(Double.toString(sellRate));
            sellPrice = sellRate * Double.parseDouble(qty.getText());
            price.setText(Double.toString(sellPrice));
            name.setText(product.getName());
        }
    }

    /**
     * Function to converts List of String array into a 2D String Array
     * 
     * @param items The List for conversion
     * @return Converted data
     */
    public String[][] getItemsData(List<String[]> items) {
        String[][] itemsData = new String[items.size()][];
        int i, size = items.size();
        for (i = 0; i < size; i++) {
            itemsData[i] = items.get(i);
        }
        return itemsData;
    }

    /**
     * Function to convert ArrayList of string to array of strings
     * 
     * @param al The ArrayList to be converted
     * @return Converted data
     */
    public String[] toStringArray(ArrayList<String> al) {
        String[] toReturn = { al.get(0), al.get(1), al.get(2), al.get(3) };
        return toReturn;
    }

    /**
     * Function to get the id and change in stock of the items in the bill ad String
     * array
     * 
     * @return The id and change in stock of products
     */
    public String[] getIdStock() {
        String[] toreturn = { id.getText(), qty.getText() };
        return toreturn;
    }

}
