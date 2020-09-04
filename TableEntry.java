//********************************************************************************
//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
//********************************************************************************
/**
 * A class that will be used to create objects to store in 
 * threeten hash table.
 * @param <K> the key to the object
 * @param <V> the value in the object
 */
class TableEntry<K,V> {

	/**
	 * the key used to differentiate each object made.
	 */
	private K key;

	/**
	 * the value stored in each object.
	 */
	private V value;
	
	/**
	 * the contructor for the class.
	 * @param key the key for the object.
	 * @param value the value inside the object.
	 */
	public TableEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * a getter method that returns the key of the object.
	 * @return the key inside the object
	 */
	public K getKey() {
		return key;
	}

	/**
	 * a getter method that returns a value of the object.
	 * @return the value of the object
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * a toString method that list the key and the associating value of the object.
	 * @return the string representation of the object
	 */
	public String toString() {
		return key.toString()+":"+value.toString();
	}
}