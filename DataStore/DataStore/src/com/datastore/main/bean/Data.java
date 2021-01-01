package com.datastore.main.bean;

import java.io.Serializable;

import org.json.simple.JSONObject;

/**
 * @author Sulagna Halder
 *
 *         The model class used to write in DataStore
 */
public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serial_Version_UID = 1L;
	String key;
	int time_To_Live;
	JSONObject value;
	long creation_Date_Time_Millis;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getTimeToLive() {
		return time_To_Live;
	}

	public void setTimeToLive(int timeToLive) {
		this.time_To_Live = time_To_Live;
	}

	public JSONObject getValue() {
		return value;
	}

	public void setValue(JSONObject value) {
		this.value = value;
	}

	public long getCreationDateTimeMillis() {
		return creation_Date_Time_Millis;
	}

	public void setCreationDateTimeMillis(long creation_Date_Time_Millis) {
		this.creation_Date_Time_Millis = creation_Date_Time_Millis;
	}

}
