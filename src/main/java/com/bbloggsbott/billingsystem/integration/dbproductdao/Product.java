package com.bbloggsbott.billingsystem.integration.dbproductdao;

import com.bbloggsbott.billingsystem.service.connectionservice.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The data access object for products
 */
public class Product {
    private int ID;
    private String Name;
    private BigDecimal buyPrice, sellPrice, stock;
    private String type;
    private static Connection conn;

    /**
     * Constructor for the DAO
     * 
     * @param ID The ID of the product
     */
    public Product(int ID) {
        this.ID = ID;
    }

    /**
     * Constructor for the DAO
     * 
     * @param Name      Name of the Product
     * @param buyPrice  Buying price of the product
     * @param sellPrice Selling price of the product
     * @param type      The Type of the product(No.s or Kg)
     * @param stock     The current stock of the product
     */
    public Product(String Name, BigDecimal buyPrice, BigDecimal sellPrice, String type, BigDecimal stock) {
        this.Name = Name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.type = type;
        this.stock = stock;
    }

    /**
     * Constructor for the DAO
     * 
     * @param ID        ID of the Product
     * @param Name      Name of the Product
     * @param buyPrice  Buying price of the product
     * @param sellPrice Selling price of the product
     * @param type      The Type of the product(No.s or Kg)
     * @param stock     The current stock of the product
     */
    public Product(int ID, String Name, BigDecimal buyPrice, BigDecimal sellPrice, String type, BigDecimal stock) {
        this.ID = ID;
        this.Name = Name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.type = type;
        this.stock = stock;
    }

    /**
     * Function to get the ID of the Product
     * 
     * @return int The ID of the product
     */
    public int getID() {
        return ID;
    }

    /**
     * Function to get the name of the Product
     * 
     * @return String The Name of the product
     */
    public String getName() {
        return Name;
    }

    /**
     * Function to set the name of the Product
     * 
     * @param Name The new name of the product
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Function to get the BuyPrice of the Product
     * 
     * @return BigDecimal BuyPrice of the product
     */
    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    /**
     * Function to set the BuyPrice of the Product
     * 
     * @param buyPrice The new BuyPrice of the product
     */
    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    /**
     * Function to get the SellPrice of the Product
     * 
     * @return BigDecimal SellPrice of the product
     */
    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    /**
     * Function to set the SellPrice of the Product
     * 
     * @param sellPrice The new SellPrice of the product
     */
    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * Function to get the type of the product
     * 
     * @return String The type of the Product
     */
    public String getType() {
        return type;
    }

    /**
     * Function to set the tpe of the product
     * 
     * @param type The new Type of the product
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Function to get stock of the product
     * 
     * @return BigDecimal The stock of the product
     */
    public BigDecimal getStock() {
        return stock;
    }

    /**
     * Function to set the stock of the product
     * 
     * @param stock The new stock of the product
     */
    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    /**
     * Function to create the table for products
     * 
     * @return boolean Success of the task
     */
    public static boolean createTable() {
        conn = ConnectionFactory.getConnection("products");
        try {
            assert conn != null;
            Statement s = conn.createStatement();
            s.executeUpdate(
                    "create table products (ID integer primary key, name varchar(50), buyprice Decimal, sellprice decimal, type varchar(5), stock decimal)");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Function to add products to the table
     * 
     * @return boolean Success of the task
     */
    public boolean insertProduct() {
        conn = ConnectionFactory.getConnection("products");
        PreparedStatement ps = null;
        try {
            assert conn != null;
            ps = conn.prepareStatement("insert into products values(?,?,?,?,?,?)");
            ps.setInt(1, ID);
            ps.setString(2, Name);
            ps.setBigDecimal(3, buyPrice);
            ps.setBigDecimal(4, sellPrice);
            ps.setString(5, type);
            ps.setBigDecimal(6, stock);
            ps.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Function to delete products from the table
     * 
     * @return boolean Success of the task
     */
    public boolean deleteProduct() {
        conn = ConnectionFactory.getConnection("products");
        PreparedStatement ps;
        try {
            assert conn != null;
            ps = conn.prepareStatement("delete from products where ID = ?");
            ps.setInt(1, ID);
            ps.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Function to update products in the table
     * 
     * @return boolean Success of the task
     */
    public boolean updateProduct() {
        conn = ConnectionFactory.getConnection("products");
        PreparedStatement ps = null;
        try {
            assert conn != null;
            ps = conn.prepareStatement(
                    "update products set name = ?, buyprice = ?, sellprice = ?, type = ?, stock = ? where id = ? ");
            ps.setString(1, Name);
            ps.setBigDecimal(2, buyPrice);
            ps.setBigDecimal(3, sellPrice);
            ps.setString(4, type);
            ps.setBigDecimal(5, stock);
            ps.setInt(6, ID);
            ps.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Function to return product details as a string array
     * 
     * @return The details as String Array
     */
    public String[] toStringArray() {
        String[] toreturn = { Integer.toString(ID), Name, buyPrice.toString(), sellPrice.toString(), type,
                stock.toString() };
        return toreturn;
    }

    /**
     * Function to return product details as a string
     * 
     * @return String The details as String
     */
    public String toString() {
        return "Name: " + Name + "\t\tBuy Price: " + buyPrice.toString() + "\t\tSellPrice: " + sellPrice.toString()
                + "\t\tStock: " + stock + " " + (type.equals("true") ? "No.s" : "Kgs") + "\n";
    }

}
