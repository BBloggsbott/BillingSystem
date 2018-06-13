package com.bbloggsbott.billingsystem.service.productservice;

import java.awt.Frame;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;

public class StockStatement extends Thread{
    @Override
    public void run(){
        String fileName = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        try{
            PrintWriter stockWriter = new PrintWriter("stockStatements/"+fileName+".txt", "UTF-8");
            ArrayList<Product> products = ProductLookup.lookupAll();
            System.out.println("Stock Statement Generation initiated. Please dont close the application till you get the confirmation of completion of this task");
            for(int i = 0;i<products.size();i++){
                stockWriter.write(products.get(i).toString());
            } 
            stockWriter.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println("Stock Statement Generation Completed");
        }
    }
}