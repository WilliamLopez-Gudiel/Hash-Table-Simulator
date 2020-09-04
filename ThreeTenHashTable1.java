/**
 * A class the represents a hash table that uses linear probing to handle collisions.
 * @param <K> a generic variable that represents keys to objects
 * @param <V> a generic variable that represent values in objects
 */
class ThreeTenHashTable1<K,V> {
	//you must use this storage for the hash table
	//and you may not alter this variable's name, type, etc.
	/**
	 * an array of generics used to store generic objects.
	 */
	private TableEntry<K,V>[] storage;
	
	/* +++++++++++++++++++ YOUR CODE HERE +++++++++++++++++++ */
	//Add additional instance variables here!
	//You may also add additional _private_ methods if you'd like.
	/**
	 * the number of items in the hash table.
	 */
	private int items = 0;
	/**
	 * the capacity of the hashtable.
	 */
	private int capacity = 0;
	/**
	 * an array that tracks tombstones.
	 */
	private int[] tombstones;
	
	/**
	 * the contructor for the hashtable class.
	 * @param size the starting capacity of the hashtable
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashTable1(int size) {
		//Create a hash table where the size of the storage is
		//the provided size (number of "slots" in the table)
		//You may assume size is >= 2
		storage = (TableEntry<K,V>[]) new TableEntry[size];
		tombstones = new int[size];
		capacity = size;

		//Remember... if you want an array of ClassWithGeneric<V>, the following format ___SHOULD NOT___ be used:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>,[]) Object[10];
		//instead, use this format:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>,[]) ClassWithGeneric[10];
	}
	
	/**
	 * returns the current capacity of the hashtable.
	 * @return the current capacity
	 */
	public int getCapacity() {
		//return how many "slots" are in the table
		//O(1) 
		
		return capacity;
	}
	/**
	 * the current numner of objects in the hashtable.
	 * @return the number of objects
	 */
	public int size() {
		//return the number of elements in the table
		//O(1)
		return items;
	}
	/**
	 * helper method to get the absolute value of the given number.
	 * @param theNumber a random number
	 * @return the absolute value of the given number
	 */
	private int abs(int theNumber){
		int abs = (theNumber < 0) ? -theNumber : theNumber;
		return abs;
	}

	/**
	 * an add method that adds a object by hashing its key and using
	 * the resulting number as an index for the object to be placed in
	 * if a object is already in there than use linear probing to handle
	 * it.
	 * @param k a key
	 * @param v a value
	 */
	public void put(K k, V v) {
		//Place value v at the location of key k.
		//Use linear probing if that location is in use.
		//You may assume both k and v will not be null.
		//Hint: Make a TableEntry to store in storage
		//and use the absolute value of k.hashCode() for
		//the probe start.
		
		//If the key already exists in the table
		//replace the current value with v.
		
		//If the load on the table is >= 80% _after_ adding
		//expand the table to twice the size.
		
		//Worst case: O(n), Average case: O(1)
		
		int keyIndex = abs(k.hashCode()) % capacity;
		int bucketsChecked = 0;
		double theItems = 0.0;
		double theCapacity = 0.0;
		TableEntry<K,V> addThis = new TableEntry<K,V>(k,v);
		while(bucketsChecked < capacity){
			if(storage[keyIndex] == null){
				storage[keyIndex] = addThis;
				tombstones[keyIndex] = 0;
				items++;
				theItems = items;
				theCapacity = capacity;
				if(theItems / theCapacity >= .8){
					rehash(capacity * 2);
				} 
				break;
			}
			if(storage[keyIndex].getKey().equals(k)){
				storage[keyIndex] = addThis;
				theItems = items;
				theCapacity = capacity;
				if(theItems / theCapacity >= .8){
					rehash(capacity * 2);
				} 
				break;
			}
			keyIndex = (keyIndex + 1) % capacity;
			bucketsChecked++;
		}
	}
	/**
	 * a remove method to remove a object from the array.
	 * @param k the key of the object to be removed.
	 * @return the value of the object removed.
	 */
	public V remove(K k) {
		//Remove the given key (and associated value)
		//from the table. Return the value removed.		
		//If the value is not in the table, return null.
		
		//Hint 1: Remember to leave a tombstone!
		//Hint 2: Does it matter what a tombstone is?
		//   Yes and no... You need to be able to tell
		//   the difference between an empty spot and
		//   a tombstone and you also need to be able
		//   to tell the difference between a "real"
		//   element and a tombstone.
		
		//Worst case: O(n), Average case: O(1)
		int keyIndex = abs(k.hashCode()) % capacity;
		int bucketsChecked = 0;

		while((storage[keyIndex] != null || tombstones[keyIndex] == 1) && bucketsChecked < capacity){
			if(storage[keyIndex] != null && storage[keyIndex].getKey().equals(k)){
				V returnThis = storage[keyIndex].getValue();
				storage[keyIndex] = null;
				tombstones[keyIndex] = 1;
				items--;
				return returnThis;
				
			}

			keyIndex = (keyIndex + 1) % capacity;
			bucketsChecked++;

		}
		
		return null;
	}
	/**
	 * a search method the retrieves a object based on the key given.
	 * @param k the key to the object searched for
	 * @return the value of the object being searched for
	 */
	public V get(K k) {
		//Given a key, return the value from the table.
		
		//If the value is not in the table, return null.
		
		//Worst case: O(n), Average case: O(1)

		int keyIndex = abs(k.hashCode()) % capacity;
		int bucketsChecked = 0;

		while((storage[keyIndex] != null || tombstones[keyIndex] == 1) && bucketsChecked < capacity){
			if(storage[keyIndex] != null && storage[keyIndex].getKey().equals(k)){
				return storage[keyIndex].getValue();
			}
			keyIndex = (keyIndex + 1) % capacity;

			bucketsChecked++;
		}
		

		
		return null;
	}
	/**
	 * checks an index based on the input and return whether there
	 * is a tombstone at the index or not.
	 * @param loc the index to be looked at
	 * @return true is there is a tombstone at the index, else false
	 */
	public boolean isTombstone(int loc) {
		//this is a helper method needed for printing
		
		//return whether or not there is a tombstone at the
		//given index
		
		//O(1)

		if(tombstones[loc] == 1){
			return true;
		}
		
		return false;
	}
	
	/**
	 * a helper method to insert a element into the hashtable after the change of capacity.
	 * @param arr the new hastable 
	 * @param keyIndex the index element will be stored into
	 * @param size the news capacity
	 * @param element the object to be stored
	 */
	private void hashinsert(TableEntry<K,V>[] arr,int keyIndex,int size,TableEntry<K,V> element){
		int bucketsChecked = 0;
		while(bucketsChecked < size){
			if(arr[keyIndex] == null){
				arr[keyIndex] = element;
				break;
			}
			if(arr[keyIndex].getKey().equals(element.getKey())){
				arr[keyIndex] = element;
				break;
			}
			keyIndex = (keyIndex + 1) % size;
			bucketsChecked++;
		}

	}

	/**
	 * change the current capacity based on the input and rehash all
	 * the objects while inserting them into the new hashtable.
	 * @param size the new capacity
	 * @return true if reshashing was sucessful, else false
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {
		//Increase or decrease the size of the storage,
		//rehashing all values.
		
		//Note: you should start at the beginning of the
		//old table and go to the end (linear traversal)
		//to move items into the new table. If you go
		//backwards, etc. your elements will end up out
		//of order compared to the expected order.
		
		//If the new size won't fit all the elements,
		//with at least _one_ empty space, return false
		//and do not rehash. Return true if you were
		//able to rehash.
		if(size - items < 1){
			return false;
		}
		TableEntry<K,V>[] newHash = (TableEntry<K,V>[]) new TableEntry[size];
		int theBuckets = 0;
		while(theBuckets < capacity){
			if(storage[theBuckets] != null){
				K key = storage[theBuckets].getKey();
				int newIndex = abs(key.hashCode()) % size;
				hashinsert(newHash, newIndex,size,storage[theBuckets]);
			}
			theBuckets++;
		}

		capacity = size;
		storage = newHash;
		tombstones = new int[size];
		




		
		return true;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	
	/**
	 * a main method used for debugging purposes.
	 * @param args a string array that is not used
	 */
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		ThreeTenHashTable1<String,String> st1 = new ThreeTenHashTable1<>(10);
		ThreeTenHashTable1<String,Integer> st2 = new ThreeTenHashTable1<>(5);
		
		if(st1.getCapacity() == 10 && st2.getCapacity() == 5 && st1.size() == 0 && st2.size() == 0) {
			System.out.println("Yay 1");
		}
		
		st1.put("a","apple");
		st1.put("b","banana");
		st1.put("banana","b");
		st1.put("b","butter");
		
		if(st1.toString().equals("a:apple\nb:butter\nbanana:b") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:apple\n[8]: b:butter\n[9]: banana:b")) {
			System.out.println("Yay 2");
		}
		System.out.println(st1.getCapacity() == 10);
		System.out.println(st1.size() == 3);
		System.out.println(st1.get("a").equals("apple"));
		System.out.println(st1.get("b").equals("butter"));
		System.out.println(st1.get("banana").equals("b"));
		if(st1.getCapacity() == 10 && st1.size() == 3 && st1.get("a").equals("apple") && st1.get("b").equals("butter") && st1.get("banana").equals("b")) {
			System.out.println("Yay 3");
		}
		
		st2.put("a",1);
		st2.put("b",2);
		st2.put("e",3);
		st2.put("y",4);

		System.out.println(st2);
		System.out.println(st2.toString().equals("e:3\ny:4\na:1\nb:2"));
		System.out.println(st2.toStringDebug().equals("[0]: null\n[1]: e:3\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null"));
		if(st2.toString().equals("e:3\ny:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: e:3\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
			System.out.println("Yay 4");
		}
		
		if(st2.getCapacity() == 10 && st2.size() == 4 && st2.get("a").equals(1) && st2.get("b").equals(2) && st2.get("e").equals(3) && st2.get("y").equals(4)) {
			System.out.println("Yay 5");
		}
		
		if(st2.remove("e").equals(3) && st2.getCapacity() == 10 && st2.size() == 3 && st2.get("e") == null && st2.get("y").equals(4)) {
			System.out.println("Yay 6");
		}
		
		if(st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: tombstone\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
			System.out.println("Yay 7");
		}

		if(st2.rehash(2) == false && st2.size() == 3 && st2.getCapacity() == 10) {
			System.out.println("Yay 8");
		}
		
		if(st2.rehash(4) == true && st2.size() == 3 && st2.getCapacity() == 4) {
			System.out.println("Yay 9");
		}
		
		if(st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: y:4\n[2]: a:1\n[3]: b:2")) {
			System.out.println("Yay 10");
		}
		
		ThreeTenHashTable1<String,String> st3 = new ThreeTenHashTable1<>(2);
		st3.put("a","a");
		st3.remove("a");
		
		if(st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: tombstone")) {
			st3.put("a","a");
			if(st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: a:a")) {
				System.out.println("Yay 11");
			}
		}
		
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	//********************************************************************************
	/**
	 * a toString method to print the objects of the hashtable.
	 * @return a potrayal of the hash table
	 */
	public String toString() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(storage[i] != null && !isTombstone(i)) {
				s.append(storage[i]);
				s.append("\n");
			}
		}
		return s.toString().trim();
	}
	/**
	 * a method to print all the contents in the hashtable.
	 * @return all the content inside the hash table
	 */
	public String toStringDebug() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(!isTombstone(i)) {
				s.append("[" + i + "]: " + storage[i] + "\n");
			}
			else {
				s.append("[" + i + "]: tombstone\n");
			}
			
		}
		return s.toString().trim();
	}
}