/*
 * SortedList.java 
 * 
 * Singly linked list, with merge and no sentinels
 * 
 * Author: Brad Wannow
 * 
 * SortedList is an implementation of a singly linked list 
 * using Comparable to merge two lists into one ordered list.
 * The list allows for two lists to be created, with an updating size
 * and option to insert and remove items from the two lists while keeping
 * the list fully sorted. 
 * 
 */
import java.io.*;
import java.util.*;
 
/**
 * A singly linked sorted list 
 * 
 * @param <T> Allows for different types of data (i.e. strings and ints)
 * 
 */
public class SortedList<T extends Comparable<? super T>> {
    private class Node {

        private T data;
        private Node next;
        private Node(T d, Node n) {
            data = d;
            next = n;
        }  
    }
    

    // Reference to the first node in the list
    private Node head; 
    // The number of elements in the list
    private int size; 
    
    // Constructor for an empty list
    public SortedList() {
        head = null; //no sentinel node
        size = 0;
    }
    
    /**
     * Constructor for two lists
     * 
     * @param SortedList<T> s1 
     * @param SortedList<T> s2 
     */   
    public SortedList(SortedList<T> s1, SortedList<T> s2) {
    	
    //PRE: s1.size() > 0 && s2.size() > 0
    // Constructor for the list created from two SortedLists
    // A new SortedList is created containing all the data from s1 and s2 
    // The implementation must take advantage of the fact that s1 and s2 //are sorted. The implementation cannot use the insert method
    	
        head = mergeSort(s1.head, s2.head); // Calls mergeSort method with implements standard mergeSort procedure
        size = s1.size + s2.size;
    }
    
    /**
     * Method to insert item at list in sorted position
     * 
     * @param T the item to be inserted into the list
     */
    public void insert(T item) {
    	//Insert item into the list so the list remains sorted 
    	//The list can contain duplicates
    	
        // If List is empty, simply add item at head
        if(size == 0) {
            head = new Node(item, head);
            size = 1;   
            return;
        }
        
        // Proper position to insert the item in the list
        int count = getPos(item);
        
        // Continue iterating to find where to insert
  
            Node pos = head; 
            
            for(int j = 0; j < count - 1; j++) {
                pos = pos.next; 
            }
           
            pos.next = new Node(item, pos.next);
        
        
        // Increase size every time new item is added
        size++;
    }
   
    /**
     * remove T Object item from list
     * 
     * @param T item
     */  
    public void remove(T item) {
    	//Remove all occurrences of item from the list
    	
        int count = 0;
        Node temp = this.head;
        
        // If there is nothing to remove as list is empty, break out of method
        if(this.size() == 0) return;

        // If item is not found, move on
        while(temp.next.data.compareTo(item) < 0 && temp.next != null ) {
            temp = temp.next;
        }
            
        Node nextPos = temp;
        
        // If item if found, remove item and shift items back
        while(nextPos.next.data.compareTo(item) == 0 && nextPos.next != null) {
            count += 1;
            nextPos = nextPos.next;
        }
          
        size = size - count; // Decrease size according to number of times item was found
        temp.next = nextPos.next;
    }
    
    
    /**
     * Returns the number of times item found in list
     * 
     * @param T item
     * @return int count, number of times item is found
     */
    public int find(T item) {
    	//Return the number of times item is found on the list 
    	//Use equals or compareTo not == to compare items
    	    
    	    // Count that item is found
        int count = 0;
        Node index = head;
        
        // While there is a node with data that is not item, iterate
        while(index != null) {
        	    // When the item is found, count = count + 1
            if(item.compareTo(index.data) == 0) count++;
            
            index = index.next;
        }
        
        // Return number of times item is found
        return count;
    }
    
    /**
     * The number of elements in the list
     * 
     * @return int size
     */
    public int size() {
    	//Return the number of items in the list
    	
        return size;
    }
    
    /**
     * The output of the List
     * 
     * @return String stringList
     * 
     */
    public String toString() {
    //Return a string representation of the list
    	//The string representation of the list is a [ followed by the items in the list //separated by commas followed by a ]
    	//For example a list of integers could look like [2,3,7,10,50,107]
    	
        String stringList = "";
        Node temp = head;
        // Iterate through to add each node to String Object
        while(temp != null) {
            if(temp.next == null) {
                stringList = stringList + temp.data;
                // To prevent repetition of last item, this will break out of loop at the end
                if(temp.next == null) {
                	    break;
                }
            }
            
            // Add comma after every item is added
            stringList = stringList + temp.data + "," ;
            // Shifts list to next
            temp = temp.next;
        }
        
        // Once stringList is complete, add the ending bracket and return
        return "[" + stringList + "]";
    }   
    
    
    
    /**
     * Standard mergeSort algorithm for two linked lists
     * 
     * Author: Brad Wannow
     * Derived from similar mergeSort algorithm learned in CS 220 with Prof. Maraist
     * 
     * @return String stringList
     * @param Node s1
     * @param Node s2
     * 
     */   
    private  Node mergeSort(Node s1, Node s2) {
        Node fullList = null;
        
        // If the first list is empty, return second list in full
        if(s1 == null) {
            return s2;
        }
        
        // Same with second list, if first list is empty
        else if(s2 == null) {
            return s1;
        }
        
        // If both lists have items, complete mergeSort recursive algorithm
        if(s1.data.compareTo(s2.data) == -1) {
            fullList = s1;
            fullList.next = mergeSort(s1.next, s2);
        } 
        
        else {
            fullList = s2;
            fullList.next = mergeSort(s1, s2.next);
        }
        
        // After merge is complete, return full sorted list
        return fullList;
    }
  
    /**
     * Helper method to find requested position
     * 
     * @return int of requested position
     * @param T item
     * 
     */ 
    private int getPos(T item) {
    	
    	    //Keeps track of position
    	    int index = 0;
    	    Node temp = head;
    	         
    	    // Iterate until item is found
    	    while(temp != null && item.compareTo(temp.data) > 0){
    	        // Keep adding to index if not found
    	        index++;
    	        temp = temp.next;
    	    }
    	    
    	    // Return proper index when found
    	    return index;
   }
    
}
		