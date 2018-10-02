/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.service;

import flooringmastery.dao.InvalidProductException;
import flooringmastery.dao.Dao;
import flooringmastery.dao.InvalidStateException;
import flooringmastery.dao.PersistenceException;
import flooringmastery.dao.TrainingException;
import flooringmastery.dto.Order;
import flooringmastery.dto.Product;
import flooringmastery.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author siggelkow
 */
public class ServiceImpl implements Service {

    Dao dao;

    ServiceImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public List<Order> displayOrders(LocalDate date) throws PersistenceException {
        return dao.displayOrders(date);
    }

    @Override
    public void addOrder(LocalDate date, Order order) throws PersistenceException, TrainingException {
        dao.addOrder(date, order);
    }

    @Override
    public int getOrderNum(LocalDate date) throws PersistenceException, TrainingException {
        int highest = 0;
        try {
            List<Order> orders = displayOrders(date);

            for (Order o : orders) {
                if (o.getOrderNumber() > highest) {
                    highest = o.getOrderNumber();
                }
            }
            highest++;
        } catch (PersistenceException e) {
            highest = 1;
        }

        return highest;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNum) throws PersistenceException {
        return dao.getOrder(date, orderNum);
    }

    @Override
    public void removeOrder(LocalDate date, Order order) throws PersistenceException, TrainingException {
        dao.removeOrder(date, order);
    }

    @Override
    public boolean validateAvailableProduct(String productName) throws PersistenceException, InvalidProductException {
        Product product = dao.sendProduct(productName);
        if (product == null) {
            throw new InvalidProductException("Not a valid product entry.");
        } else {
            return true;
        }
    }

    @Override
    public boolean validateServicedState(String stateName) throws PersistenceException, InvalidStateException {
        State state = dao.sendState(stateName);
        if (state == null) {
            throw new InvalidStateException("Not a valid state entry.");
        } else {
            return true;
        }
    }

    @Override
    public Order calculateCost(Order order) throws PersistenceException, InvalidStateException, InvalidProductException {
        State thisState = dao.sendState(order.getState().getStateName());
        Product thisProduct = dao.sendProduct(order.getProduct().getType());
        order.setState(thisState);
        order.setProduct(thisProduct);

        //calculating costs from the product
        order.setMaterialCost(order.getArea().multiply(order.getProduct().getCostPerSqFoot()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getArea().multiply(order.getProduct().getLaborCostPerSqFoot()).setScale(2, RoundingMode.HALF_UP));

        BigDecimal subTotal = order.getLaborCost().add(order.getMaterialCost().setScale(2, RoundingMode.HALF_UP));

        //calculating tax
        order.setTax((order.getState().getTaxRate().divide(new BigDecimal("100"))).multiply(subTotal).setScale(2, RoundingMode.HALF_UP));

        //calculating total cost
        order.setTotalCost(subTotal.add(order.getTax()).setScale(2, RoundingMode.HALF_UP));

        return order;
    }

    @Override
    public boolean validateCustomerInfo(String customerName) throws InvalidCustomerException {
        if (customerName.equals("")) {
            throw new InvalidCustomerException("Not a valid customer entry.");
        } else {
            return true;
        }
    }

    @Override
    public void editOrder(LocalDate date, Order order) throws PersistenceException, TrainingException {
        dao.editOrder(date, order);
    }

    @Override
    public List<State> displayStates() throws PersistenceException {
        return dao.getStates();
    }

    @Override
    public List<Product> displayProducts() throws PersistenceException {
        return dao.getProducts();
    }

    @Override
    public String sendMode() throws PersistenceException {
        return dao.sendMode();
    }
}
