package com.yaps.petstore.ui.swing.order;

import com.yaps.petstore.domain.order.Order;
import com.yaps.petstore.domain.orderline.OrderLine;
import com.yaps.petstore.exception.ObjectNotFoundException;
import com.yaps.petstore.logging.Trace;
import com.yaps.petstore.service.OrderService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Iterator;

/**
 * This frame is used by the employee to display and remove an order.
 */
public final class ManageOrderFrame extends AbstractOrderFrame {

    private final JButton buttonFindOrder = new JButton();
    private final JButton buttonDeleteOrder = new JButton();

    public ManageOrderFrame() {
        initComponents();
        setTitle("Pet Store - Manage Order");
        setSize(500, 610);
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        initComponents(false);

        // Panel North
        labelTitle.setText("Manage Order");

        // Panel South
        panelSouth.setLayout(new GridLayout(1, 2));

        buttonFindOrder.setText("Find Order");
        buttonFindOrder.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonFindOrderActionPerformed();
            }
        });
        panelSouth.add(buttonFindOrder);

        buttonDeleteOrder.setText("Delete order");
        buttonDeleteOrder.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                buttonDeleteOrderActionPerformed();
            }
        });
        panelSouth.add(buttonDeleteOrder);

        getContentPane().add(panelSouth, BorderLayout.SOUTH);
    }

    // By calling this method it deletes the selected order
    private void buttonDeleteOrderActionPerformed() {
        final String mname = "buttonDeleteOrderActionPerformed";

        final String orderId = textOrderId.getText();
        if ("".equals(orderId)) {
            JOptionPane.showMessageDialog(this, "You have to enter an order id ", "Delete", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Asks if we want to remove the order
        final int anwser = JOptionPane.showConfirmDialog(this, "Do you want to remove order id " + orderId, "Delete", JOptionPane.YES_NO_OPTION);
        if (anwser == JOptionPane.NO_OPTION)
            return;

        final OrderService service = new OrderService();
        try {
            service.deleteOrder(orderId);
            clearFrame();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the order service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(_cname, mname, e);
        }
    }

    // By calling this method it gets all the information of the order from the database
    private void buttonFindOrderActionPerformed() {
        final String mname = "buttonFindOrderActionPerformed";

        final OrderService service = new OrderService();
        final Order order;
        try {
            order = service.findOrder(textOrderId.getText());
            textOrderDate.setText(DateFormat.getInstance().format(order.getOrderDate()));
            textCustomerId.setText(order.getCustomer().getId());
            textFirstName.setText(order.getFirstname());
            textLastName.setText(order.getLastname());
            textStreet1.setText(order.getStreet1());
            textStreet2.setText(order.getStreet2());
            textCity.setText(order.getCity());
            textState.setText(order.getState());
            textZipcode.setText(order.getZipcode());
            textCountry.setText(order.getCountry());
            textCCNumber.setText(order.getCreditCardNumber());
            textCCType.setText(order.getCreditCardType());
            textCCExpDate.setText(order.getCreditCardExpiryDate());

            int i = 0, quantity;
            double unitCost, subtotal, total = 0;
            for (Iterator iterator = order.getOrderLines().iterator(); iterator.hasNext();) {
                final OrderLine orderLine = (OrderLine) iterator.next();
                textItemId[i].setText(orderLine.getItem().getId());
                textItemName[i].setText(orderLine.getItem().getName());
                quantity = orderLine.getQuantity();
                unitCost = orderLine.getUnitCost();
                subtotal = quantity * unitCost;
                total += subtotal;
                textItemQuantity[i].setText(String.valueOf(quantity));
                textItemUnitCost[i].setText(String.valueOf(unitCost));
                textItemSubTotoal[i].setText(String.valueOf(subtotal));
                i++;
            }
            textTotal.setText(String.valueOf(total));

        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Order id not found", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot access the order service", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(_cname, mname, e);
        }
    }
}
