package com.yaps.petstore.service;

import com.yaps.petstore.domain.customer.Customer;
import com.yaps.petstore.domain.order.Order;
import com.yaps.petstore.domain.orderline.OrderLine;
import com.yaps.petstore.exception.*;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class is a facade for all order services.
 */
public final class OrderService {

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Given a Order object, this method creates a Order.
     *
     * @param order cannot be null.
     * @return the created Order
     * @throws CreateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public Order createOrder(final Order order) throws CreateException, CheckException {
        if (order == null)
            throw new CreateException("Order object is null");

        if (order.getCustomer() == null)
            throw new CheckException("Invalid Customer");

        if (order.getOrderLines() == null || order.getOrderLines().size() < 0)
            throw new CheckException("There are no order lines");

        // Finds the customer
        try {
            order.getCustomer().findByPrimaryKey(order.getCustomer().getId());
        } catch (FinderException e) {
            throw new CreateException("Customer must exist to create an order");
        }

        // Creates the object
        order.create();

        // Creates all the orderLines linked with the order
        for (Iterator iterator = order.getOrderLines().iterator(); iterator.hasNext();) {
            final OrderLine orderLine = (OrderLine) iterator.next();
            orderLine.setOrder(order);
            orderLine.create();
        }

        return order;
    }

    /**
     * Given an id this method uses the Order domain object to load all the data of this
     * object.
     *
     * @param orderId identifier
     * @return Order
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     */
    public Order findOrder(final String orderId) throws FinderException, CheckException {
        final Order order = new Order();
        final Customer customer = new Customer();

        // Finds the object
        order.findByPrimaryKey(orderId);

        // Retreives the data for the customer and sets it
        customer.findByPrimaryKey(order.getCustomer().getId());
        order.setCustomer(customer);

        // Retreives the data for all the order lines
        final Collection orderLines = new OrderLine().findAll(orderId);
        order.setOrderLines(orderLines);

        return order;
    }

    /**
     * Given an id, this method finds an Order domain object and then calls its deletion
     * method.
     *
     * @param orderId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public void deleteOrder(final String orderId) throws RemoveException, CheckException {
        final Order order = new Order();

        // Checks if the object exists
        try {
            order.findByPrimaryKey(orderId);
        } catch (FinderException e) {
            throw new RemoveException("Order must exist to be deleted");
        }

        // Deletes the object
        order.remove();
    }
}
