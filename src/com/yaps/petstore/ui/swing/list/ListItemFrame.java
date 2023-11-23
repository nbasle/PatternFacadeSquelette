package com.yaps.petstore.ui.swing.list;

import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.service.CatalogService;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class lists all the items of the system.
 */
public final class ListItemFrame extends AbstractListFrame {

    public ListItemFrame() {
        super();
        setTitle("Lists all the items");
    }

    protected String[] getColumnNames() {

        final String[] columnNames = {"ID", "Name", "Unit Cost", "Product Id", "Product Name"};
        return columnNames;
    }

    protected String[][] getData() throws FinderException {
        final String[][] data;

        final CatalogService service = new CatalogService();
        final Collection items;

        items = service.findItems();
        data = new String[items.size()][5];

        int i = 0;
        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            final Item item = (Item) iterator.next();
            data[i][0] = item.getId();
            data[i][1] = item.getName();
            data[i][2] = String.valueOf(item.getUnitCost());
            data[i][3] = item.getProduct().getId();
            data[i][4] = item.getProduct().getName();
            i++;
        }
        return data;
    }
}
