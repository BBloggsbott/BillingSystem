package com.bbloggsbott.billingsystem.presentation.producthandling;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bbloggsbott.billingsystem.service.productservice.ProductLookup;

public class SearchProductFrame extends JFrame implements ActionListener{
    JPanel header, content;
    JLabel title, idLabel, nameLabel;
    JTextField id, name;
    JButton getProduct;
    String[] columnNames = { "ID", "Name", "Buy Price", "Sell Price", "Type", "Stock" };;
    JTable resultTable;
    JScrollPane scrollPane;
    Object[][] items;
    DefaultTableModel resultModel;
    public SearchProductFrame(){
        
        title = new JLabel("Search Product");
        idLabel = new JLabel("ID");
        nameLabel = new JLabel("Name");

        id = new JTextField(8);
        name = new JTextField(10);
        getProduct = new JButton("Get Products");

        header = new JPanel(new GridLayout(3, 1));
        JPanel header1 = new JPanel(new FlowLayout());
        JPanel header2 = new JPanel(new FlowLayout());
        JPanel header3 = new JPanel(new FlowLayout());
        JPanel footer = new JPanel(new FlowLayout());
        header1.add(title);
        header2.add(idLabel);
        header2.add(id);
        header3.add(nameLabel);
        header3.add(name);
        footer.add(getProduct);

        header.add(header1);
        header.add(header2);
        header.add(header3);

        resultModel = new DefaultTableModel();
        for (int i = 0; i < 6; i++) {
            resultModel.addColumn(columnNames[i]);
        }
        resultTable = new JTable(resultModel);
        scrollPane = new JScrollPane(resultTable);
        resultTable.setFillsViewportHeight(true);

        content = new JPanel(new GridLayout(1, 1));
        content.add(scrollPane);

        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        getProduct.addActionListener(this);

        setSize(900, 400);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == getProduct){
            if (!id.getText().isEmpty()) {
                name.setText("");
                items = ProductLookup.toTwoDArray(ProductLookup.lookupByID(Integer.parseInt(id.getText())));
                resultModel.setRowCount(0);
                for (int i = 0; i < items.length; i++) {
                    resultModel.addRow(items[i]);
                }
            } else if (!name.getText().isEmpty()) {
                id.setText("");
                items = ProductLookup.toTwoDArray(ProductLookup.lookupByName(name.getText()));
                resultModel.setRowCount(0);
                for (int i = 0; i < items.length; i++) {
                    resultModel.addRow(items[i]);
                }
            }
        }
    }
}