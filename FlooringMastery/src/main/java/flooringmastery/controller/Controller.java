/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.controller;

import flooringmastery.dao.InvalidProductException;
import flooringmastery.dao.InvalidStateException;
import flooringmastery.dao.PersistenceException;
import flooringmastery.dao.TrainingException;
import flooringmastery.dto.Order;
import flooringmastery.dto.Product;
import flooringmastery.dto.State;
import flooringmastery.service.InvalidAreaException;
import flooringmastery.service.InvalidCustomerException;
import flooringmastery.service.Service;
import flooringmastery.view.View;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author siggelkow
 */
public class Controller {

    View view;
    Service service;

    Controller(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean keepGoing = true;
        String mode = null;
        try {
            mode = service.sendMode();
            view.displayMode(mode);
        } catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        while (keepGoing) {
            try {
                int userChoice = view.displayMenu(mode);
                switch (userChoice) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        System.exit(0);
                        break;
                }
            } catch (PersistenceException | TrainingException | NumberFormatException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }

    public void displayOrders() throws PersistenceException {
        LocalDate date = null;
        do {
            try {
                date = view.askDate();
            } catch (DateTimeParseException e) {
                view.displayErrorMessage("Please enter date in give format");
            }
        } while (date == null);

        try {
            List<Order> orders = new ArrayList<>(service.displayOrders(date));
            view.displayOrdersBanner();
            view.displayOrders(orders);
        } catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        view.returnToMainMenu();
    }

    //
    //addOrder
    public void addOrder() throws PersistenceException, TrainingException {
        //date entry
        LocalDate ld = null;
        do {
            try {
                ld = view.askOrderDate();
            } catch (DateTimeParseException e) {
                view.displayErrorMessage("Please enter date in give format");
            }
        } while (ld == null);
        view.takeAnOrderBanner();
        Order newOrder = new Order();
        State state = new State();
        Product product = new Product();
        newOrder.setProduct(product);

        //customer entry
        boolean c = false;
        do {
            try {
                String customer = view.takeAnOrderCustomer();
                c = service.validateCustomerInfo(customer);
                if (c) {
                    newOrder.setCustomerName(customer);
                }
            } catch (InvalidCustomerException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } while (!c);

        //state entry
        boolean s = false;
        do {
            try {
                List<State> states = service.displayStates();
                String stateName = view.takeAnOrderState(states);
                s = service.validateServicedState(stateName);
                if (s) {
                    state.setStateName(stateName);
                    newOrder.setState(state);
                }
            } catch (InvalidStateException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } while (!s);

        //product entry
        boolean p = false;
        do {
            try {
                List<Product> products = service.displayProducts();
                String productType = view.takeAnOrderProduct(products);
                p = service.validateAvailableProduct(productType);
                if (p) {
                    product.setType(productType);
                    newOrder.setProduct(product);
                }
            } catch (InvalidProductException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } while (!p);

        //area entry
        try {
            BigDecimal area = view.takeAnOrderArea();
            newOrder.setArea(area);
        } catch (InvalidAreaException e) {
            view.displayErrorMessage(e.getMessage());
        }

        //determining order num
        int orderNum = service.getOrderNum(ld);
        newOrder.setOrderNumber(orderNum);
        //calculating other object variables
        try {
            service.calculateCost(newOrder);
        } catch (InvalidStateException | InvalidProductException e) {
            view.displayErrorMessage(e.getMessage());
        }

        //displaying & adding the new object
        view.displayOneOrder(newOrder);
        int confirm = view.addConfirm();
        if (confirm == 1) {
            service.addOrder(ld, newOrder);
            view.addSuccess();
        }
        view.returnToMainMenu();
    }

    //editOrder
    public void editOrder() throws PersistenceException, TrainingException {
        //entering date
        LocalDate ld = null;
        do {
            try {
                ld = view.askOrderDate();
            } catch (DateTimeParseException e) {
                view.displayErrorMessage("Please enter date in give format");
            }
        } while (ld == null);

        //entering order num and finding order
        Order order = null;
        do {
            try {
                int orderNum = view.editOrderGetNum();
                order = service.getOrder(ld, orderNum);
            } catch (PersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } while (order == null);

        //displaying and entering new info
        boolean c = false;
        do {
            try {
                String customer = view.displayOneOrderToEditCustomer(order);
                c = service.validateCustomerInfo(customer);
                if (c) {
                    order.setCustomerName(customer);
                }
            } catch (InvalidCustomerException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } while (!c);

        State state = new State();
        boolean s = false;
        do {
            try {
                List<State> states = service.displayStates();
                String stateName = view.editState(order, states);
                s = service.validateServicedState(stateName);
                if (s) {
                    state.setStateName(stateName);
                    order.setState(state);
                }
            } catch (InvalidStateException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } while (!s);

        Product product = new Product();
        boolean p = false;
        do {
            try {
                List<Product> products = service.displayProducts();
                String productType = view.editProduct(order, products);
                p = service.validateAvailableProduct(productType);
                if (p) {
                    product.setType(productType);
                    order.setProduct(product);
                }
            } catch (InvalidProductException e) {
                view.displayErrorMessage(e.getMessage());
            }
        } while (!p);

        BigDecimal area = view.editArea(order);
        order.setArea(area);
        
        try {
            service.calculateCost(order);
        } catch (InvalidStateException | InvalidProductException e) {
            view.displayErrorMessage(e.getMessage());
        }

        //display info- dis okay?
        view.displayOneOrder(order);
        int confirm = view.editConfirm();
        if (confirm == 1) {
            service.editOrder(ld, order);
            view.editOrderSuccess();
        }
    }

//removeOrder
    public void removeOrder() throws PersistenceException, TrainingException {
        LocalDate ld = null;
        do {
            try {
                ld = view.askOrderDate();
            } catch (DateTimeParseException e) {
                view.displayErrorMessage("Please enter date in given format");
            }
        } while (ld == null);
        Order order;

        int orderNum = view.removeOrderGetNum();
        order = service.getOrder(ld, orderNum);
        if (order == null) {
            view.displayErrorMessage("Order number does not exist.");
            view.returnToMainMenu();
        } else {
            view.displayOneOrder(order);
            int confirm = view.removeConfirm();
            if (confirm == 1) {
                try {
                    service.removeOrder(ld, order);
                } catch (PersistenceException e) {
                    view.displayErrorMessage(e.getMessage());
                }
                view.removeSuccess();
            }
            view.returnToMainMenu();
        }
    }

}
