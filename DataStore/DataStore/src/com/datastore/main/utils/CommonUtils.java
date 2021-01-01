package com.datastore.main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;

import com.datastore.main.bean.Data;

/**
 * @author Sulagna Halder This class Handles general util methods like validation,
 *         file operation like read,write,update
 */
public class CommonUtils {

	/**
	 * To get the current processName
	 * 
	 * @return the process name as a string
	 */
	public static String getProcessName() {
		String process_Name = ManagementFactory.getRuntimeMXBean().getName();
		return process_Name;
	}

	/**
	 * To validate the key of the element
	 * 
	 * @param key
	 *            The key of the element
	 * @return the status of the validation either true or false
	 */
	public static boolean isKeyNameValid(String key) {
		if (key.length() > Constants.KEY_MAX_LENGTH) {
			return false;
		}
		return true;
	}

	/**
	 * To check if the given key is already available in DataStore or not
	 * 
	 * @param key
	 *            The key of the element
	 * @param filePath
	 *            The DataStore location in laptop
	 * @return returns true if key is already available otherwise false
	 */
	public static boolean isKeyExists(String key, String filePath) {
		boolean is_Key_Exists = false;
		FileInputStream file_Input_Stream = null;
		ObjectInputStream object_Input_Stream = null;
		FileOutputStream file_Output_Stream = null;
		ObjectOutputStream object_Output_Stream = null;
		HashMap<String, Data> dataMap = new HashMap<String, Data>();
		try {
			File file = new File(filePath);
			// check if files exists
			if (file.exists()) {
				file_Input_Stream = new FileInputStream(file);
				object_Input_Stream = new ObjectInputStream(file_Input_Stream);
				dataMap = (HashMap<String, Data>) object_Input_Stream
						.readObject();
				// check if key exists
				if (dataMap.containsKey(key)) {
					is_Key_Exists = true;
				}

				file_Input_Stream.close();
				object_Input_Stream.close();
			}

			// validate against time to live and destroy the object if time
			// expired
			if (is_Key_Exists) {
				Data data = dataMap.get(key);
				long current_Date_Time_Millis = new Date().getTime();
				if (data.getTimeToLive() > 0
						&& (current_Date_Time_Millis - data
								.getCreationDateTimeMillis()) >= (data
								.getTimeToLive() * Constants.MILLISECONDS)) {
					// the object is expired, So remove from datastore
					dataMap.remove(key);
					file_Output_Stream = new File_Output_Stream(file);
					object_Output_Stream = new Object_Output_Stream(
							file_Output_Stream);
					object_Output_Stream.writeObject(dataMap);
					file_Output_Stream.close();
					object_Output_Stream.close();

					// Since object is removed the key is available for storage
					is_Key_Exists = false;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			if (file_Input_Stream != null) {
				try {
					file_Input_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (object_Input_Stream != null) {
				try {
					object_Input_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return is_Key_Exists;
	}

	/**
	 * To write the DataStore-File Handling
	 * 
	 * @param data
	 *            The element to write in DataStore
	 * @param filePath
	 *            The DataStore location on the laptop
	 * @return the status of the write operation, true if succeeded otherwise
	 *         false
	 */
	public static boolean writeData(Data data, String filePath) {
		FileOutputStream file_Output_Stream = null;
		ObjectOutputStream object_Output_Stream = null;
		FileInputStream file_Input_Stream = null;
		ObjectInputStream object_Input_Stream = null;
		HashMap<String, Data> dataMap = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				// read the existing file data
				file_Input_Stream = new FileInputStream(file);
				object_Input_Stream = new ObjectInputStream(file_Input_Stream);
				dataMap = (HashMap<String, Data>) object_Input_Stream
						.readObject();

				file_Input_Stream.close();
				object_Input_Stream.close();

				// add the new element
				dataMap.put(data.getKey(), data);

				// write the data to file
				file_Output_Stream = new FileOutputStream(file);
				object_Output_Stream = new ObjectOutputStream(file_Output_Stream);
				object_Output_Stream.writeObject(dataMap);
				file_Output_Stream.close();
				object_Output_Stream.close();

				return true;
			} else {
				dataMap = new HashMap<String, Data>();
				dataMap.put(data.getKey(), data);

				// write the data to file
				file_Output_Stream = new FileOutputStream(file);
				object_Output_Stream = new ObjectOutputStream(file_Output_Stream);
				object_Output_Stream.writeObject(dataMap);
				file_Output_Stream.close();
				object_Output_Stream.close();

				return true;
			}
		} catch (Exception exception) {
			return false;
		} finally {
			if (file_Input_Stream != null) {
				try {
					file_Input_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (object_Input_Stream != null) {
				try {
					object_Input_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (file_Output_Stream != null) {
				try {
					file_Output_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (object_Output_Stream != null) {
				try {
					object_Output_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * To read an element from the DataStore
	 * 
	 * @param key
	 *            The key of the element to read
	 * @param filePath
	 *            The DataStore location on the laptop
	 * @return returns the element if available otherwise returns null
	 */
	public static Data readData(String key, String filePath) {
		FileInputStream file_Input_Stream = null;
		ObjectInputStream object_Input_Stream = null;
		HashMap<String, Data> dataMap = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				// read the existing file data
				file_Input_Stream = new FileInputStream(file);
				object_Input_Stream = new ObjectInputStream(file_Input_Stream);
				dataMap = (HashMap<String, Data>) objectInputStream
						.readObject();

				file_Input_Stream.close();
				object_Input_Stream.close();
				return dataMap.get(key);
			} else {
				return null;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		} finally {
			if (file_Input_Stream != null) {
				try {
					file_Input_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (object_Input_Stream != null) {
				try {
					object_Input_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * To delete an element from the DataStore
	 * 
	 * @param key
	 *            The key of the element to delete from the DataStore
	 * @param filePath
	 *            The location of the DataStore in laptop
	 * @return the status of the deletion operation, true is element is deleted
	 *         otherwise false.
	 */
	public static boolean deleteData(String key, String filePath) {

		FileOutputStream file_Output_Stream = null;
		ObjectOutputStream object_Output_Stream = null;
		FileInputStream file_Input_Stream = null;
		ObjectInputStream object_Input_Stream = null;
		HashMap<String, Data> dataMap = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				// read the existing file data
				file_Input_Stream = new FileInputStream(file);
				object_Input_Stream = new ObjectInputStream(file_Input_Stream);
				dataMap = (HashMap<String, Data>) object_Input_Stream
						.readObject();

				file_Input_Stream.close();
				object_Input_Stream.close();

				// add the new element
				dataMap.remove(key);

				// write the data to file
				file_Output_Stream = new FileOutputStream(file);
				object_Output_Stream = new ObjectOutputStream(file_Output_Stream);
				object_Output_Stream.writeObject(dataMap);
				file_Output_Stream.close();
				object_Output_Stream.close();

				return true;
			}
			return false;
		} catch (Exception exception) {
			return false;
		} finally {
			if (file_Input_Stream != null) {
				try {
					file_Input_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (object_Input_Stream != null) {
				try {
					object_Input_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (file_Output_Stream != null) {
				try {
					file_Output_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (object_Output_Stream != null) {
				try {
					object_Output_Stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
