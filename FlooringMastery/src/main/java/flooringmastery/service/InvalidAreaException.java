/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.service;

/**
 *
 * @author siggelkow
 */
public class InvalidAreaException extends Exception {
            
    public InvalidAreaException(String message) {
        super(message);
    }

    public InvalidAreaException(String message, Throwable cause) {
        super(message, cause);
    }
}
