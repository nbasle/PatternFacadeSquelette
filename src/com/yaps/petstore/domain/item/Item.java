package com.yaps.petstore.domain.item;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

/**
 * This class represents an Item in the catalog of the YAPS company.
 * The catalog is divided into catagories. Each one divided into products
 * and each product in items.
 */
public final class Item extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _name;
    private double _unitCost;
    private Product _product;

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new ItemDAO();
    }

    public Item() {
    }

    public Item(final String id) {
        _id = id;
    }

    public Item(final String id, final String name, final double unitCost, final Product product) {
        _id = id;
        _name = name;
        _unitCost = unitCost;
        _product = product;
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
        final Item temp = (Item) _dao.select(id);

        // Sets data to current object
        _id = temp.getId();
        _name = temp.getName();
        _unitCost = temp.getUnitCost();
        _product = temp.getProduct();
    }

    protected void checkData() throws CheckException {
        checkId(_id);
        if (_name == null || "".equals(_name))
            throw new CheckException("Invalid name");
        if (_unitCost <= 0)
            throw new CheckException("Invalid unit cost");
        if (_product == null || _product.getId() == null || "".equals(_product.getId()))
            throw new CheckException("Invalid product");
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

    public double getUnitCost() {
        return _unitCost;
    }

    public void setUnitCost(final double unitCost) {
        _unitCost = unitCost;
    }

    public Product getProduct() {
        return _product;
    }

    public void setProduct(final Product product) {
        _product = product;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tItem {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tName=").append(_name);
        buf.append("\n\t\tUnit Cost=").append(_unitCost);
        buf.append("\n\t\tProduct Id=").append(_product.getId());
        buf.append("\n\t\tProduct Name=").append(_product.getName());
        buf.append("\n\t}");
        return buf.toString();
    }
}
