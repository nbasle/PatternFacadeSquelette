package com.yaps.petstore.domain.category;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

import java.util.Collection;

/**
 * This class represents a Category in the catalog of the YAPS company.
 * The catalog is divided into catagories. Each one divided into products
 * and each product in items.
 */
public final class Category extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _name;
    private String _description;
    private Collection _products;

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new CategoryDAO();
    }

    public Category() {
    }

    public Category(final String id) {
        _id = id;
    }

    public Category(final String id, final String name, final String description) {
        _id = id;
        _name = name;
        _description = description;
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
        final Category temp = (Category) _dao.select(id);

        // Sets data to current object
        _id = temp.getId();
        _name = temp.getName();
        _description = temp.getDescription();
        _products = temp.getProducts();
    }

    protected void checkData() throws CheckException {
        checkId(_id);
        if (_name == null || "".equals(_name))
            throw new CheckException("Invalid name");
        if (_description == null || "".equals(_description))
            throw new CheckException("Invalid description");
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

    public Collection getProducts() {
        return _products;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tCategory {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tName=").append(_name);
        buf.append("\n\t\tDescription=").append(_description);
        buf.append("\n\t}");
        return buf.toString();
    }
}
