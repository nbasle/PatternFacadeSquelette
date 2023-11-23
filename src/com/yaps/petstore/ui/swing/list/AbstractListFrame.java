package com.yaps.petstore.ui.swing.list;

import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

import javax.swing.*;
import java.awt.*;

/**
 * This abstract class helps concrete classes to display lists of data in a JTable.
 */
public abstract class AbstractListFrame extends JFrame {

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    private JTable table;

    public AbstractListFrame() {
        super();
        initComponents();
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        final String mname = "initComponents";

        try {
            table = new JTable(getData(), getColumnNames());
            table.setPreferredScrollableViewportSize(new Dimension(500, 70));

            final JScrollPane scrollPane = new JScrollPane(table);
            getContentPane().add(scrollPane);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot display the list", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(_cname, mname, e);
        }
    }

    protected abstract String[] getColumnNames();

    // This method returns all the data to fill the table
    protected abstract String[][] getData() throws FinderException;
}
