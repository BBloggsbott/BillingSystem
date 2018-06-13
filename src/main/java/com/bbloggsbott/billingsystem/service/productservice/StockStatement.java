package com.bbloggsbott.billingsystem.service.productservice;

import java.awt.Frame;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;

/**
 * Class provides methods to generate Stock Statements. Generates Stock
 * Statement and stores them in a file with current date
 */
public class StockStatement extends Thread {
    /**
     * Run the thread
     */
    @Override
    public void run() {
        String fileName = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String statement = "";
        try {
            PrintWriter stockWriter = new PrintWriter("stockStatements/" + fileName + ".txt", "UTF-8");
            ArrayList<Product> products = ProductLookup.lookupAll();
            JOptionPane.showMessageDialog(null, "Stock Statement generating. You will be notified once it is complete");
            System.out.println(
                    "Stock Statement Generation initiated. Please dont close the application till you get the confirmation of completion of this task");
            for (int i = 0; i < products.size(); i++) {
                statement = statement + products.get(i).toString();
            }
            stockWriter.write(statement);
            stockWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JOptionPane.showMessageDialog(null, "Stock Statement Generation Complete");
            System.out.println("Stock Statement Generation Completed");
        }
    }
}