package com.bbloggsbott.billingsystem.service.firstrunservice;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URI;
import java.util.Scanner;

import com.bbloggsbott.billingsystem.integration.dbproductdao.Product;
import com.bbloggsbott.billingsystem.integration.dbusersdao.User;
import com.bbloggsbott.billingsystem.service.connectionservice.ConnectionFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class with method to generate the resources to run the application
 */
public class FirstRun {
    /**
     * Constructor
     */
    public FirstRun() {
    }

    /**
     * Function to create the dependencies for the running of the application
     * 
     * @return boolean Success of the task
     */
    public boolean setUpProject() {
        try {
            System.out.println("Running application for the first time. Starting Configuration...");
            new File("db").mkdir();
            new File("bills").mkdir();
            new File("stockStatements").mkdir();
            Scanner scanner = new Scanner(System.in);
            String username, password, name;
            System.out.print("Enter Username: ");
            username = scanner.next();
            System.out.print("Enter Password: ");
            password = scanner.next();
            System.out.print("Enter Name: ");
            name = scanner.next();
            System.out.print("Enter Gmail ID(yourmail@gmail.com) : ");
            String email = scanner.next();
            System.out.println("Enter company name: ");
            String company = scanner.next() + "\n";
            System.out.println("Enter company address: ");
            company = company + scanner.next() + "\n";
            System.out.println("Enter company Phone number ");
            company = company + scanner.next();
            System.out.println("Setting up Product Database");
            ConnectionFactory.getConnection("products");
            if (!Product.createTable()) {
                System.out.print("Product Table Creation Failed. Exiting.");
                System.exit(0);
            }
            System.out.println("Setting up User Database");
            ConnectionFactory.getConnection("Users");
            if (!User.createTable()) {
                System.out.print("Users Table Creation Failed. Exiting.");
                System.exit(0);
            } else {
                User user = new User(username, name, password, true);
                if (!user.insertUser()) {
                    System.out.print("Users Creation Failed. Exiting.");
                    System.exit(0);
                }
            }
            JSONObject jsonObject = new JSONObject();
            System.out.println("Setting up required files");
            File f = new File("billing.json");
            PrintWriter fw = new PrintWriter(f);
            jsonObject.put("firstRun", "false");
            jsonObject.put("billNo", 1);
            jsonObject.put("productID", 1);
            jsonObject.put("emailID", email);
            jsonObject.put("company", company);
            fw.write(jsonObject.toJSONString());
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}