package persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.DBOperationException;
import model.SuperMarket;

public interface SuperMarketDao {

	// CREATE
	public void insert(SuperMarket SuperMarket) throws DBOperationException;

	// RETRIEVE
	public ArrayList<SuperMarket> retrieveAll();

	public SuperMarket retrieveByPrimaryKey(Long id);

	// UPDATE
	public void update(SuperMarket SuperMarket) throws DBOperationException;

	public void setAffiliate(long id, boolean b) throws DBOperationException;

	// DELETE
	public void delete(SuperMarket SuperMarket);

	public ArrayList<SuperMarket> retrieveAffiliate();

	public ArrayList<SuperMarket> retrieveAffiliateDontSellProduct(long barcode);

	public HashMap<String, Long> getCashForAllSupermarkets();

	public HashMap<String, Long> getUnitsForAllSupermarkets();

}
