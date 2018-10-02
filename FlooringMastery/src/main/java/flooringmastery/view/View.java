/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.view;

import flooringmastery.dao.InvalidStateException;
import flooringmastery.dto.Order;
import flooringmastery.dto.Product;
import flooringmastery.dto.State;
import flooringmastery.service.InvalidAreaException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author siggelkow
 */
public class View {

    private UserIO io;
    DateTimeFormatter userFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    View(UserIO io) {
        this.io = io;
    }

    public void displayMode(String mode) {
        if (mode.equals("Training")) {
            io.print("");
            io.print("=== You are in " + mode + " Mode. You will not be able to save any information you enter. ===");
        } else if (mode.equals("Production")) {
            io.print("");
            io.print("=== You are in " + mode + " Mode. Any changes you make will be saved. ===");
        }
    }

    public int displayMenu(String mode) {
        io.print("<< Flooring Orders >>");
        io.print("(" + mode + " mode)");
        io.print("1.) Display Orders");
        io.print("2.) Add an Order");
        io.print("3.) Edit an Order");
        io.print("4.) Remove an Order");
        io.print("5.) Quit");
        return io.readInt("Please select an option (1-5)", 1, 5);
    }

    public LocalDate askDate() {
        return LocalDate.parse(io.readString(
                "What date would you like to view orders for? (MM/dd/yyyy)"), userFormatter);
    }

    public LocalDate askOrderDate() {
        return LocalDate.parse(io.readString(
                "What date was this order placed? (MM/dd/yyyy)"), userFormatter);
    }

    public void displayOrdersBanner() {
        io.print("<< Displaying Orders >>");
    }

    public void displayOrders(List<Order> orders) {
        for (Order o : orders) {
            io.print("========================");
            io.print("Order Number: " + o.getOrderNumber());
            io.print("========================");
            io.print("Customer Name: " + o.getCustomerName());
            io.print("State: " + o.getState().getStateName());
            io.print("Tax Rate: " + o.getState().getTaxRate());
            io.print("Product Type: " + o.getProduct().getType());
            io.print("Area: " + o.getArea());
            io.print("Cost Per Square Foot: $" + o.getProduct().getCostPerSqFoot());
            io.print("Labor Cost Per Square Foot: $" + o.getProduct().getLaborCostPerSqFoot());
            io.print("Material Cost: $" + o.getMaterialCost());
            io.print("Labor Cost: $" + o.getLaborCost());
            io.print("Tax: $" + o.getTax());
            io.print("Total Cost: $" + o.getTotalCost());
        }
    }

    public void displayOneOrder(Order o) {
        io.print("========================");
        io.print("Order Number: " + o.getOrderNumber());
        io.print("========================");
        io.print("Customer Name: " + o.getCustomerName());
        io.print("State: " + o.getState().getStateName());
        io.print("Tax Rate: " + o.getState().getTaxRate());
        io.print("Product Type: " + o.getProduct().getType());
        io.print("Area: " + o.getArea());
        io.print("Cost Per Square Foot: $" + o.getProduct().getCostPerSqFoot());
        io.print("Labor Cost Per Square Foot: $" + o.getProduct().getLaborCostPerSqFoot());
        io.print("Material Cost: $" + o.getMaterialCost());
        io.print("Labor Cost: $" + o.getLaborCost());
        io.print("Tax: $" + o.getTax());
        io.print("Total Cost: $" + o.getTotalCost());
    }

    public String displayOneOrderToEditCustomer(Order o) {
        io.print("========================");
        io.print("Order Number: " + o.getOrderNumber());
        io.print("========================");
        String customer = io.readString("Customer Name (" + o.getCustomerName() + "):");
        if (customer != null && !customer.trim().equals("")) {
            return customer;
        } else {
            return o.getCustomerName();
        }
    }

    public String editState(Order o, List<State> states) {
        List<String> stateNames = states.stream()
                .map((s) -> s.getStateName())
                .collect(Collectors.toList());
        String state = io.readString("State " + Arrays.toString(stateNames.toArray())
                + " | (Currently: " + o.getState().getStateName() + "):");
        if (state != null && !state.trim().equals("")) {
            return state;
        } else {
            return o.getState().getStateName();
        }
    }

    public String editProduct(Order o, List<Product> products) {
        List<String> productTypes = products.stream()
                .map((p) -> p.getType())
                .collect(Collectors.toList());
        String product = io.readString("Flooring Type " + Arrays.toString(productTypes.toArray()) 
                + " | (Currently: " + o.getProduct().getType() + "):");
        if (product != null && !product.trim().equals("")) {
            return product;
        } else {
            return o.getProduct().getType();
        }
    }

    public BigDecimal editArea(Order o) {
        BigDecimal orderArea = null;
        String area = null;
        do {
            try {
                area = io.readString("Area (" + o.getArea() + "):");
                orderArea = new BigDecimal(area);
                if (area != null && !area.trim().equals("") && orderArea.compareTo(BigDecimal.ZERO) >= 0) {
                    return orderArea;
                }
                if (orderArea.compareTo(BigDecimal.ZERO) <= 0) {
                    io.print("Please enter a valid area number.");
                    continue;
                }

            } catch (NumberFormatException e) {
                if (area.equals("") || area == null) {
                    return o.getArea();
                }
                io.print("Please enter a valid area number.");
            }
        } while (orderArea == null || orderArea.compareTo(BigDecimal.ZERO) <= 0);
        return o.getArea();
    }

    public int editConfirm() {
        return io.readInt("Are you sure you would like to make these changes to this order? 1.) Yes or 2.) No", 1, 2);
    }

    public void editOrderSuccess() {
        io.print("=== You successfully edited the order! ===");
    }

    public void takeAnOrderBanner() {
        io.print("<< Please enter order information >>");
    }

    public String takeAnOrderCustomer() {
        return io.readString("Customer Name: ");
    }

    public String takeAnOrderState(List<State> states) {
        List<String> stateNames = states.stream()
                .map((s) -> s.getStateName())
                .collect(Collectors.toList());
        System.out.print("State ");
        System.out.print(Arrays.toString(stateNames.toArray()));
        return io.readString(":");
    }

    public String takeAnOrderProduct(List<Product> products) {
        List<String> productTypes = products.stream()
                .map((p) -> p.getType())
                .collect(Collectors.toList());
        System.out.print("Flooring Type");
        System.out.print(Arrays.toString(productTypes.toArray()));
        return io.readString(":");
    }

    public BigDecimal takeAnOrderArea() throws InvalidAreaException {
        BigDecimal orderArea = null;
        do {
            try {
                orderArea = io.readBigDecimal("Area: ");
                if (orderArea.compareTo(BigDecimal.ZERO) <= 0) {
                    io.print("Please enter a valid area number.");
                }
            } catch (NumberFormatException e) {
                io.print("Please enter a valid area number.");
            }
        } while (orderArea == null || orderArea.compareTo(BigDecimal.ZERO) <= 0);

        return orderArea;
    }

    public int addConfirm() {
        return io.readInt("Are you sure you would like to submit this order? 1.) Yes or 2.) No", 1, 2);
    }

    public void addSuccess() {
        io.print("=== Your new order has been entered! ===");
    }

    public int removeOrderGetNum() {
        return io.readInt("What is the order number of the order you would like to remove?");
    }

    public int editOrderGetNum() {
        return io.readInt("What is the order number of the order you would like to edit?");
    }

    public int removeConfirm() {
        return io.readInt("Are you sure you would like to remove this order? 1.) Yes or 2.) No", 1, 2);
    }

    public void removeSuccess() {
        io.print("=== You successfully removed the order! ===");
    }

    public void returnToMainMenu() {
        io.readString("Hit enter to return to the main menu.");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== Error ===");
        io.print(errorMsg);
    }

}
