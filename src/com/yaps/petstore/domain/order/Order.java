/*
 * Created on 19 nov. 2005
 *
 * Class Order
 */
package com.yaps.petstore.domain.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.domain.customer.Customer;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

/**
 * @author Veronique
 * Class Order
 */
public final class Order extends PersistentObject {
	
///////////////////Attributes//////////////////
	private String _orderdate;
	private String _firstname;
	private String _lastname;
	private String _street1;
	private String _street2;
	private String _city;
	private String _state;
	private String _zipCode;
	private String _country;
	private String _creditCardNumber;
	private String _creditCardType;
	private String _creditCardExpiryDate;	
	private Customer _customer;
	private Collection _orderLine = new ArrayList();
	
	
	
//////////////////Constructors////////////////////
	{
		_dao = new OrderDAO();
	}
	  /**
	 * @param firstname
	 * @param lastname
	 * @param street1
	 * @param city
	 * @param zipCode
	 * @param country
	 * @param customer
	 */
	public Order(final String firstname, final String lastname, final String street1, final String city, final String zipCode, final String country, final Customer customer) {
		
	_firstname = firstname;
	_lastname = lastname;
	_street1 = street1;
	_city = city;
	_zipCode = zipCode;
	_country = country;
	_customer = customer;
	_id = getUniqueId("Order");
	
	}
	/**
	 * @param firstname
	 * @param lastname
	 * @param street1
	 * @param city
	 * @param zipCode
	 * @param country
	 * @param customer
	 */
	public Order(final String id, final String orderdate, final String firstname, final String lastname, final String street1, final String street2, final String city, final String state,final String zipCode, final String country, final String creditCardNumber, final String creditCardType, final String creditCardExpiryDate,final Customer customer) {
	_id =id;
	_orderdate = orderdate;
	_firstname = firstname;
	_lastname = lastname;
	_street1 = street1;
	_street2 = street2;
	_city = city;
	_state = state;
	_zipCode = zipCode;
	_country = country;
	_creditCardNumber = creditCardNumber;
	_creditCardType = creditCardType;
	_creditCardExpiryDate = creditCardExpiryDate;	
	_customer = customer;		
	}
    /**
	 * default constructor
	 */
	public Order() {
		
		
	}
	/**
	 * Construct an order with an associated id
	 * @param orderId
	 */
	public Order(final String orderId) {
		_id = orderId;		
	}
	////////////////////Protected method//////////////////
	/**
	 * 
	 */
	protected  void checkData() throws CheckException {
		checkId(_id);
		
		if(_firstname == null || "".equals(_firstname))
			throw new CheckException("Invalid firstname");
		if(_lastname == null || "".equals(_lastname))
			throw new CheckException("Invalid lastname");
		if(_street1 == null || "".equals(_street1))
			throw new CheckException("Invalid street1");
		if(_city == null || "".equals(_city))
			throw new CheckException("Invalid city");
		if(_zipCode == null || "".equals(_zipCode))
			throw new CheckException("Invalid zipcode");
		if(_country == null || "".equals(_country))
			throw new CheckException("Invalid country");
		if(_customer == null || _customer.getId() == null || "".equals(_customer.getId()))
			throw new CheckException("Invalid customer");
	}
	////////////////////Public methods//////////////////
	/**
	 * find customer from the id foreign key
	 * @param id
	 */
	public  void findByPrimaryKey(final String id) throws FinderException, CheckException {
		final String mname = "findByPrimaryKey";
		Trace.entering(_cname, mname, id);
		
		// Checks data integrity
		checkId(id);
		
		// Uses the DAO to access the persistent layer
		final Order temp = (Order) _dao.select(id);
		
		// Sets daat to current object
		_id = temp.getId();
		_orderdate = temp.getOrderDate();
		_firstname = temp.getFirstname();
		_lastname = temp.getLastname();
		_street1 = temp.getStreet1();
		_street2 = temp.getStreet2();
		_city = temp.getCity();
		_state = temp.getState();
		_zipCode = temp.getZipcode();
		_country = temp.getCountry();
		_creditCardNumber = temp.getCreditCardNumber();
		_creditCardType = temp.getCreditCardType();
		_creditCardExpiryDate = temp.getCreditCardExpiryDate();	
		_customer = temp.getCustomer();
		
	}
	public String toString() {
		
        final StringBuffer buf = new StringBuffer();
        buf.append("\n\tOrder {");
        buf.append("\n\t\tId=").append(_id);
        buf.append("\n\t\tOrderdate=").append(_orderdate);
        buf.append("\n\t\tFirstname=").append(_firstname);
        buf.append("\n\t\tLastname=").append(_lastname);
        buf.append("\n\t\tStreet1=").append(_street1);
        buf.append("\n\t\tStreet2=").append(_street2);
        buf.append("\n\t\tCity=").append(_city);
        buf.append("\n\t\tState=").append(_state);
        buf.append("\n\t\tZipCode=").append(_zipCode);
        buf.append("\n\t\tCountry=").append(_country);
        buf.append("\n\t\tCreditCardNumber=").append(_creditCardNumber);
        buf.append("\n\t\tCreditCardType=").append(_creditCardType);
        buf.append("\n\t\tCreditCardExpiryDate=").append(_creditCardExpiryDate);
        buf.append("\n\t\tCustomer Id=").append(_customer.getId());
        buf.append("\n\t\tCustomer FirstName=").append(_customer.getFirstname());
        buf.append("\n\t}");
        return buf.toString();
    }
	////////////////////Buisiness methods//////////////
	/**
	 *  orderDate accessors
	 */
	/**
	 * Accessor set
	 * @param orderdate
	 */
	public void setOrderDate(final String orderdate) {
		_orderdate = orderdate;
	}
	/**
	 * Accessor get
	 * @param orderdate
	 */
	public String getOrderDate() {
		return _orderdate;
	}
	/**
	 * firstname accessors
	 */
	/**
	 * Accessor set
	 * @param firstname
	 */
	public void setFirstname(final String firstname) {
		_firstname = firstname;
	}
	/**
	 * Accessor get
	 * @return firstname
	 */
	public String getFirstname() {		
		return _firstname;
	}
	/**
	 * lastname accessors
	 */
	/**
	 * Accessor set
	 * @param lastname
	 */
	public void setLastname(final String lastname) {
		_lastname = lastname;
	}
	/**
	 * Accessor get
	 * @return lastname
	 */
	public String getLastname() {		
		return _lastname;
	}
	/**
	 * street1 accessors
	 */
	/**
	 * Accessor set
	 * @param street1
	 */
	public void setStreet1(final String street1) {
		_street1 = street1;
	}
	/**
	 * Accessor get
	 * @return street1
	 */
	public String getStreet1() {		
		return _street1;
	}
	/**
	 * street2 accessors
	 */
	/**
	 * Accessor set
	 * @param street2
	 */
	public void setStreet2(final String street2) {
		_street2 = street2;
	}
	/**
	 * Accessor get
	 * @return street2
	 */
	public String getStreet2() {		
		return _street2;
	}
	/**
	 * city accessors
	 */
	/**
	 * Accessor set
	 * @param city
	 */
	public void setCity(final String city) {
		_city = city;
	}
	/**
	 * Accessor get
	 * @return city
	 */
	public String getCity() {		
		return _city;
	}
	/**
	 * state accessors
	 */
	/**
	 * Accessor set
	 * @param state
	 */
	public void setState(final String state) {
		_state = state;
	}
	/**
	 * Accessor get
	 * @return state
	 */
	public String getState() {		
		return _state;
	}
	/**
	 * zipCode accessors
	 */
	/**
	 * Accessor set
	 * @param ZipCode
	 */
	public void setZipcode(final String zipCode) {
		_zipCode = zipCode;
	}
	/**
	 * Accessor get
	 * @return zipCode
	 */
	public String getZipcode() {		
		return _zipCode;
	}
	/**
	 * country accessors
	 */
	/**
	 * Accessor set
	 * @param country
	 */
	public void setCountry(final String country) {
		_country = country;
	}
	/**
	 * Accessor get
	 * @return country
	 */
	public String getCountry() {		
		return _country;
	}
	
	
	
	/**
	 * creditCardNumber accessors
	 */
	/**
	 * Accessor set
	 * @param creditCardNumber
	 */
	public void setCreditCardNumber(final String creditCardNumber) {
		_creditCardNumber = creditCardNumber;
	}
	/**
	 * Accessor get
	 * @return creditCardNumber
	 */
	public String getCreditCardNumber() {		
		return _creditCardNumber;
	}
	
	
	/**
	 * creditCardType accessors
	 */
	/**
	 * Accessor set
	 * @param creditCardType
	 */
	public void setCreditCardType(final String creditCardType) {
		_creditCardType = creditCardType;
	}
	/**
	 * Accessor get
	 * @return creditCardType
	 */
	public String getCreditCardType() {		
		return _creditCardType;
	}
	
	
	/**
	 * creditCardExpiryDate accessors
	 */
	/**
	 * Accessor set
	 * @param creditCardExpiryDate
	 */
	public void setCreditCardExpiryDate(final String creditCardExpiryDate) {
		_creditCardExpiryDate = creditCardExpiryDate;
	}
	/**
	 * Accessor get
	 * @return creditCardExpiryDate
	 */
	public String getCreditCardExpiryDate() {		
		return _creditCardExpiryDate;
	}
	
	/**
	 * Cutomer accessors
	 */
	/**
	 * Accessor set
	 * @param customer
	 */
	public void setCustomer(final Customer customer) {
		_customer = customer;
	}
	/**
	 * Accessor get
	 * @return customer
	 */
	public Customer getCustomer() {		
		return _customer;
	}

   /**
    * Accessor for orderLine
   */
	/**
	 *  Accessor set
	 *  @param orderLine
	 */
	public void setOrderLines(final Collection orderLine) {
		Iterator iteratorO = orderLine.iterator();
		
		while (iteratorO.hasNext())
		_orderLine.add(iteratorO.next());
	}
	/**
	 *  Accessor get
	 *  @return orderLine
	 */
	public Collection getOrderLines()
	{
		return _orderLine;
	}
}
