package com.yaps.petstore.ui.swing.list;

import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.service.CatalogService;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class lists all the products of the system.
 */
public final class ListProductFrame extends AbstractListFrame {

    public ListProductFrame() {
        super();
        setTitle("Lists all the products");
    }

    protected String[] getColumnNames() {

        final String[] columnNames = {"ID", "Name", "Description", "Category Id", "Category Name"};
        return columnNames;
    }

    protected String[][] getData() throws FinderException {
        final String[][] data;

        final CatalogService service = new CatalogService();
        final Collection products;

        products = service.findProducts();
        data = new String[products.size()][5];

        int i = 0;
        for (Iterator iterator = products.iterator(); iterator.hasNext();) {
            final Product product = (Product) iterator.next();
            data[i][0] = product.getId();
            data[i][1] = product.getName();
            data[i][2] = product.getDescription();
            data[i][3] = product.getCategory().getId();
            data[i][4] = product.getCategory().getName();
            i++;
        }
        return data;
    }
}
