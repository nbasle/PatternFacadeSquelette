/*
 * Created on 19 nov. 2005
 *
 * Class CustomerService
 * 
 */
package com.yaps.petstore.service;


import java.util.Collection;

import com.yaps.petstore.domain.customer.Customer;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.CreateException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.exception.ObjectNotFoundException;
import com.yaps.petstore.exception.RemoveException;
import com.yaps.petstore.exception.UpdateException;

/**
 * @author Veronique
 *
 * Class CustomerService
 */
public final class CustomerService {
	
	/////Constructors/////
	public CustomerService(){
		
	}
	/*=========================================
	 * Public methods
	 *========================================*/
/**
 * Create a cutomer from a customer object
 * 
 * @param customer cannot be null
 * @throws DuplicateKayException if an object with the same id
 * 								 already exists
 * @throws CreateException if an error occurs
 * @throws CheckException if an invalid data is detected
 */
	
	public Customer createCustomer(final Customer customer) throws CreateException, CheckException {
		if (customer == null)
			throw new CreateException("Customer object is null");
		return (Customer)customer.create();
	}

	/**
	 *  delete the customer assoiated to the id
	 * @param customerId identifier
	 * @throws RemoveException if an error occurs
	 * @throws CheckException if an invalid data is detected
	 */
	public void deleteCustomer(final String customerId) throws RemoveException, CheckException {		
		final Customer customer = new Customer();
		
		// Checks if the object exists
		try {
			customer.findByPrimaryKey(customerId);
		} catch(FinderException e) {
			throw new RemoveException("Customer must exist to be deleted");
		}
		
		// Deletes the object
		customer.remove();
	}

	/**
	 * Return the customer associated to the id
	 * @param customerId
	 * @return customer object
	 * @throws ObjectNotFoundException if no object is found
	 * @throws FinderException if an error occurs
	 * @throws CheckException if an invalid data is detected
	 */
	public Customer findCustomer(String customerId) throws FinderException, CheckException {
		final Customer customer = new Customer();
		
		//Finds the object
		customer.findByPrimaryKey(customerId);
		
		return customer;
	}
	/**
	 * Return all the customers
	 * @return a colection of customer
	 * @throws ObjectNotFoundException if the collection is empty
	 * @throws FinderException if an error occurs
	 */
	public Collection findCustomers() throws FinderException {
		final Collection result = new Customer().findAll();
		
		return result;
	}
	/**
	 * Update the customer 
	 * @param customer cannot be null
	 * @throws UpdateException if an error occurs
	 * @throws CheckException if a invalid data is detected
	 */
	public void updateCustomer(final Customer customer) throws UpdateException, CheckException {
		
		if(customer == null)
			throw new UpdateException("Customer object is null");
		
		//Updates the customer
		customer.update();
	}

}
