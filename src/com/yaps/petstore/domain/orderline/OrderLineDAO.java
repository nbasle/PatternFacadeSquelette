/*
 * Created on 19 nov. 2005
 * OrderLine class
 */
package com.yaps.petstore.domain.orderline;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.domain.order.Order;
import com.yaps.petstore.persistence.AbstractDataAccessObject;

/**
 * @author Veronique
 * OrderLine class
 */
public class OrderLineDAO extends AbstractDataAccessObject {

	///////////////////Attributes//////////////////
	private static final String TABLE = "T_ORDER_LINE";
	private static final String COLUMNS ="ID, QUANTITY, UNITCOST, ORDER_FK, ITEM_FK";
	
	///////////////////Buisiness methods/////////////////////
	/**
	 *  insert orderlin information in table
	 */
	protected String getInsertSqlStatement(final PersistentObject object) {
		final OrderLine orderLine = (OrderLine)object;
		final String sql;
		sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + orderLine.getId() + "', '" + orderLine.getQuantity() + "', '" + orderLine.getUnitCost() + "', '" + orderLine.getOrder().getId() + "', '" + orderLine.getItem().getId() + "' )";
		return sql;
	}

	/**
	 * delete a orderline line
	 */
	protected String getDeleteSqlStatement(final String id) {
		final String sql;
		sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
		return sql;
	}

	/**
	 * Update information for orderLine
	 */
	protected String getUpdateSqlStatement(final PersistentObject object) {
		final OrderLine orderLine = (OrderLine) object;
		final String sql;
		sql = "UPDATE " + TABLE + " SET QUANTITY = '" +orderLine.getQuantity() + "', UNITCOST = '" + orderLine.getUnitCost() +"', ORDER_FK = '" + orderLine.getOrder().getId() + "', ITEM_FK = '" + orderLine.getItem().getId() + "' WHERE ID = '" + orderLine.getId() + "' ";
		return sql;
	}

	/**
	 *  return information on a orderLine
	 */
	protected String getSelectSqlStatement(final String id) {
		final String sql;
		sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ID = '" + id + "' ";
		return sql;
	}

	/**
	 * return all orderLines
	 */
	protected String getSelectAllSqlStatement() {
		final String sql;
		sql = "SELECT " + COLUMNS + " FROM " + TABLE;
		return sql;
	}

	/**
	 * create orderLine Object
	 */
	protected PersistentObject transformResultset2PersistentObject(
			ResultSet resultSet) throws SQLException {
		final OrderLine orderLine;
		orderLine = new OrderLine(resultSet.getString(1), resultSet.getInt(2), resultSet.getDouble(3), new Order(resultSet.getString(4)), new Item(resultSet.getString(5)));
		return orderLine;
	}

}
