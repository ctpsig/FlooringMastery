/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flooringmastery.service;

import flooringmastery.dao.Dao;
import flooringmastery.dao.DaoStubImpl;
import flooringmastery.dao.InvalidProductException;
import flooringmastery.dao.InvalidStateException;
import flooringmastery.dao.PersistenceException;
import flooringmastery.dto.Order;
import flooringmastery.dto.Product;
import flooringmastery.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author siggelkow
 */
public class ServiceImplTest {

    Dao testDao = new DaoStubImpl();
    Service service = new ServiceImpl(testDao);

    public ServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of validateAvailableProduct method, of class ServiceImpl.
     */
    @Test
    public void testValidateAvailableProduct() throws PersistenceException {
        String productType = "Wood";
        try {
            assertTrue(service.validateAvailableProduct(productType));
        } catch (InvalidProductException e) {
            fail("InvalidProductException was not expected.");
        }
    }

    @Test
    public void testValidateProductFail() throws PersistenceException, InvalidStateException {
        String productType = "wood";
        try {
            assertTrue(service.validateAvailableProduct(productType));
            fail("InvalidStateException was not thrown");
        } catch (InvalidProductException e) {
            return;
        }
    }

    /**
     * Test of validateValidInput method, of class ServiceImpl.
     */
    @Test
    public void testValidateServicedState() throws PersistenceException, InvalidStateException {
        String stateName = "OH";
        try {
            assertTrue(service.validateServicedState(stateName));
        } catch (InvalidStateException e) {
            fail("InvalidStateException was not expected.");
        }
    }

    @Test
    public void testValidateServicedStateFail() throws PersistenceException, InvalidStateException {
        String stateName = "Oh";
        try {
            assertTrue(service.validateServicedState(stateName));
            fail("InvalidStateException was not thrown");
        } catch (InvalidStateException e) {
            return;
        }
    }
    
    @Test
    public void testValidateCustomerInfo() throws Exception {
        String customer = "Al";
        try {
            assertTrue(service.validateCustomerInfo(customer));
        } catch (InvalidCustomerException e) {
            fail("InvalidCustomerException was not expected.");
        }
    }
        
    @Test
    public void testValidateCustomerInfoFail() throws Exception {
        String customer = "";
        try {
            assertTrue(service.validateCustomerInfo(customer));
            fail("InvalidCustomerException was not thrown.");
        } catch (InvalidCustomerException e) {
            return;
        }
    }

    /**
     * Test of calculateCost method, of class ServiceImpl.
     */
    @Test
    public void testCalculateCost() throws Exception {
        Order order = testDao.getOrder(LocalDate.parse("1999-06-01"), 1);

        service.calculateCost(order);

        assertEquals(order.getMaterialCost(), new BigDecimal("515.00"));
        assertEquals(order.getLaborCost(), new BigDecimal("475.00"));
        assertEquals(order.getTax(), new BigDecimal("61.88"));
        assertEquals(order.getTotalCost(), new BigDecimal("1051.88"));

    }

    /**
     * Test of getOrderNum method, of class ServiceImpl.
     */
    @Test
    public void testGetOrderNum() throws Exception {
        List<Order> orders = testDao.displayOrders(LocalDate.parse("1999-06-01"));
        Order onlyOrder2 = new Order();
        onlyOrder2.setCustomerName("Siggelkow");
        State state2 = new State();
        state2.setStateName("OH");
        onlyOrder2.setState(state2);
        Product product2 = new Product();
        product2.setType("Wood");
        onlyOrder2.setProduct(product2);
        onlyOrder2.setArea(new BigDecimal("100.00"));
        int orderNum = service.getOrderNum(LocalDate.parse("1999-06-01"));
        onlyOrder2.setOrderNumber(orderNum);
        orders.add(onlyOrder2);
        
        assertEquals(onlyOrder2.getOrderNumber(), 2);
    }
    
    @Test
    public void testGetOrderNum1() throws Exception {
        List<Order> orders = new ArrayList<>();
        Order onlyOrder = new Order();
        int orderNum = service.getOrderNum(LocalDate.parse("1999-06-02"));
        onlyOrder.setOrderNumber(orderNum);
        
        assertEquals(onlyOrder.getOrderNumber(), 1);
    }

}
