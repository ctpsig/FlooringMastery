/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.view;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author siggelkow
 */
public class UserIOConsoleImpl implements UserIO {
    
    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        return Double.parseDouble(sc.nextLine());
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double userAnswer;
        do{
            System.out.println(prompt);
            userAnswer = Double.parseDouble(sc.nextLine());
        } while(userAnswer < min || userAnswer > max);
        return userAnswer;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        return Float.parseFloat(sc.nextLine());
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float userAnswer;
        do{
            System.out.println(prompt);
            userAnswer = Float.parseFloat(sc.nextLine());
        } while (userAnswer < min || userAnswer > max);
        return userAnswer;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        return Integer.parseInt(sc.nextLine());
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int userAnswer;
        do{
           System.out.println(prompt);
           userAnswer = Integer.parseInt(sc.nextLine());
        } while (userAnswer < min || userAnswer > max);
        return userAnswer;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        return Long.parseLong(sc.nextLine());
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long userAnswer;
        do{
            System.out.println(prompt);
            userAnswer = Long.parseLong(sc.nextLine());
        } while (userAnswer < min || userAnswer > max);
        return userAnswer;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        System.out.println(prompt);
        return new BigDecimal(sc.nextLine());
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        BigDecimal userAnswer;
        do {
        System.out.println(prompt);
        userAnswer = new BigDecimal(sc.nextLine());
        } while (userAnswer.compareTo(min) < 0 || userAnswer.compareTo(max) > 0);
        return userAnswer;
        
    }
    
}
