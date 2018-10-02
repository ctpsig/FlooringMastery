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
public class DaoImplTest {

    public Dao testDao = new DaoImpl();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");

    public DaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws PersistenceException {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws PersistenceException, TrainingException {
        LocalDate ld = LocalDate.parse("06011999", formatter);
        List<Order> orders = testDao.displayOrders(ld);
        for (Order curOrder : orders) {
            testDao.removeOrder(ld, curOrder);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of displayOrders method, of class DaoImpl.
     */
    @Test
    public void testDisplayOrders() throws PersistenceException, TrainingException {
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Siggelkow");
        State state = new State();
        state.setStateName("OH");
        state.setTaxRate(new BigDecimal("6.25"));
        order.setState(state);
        Product product = new Product();
        product.setType("Wood");
        product.setCostPerSqFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSqFoot(new BigDecimal("4.75"));
        order.setProduct(product);
        order.setArea(new BigDecimal("100.00"));
        order.setMaterialCost(new BigDecimal("500.00"));
        order.setLaborCost(new BigDecimal("400.00"));
        order.setTax(new BigDecimal("45.76"));
        order.setTotalCost(new BigDecimal("1000.00"));
        LocalDate ld = LocalDate.parse("06011999", formatter);
        testDao.addOrder(ld, order);

        Order order2 = new Order();
        order2.setOrderNumber(2);
        order.setCustomerName("Siggelkow");
        State state2 = new State();
        state2.setStateName("OH");
        state2.setTaxRate(new BigDecimal("6.25"));
        order2.setState(state);
        Product product2 = new Product();
        product2.setType("Wood");
        product2.setCostPerSqFoot(new BigDecimal("5.15"));
        product2.setLaborCostPerSqFoot(new BigDecimal("4.75"));
        order2.setProduct(product);
        order2.setArea(new BigDecimal("100.00"));
        order2.setMaterialCost(new BigDecimal("500.00"));
        order2.setLaborCost(new BigDecimal("400.00"));
        order2.setTax(new BigDecimal("45.76"));
        order2.setTotalCost(new BigDecimal("1000.00"));
        testDao.addOrder(ld, order2);

        assertEquals(testDao.displayOrders(ld).size(), 2);
    }

    /**
     * Test of sendStates method, of class DaoImpl.
     */
    @Test
    public void testSendStates() throws PersistenceException, InvalidStateException {
        State state1 = new State();
        state1.setStateName("MI");
        state1.setTaxRate(new BigDecimal("5.75"));
        State state2 = new State();
        
        state2 = testDao.sendState("MI");
        assertEquals(state1, state2);
    }

    /**
     * Test of sendProducts method, of class DaoImpl.
     */
    @Test
    public void testSendProducts() throws Exception {
        Product product1 = new Product();
        product1.setType("Wood");
        product1.setCostPerSqFoot(new BigDecimal("5.15"));
        product1.setLaborCostPerSqFoot(new BigDecimal("4.75"));
        Product product2 = new Product();
        
        product2 = testDao.sendProduct("Wood");
        assertEquals(product1, product2);
    }

    /**
     * Test of addOrder method, of class DaoImpl.
     */
    @Test
    public void testAddAndGetOrder() throws PersistenceException, TrainingException {
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Siggelkow");
        State state = new State();
        state.setStateName("OH");
        state.setTaxRate(new BigDecimal("6.25"));
        order.setState(state);
        Product product = new Product();
        product.setType("Wood");
        product.setCostPerSqFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSqFoot(new BigDecimal("4.75"));
        order.setProduct(product);
        order.setArea(new BigDecimal("100.00"));
        order.setMaterialCost(new BigDecimal("500.00"));
        order.setLaborCost(new BigDecimal("400.00"));
        order.setTax(new BigDecimal("45.76"));
        order.setTotalCost(new BigDecimal("1000.00"));
        LocalDate ld = LocalDate.parse("06011999", formatter);
        testDao.addOrder(ld, order);
        Order fromDao = testDao.getOrder(ld, order.getOrderNumber());

        assertEquals(order, fromDao);
    }

    /**
     * Test of editOrder method, of class DaoImpl.
     */
    @Test
    public void testEditOrder() throws Exception{
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Siggelkow");
        State state = new State();
        state.setStateName("OH");
        state.setTaxRate(new BigDecimal("6.25"));
        order.setState(state);
        Product product = new Product();
        order.setProduct(product);
        product.setType("Wood");
        product.setCostPerSqFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSqFoot(new BigDecimal("4.75"));
        order.setArea(new BigDecimal("100.00"));
        order.setMaterialCost(new BigDecimal("500.00"));
        order.setLaborCost(new BigDecimal("400.00"));
        order.setTax(new BigDecimal("45.76"));
        order.setTotalCost(new BigDecimal("1000.00"));
        LocalDate ld = LocalDate.parse("06011999", formatter);
        testDao.addOrder(ld, order);
        
        order.setCustomerName("Charlie");
        order.setArea(new BigDecimal("200.00"));
        testDao.editOrder(ld, order);
        
        assertEquals(testDao.getOrder(ld, 1), order);
    }

    /**
     * Test of removeOrder method, of class DaoImpl.
     */
    @Test
    public void testRemoveOrder() throws PersistenceException, TrainingException {
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Siggelkow");
        State state = new State();
        state.setStateName("OH");
        state.setTaxRate(new BigDecimal("6.25"));
        order.setState(state);
        Product product = new Product();
        order.setProduct(product);
        product.setType("Wood");
        product.setCostPerSqFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSqFoot(new BigDecimal("4.75"));
        order.setArea(new BigDecimal("100.00"));
        order.setMaterialCost(new BigDecimal("500.00"));
        order.setLaborCost(new BigDecimal("400.00"));
        order.setTax(new BigDecimal("45.76"));
        order.setTotalCost(new BigDecimal("1000.00"));
        LocalDate ld = LocalDate.parse("06011999", formatter);
        testDao.addOrder(ld, order);

        Order order2 = new Order();
        order2.setOrderNumber(2);
        order2.setCustomerName("Siggelkow");
        State state2 = new State();
        state2.setStateName("OH");
        state2.setTaxRate(new BigDecimal("6.25"));
        order2.setState(state);
        Product product2 = new Product();
        product2.setType("Wood");
        product2.setCostPerSqFoot(new BigDecimal("5.15"));
        product2.setLaborCostPerSqFoot(new BigDecimal("4.75"));
        order2.setProduct(product);
        order2.setArea(new BigDecimal("100.00"));
        order2.setMaterialCost(new BigDecimal("500.00"));
        order2.setLaborCost(new BigDecimal("400.00"));
        order2.setTax(new BigDecimal("45.76"));
        order2.setTotalCost(new BigDecimal("1000.00"));
        testDao.addOrder(ld, order2);
        
        testDao.removeOrder(ld, order2);
        assertEquals(1, testDao.displayOrders(ld).size());
        assertNull(testDao.getOrder(ld, order2.getOrderNumber()));
    }

}
