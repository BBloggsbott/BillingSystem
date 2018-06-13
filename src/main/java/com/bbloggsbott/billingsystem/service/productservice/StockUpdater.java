package com.bbloggsbott.billingsystem.service.productservice;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;

/**
 * Provides method to update stock of products
 */
public class StockUpdater {

    /**
     * Updates stock of the products passed as parameter
     * 
     * @param products The products to update. products[0] = ID
     *                 and products[1] = stock update value.
     * @return boolean Success of the task
     */
    public static boolean reduceStock(List<String[]> products) {
        Product product;
        Iterator itr = products.iterator();
        String[] element;
        int i = 0;
        BigDecimal stockUpdate;
        while (itr.hasNext()) {
            element = products.get(i);
            product = ProductLookup.lookupByID(Integer.parseInt(element[0])).get(0);
            stockUpdate = new BigDecimal(element[i]);
            product.setStock(product.getStock().subtract(stockUpdate));
            if (!product.updateProduct()) {
                return false;
            }
            itr.next();
        }
        return true;
    }
}