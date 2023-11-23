package com.yaps.petstore.ui.swing.order;

import com.yaps.petstore.domain.customer.Customer;
import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.domain.order.Order;
import com.yaps.petstore.domain.orderline.OrderLine;
import com.yaps.petstore.exception.ObjectNotFoundException;
import com.yaps.petstore.logging.Trace;
import com.yaps.petstore.service.CatalogService;
import com.yaps.petstore.service.CustomerService;
import com.yaps.petstore.service.OrderService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This frame is used by the employee to create an order. To create an order the employee has
 * to first find the customer, calculate the order total amount and then create it.
 */
public final class CreateOrderFrame extends AbstractOrderFrame {

    private final JButton buttonFindCustomer = new JButton();
    private final JButton buttonCalculate = new JButton();
    private final JButton buttonCreate = new JButton();
    private final JButton buttonClear = new JButton();

    public CreateOrderFrame() {
        initComponents();
        setSize(500, 610);
        setTitle("Pet Store - Create Order");
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        initComponents(true);

        // Panel North
        labelTitle.setText("Create Order");

        // Panel South
        panelSouth.setLayout(new GridLayout(1, 4));

        buttonFindCustomer.setText("Find Customer");
        buttonFindCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonFindCustomerActionPerformed();
            }
        });
        panelSouth.add(buttonFindCustomer);

        buttonCalculate.setText("Calculate order");
        buttonCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonCalculateActionPerformed();
            }
        });
        panelSouth.add(buttonCalculate);

        buttonCreate.setText("Create order");
        buttonCreate.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonCreateActionPerformed();
            }
        });
        panelSouth.add(buttonCreate);

        buttonClear.setText("Clear");
        buttonClear.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonClearActionPerformed();
            }
        });
        panelSouth.add(buttonClear);

        getContentPane().add(panelSouth, BorderLayout.SOUTH);
    }

    // Calling this method creates an order into the system
    private void buttonCreateActionPerformed() {
        final String mname = "buttonCreateActionPerformed";

        final OrderService service = new OrderService();
        final Order order;
        try {
            // Sets all the Order data
            order = new Order(textFirstName.getText(), textLastName.getText(), textStreet1.getText(), textCity.getText(), textZipcode.getText(), textCountry.getText(), new Customer(textCustomerId.getText()));
            order.setStreet2(textStreet2.getText());
            order.setState(textState.getText());
            order.setCreditCardType(textCCType.getText());
            order.setCreditCardNumber(textCCNumber.getText());
            order.setCreditCardExpiryDate(textCCExpDate.getText());

            // Sets all the order items data
            final Collection orderLines = new ArrayList();
            OrderLine orderLine;
            String orderLineId;
            for (int i = 0; i < 5; i++) {
                orderLineId = textItemId[i].getText();
                if (!"".equals(orderLineId)) {
                    orderLine = new OrderLine(Integer.parseInt(textItemQuantity[i].getText()), Double.parseDouble(textItemUnitCost[i].getText()), order, new Item(orderLineId));
                    orderLines.add(orderLine);
                }
            }
            order.setOrderLines(orderLines);

            // Create the order
            final Order result = service.createOrder(order);
            textOrderId.setText(result.getId());
            textOrderDate.setText(DateFormat.getDateInstance().format(result.getOrderDate()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the order service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(_cname, mname, e);
        }
    }

    // Calling this method calculates the order total and all sub-totals
    private void buttonCalculateActionPerformed() {
        final String mname = "buttonCalculateActionPerformed";

        final CatalogService service = new CatalogService();
        final StringBuffer itemNotFound = new StringBuffer();
        String itemId, quantity;
        boolean itemToCalculate = false;
        Item item;

        try {

            // For all the items we get all the information (price, name...)
            for (int i = 0; i < 5; i++) {
                itemId = textItemId[i].getText();
                if (!"".equals(itemId)) {
                    try {
                        item = service.findItem(itemId);
                        textItemName[i].setText(item.getName());
                        textItemUnitCost[i].setText(String.valueOf(item.getUnitCost()));
                        itemToCalculate = true;
                    } catch (ObjectNotFoundException e) {
                        itemNotFound.append("\n" + itemId);
                    }
                }
            }

            // Some items are not found in the database
            if (itemNotFound.length() != 0) {
                JOptionPane.showMessageDialog(this, "The following items are not found:" + itemNotFound, "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (itemToCalculate) {
                // Else we calculate the total of the order and the subtotals of each row
                double total = 0, subtotal;
                for (int i = 0; i < 5; i++) {
                    quantity = textItemQuantity[i].getText();
                    if (!"".equals(quantity)) {
                        subtotal = Integer.parseInt(quantity) * Double.parseDouble(textItemUnitCost[i].getText());
                        total += subtotal;
                        textItemSubTotoal[i].setText(String.valueOf(subtotal));
                    }
                }
                textTotal.setText(String.valueOf(total));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the catalog service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(_cname, mname, e);
        }
    }

    // Calling this method clear all the form
    private void buttonClearActionPerformed() {
        clearFrame();
    }

    // Calling this method gets the customer information from the system
    private void buttonFindCustomerActionPerformed() {
        final String mname = "buttonFindCustomerActionPerformed";

        final CustomerService service = new CustomerService();
        final Customer customer;
        try {
            customer = service.findCustomer(textCustomerId.getText());
            textCity.setText(customer.getCity());
            textCountry.setText(customer.getCountry());
            textFirstName.setText(customer.getFirstname());
            textLastName.setText(customer.getLastname());
            textState.setText(customer.getState());
            textStreet1.setText(customer.getStreet1());
            textStreet2.setText(customer.getStreet2());
            textZipcode.setText(customer.getZipcode());
        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Customer id not found", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the customer service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(_cname, mname, e);
        }
    }
}
