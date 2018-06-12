package com.bbloggsbott.billingsystem.service.productservice;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;

public class StockUpdater{

    public static boolean reduceStock(List<String[]> products){
        Product product;
        Iterator itr = products.iterator();
        String[] element;
        int i = 0;
        BigDecimal stockUpdate;
        while(itr.hasNext()){
            element = products.get(i);
            product = ProductLookup.lookupByID(Integer.parseInt(element[0])).get(0);
            stockUpdate = new BigDecimal(element[1]);
            product.setStock(product.getStock().subtract(stockUpdate));
            if(!product.updateProduct()){
                return false;
            }
        }
        return true;
    }
}