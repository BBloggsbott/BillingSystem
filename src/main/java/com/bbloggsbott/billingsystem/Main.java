package com.bbloggsbott.billingsystem;

import java.io.File;

import com.bbloggsbott.billingsystem.presentation.startups.LoginFrame;
import com.bbloggsbott.billingsystem.service.firstrunservice.FirstRun;

/**
 * The class with the main function
 */
public class Main {
    /**
     * The main function for the application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        File f = new File("billing.json");
        if (!f.exists()) {
            FirstRun fr = new FirstRun();
            fr.setUpProject();
        }
        LoginFrame lf = new LoginFrame();
    }
}