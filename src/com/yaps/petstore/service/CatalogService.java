package com.yaps.petstore.service;

import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.exception.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class is a facade for all catalog services.
 */
public final class CatalogService {

    // ======================================
    // =      Category Business methods     =
    // ======================================
    /**
     * Given a Category object, this method creates a Category.
     *
     * @param category cannot be null.
     * @return the created Category
     * @throws DuplicateKeyException is thrown if an object with the same id
     *                               already exists in the system
     * @throws CreateException       is thrown if a DomainException is caught
     *                               or a system failure is occurs
     * @throws CheckException        is thrown if a invalid data is found
     */
    public Category createCategory(final Category category) throws CreateException, CheckException {
        if (category == null)
            throw new CreateException("Category object is null");

        // Creates the object
        return (Category) category.create();
    }

    /**
     * Given an id this method uses the Category domain object to load all the data of this
     * object.
     *
     * @param categoryId identifier
     * @return Category
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     */
    public Category findCategory(final String categoryId) throws FinderException, CheckException {
        final Category category = new Category();

        // Finds the object
        category.findByPrimaryKey(categoryId);

        return category;
    }

    /**
     * Given an id, this method finds a Category domain object and then calls its deletion
     * method.
     *
     * @param categoryId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public void deleteCategory(final String categoryId) throws RemoveException, CheckException {
        final Category category = new Category();

        // Checks if the object exists
        try {
            category.findByPrimaryKey(categoryId);
        } catch (FinderException e) {
            throw new RemoveException("Category must exist to be deleted");
        }

        // Deletes the object
        category.remove();
    }

    /**
     * Given a Category object, this method updates a Category.
     *
     * @param category cannot be null.
     * @throws UpdateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public void updateCategory(final Category category) throws UpdateException, CheckException {
        if (category == null)
            throw new UpdateException("Category object is null");

        // Updates the object
        category.update();
    }

    /**
     * This method return all the categories from the system. It uses the Category domain object
     * to get the data.
     *
     * @return a collection of Category
     * @throws ObjectNotFoundException is thrown if the collection is empty
     */
    public Collection findCategories() throws FinderException {

        // Finds all the objects
        final Collection categories = new Category().findAll();

        return categories;
    }

    // ======================================
    // =      Product Business methods     =
    // ======================================
    /**
     * Given a Product object, this method creates a Product.
     *
     * @param product cannot be null.
     * @return the created Product
     * @throws DuplicateKeyException is thrown if an object with the same id
     *                               already exists in the system
     * @throws CreateException       is thrown if a DomainException is caught
     *                               or a system failure is occurs
     * @throws CheckException        is thrown if a invalid data is found
     */
    public Product createProduct(final Product product) throws CreateException, CheckException {
        if (product == null)
            throw new CreateException("Product object is null");

        if (product.getCategory() == null)
            throw new CheckException("Invalid Category");

        // Finds the linked object
        try {
            product.getCategory().findByPrimaryKey(product.getCategory().getId());
        } catch (FinderException e) {
            throw new CreateException("Category must exist to create a product");
        }

        // Creates the object
        return (Product) product.create();

    }

    /**
     * Given an id this method uses the Product domain object to load all the data of this
     * object.
     *
     * @param productId identifier
     * @return Product
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     */
    public Product findProduct(final String productId) throws FinderException, CheckException {
        final Product product = new Product();

        // Finds the object
        product.findByPrimaryKey(productId);

        // Retreives the data for the linked object
        product.getCategory().findByPrimaryKey(product.getCategory().getId());

        return product;
    }

    /**
     * Given an id, this method finds a Product domain object and then calls its deletion
     * method.
     *
     * @param productId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public void deleteProduct(final String productId) throws RemoveException, CheckException {
        final Product product = new Product();

        // Checks if the object exists
        try {
            product.findByPrimaryKey(productId);
        } catch (FinderException e) {
            throw new RemoveException("Product must exist to be deleted");
        }

        // Deletes the object
        product.remove();
    }

    /**
     * Given a Product object, this method updates a Product.
     *
     * @param product cannot be null.
     * @throws UpdateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public void updateProduct(final Product product) throws UpdateException, CheckException {
        if (product == null)
            throw new UpdateException("Product object is null");

        if (product.getCategory() == null)
            throw new CheckException("Invalid Category");

        // Finds the linked object
        try {
            product.getCategory().findByPrimaryKey(product.getCategory().getId());
        } catch (FinderException e) {
            throw new UpdateException("Category must exist to update a product");
        }

        // Updates the object
        product.update();
    }

    /**
     * This method return all the products from the system. It uses the Product domain object
     * to get the data.
     *
     * @return a collection of Product
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     */
    public Collection findProducts() throws FinderException {
        final Collection result = new ArrayList();

        // Finds all the objects
        final Collection products = new Product().findAll();

        // For each object
        for (Iterator iterator = products.iterator(); iterator.hasNext();) {
            final Product product = (Product) iterator.next();

            try {
                // Retreives the data for the linked object
                product.getCategory().findByPrimaryKey(product.getCategory().getId());
            } catch (CheckException e) {
                throw new FinderException("Cannot find the category");
            }

            // Adds the object to the collection
            result.add(product);
        }

        return result;
    }

    // ======================================
    // =        Item Business methods       =
    // ======================================
    /**
     * Given a Item object, this method creates a Item.
     *
     * @param item cannot be null.
     * @return the created Item
     * @throws DuplicateKeyException is thrown if an object with the same id
     *                               already exists in the system
     * @throws CreateException       is thrown if a DomainException is caught
     *                               or a system failure is occurs
     * @throws CheckException        is thrown if a invalid data is found
     */
    public Item createItem(final Item item) throws CreateException, CheckException {
        if (item == null)
            throw new CreateException("Item object is null");

        if (item.getProduct() == null)
            throw new CheckException("Invalid Product id");

        // Finds the linked object
        try {
            item.getProduct().findByPrimaryKey(item.getProduct().getId());
        } catch (FinderException e) {
            throw new CreateException("Product must exist to create an item");
        }

        // Creates the object
        return (Item) item.create();
    }

    /**
     * Given an id this method uses the Item domain object to load all the data of this
     * object.
     *
     * @param itemId identifier
     * @return Item
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     */
    public Item findItem(final String itemId) throws FinderException, CheckException {
        final Item item = new Item();

        // Finds the object
        item.findByPrimaryKey(itemId);

        // Retreives the data for the linked object
        item.getProduct().findByPrimaryKey(item.getProduct().getId());

        return item;
    }

    /**
     * Given an id, this method finds a Item domain object and then calls its deletion
     * method.
     *
     * @param itemId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public void deleteItem(final String itemId) throws RemoveException, CheckException {
        final Item item = new Item();

        // Checks if the object exists
        try {
            item.findByPrimaryKey(itemId);
        } catch (FinderException e) {
            throw new RemoveException("Item must exist to be deleted");
        }

        // Deletes the object
        item.remove();
    }

    /**
     * Given a Item object, this method updates an Item.
     *
     * @param item cannot be null.
     * @throws UpdateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public void updateItem(final Item item) throws UpdateException, CheckException {
        if (item == null)
            throw new UpdateException("Item object is null");

        if (item.getProduct() == null)
            throw new CheckException("Invalid Product");

        // Finds the linked object
        try {
            item.getProduct().findByPrimaryKey(item.getProduct().getId());
        } catch (FinderException e) {
            throw new UpdateException("Product must exist to update an item");
        }

        // Updates the object
        item.update();
    }

    /**
     * This method return all the items from the system. It uses the Item domain object
     * to get the data.
     *
     * @return a collection of Item
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     */
    public Collection findItems() throws FinderException {
        final Collection result = new ArrayList();

        // Finds all the objects
        final Collection items = new Item().findAll();

        // For each object
        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            final Item item = (Item) iterator.next();

            try {
                // Retreives the data for the linked object
                item.getProduct().findByPrimaryKey(item.getProduct().getId());
            } catch (CheckException e) {
                throw new FinderException("Cannot find the product");
            }

            // Adds the object to the collection
            result.add(item);
        }

        return result;
    }
}
