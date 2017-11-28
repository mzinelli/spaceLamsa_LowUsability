package com.mpu.spinv.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * AdvList.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-20
 */
public class AdvList<T> {

	/**
	 * The list containing the keys to the values.
	 */
	private List<String> keyList;

	/**
	 * The list containing the values of the keys.
	 */
	private List<T> valuesList;

	/**
	 * The list's size.
	 */
	private int size;

	private boolean overwriteValues;

	public AdvList() {
		keyList = new ArrayList<String>();
		valuesList = new ArrayList<T>();
		size = 0;

		// Default params.
		overwriteValues = false;
	}

	/**
	 * Adds an element to the list.
	 * 
	 * @param key
	 *            The identifier of the element to be added.
	 * @param value
	 *            The object to add to the list.
	 */
	public void add(String key, T value) {
		if (overwriteValues || !containsKey(key)) {
			keyList.add(key);
			valuesList.add(value);
			size++;
		}
	}

	/**
	 * Removes a object from the list.
	 * 
	 * @param key
	 *            The identifier of the object to remove from the list.
	 * @return The object removed, null otherwise.
	 */
	public T remove(String key) {
		T removed = null;

		for (int i = 0; i < keyList.size(); i++) {
			if (key.equals(keyList.get(i))) {
				removed = valuesList.get(i);
				valuesList.remove(i);
				keyList.remove(i);
				size--;
			}
		}

		return removed;
	}
	
	/**
	 * Clears the list.
	 */
	public void clear() {
		size = 0;
		keyList.clear();
		valuesList.clear();
	}
	
	public void forEach(BiConsumer<String, T> consumer) {
		for (int i = 0; i < size; i++) {
			consumer.accept(keyList.get(i), valuesList.get(i));
		}
	}

	/**
	 * Checks if a key exists in the list.
	 * 
	 * @param key
	 *            the id to be evaluated.
	 * @return true if the id is exists within the list, false otherwise.
	 */
	public boolean containsKey(String key) {
		for (int i = 0; i < keyList.size(); i++)
			if (key.equals(keyList.get(i)))
				return true;

		return false;
	}

	// Getters and Setters

	public int size() {
		return size;
	}
	
	public T get(String key) {
		T toGet = null;
		
		for (int i = 0; i < keyList.size(); i++) {
			if (key.equals(keyList.get(i))) {
				toGet = valuesList.get(i);
				break;
			}
		}
		
		return toGet;
	}
	
	public T get(int i) {
		if (i >= 0 && i < size)
			return valuesList.get(i);
		return null;
	}

	public boolean isOverwriteValues() {
		return overwriteValues;
	}

	public void setOverwriteValues(boolean overwriteValues) {
		this.overwriteValues = overwriteValues;
	}

}
