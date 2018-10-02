/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.dao;

/**
 *
 * @author siggelkow
 */
public class TrainingException extends Exception {

    public TrainingException(String message) {
        super(message);
    }

    public TrainingException(String message, Throwable cause) {
        super(message, cause);
    }

}
