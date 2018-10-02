/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.dao;

import flooringmastery.dto.Order;
import flooringmastery.dto.Product;
import flooringmastery.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author siggelkow
 */
public class DaoStubImpl implements Dao {

    Order onlyOrder;
    List<Order> orders = new ArrayList<>();
    List<State> states = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
    LocalDate ld = LocalDate.parse("06011999", formatter);

    public DaoStubImpl() {
        onlyOrder = new Order();
        onlyOrder.setOrderNumber(1);
        onlyOrder.setCustomerName("Siggelkow");
        State state = new State();
        state.setStateName("OH");
        onlyOrder.setState(state);
        Product product = new Product();
        product.setType("Wood");
        onlyOrder.setProduct(product);
        onlyOrder.setArea(new BigDecimal("100.00"));
        orders.add(onlyOrder);
    }

    @Override
    public List<Order> displayOrders(LocalDate date) throws PersistenceException {
        if(date.equals(ld)){
            return orders;
        } else{
            return new ArrayList<>();
        }
    }

    @Override
    public State sendState(String stateName) throws PersistenceException {
        State onlyState = new State();
        onlyState.setStateName("OH");
        onlyState.setTaxRate(new BigDecimal("6.25"));
        if (stateName.equals(onlyState.getStateName())){
            return onlyState;
        } else {
            return null;
        }
    }

    @Override
    public Product sendProduct(String productType) throws PersistenceException {
        Product onlyProduct = new Product();
        onlyProduct.setType("Wood");
        onlyProduct.setCostPerSqFoot(new BigDecimal("5.15"));
        onlyProduct.setLaborCostPerSqFoot(new BigDecimal("4.75"));
        if (productType.equals(onlyProduct.getType())){
            return onlyProduct;
        } else {
            return null;
        }
        
    }

    @Override
    public void addOrder(LocalDate date, Order order) throws PersistenceException {

    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
        if (orderNumber == onlyOrder.getOrderNumber()){
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void editOrder(LocalDate date, Order order) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeOrder(LocalDate date, Order order) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<State> getStates() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Product> getProducts() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String sendMode() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
