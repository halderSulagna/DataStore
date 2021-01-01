package com.datastore.main;

import java.util.Date;

import org.json.simple.JSONObject;

import com.datastore.main.bean.Data;
import com.datastore.main.utils.CommonUtils;
import com.datastore.main.utils.Constants;

/**
 * @author Sulagna Halder
 *
 *         This class handles with new DataStore instantiation and provides
 *         various operations like create elemenet, read&delete element.
 */

public final class DataStore {

	private String data_Store_Loc = "";
	private String data_Store_Name = "";

	/**
	 * Constructor initialize the DataStore with default storage location
	 */
	public DataStore() {
		try {
			data_Store_Loc = Constants.default_Data_Store_Loc;
			data_Store_Name = "datastore-" + CommonUtils.getProcessName();
		} catch (Exception exception) {

		}
	}

	/**
	 * Constructor initialize the DataStore with given storage location
	 * 
	 * @param filePath
	 *            the storage location path
	 */
	public DataStore(String filePath) {
		try {
			data_Store_Loc = filePath;
			data_Store_Name = "datastore-" + CommonUtils.getProcessName();
		} catch (Exception exception) {

		}

	}

	// Operations

	/**
	 * 
	 * Method to create an element in the DataStore
	 * 
	 * @param key
	 *            The key of the element
	 * @param value
	 *            The value of the element
	 * @return status of the operation
	 */
	public synchronized String create(String key, JSONObject value) {
		try {
			return create(key, value, -1);
		} catch (Exception exception) {
			return Constants.FAILURE_CREATE;
		}
	}

	/**
	 * Method to create an element in the DataStore
	 * 
	 * @param key
	 *            The key of the element
	 * @param value
	 *            The value of the element
	 * @param timeToLive
	 *            Number of seconds after which the element is destroyed
	 * @return status of the operation
	 */
	public synchronized String create(String key, JSONObject value,
			int time_To_Live) {
		try {
			String filePath = data_Store_Loc + "/" + data_Store_Name;
			// validate the key
			if (!CommonUtils.isKeyNameValid(key)) {
				return Constants.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (CommonUtils.isKeyExists(key, filePath)) {
				return Constants.FAILURE_KEY_ALREADY_AVAILABLE;
			}
			// success flow
			Data data = new Data();
			data.setKey(key);
			if (timeToLive > 0) {
				data.setTimeToLive(time_To_Live);
			} else {
				data.setTimeToLive(-1);
			}
			data.setValue(value);
			data.setCreationDateTimeMillis(new Date().getTime());

			if (CommonUtils.writeData(data, filePath)) {
				return Constants.SUCCESS_CREATE;
			} else {
				return Constants.FAILURE_CREATE;
			}
		} catch (Exception exception) {
			return Constants.FAILURE_CREATE;
		}
	}

	/**
	 * Method to read an element from the DataStore
	 * 
	 * @param key
	 *            The key of the element to read the element
	 * @return The value as type of JSONObject
	 */
	public synchronized Object read(String key) {
		try {
			String filePath = data_Store_Loc + "/" + data_Store_Name;
			// validate the key
			if (!CommonUtils.isKeyNameValid(key)) {
				return Constants.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (!CommonUtils.isKeyExists(key, filePath)) {
				return Constants.FAILURE_KEY_NOT_AVAILABLE;
			}
			// success flow

			Data data = CommonUtils.readData(key, filePath);
			if (null != data) {
				return data.getValue();
			}
			return Constants.FAILURE_READ;
		} catch (Exception exception) {
			exception.printStackTrace();
			return Constants.FAILURE_READ;
		}
	}

	/**
	 * Method to delete an element from the DataStore
	 * 
	 * @param key
	 *            The key of the element to read the element
	 * @return The status of the delete operation
	 */
	public synchronized Object delete(String key) {
		try {
			String filePath = data_Store_Loc + "/" + data_Store_Name;
			// validate the key
			if (!CommonUtils.isKeyNameValid(key)) {
				return Constants.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (!CommonUtils.isKeyExists(key, filePath)) {
				return Constants.FAILURE_KEY_NOT_AVAILABLE;
			}
			// success flow

			if (CommonUtils.deleteData(key, filePath)) {
				return Constants.SUCCESS_DELETE;
			}
			return Constants.FAILURE_DELETE;
		} catch (Exception exception) {
			exception.printStackTrace();
			return Constants.FAILURE_DELETE;
		}
	}
}
