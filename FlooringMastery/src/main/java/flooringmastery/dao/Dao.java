/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.dao;

import flooringmastery.dto.Order;
import flooringmastery.dto.Product;
import flooringmastery.dto.State;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author siggelkow
 */
public interface Dao {
    public List<Order> displayOrders(LocalDate date) throws PersistenceException;
    public State sendState(String stateName) throws PersistenceException, InvalidStateException;
    public Product sendProduct(String productType) throws PersistenceException, InvalidProductException;
    public List<State> getStates() throws PersistenceException;
    public List<Product> getProducts() throws PersistenceException;
    public void addOrder(LocalDate date, Order order) throws PersistenceException, TrainingException;
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException;
    public void editOrder(LocalDate date, Order order) throws PersistenceException, TrainingException;
    public void removeOrder(LocalDate date, Order order) throws PersistenceException, TrainingException;
    public String sendMode() throws PersistenceException;
    //saveOrder??
}
