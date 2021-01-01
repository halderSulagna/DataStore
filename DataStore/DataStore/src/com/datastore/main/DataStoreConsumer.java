package com.datastore.main;

import org.json.simple.JSONObject;

/**
 * @author Sulagna Halder
 *
 */
public class DataStoreConsumer {
	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstName", "Sulagna");
		jsonObject.put("lastName", "Halder");
		jsonObject.put("address", "West Bengal");
		System.out
				.println("_____________________________________________________________");
		System.out
				.println(".......................... CREATE ...........................");
		System.out
				.println("_____________________________________________________________");
		// Create Operation
		// DataStore myDataStore = new DataStore();
		DataStore my_Data_Store = new DataStore(
				"C:\Users\user\AndroidStudioProjects\DataStore");
		System.out.println(my_Data_Store.create("1", jsonObject, 10));// success
		System.out.println(my_Data_Store.create("1", jsonObject));// failure
		System.out.println(my_Data_Store.create("1", jsonObject, 10));// failure
		System.out.println(my_Data_Store.create("2", jsonObject));// success
		System.out.println(my_Data_Store.create(
				"This Is KeyName Validation", new JSONObject()));// failure
		try {
			// wait for 10 seconds
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("................ AFTER WAIT ...............");
		jsonObject.put("age", "22");
		System.out.println(my_Data_Store.create("1", jsonObject, 10));// success
		System.out.println(my_Data_Store.create("1", jsonObject));// failure
		System.out.println(my_Data_Store.create("1", jsonObject, 10));// failure
		System.out.println(my_Data_Store.create("2", jsonObject));// failure

		// Read Operation
		System.out
				.println("_____________________________________________________________");
		System.out
				.println("......................... READ ..............................");
		System.out
				.println("_____________________________________________________________");
		System.out.println(my_Data_Store.read("1"));// success
		System.out.println(my_Data_Store.read("2"));// success
		System.out.println(my_Data_Store.read("3"));// failure
		System.out.println(my_Data_Store
				.read("This Is KeyName Validation"));// failure
		try {
			// wait for 10 seconds
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(".................. AFTER WAIT ..................");
		System.out.println(my_Data_Store.read("1"));// failure
		System.out.println(my_Data_Store.read("2"));// success

		System.out
				.println("_____________________________________________________________");
		System.out
				.println("....................... DELETE ..............................");
		System.out
				.println("_____________________________________________________________");
		System.out.println(my_Data_Store.delete("1"));// failure
		System.out.println(my_Data_Store.delete("2"));// success
		System.out.println(my_Data_Store.delete("2"));// failure
		System.out.println(my_Data_Store.delete("3"));// failure
		System.out.println(my_Data_Store
				.delete("This Is KeyName Validation"));// failure
		//
		/*
		 * DataStore myDataStore1 = new
		 * DataStore("C:\\Users\\John\\Documents\\DataStore");
		 * System.out.println(myDataStore1.create("1", new JSONObject(), 10));
		 */
	}
}
