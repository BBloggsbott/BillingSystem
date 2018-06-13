package com.bbloggsbott.billingsystem.service.billingservice;

import com.bbloggsbott.billingsystem.integration.dbusersdao.User;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.bbloggsbott.billingsystem.service.billingservice.wagu.components.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

/**
 * Class to create the bill
 */
public class BillCreator {
    private static String company;
    List<String> t1Headers;
    List<List<String>> t1Rows;
    String t2Desc;
    List<String> t2Headers;
    List<List<String>> t2Rows;
    List<Integer> t2ColWidths;
    String t3Desc;
    String summary, summaryVal;
    User user;
    String customerEmail, billNo;

    /**
     * Constructor that initializes the variable of the bill
     * 
     * @param user          The user who generates the bill
     * @param customerName  The customer name for the bill
     * @param customerEmail The Email ID of the customer
     * @param billNo        The bill number
     * @param items         The list of the items for the bill
     * @param total         The total amount of the bill
     */
    public BillCreator(User user, String customerName, String customerEmail, String billNo, List<List<String>> items,
            double total) {
        this.user = user;
        this.customerEmail = customerEmail;
        this.billNo = billNo;
        company = getCompany();
        System.out.println("Got company");
        t1Headers = Arrays.asList("INFO", "CUSTOMER");
        t1Rows = Arrays.asList(
                Arrays.asList("DATE: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()), customerName),
                Arrays.asList("TIME: " + new SimpleDateFormat("HH:mm:ss").format(new Date()), ""),
                Arrays.asList("BILL NO: " + billNo, ""));
        t2Desc = "SELLING DETAILS";
        t2Headers = Arrays.asList("ITEM", "RATE", "QTY", "PRICE");
        t2Rows = items;
        t2ColWidths = Arrays.asList(17, 9, 5, 12);
        t3Desc = "Summary";
        summary = "TOTAL: \n";
        summaryVal = Double.toString(total) + "\n";

    }

    /**
     * The function to generate the bill
     * 
     * @return String The bill as a string
     */
    public String generateBill() {
        Board b = new Board(48);
        b.setInitialBlock(new Block(b, 46, 7, company).allowGrid(false).setBlockAlign(Block.BLOCK_CENTRE)
                .setDataAlign(Block.DATA_CENTER));
        b.appendTableTo(0, Board.APPEND_BELOW, new Table(b, 48, t1Headers, t1Rows));
        b.getBlock(3).setBelowBlock(new Block(b, 46, 1, t2Desc).setDataAlign(Block.DATA_CENTER));
        b.appendTableTo(5, Board.APPEND_BELOW, new Table(b, 48, t2Headers, t2Rows, t2ColWidths));
        b.getBlock(10).setBelowBlock(new Block(b, 46, 1, t3Desc).setDataAlign(Block.DATA_CENTER));
        Block summaryBlock = new Block(b, 35, 9, summary).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_RIGHT);
        b.getBlock(14).setBelowBlock(summaryBlock);
        Block summaryValBlock = new Block(b, 12, 9, summaryVal).allowGrid(false).setDataAlign(Block.DATA_MIDDLE_RIGHT);
        summaryBlock.setRightBlock(summaryValBlock);
        String bill = b.invalidate().build().getPreview();
        bill = bill + "\nUser: " + user.getName();
        bill = bill + "(" + user.getUserName() + ")\n";
        return bill;
    }

    /**
     * Function to generate and mail the bill to the customer
     * 
     * @return boolean The success of the task
     */
    public boolean gernerateAndMailBill() {
        String password;
        JPanel forPassword = new JPanel(new FlowLayout());
        JLabel passLabel = new JLabel("Password: ");
        JPasswordField pass = new JPasswordField(10);
        forPassword.add(passLabel);
        forPassword.add(pass);
        String[] options = { "OK", "Cancel" };
        int option = JOptionPane.showOptionDialog(null, forPassword, "Enter Password", JOptionPane.NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        if (option == 0) {
            password = new String(pass.getPassword());
            BillMailer bm = new BillMailer(password);
            if (bm.mailBill(generateBill(), customerEmail, billNo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to get bill number from billing.json
     * 
     * @return Long Bill number
     */
    public static Long getBillNo() {
        JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader(new File("billing.json"));
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;
            Long billNo = (Long) jsonObject.get("billNo");
            fr.close();
            return billNo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Long(-1);
    }

    /**
     * Function to increment and write the bill number to billing.json
     * 
     * @return boolean Success of the task
     */
    public static boolean incrementBillNo() {
        JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader("billing.json");
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;
            fr.close();
            FileWriter fw = new FileWriter("billing.json");
            Long billNo = (Long) jsonObject.get("billNo");
            billNo += 1;
            jsonObject.put("billNo", billNo);
            fw.write(jsonObject.toJSONString());
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Function to process a List for bill generation
     * 
     * @param items The data to process for printing
     * @return Processed data
     */
    public static List<List<String>> processForPrinting(List<String[]> items) {
        List<List<String>> toPrint = Arrays.asList(toList(items.get(0)));
        for (int i = 1; i < items.size(); i++) {
            toPrint.add(toList(items.get(i)));
        }
        return toPrint;
    }

    /**
     * Function to convert Data
     * 
     * @return Converted data
     */
    public static List<String> toList(String[] toConvert) {
        return Arrays.asList(toConvert[0], toConvert[1], toConvert[2], toConvert[3]);
    }

    /**
     * Function to get the company details from billing.json for bill generation
     * 
     * @return String Company Details
     */
    private String getCompany() {
        JSONParser parser = new JSONParser();
        try {
            FileReader fr = new FileReader("billing.json");
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;
            fr.close();
            String returnCompany = (String) jsonObject.get("company");
            company = company + "\n\n";
            return returnCompany;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
