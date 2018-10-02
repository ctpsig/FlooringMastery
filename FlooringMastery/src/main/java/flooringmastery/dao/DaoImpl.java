/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.dao;

import flooringmastery.dto.Order;
import flooringmastery.dto.Product;
import flooringmastery.dto.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author siggelkow
 */
public class DaoImpl implements Dao {

    private Map<Integer, Order> orders = new HashMap<>();
    private List<State> states = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private boolean production;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
    public static final String STATES_FILE = "states.txt";
    public static final String PRODUCTS_FILE = "products.txt";
    public static final String MODE_FILE = "mode.txt";
    public static final String DELIMITER = ",";

    @Override
    public List<Order> displayOrders(LocalDate date) throws PersistenceException {
        loadOrderFile(date);
        return new ArrayList<>(orders.values());
    }

    @Override
    public State sendState(String stateName) throws PersistenceException {
        loadStates();
        for (State s : states) {
            if (s.getStateName().equals(stateName)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public Product sendProduct(String productType) throws PersistenceException {
        loadProducts();
        for (Product p : products) {
            if (p.getType().equals(productType)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void addOrder(LocalDate date, Order order) throws PersistenceException, TrainingException {
        loadProductionTraining();
        orders.put(order.getOrderNumber(), order);
        saveOrderFile(date);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
        loadOrderFile(date);
        return orders.get(orderNumber);
    }

    @Override
    public void editOrder(LocalDate date, Order order) throws PersistenceException, TrainingException {
        loadProductionTraining();
        orders.replace(order.getOrderNumber(), order);
        saveOrderFile(date);
    }

    @Override
    public void removeOrder(LocalDate date, Order order) throws PersistenceException, TrainingException {
        loadProductionTraining();
        loadOrderFile(date);
        orders.remove(order.getOrderNumber());
        saveOrderFile(date);
    }

    private void loadOrderFile(LocalDate date) throws PersistenceException {
        orders.clear();
        String orderDate = date.format(formatter);
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("Orders_" + orderDate + ".txt")));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Order file could not be loaded.", e);
        }

        String currentLine;
        String[] currentToken;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentToken = currentLine.split(DELIMITER);
            Order currentOrder = new Order();
            currentOrder.setOrderNumber(Integer.parseInt(currentToken[0]));
            currentOrder.setCustomerName(currentToken[1]);
            State currentState = new State();
            currentState.setStateName(currentToken[2]);
            currentState.setTaxRate(new BigDecimal(currentToken[3]));
            currentOrder.setState(currentState);
            Product currentProduct = new Product();
            currentProduct.setType(currentToken[4]);
            currentOrder.setArea(new BigDecimal(currentToken[5]));
            currentOrder.setProduct(currentProduct);
            currentProduct.setCostPerSqFoot(new BigDecimal(currentToken[6]));
            currentProduct.setLaborCostPerSqFoot(new BigDecimal(currentToken[7]));
            currentOrder.setMaterialCost(new BigDecimal(currentToken[8]));
            currentOrder.setLaborCost(new BigDecimal(currentToken[9]));
            currentOrder.setTax(new BigDecimal(currentToken[10]));
            currentOrder.setTotalCost(new BigDecimal(currentToken[11]));
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
    }

    private void saveOrderFile(LocalDate date) throws PersistenceException, TrainingException {
        if (production == true) {
            PrintWriter out;
            String orderDate = date.format(formatter);

            List<Order> orderList = new ArrayList<>(orders.values());
            try {
                out = new PrintWriter(new FileWriter("Orders_" + orderDate + ".txt"));
            } catch (IOException e) {
                throw new PersistenceException("Could not save order data.", e);
            }

            for (Order order : orderList) {
                out.println(order.getOrderNumber() + DELIMITER
                        + order.getCustomerName() + DELIMITER
                        + order.getState().getStateName() + DELIMITER
                        + order.getState().getTaxRate() + DELIMITER
                        + order.getProduct().getType() + DELIMITER
                        + order.getArea() + DELIMITER
                        + order.getProduct().getCostPerSqFoot() + DELIMITER
                        + order.getProduct().getLaborCostPerSqFoot() + DELIMITER
                        + order.getMaterialCost() + DELIMITER
                        + order.getLaborCost() + DELIMITER
                        + order.getTax() + DELIMITER
                        + order.getTotalCost());
                out.flush();
            }
            out.close();
        } else {
            throw new TrainingException("You are in training mode. Could not save data.");
        }
    }

    private void loadStates() throws PersistenceException {
        states = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(STATES_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("States file could not be loaded.", e);
        }
        String currentLine;
        String[] currentToken;
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentToken = currentLine.split(DELIMITER);
            State currentState = new State();
            currentState.setStateName(currentToken[0]);
            currentState.setTaxRate(new BigDecimal(currentToken[1]));
            states.add(currentState);
        }
    }

    private void loadProducts() throws PersistenceException {
        products = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Products file could not be loaded.", e);
        }
        String currentLine;
        String[] currentToken;
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentToken = currentLine.split(DELIMITER);
            Product currentProduct = new Product();
            currentProduct.setType(currentToken[0]);
            currentProduct.setCostPerSqFoot(new BigDecimal(currentToken[1]));
            currentProduct.setLaborCostPerSqFoot(new BigDecimal(currentToken[2]));
            products.add(currentProduct);
        }
    }

    private String loadProductionTraining() throws PersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(MODE_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Prod/Training Mode File could not be loaded", e);
        }
        
        scanner.nextLine();
        String mode = scanner.nextLine();

        if (mode.equals("Production")) {
            production = true;
        } else {
            production = false;
        }
        return mode;
    }

    @Override
    public List<State> getStates() throws PersistenceException {
        loadStates();
        return states;
    }

    @Override
    public List<Product> getProducts() throws PersistenceException {
        loadProducts();
        return products;
    }

    @Override
    public String sendMode() throws PersistenceException {
        return loadProductionTraining();
    }
}
