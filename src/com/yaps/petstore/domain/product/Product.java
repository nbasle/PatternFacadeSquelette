package com.yaps.petstore.domain.product;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

import java.util.Collection;

/**
 * This class represents a Product in the catalog of the YAPS company.
 * The catalog is divided into catagories. Each one divided into products
 * and each product in items.
 */
public final class Product extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _name;
    private String _description;
    private Category _category;
    private Collection _items;

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new ProductDAO();
    }

    public Product() {
    }

    public Product(final String id) {
        _id = id;
    }

    public Product(final String id, final String name, final String description, final Category category) {
        _id = id;
        _name = name;
        _description = description;
        _category = category;
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public void findByPrimaryKey(final String id) throws FinderException, CheckException {
        final String mname = "findByPrimaryKey";
        Trace.entering(_cname, mname, id);

        // Checks data integrity
        checkId(id);

        // Uses the DAO to access the persistent layer
        final Product temp = (Product) _dao.select(id);

        // Sets data to current object
        _id = temp.getId();
        _name = temp.getName();
        _description = temp.getDescription();
        _category = temp.getCategory();
        _items = temp.getItems();
    }

    protected void checkData() throws CheckException {
        checkId(_id);
        if (_name == null || "".equals(_name))
            throw new CheckException("Invalid name");
        if (_description == null || "".equals(_description))
            throw new CheckException("Invalid description");
        if (_category == null || _category.getId() == null || "".equals(_category.getId()))
            throw new CheckException("Invalid category");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(final String description) {
        _description = description;
    }

    public Category getCategory() {
        return _category;
    }

    public void setCategory(final Category category) {
        _category = category;
    }

    public Collection getItems() {
        return _items;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tProduct {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tName=").append(_name);
        buf.append("\n\t\tDescription=").append(_description);
        buf.append("\n\t\tCategory Id=").append(_category.getId());
        buf.append("\n\t\tCategory Name=").append(_category.getName());
        buf.append("\n\t}");
        return buf.toString();
    }
}
