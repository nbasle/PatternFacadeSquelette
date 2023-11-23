package com.yaps.petstore.service;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.domain.customer.Customer;
import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.domain.order.Order;
import com.yaps.petstore.domain.orderline.OrderLine;
import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.exception.*;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class tests the CatalogService class
 */
public final class OrderServiceTest extends AbstractTestCase {

    public OrderServiceTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(OrderServiceTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindOrderWithInvalidValues() throws Exception {
        final OrderService service = getOrderService();

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            service.findOrder(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findOrder(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findOrder(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testServiceCreateOrder() throws Exception {
        final String id = getUniqueStringId();
        Order order = null;

        // Creates an object
        final String orderId = createOrder(id);

        // Ensures that the object exists
        try {
            order = findOrder(orderId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkOrder(order, id);

        // Cleans the test environment
        deleteOrder(orderId);

        try {
            findOrder(orderId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateOrderWithInvalidValues() throws Exception {
        final OrderService service = getOrderService();
        Order order;

        // Creates an object with a null parameter
        try {
            service.createOrder(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            order = new Order(new String(), new String(), new String(), new String(), new String(), new String(), null);
            service.createOrder(order);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            order = new Order(null, null, null, null, null, null, null);
            service.createOrder(order);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=          Private Methods       =
    //==================================
    private OrderService getOrderService() {
        return new OrderService();
    }

    private CustomerService getCustomerService() {
        return new CustomerService();
    }

    private CatalogService getCatalogService() {
        return new CatalogService();
    }

    private Order findOrder(final String id) throws FinderException, CheckException {
        final Order order = getOrderService().findOrder(id);
        return order;
    }

    // Creates a category first, then a product linked to this category and an item linked to the product
    // Creates a Customer and an order linked to the customer
    // Creates an orderLine linked to the order and the item
    private String createOrder(final String id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        getCatalogService().createProduct(product);
        // Create Item
        final Item item = new Item("item" + id, "name" + id, Double.parseDouble(id), product);
        getCatalogService().createItem(item);

        // Create Customer
        final Customer customer = new Customer("custo" + id, "firstname" + id, "lastname" + id);
        getCustomerService().createCustomer(customer);

        // Create Order
        final Order order = new Order("firstname" + id, "lastname" + id, "street1" + id, "city" + id, "zip" + id, "country" + id, customer);
        order.setStreet2("street2" + id);
        order.setCreditCardExpiryDate("ccexp" + id);
        order.setCreditCardNumber("ccnum" + id);
        order.setCreditCardType("cctyp" + id);
        order.setState("state" + id);

        // Creates two order lines
        final OrderLine oi1 = new OrderLine(Integer.parseInt(id), item.getUnitCost(), order, item);
        final OrderLine oi2 = new OrderLine(Integer.parseInt(id), item.getUnitCost(), order, item);
        final Collection orderLines = new ArrayList();
        orderLines.add(oi1);
        orderLines.add(oi2);
        order.setOrderLines(orderLines);

        final Order result = getOrderService().createOrder(order);
        return result.getId();
    }

    private void deleteOrder(final String orderId) throws RemoveException, CheckException {
        getOrderService().deleteOrder(orderId);
    }

    private void checkOrder(final Order order, final String id) {
        assertEquals("firstname", "firstname" + id, order.getFirstname());
        assertEquals("lastname", "lastname" + id, order.getLastname());
        assertEquals("city", "city" + id, order.getCity());
        assertEquals("country", "country" + id, order.getCountry());
        assertEquals("state", "state" + id, order.getState());
        assertEquals("street1", "street1" + id, order.getStreet1());
        assertEquals("street2", "street2" + id, order.getStreet2());
        assertEquals("zipcode", "zip" + id, order.getZipcode());
        assertEquals("CreditCardExpiryDate", "ccexp" + id, order.getCreditCardExpiryDate());
        assertEquals("CreditCardNumber", "ccnum" + id, order.getCreditCardNumber());
        assertEquals("CreditCardType", "cctyp" + id, order.getCreditCardType());
        assertEquals("order items", 2, order.getOrderLines().size());
        assertEquals("item id", "item" + id, ((OrderLine)order.getOrderLines().iterator().next()).getItem().getId());
    }
}
