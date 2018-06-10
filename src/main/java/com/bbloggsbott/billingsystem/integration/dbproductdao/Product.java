package com.bbloggsbott.billingsystem.integration.dbproductdao;

import com.bbloggsbott.billingsystem.service.connectionservice.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Product {
    private int ID;
    private String Name;
    private BigDecimal buyPrice, sellPrice, stock;
    private String type;
    private static Connection conn;

    public Product(int ID){
        this.ID = ID;
    }

    public Product(String Name, BigDecimal buyPrice, BigDecimal sellPrice, String type, BigDecimal stock){
        this.Name = Name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.type = type;
        this.stock = stock;
    }

    public Product(int ID, String Name, BigDecimal buyPrice, BigDecimal sellPrice, String type, BigDecimal stock){
        this.ID = ID;
        this.Name = Name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.type = type;
        this.stock = stock;
    }

    public int getID(){
        return ID;
    }

    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }


    public BigDecimal getBuyPrice(){
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice){
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice(){
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice){
        this.sellPrice = sellPrice;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public BigDecimal getStock(){
        return stock;
    }

    public void setStock(BigDecimal stock){
        this.stock = stock;
    }



    public static boolean createTable(){
        conn = ConnectionFactory.getConnection("products");
        try {
            assert conn != null;
            Statement s = conn.createStatement();
            s.executeUpdate("create table products (ID integer primary key, name varchar(50), buyprice Decimal, sellprice decimal, type varchar(5), stock decimal)");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    public boolean insertProduct(){
        conn = ConnectionFactory.getConnection("products");
        PreparedStatement ps = null;
        try {
            assert conn != null;
            ps = conn.prepareStatement("insert into products values(?,?,?,?,?,?)");
            ps.setInt(1, ID);
            ps.setString(2, Name);
            ps.setBigDecimal(3,buyPrice);
            ps.setBigDecimal(4,sellPrice);
            ps.setString(5,type);
            ps.setBigDecimal(6,stock);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteProduct(){
        conn = ConnectionFactory.getConnection("products");
        PreparedStatement ps = null;
        try {
            assert conn != null;
            ps = conn.prepareStatement("delete from products where ID = ?");
            ps.setInt(1, ID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProduct(){
        conn = ConnectionFactory.getConnection("products");
        PreparedStatement ps = null;
        try {
            assert conn != null;
            ps = conn.prepareStatement("update products set name = ?, buyprice = ?, sellprice = ?, type = ?, stock = ? where id = ? ");
            ps.setString(1, Name);
            ps.setBigDecimal(2,buyPrice);
            ps.setBigDecimal(3,sellPrice);
            ps.setString(4,type);
            ps.setBigDecimal(5,stock);
            ps.setInt(6, ID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[] toStringArray(){
        String[] toreturn = {Integer.toString(ID), Name, buyPrice.toString(), sellPrice.toString(), type, stock.toString()};
        return toreturn;
    }

}
