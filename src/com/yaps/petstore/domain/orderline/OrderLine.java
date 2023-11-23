/*
 * Created on 19 nov. 2005
 *
 * Class OrderLine
 */
package com.yaps.petstore.domain.orderline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.yaps.petstore.domain.PersistentObject;
import com.yaps.petstore.domain.item.Item;
import com.yaps.petstore.domain.order.Order;
import com.yaps.petstore.exception.CheckException;
import com.yaps.petstore.exception.FinderException;
import com.yaps.petstore.logging.Trace;

/**
 * @author Veronique
 *
 * Class OrderLine
 */
public final class OrderLine extends PersistentObject {
	////////////////////Attributes///////////////////
	private int _quantity;
	private double _unitCost;
	private Order _order;
	private Item _item;
	
	
	////////////////////Constructors/////////////////
	{
		_dao = new OrderLineDAO();
	}
    /**
     * Constructor
	 * @param id
	 * @param quantity
	 * @param unitCost
	 * @param order
	 * @param item
	 */
	public OrderLine(String id, int quantity, double unitCost, Order order, Item item) {
		_id =id;
		_quantity = quantity;
		_unitCost = unitCost;
		_order = order;
		_item = item;
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructor
 * @param quantity
 * @param unitCost
 * @param order
 * @param item
 */
public OrderLine(int quantity, double unitCost, Order order, Item item) {
	
	_quantity = quantity;
	_unitCost = unitCost;
	_order = order;
	_item = item;
	_id = getUniqueId("OrderLine");
}
	/**
	 * 
	 */
	public OrderLine() {
				
	}
	/**
	 * @param orderLineId
	 */
	public OrderLine(String orderLineId) {
		
		_id = orderLineId;
	}
	/////////////////////Business methods///////////////
	public void findByPrimaryKey(final String id) throws FinderException, CheckException {
		final String mname = "findByPrimaryKey";
		Trace.entering(_cname, mname, id);
		
		// Checks data integrity
		checkId(id);
		
		//Use the DAO to access the persistent layer
		final OrderLine temp =(OrderLine)_dao.select(id);
		_quantity = temp.getQuantity();
		_unitCost = temp.getUnitCost();
		_item = temp.getItem();
	}
	protected void checkData() throws CheckException {
		checkId(_id);
		if (_quantity == 0)
			throw new CheckException("Invalid quantity");
		if (_unitCost == 0)
			throw new CheckException("Invalid unitCost");
		if (_item == null || _item.getId() == null || "".equals(_item))
			throw new CheckException("Invalid item");
	}
	////////////////////Public methods///////////////////
	/**
	 *  Accessors for quantity	 
	 */
	/**
	 *  set accessor
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		_quantity = quantity;
	}
	/**
	 *  get accessor
	 * @return quantity	 
	 */
	public int getQuantity () {
		return _quantity;
	}
	/****
	 * Accessor for unitCost 
	 */
	/**
	 * Accessor set
	 * @param unitCost
	 */
	public void setUnitCost(double unitCost){
		_unitCost = unitCost;
	}
	/**
	 * Accessor get
	 * @return
	 */
	public double getUnitCost() {
		return _unitCost;
	}
	/**
	 * Accessor of order
	 */
	/**
	 * Accessor set
	 * order
	 */
	public void setOrder(Order order){
		_order = order;
	}
	/**
	 * Accessor get
	 * @return order
	 */
	public Order getOrder() {
		return _order;
	}
	/**
	 * Accessor of item
	 */
	/**
	 * Accessor set
	 * item
	 */
	public void setItem(Item item){
		_item = item;
	}
	/**
	 * Accessor get
	 * @return
	 */
	public Item getItem() {
		return _item;
	}
	/**
	 * @param orderId
	 * @return
	 */
	public Collection findAll(String orderId) throws FinderException {
		final Collection tempAllElements = findAll();
		final Collection resultList = new ArrayList();
		 
		Iterator temp_iterator = tempAllElements.iterator();
		while(temp_iterator.hasNext())
		{
			final OrderLine temp_orderLine = (OrderLine)temp_iterator.next();
			if (temp_orderLine._order.getId().equals(orderId))
			{
				resultList.add(temp_orderLine);
			}
		}
		return resultList;
	}
}
