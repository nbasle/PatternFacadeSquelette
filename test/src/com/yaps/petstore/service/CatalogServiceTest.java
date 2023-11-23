package com.yaps.petstore.service;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.domain.category.Category;
import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.domain.product.Product;
import com.yaps.petstore.exception.*;
import junit.framework.TestSuite;

/**
 * This class tests the CatalogService class
 */
public final class CatalogServiceTest extends AbstractTestCase {

    public CatalogServiceTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CatalogServiceTest.class);
    }

    //==================================
    //=   Test cases for Category      =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            service.findCategory(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findCategory(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findCategory(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllCategories() throws Exception {
        final String id = getUniqueStringId();

        // First findAll
        final int firstSize = findAllCategories();

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllCategories();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testServiceCreateCategory() throws Exception {
        final String id = getUniqueStringId();
        Category category = null;

        // Ensures that the object doesn't exist
        try {
            findCategory(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCategory(category, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCategory(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        Category category;

        // Creates an object with a null parameter
        try {
            service.createCategory(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            category = new Category(new String(), new String(), new String());
            service.createCategory(category);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            category = new Category(null, null, null);
            service.createCategory(category);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateCategory() throws Exception {
        final String id = getUniqueStringId();
        final String updatedId = getUniqueStringId();

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        Category category = null;
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCategory(category, id);

        // Updates the object with new values
        updateCategory(category, updatedId);

        // Ensures that the object still exists
        Category categoryUpdated = null;
        try {
            categoryUpdated = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCategory(categoryUpdated, updatedId);

        // Cleans the test environment
        deleteCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

        /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        Category category;

        // Updates an object with a null parameter
        try {
            service.updateCategory(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            category = new Category(new String(), new String(), new String());
            service.updateCategory(category);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            category = new Category(null, null, null);
            service.updateCategory(category);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

/**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownCategory() throws Exception {
        final String id = getUniqueStringId();

        // Ensures that the object doesn't exist
        try {
            findCategory(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteCategory(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }


    //==================================
    //=    Test cases for product      =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            service.findProduct(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findProduct(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findProduct(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllProducts() throws Exception {
        final String id = getUniqueStringId();

        // First findAll
        final int firstSize = findAllProducts();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllProducts();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testServiceCreateProduct() throws Exception {
        final String id = getUniqueStringId();
        Product product = null;

        // Ensures that the object doesn't exist
        try {
            findProduct(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProduct(product, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createProduct(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        Product product;

        // Creates an object with a null parameter
        try {
            service.createProduct(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            product = new Product(new String(), new String(), new String(), null);
            service.createProduct(product);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            product = new Product(null, null, null, null);
            service.createProduct(product);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testServiceCreateProductWithInvalidCategory() throws Exception {
        final int id = getUniqueId();
        final CatalogService service = getCatalogService();
        Product product;

        // Creates an object with no object linked
        try {
            product = new Product("prod" + id, "name" + id, "description" + id, null);
            service.createProduct(product);
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with a null linked object
        try {
            product = new Product("prod" + id, "name" + id, "description" + id, null);
            product.setCategory(null);
            service.createProduct(product);
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            product = new Product("prod" + id, "name" + id, "description" + id, new Category());
            product.setCategory(new Category());
            service.createProduct(product);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an unknown linked object
        try {
            final Category category = new Category("cat" + id, "name" + id, "description" + id);
            product = new Product("prod" + id, "name" + id, "description" + id, category);
            service.createProduct(product);
            fail("Object with an unknown object linked should not be created");
        } catch (CreateException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateProduct() throws Exception {
        final String id = getUniqueStringId();
        final String updatedId = getUniqueStringId();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        Product product = null;
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProduct(product, id);

        // Updates the object with new values
        updateProduct(product, updatedId);

        // Ensures that the object still exists
        Product productUpdated = null;
        try {
            productUpdated = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkProduct(productUpdated, updatedId);

        // Cleans the test environment
        deleteProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

     /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        Product product;

        // Updates an object with a null parameter
        try {
            service.updateProduct(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            product = new Product(new String(), new String(), new String(), null);
            service.updateProduct(product);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            product = new Product(null, null, null, null);
            service.updateProduct(product);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

   /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownProduct() throws Exception {
        final String id = getUniqueStringId();

        // Ensures that the object doesn't exist
        try {
            findProduct(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteProduct(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=      Test cases for item       =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            service.findItem(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findItem(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findItem(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllItems() throws Exception {
        final String id = getUniqueStringId();

        // First findAll
        final int firstSize = findAllItems();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllItems();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testServiceCreateItem() throws Exception {
        final String id = getUniqueStringId();
        Item item = null;

        // Ensures that the object doesn't exist
        try {
            findItem(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(item, id);

        // Cleans the test environment
        deleteItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        Item item;

        // Creates an object with a null parameter
        try {
            service.createItem(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            item = new Item(new String(), new String(), 0, null);
            service.createItem(item);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            item = new Item(null, null, 0, null);
            service.createItem(item);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testServiceCreateItemWithInvalidProduct() throws Exception {
        final CatalogService service = getCatalogService();
        final int id = getUniqueId();
        Item item;

        // Creates an object with no object linked
        try {
            item = new Item("item" + id, "name" + id, id, null);
            service.createItem(item);
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with a null linked object
        try {
            item = new Item("item" + id, "name" + id, id, null);
            item.setProduct(null);
            service.createItem(item);
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            item = new Item("item" + id, "name" + id, id, null);
            item.setProduct(new Product());
            service.createItem(item);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an unknown linked object
        try {
            final Category category = new Category("cat" + id, "name" + id, "description" + id);
            final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
            item = new Item("item" + id, "name" + id, id, product);
            service.createItem(item);
            fail("Object with an unknown object linked should not be created");
        } catch (CreateException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateItem() throws Exception {
        final String id = getUniqueStringId();
        final String updatedId = getUniqueStringId();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        Item item = null;
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(item, id);

        // Updates the object with new values
        updateItem(item, updatedId);

        // Ensures that the object still exists
        Item itemUpdated = null;
        try {
            itemUpdated = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkItem(itemUpdated, updatedId);

        // Cleans the test environment
        deleteItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

        /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        Item item;

        // Updates an object with a null parameter
        try {
            service.updateItem(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            item = new Item(new String(), new String(), 0, null);
            service.updateItem(item);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            item = new Item(null, null, 0, null);
            service.updateItem(item);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

/**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownItem() throws Exception {
        final String id = getUniqueStringId();

        // Ensures that the object doesn't exist
        try {
            findItem(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteItem(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private CatalogService getCatalogService() {
        return new CatalogService();
    }

    //==================================
    //=  Private Methods for Category  =
    //==================================
    private Category findCategory(final String id) throws FinderException, CheckException {
        final Category category = getCatalogService().findCategory("cat" + id);
        return category;
    }

    private int findAllCategories() throws FinderException {
        try {
            return getCatalogService().findCategories().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private void createCategory(final String id) throws CreateException, CheckException {
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(category);
    }

    private void updateCategory(final Category category, final String id) throws UpdateException, CheckException {
        category.setName("name" + id);
        category.setDescription("description" + id);
        getCatalogService().updateCategory(category);
    }

    private void deleteCategory(final String id) throws RemoveException, CheckException {
        getCatalogService().deleteCategory("cat" + id);
    }

    private void checkCategory(final Category category, final String id) {
        assertEquals("name", "name" + id, category.getName());
        assertEquals("description", "description" + id, category.getDescription());
    }

    //==================================
    //=  Private Methods for Product   =
    //==================================
    private Product findProduct(final String id) throws FinderException, CheckException {
        final Product product = getCatalogService().findProduct("prod" + id);
        return product;
    }

    private int findAllProducts() throws FinderException {
        try {
            return getCatalogService().findProducts().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first and then a product linked to this category
    private void createProduct(final String id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        getCatalogService().createProduct(product);
    }

    // Creates a category and updates the product with this new category
    private void updateProduct(final Product product, final String id) throws UpdateException, CheckException, CreateException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(category);
        // Update Product with new category
        product.setName("name" + id);
        product.setDescription("description" + id);
        product.setCategory(new Category("cat" + id));
        getCatalogService().updateProduct(product);
    }

    private void deleteProduct(final String id) throws RemoveException, CheckException {
        getCatalogService().deleteProduct("prod" + id);
        getCatalogService().deleteCategory("cat" + id);
    }

    private void checkProduct(final Product product, final String id) {
        assertEquals("name", "name" + id, product.getName());
        assertEquals("description", "description" + id, product.getDescription());
    }

    //==================================
    //=    Private Methods for Item    =
    //==================================
    private Item findItem(final String id) throws FinderException, CheckException {
        final Item item = getCatalogService().findItem("item" + id);
        return item;
    }

    private int findAllItems() throws FinderException {
        try {
            return getCatalogService().findItems().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first, then a product and then an item linked to this product
    private void createItem(final String id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        getCatalogService().createProduct(product);
        // Create Item
        final Item item = new Item("item" + id, "name" + id, Double.parseDouble(id), product);
        getCatalogService().createItem(item);
    }

    // Creates a category, a product and updates the item with this new product
    private void updateItem(final Item item, final String id) throws UpdateException, CheckException, CreateException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(category);
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        getCatalogService().createProduct(product);
        // Updates the item
        item.setName("name" + id);
        item.setUnitCost(Double.parseDouble(id));
        item.setProduct(new Product("prod" + id));
        getCatalogService().updateItem(item);
    }

    private void deleteItem(final String id) throws RemoveException, CheckException {
        getCatalogService().deleteItem("item" + id);
        getCatalogService().deleteProduct("prod" + id);
        getCatalogService().deleteCategory("cat" + id);
    }

    private void checkItem(final Item item, final String id) {
        assertEquals("name", "name" + id, item.getName());
        assertEquals("unitCost", new Double(id), new Double(item.getUnitCost()));
    }
}

