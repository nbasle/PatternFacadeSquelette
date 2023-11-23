package com.yaps.petstore.domain.customer;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.persistence.AbstractDataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class does all the database access for the class Customer.
 *
 * @see Customer
 */
final class CustomerDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_CUSTOMER";
    private static final String COLUMNS = "ID, FIRSTNAME, LASTNAME, TELEPHONE, STREET1, STREET2, CITY, STATE, ZIPCODE, COUNTRY";

    // ======================================
    // =           Business methods         =
    // ======================================
    protected String getInsertSqlStatement(final PersistentObject object) {
        final Customer customer = (Customer) object;
        final String sql;
        sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + customer.getId() + "', '" + customer.getFirstname() + "','" + customer.getLastname() + "', '" + customer.getTelephone() + "', '" + customer.getStreet1() + "', '" + customer.getStreet2() + "', '" + customer.getCity() + "', '" + customer.getState() + "', '" + customer.getZipcode() + "', '" + customer.getCountry() + "' )";
        return sql;
    }

    protected String getDeleteSqlStatement(final String id) {
        final String sql;
        sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
        return sql;
    }

    protected String getUpdateSqlStatement(final PersistentObject object) {
        final Customer customer = (Customer) object;
        final String sql;
        sql = "UPDATE " + TABLE + " SET FIRSTNAME = '" + customer.getFirstname() + "', LASTNAME = '" + customer.getLastname() + "', TELEPHONE = '" + customer.getTelephone() + "', STREET1 = '" + customer.getStreet1() + "', STREET2 = '" + customer.getStreet2() + "', CITY = '" + customer.getCity() + "', STATE = '" + customer.getState() + "', ZIPCODE = '" + customer.getZipcode() + "', COUNTRY = '" + customer.getCountry() + "' WHERE ID = '" + customer.getId() + "' ";
        return sql;
    }

    protected String getSelectSqlStatement(final String id) {
        final String sql;
        sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ID = '" + id + "' ";
        return sql;
    }

    protected String getSelectAllSqlStatement() {
        final String sql;
        sql = "SELECT " + COLUMNS + " FROM " + TABLE;
        return sql;
    }

    protected PersistentObject transformResultset2PersistentObject(final ResultSet resultSet) throws SQLException {
        final Customer customer;
        customer = new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        customer.setTelephone(resultSet.getString(4));
        customer.setStreet1(resultSet.getString(5));
        customer.setStreet2(resultSet.getString(6));
        customer.setCity(resultSet.getString(7));
        customer.setState(resultSet.getString(8));
        customer.setZipcode(resultSet.getString(9));
        customer.setCountry(resultSet.getString(10));
        return customer;
    }
}
