/*
 * Created on 19 nov. 2005
 * Class OrderDAO
 */
package com.yaps.petstore.domain.order;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.domain.customer.Customer;
import com.yaps.petstore.persistence.AbstractDataAccessObject;

/**
 * @author Veronique
 * Class OrderDAO
 */
final class OrderDAO extends AbstractDataAccessObject {

	///////////////////Attributes///////////////////
	private static final String TABLE ="T_ORDER";
	private static final String COLUMNS ="ID, ORDERDATE, FIRSTNAME, LASTNAME, STREET1, STREET2, CITY, STATE, ZIPCODE, COUNTRY, CREDITCARDNUMBER, CREDITCARDTYPE, CREDITCARDEXPIRYDATE, CUSTOMER_FK";
	
	///////////////////Business methods///////////////////
	/**
	 * Insert order information into table
	 * @param persistentbject
	 */
	protected String getInsertSqlStatement(final PersistentObject object) {
		final Order order = (Order) object;
		final String sql;
		sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + order.getId() + "', '" + order.getOrderDate() + "', '" + order.getFirstname() + "', '" + order.getLastname() + "', '" + order.getStreet1() + "', '" + order.getStreet2() + "', '" + order.getCity() + "', '" + order.getState() + "', '" + order.getZipcode() + "', '" + order.getCountry() + "', '" + order.getCreditCardNumber()+ "', '" + order.getCreditCardType()+ "', '" + order.getCreditCardExpiryDate() + "', '" + order.getCustomer().getId() + "')";
		return sql;
	}

	/**
	 * Delete a line in the table
	 * @param id
	 */
	protected String getDeleteSqlStatement(final String id) {
		final String sql;
		sql ="DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
		return sql;
	}

	/**
	 * update information in table
	 * @param object
	 */
	protected String getUpdateSqlStatement(final PersistentObject object) {
		final Order order = (Order)object;
		final String sql;
		sql = "UPDATE " + TABLE + " SET ORDERDATE = '"  + order.getOrderDate() + "', FIRSTNAME = '" + order.getFirstname() + "', LASTNAME = '" + order.getLastname() + "', STREET1 = '" + order.getStreet1() + "', STREET2 = '" + order.getStreet2() + "', CITY = '" + order.getCity() + "', STATE = '" + order.getState() + "', ZIPCODE = '" + order.getZipcode() + "', COUNTRY = '" + order.getCountry() + "', CREDITCARDNUMBER = '" + order.getCreditCardNumber()+ "', CREDITCARDTYPE = '" + order.getCreditCardType()+ "', CREDITCARDEXPIRYDATE = '" + order.getCreditCardExpiryDate() + "', CUSTOMER_FK = '" +order.getCustomer().getId() + "' WHERE ID = '" + order.getId() + "' ";
		return sql;
	}

	/**
	 * select an oder in the table with id
	 * @param id
	 */
	protected String getSelectSqlStatement(final String id) {
		final String sql;
		sql ="SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ID = '" + id + "'";
		return sql;
	}

	/**
	 * Select all lines in the table
	 */
	protected String getSelectAllSqlStatement() {
		final String sql;
		sql = "SELECT " + COLUMNS + " FROM " + TABLE;
		return sql;
	}

	/**
	 * Set information in object to return
	 */
	protected PersistentObject transformResultset2PersistentObject(final ResultSet resultSet) throws SQLException {
		final Order order;		
		order = new Order(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),resultSet.getString(7), resultSet.getString(8),resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13),new Customer(resultSet.getString(14)));
		return order;
	}

}
