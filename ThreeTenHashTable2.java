/**
 * A class that represents a hashtable for objects and uses chaining to handle collisions.
 * @param <K> keys to differentiate each object
 * @param <V> values contained in each object
 */
class ThreeTenHashTable2<K,V> {
	//you must use this storage for the hash table
	//and you may not alter this variable's name, type, etc.
	/**
	 * a generic array to store objects.
	 */
	private Node<K,V>[] storage;
	/**
	 * the current capacity of the array.
	 */
	private int capacity = 0;
	/**
	 * the current amount of objects in the hashtable.
	 */
	private int items  = 0;
	
	/* +++++++++++++++++++ YOUR CODE HERE +++++++++++++++++++ */
	//Add additional instance variables here!
	//You may also add additional _private_ methods if you'd like.
	/**
	 * a contructor for the hashtable, initializes the  starting capacity
	 * of the new hashtable.
	 * @param size the starting capacity of the hashtable
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashTable2(int size) {
		//Create a hash table where the size of the storage is
		//the provided size (number of "slots" in the table)
		//You may assume size is >= 2
		
		//Remember... if you want an array of ClassWithGeneric<V>, the following format ___SHOULD NOT___ be used:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>,[]) Object[10];
		//instead, use this format:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>,[]) ClassWithGeneric[10];
		storage = (Node<K,V>[]) new Node[size];
		capacity = size;
	}
	/**
	 * a getter method that return the current capacity of the hashtable.
	 * @return the current capacity
	 */
	public int getCapacity() {
		//return how many "slots" are in the table
		//O(1)
		return capacity;
	}
	/**
	 * a getter method that returns the current number of objects in the hashtable.
	 * @return the number of objects
	 */
	public int size() {
		//return the number of elements in the table
		//O(1)
		return items;
	}
	
	/**
	 * a helper method that gets the absolute value of the input.
	 * @param theNumber a random number
	 * @return the absolute value of input
	 */
	private int abs(int theNumber){
		int abs = (theNumber < 0) ? -theNumber : theNumber;
		return abs;
	}
	/**
	 * a add method that inserts a object into the hashtable, uses chaining to handle collisions.
	 * @param k a key
	 * @param v a value
	 */
	public void put(K k, V v) {
		//Place value v at the location of key k.
		//Use separate chaining if that location is in use.
		//You may assume both k and v will not be null.
		
		//Hint 1: Make a TableEntry to store in storage
		//and use the absolute value of k.hashCode() for
		//the location of the entry.
		
		//Hint 2: Remember that you're dealing with an array
		//of linked lists in this part of the project, not
		//an array of table entries.
		
		//If the key already exists in the table
		//replace the current value with v.
		
		//If the key does not exist in the table, add
		//the new node to the _end_ of the linked list.
		
		//If the load on the table is >= 80% _after_ adding,
		//expand the table to twice the size and rehash
		//repeatedly _until_ the load is less than 80%.
		
		//Worst case: O(n), Average case: O(1)
		double theItems = 0.0;
		int keyIndex = abs(k.hashCode()) % capacity;
		Node<K,V> head = storage[keyIndex];
		if(head == null){
			TableEntry<K,V> newKV = new TableEntry<K,V>(k,v);
			Node<K,V> addThis = new Node<K,V>(newKV);
			items++;
			storage[keyIndex] = addThis;
		}
		else{
			while(head.next != null){
				if(head.entry.getKey().equals(k)){
					TableEntry<K,V> newKV = new TableEntry<K,V>(head.entry.getKey(),v);
					head.entry = newKV;
					theItems = items;
					while(theItems / capacity >= .8){
						rehash(capacity * 2);
					} 
					return;
				}
				head = head.next;
			}
			if(head.entry.getKey().equals(k)){
				TableEntry<K,V> newKV = new TableEntry<K,V>(head.entry.getKey(),v);
				head.entry = newKV;
				theItems = items;
				while(theItems / capacity >= .8){
					rehash(capacity * 2);
				} 
				return;
			}
			TableEntry<K,V> newKV = new TableEntry<K,V>(k,v);
			Node<K,V> addThis = new Node<K,V>(newKV);
			items++;
			head.next = addThis;
		}

		theItems = items;
		while(theItems / capacity >= .8){
			rehash(capacity * 2);
		} 

	} 
	
	/**
	 * a remove method that removes a object from the hashtable.
	 * @param k the key tof the obejct to be removed
	 * @return the value of the object removed
	 */
	public V remove(K k) {
		//Remove the given key (and associated value)
		//from the table. Return the value removed.		
		//If the value is not in the table, return null.
		
		//Hint: Remember there are no tombstones for
		//separate chaining! Don't leave empty nodes!
		
		//Worst case: O(n), Average case: O(1)
		int keyIndex = abs(k.hashCode()) % capacity;
		Node<K,V> head = storage[keyIndex];
		Node<K,V> prev = null;

		while(head != null){
			if(head.entry.getKey().equals(k)){
				break;
			}
			prev = head;
			head = head.next;
		}

		if(head == null){
			return null;
		}

		items--;

		if(prev != null){
			prev.next = head.next;
		}
		else{
			storage[keyIndex] = head.next;
		}

		return head.entry.getValue();
	}
	
	/**
	 * a search method that searches for a object based on the input key.
	 * @param k the key to be searched for.
	 * @return the value that corresponds to the given key.
	 */
	public V get(K k) {
		//Given a key, return the value from the table.
		
		//If the value is not in the table, return null.
		
		//Worst case: O(n), Average case: O(1)

		int keyIndex = abs(k.hashCode()) % capacity;
		Node<K,V> head = storage[keyIndex];

		while(head != null){
			if(head.entry.getKey().equals(k)){
				return head.entry.getValue();
			}
			head = head.next;
		}

		
		return null;
	}
	
	/**
	 * a helper method that rehashes a object and inserts the object to the
	 * new hashtable.
	 * @param size the new capacity
	 * @param newHash the new hashtable
	 * @param addThis the object to be inserted
	 */
	private void insertHash(int size, Node<K,V>[] newHash, Node<K,V> addThis){
		int keyIndex = abs(addThis.entry.getKey().hashCode()) % size;
		Node<K,V> head = newHash[keyIndex];
		if(head == null){
			newHash[keyIndex] = addThis;
		}
		else{
			while(head.next != null){
				head = head.next;
			}
			head.next = addThis;
		}

	}
	/**
	 * a method that creates a new hashtable with the capacity of input size
	 * and insert all the objects from the old hashtable after rehashing.
	 * @param size new capacity of new hashtabble
	 * @return true if rehash was succesful, else false.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {
		//Increase or decrease the size of the storage,
		//rehashing all values.
		
		//Note: you should start at the beginning of the
		//old table and go through each linked list in order
		//(start to end) to move items into the new table. 
		//If you go backwards, etc. your elements will end up 
		//out of order compared to the expected order.
		
		//If the new size is less than 1, return false.
		//Return true if you were able to rehash.
		if(size < 1){
			return false;
		}
		Node<K,V>[] newHash = (Node<K,V>[]) new Node[size];
		int buckets = 0;
		while(buckets < capacity){
			Node<K,V> head = storage[buckets];
			while(head != null){
				Node<K,V> theNext = head.next;
				head.next = null;
				insertHash(size,newHash,head);
				head = theNext;
			}
			buckets++;
		}

		capacity = size;
		storage = newHash;


		
		
		return true;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	
	/**
	 * a main method for testing/debugging purposes.
	 * @param args a string array that won't be used
	 */
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		ThreeTenHashTable2<String,String> st1 = new ThreeTenHashTable2<>(10);
		ThreeTenHashTable2<String,Integer> st2 = new ThreeTenHashTable2<>(5);
		
		if(st1.getCapacity() == 10 && st2.getCapacity() == 5 && st1.size() == 0 && st2.size() == 0) {
			System.out.println("Yay 1");
		}
		
		st1.put("a","apple");
		st1.put("b","banana");
		st1.put("banana","b");
		st1.put("b","butter");

		
		
		if(st1.toString().equals("a:apple\nbanana:b\nb:butter") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:apple]->[banana:b]->null\n[8]: [b:butter]->null\n[9]: null")) {
			System.out.println("Yay 2");
		}
		
		if(st1.getCapacity() == 10 && st1.size() == 3 && st1.get("a").equals("apple") && st1.get("b").equals("butter") && st1.get("banana").equals("b")) {
			System.out.println("Yay 3");
		}
		
		st2.put("a",1);
		st2.put("b",2);
		st2.put("e",3);
		st2.put("y",4);
		System.out.println(st2);
		if(st2.toString().equals("e:3\ny:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [e:3]->[y:4]->null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:1]->null\n[8]: [b:2]->null\n[9]: null")) {
			System.out.println("Yay 4");
		}
		
		if(st2.getCapacity() == 10 && st2.size() == 4 && st2.get("a").equals(1) && st2.get("b").equals(2) && st2.get("e").equals(3) && st2.get("y").equals(4)) {
			System.out.println("Yay 5");
		}
		
		if(st2.remove("e").equals(3) && st2.getCapacity() == 10 && st2.size() == 3 && st2.get("e") == null && st2.get("y").equals(4)) {
			System.out.println("Yay 6");
		}
		
		if(st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [y:4]->null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:1]->null\n[8]: [b:2]->null\n[9]: null")) {
			System.out.println("Yay 7");
		}

		if(st2.rehash(0) == false && st2.size() == 3 && st2.getCapacity() == 10) {
			System.out.println("Yay 8");
		}
		
		if(st2.rehash(4) == true && st2.size() == 3 && st2.getCapacity() == 4) {
			System.out.println("Yay 9");
		}
		
		if(st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [y:4]->[a:1]->null\n[2]: [b:2]->null\n[3]: null")) {
			System.out.println("Yay 10");
		}
		
		ThreeTenHashTable2<String,String> st3 = new ThreeTenHashTable2<>(2);
		st3.put("a","a");
		st3.remove("a");
		
		if(st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: null")) {
			st3.put("a","a");
			if(st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: [a:a]->null")) {
				System.out.println("Yay 11");
			}
		}
		
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	//********************************************************************************
	/**
	 * a node class that contains a object containg a key and value, and a reference to the next node in a linked list.
	 * @param <K> the key of the object
	 * @param <V> the value of the object
	 */
	public static class Node<K,V> {
		/**
		 * an object that contains a key and a value.
		 */
		public TableEntry<K,V> entry;
		/**
		 * a reference to the next node on a linked list.
		 */
		public Node<K,V> next;
		
		/**
		 * a constructer for a node.
		 * @param entry an object with a key and value.
		 */
		public Node(TableEntry<K,V> entry) {
			this.entry = entry;
		}
		/**
		 * another constructor for the node.
		 * @param entry an object with a key and value
		 * @param next a reference to the next node in a linked list.
		 */
		public Node(TableEntry<K,V> entry, Node<K,V> next) {
			this(entry);
			this.next = next;
		}
		
		/**
		 * a tostring method that potrays a node's object.
		 * @return a string potrayel of a nodes key and val in its object
		 */
		public String toString() {
			return "[" + entry.toString() + "]->";
		}
	}
	/**
	 * a toString method that prints the objects in the current hashtable.
	 * @return a potrayel of the contents in the hashtable
	 */
	public String toString() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			Node<K,V> curr = storage[i];
			if(curr == null) continue;
			
			while(curr != null) {
				s.append(curr.entry.toString());
				s.append("\n");
				curr = curr.next;
			}
		}
		return s.toString().trim();
	}
	/**
	 * a method that prints all the content in the hashtable.
	 * @return potrays all the content in the corrent hashtable
	 */
	public String toStringDebug() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			Node<K,V> curr = storage[i];
			
			s.append("[" + i + "]: ");
			while(curr != null) {
				s.append(curr.toString());
				curr = curr.next;
			}
			s.append("null\n");
		}
		return s.toString().trim();
	}
}