/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.service;

import flooringmastery.dao.InvalidProductException;
import flooringmastery.dao.InvalidStateException;
import flooringmastery.dao.PersistenceException;
import flooringmastery.dao.TrainingException;
import flooringmastery.dto.Order;
import flooringmastery.dto.Product;
import flooringmastery.dto.State;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author siggelkow
 */
public interface Service {
   public List<Order> displayOrders(LocalDate date) throws PersistenceException;
   public List<State> displayStates() throws PersistenceException;
   public List<Product> displayProducts() throws PersistenceException;
   public void addOrder(LocalDate date, Order order) throws PersistenceException, TrainingException;
   public Order getOrder(LocalDate date, int orderNum) throws PersistenceException;
   public void removeOrder(LocalDate date, Order order) throws PersistenceException, TrainingException;
   public void editOrder(LocalDate date, Order order) throws PersistenceException, TrainingException;
   public int getOrderNum(LocalDate date) throws PersistenceException, TrainingException;
   public boolean validateAvailableProduct(String productName) throws PersistenceException, InvalidProductException;
   public boolean validateServicedState(String stateName) throws PersistenceException, InvalidStateException;
   public boolean validateCustomerInfo(String customerName) throws InvalidCustomerException;
   public Order calculateCost(Order order) throws PersistenceException, InvalidStateException, InvalidProductException;
   public String sendMode() throws PersistenceException;
}
