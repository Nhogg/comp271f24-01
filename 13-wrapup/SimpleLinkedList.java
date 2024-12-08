/**
 * A simple linked list object. Its nodes are defined as an inner class.
 * 
 * COMP 271 FINAL ASSIGNMENT
 * 
 * Review class SimpleLinkedList below. It's a linked list object, quite similar
 * to our TrainLine, only instead of TrainStations it uses Nodes. The nodes are
 * defined as a class-within-the-class -- this is called an inner class and it's
 * a useful coding practice for simple situations like this one here.
 * 
 * Your job is to complete TWO methods in the SimpleLinkedList. You may NOT
 * modify any part of the class and you may not import ANYTHING. Your code
 * should be focused only within the two methods you are asked to write.
 * 
 * 1) Write method findMiddle() that finds and returns the middle node of
 * a SimpleListList. For example, if the SimpleLinkedList object is:
 * 
 * A -> null ... the middle node is A
 * 
 * When the SimpleLinkedList object is:
 * 
 * A -> B --> null ... the middle node is also A
 * 
 * When the SimpleLinkedList object is:
 * 
 * A -> B --> C --> null ... the middle node is B
 * 
 * When the SimpleLinkedList object is:
 * 
 * A -> B --> C --> D --> null ... the middle node is also B
 * 
 * When the SimpleLinkedList object is:
 * 
 * A -> B --> C --> D --> E --> null ... the middle node is C, etc
 * 
 * 
 * 2) Write a method called invert that returns the inverted version of the
 * present SimpleLinkedList object. For example, if the current object is
 * 
 * A --> B --> C --> D --> E --> null
 * 
 * method invert should return the object
 * 
 * E --> D --> C --> B --> A --> null.
 * 
 */

public class SimpleLinkedList {

    /**
     * Inner class for Node. Node fields can be accessed directly, for simplicity of
     * code. This is an intentional violation of the Pact.
     */
    class Node {
        String data;
        Node next;

        /** Simple string representation for Node */
        public String toString() {
            return this.data;
        } // method Node.toString
    } // inner class Node

    /** The only field in class SimpleLinkedList */
    Node head;

    /** Add a new node to the linked list */
    public void add(String data) {
        Node newNode = new Node();
        newNode.data = data;
        if (this.head == null) {
            this.head = newNode;
        } else {
            // Traverse the list to find the last node
            Node current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            // current is now the last node in the list
            current.next = newNode;
        }
    } // method add

    /** Find and return the middle node of the linked list.
     * In order to comply with both Programmer's Pact
     * and the specifications of the assignment, we can only
     * use one traversal (while loop.) We can get around this
     * requirement by creating two cursors that step
     * in different increments. One cursor steps in
     * increments of one and the other in increments
     * of the fractional part we are trying to divide
     * into, in this case 2. When the twoStep reaches the end,
     * the single step pointer will be in the middle.
     */
    public Node findMiddle() {
        Node oneStep = this.head;
        if (oneStep != null) {
            Node twoStep = this.head;
            while (twoStep.next != null && twoStep.next.next != null) {
                oneStep = oneStep.next;
                twoStep = twoStep.next.next;
            }

        }
        return oneStep;
    } // method SimpleLinkedList.findMiddle

    /**
     * Invert a linked list.
     * Again, in oder to follow the assignment requirements,
     * we can only perform one traversal. Without logging
     * the positions of each element, we can dynamically shift
     * each element. Inside the while loop, we reverse
     * chunk by chunk, starting with A and B, then B and C,
     * C and D, and so on.
     */
    public SimpleLinkedList invert() {
        if (this.head != null) {
            Node current = this.head;
            Node previous = null;
            Node next = null;
            while (current != null) {
                next = current.next;
                current.next = previous;
                previous = current;
                current = next;

            }
            this.head = previous;
        }
        return this;
    } // method SimpleLinkedList.invert

    /** String representation for the simple linked list */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.head != null) {
            Node current = this.head;
            while (current != null) {
                sb.append(String.format("%s", current.data));
                current = current.next;
            }
        }
        return sb.toString();
    } // method SimpleLinkedList.toString

    /**
     * Driver/test code. This is some of the most embarassing code I've ever written
     * but it works. Learn from it and do not write code that bad! :-)
     */
    public static void main(String[] args) {
        SimpleLinkedList demo = new SimpleLinkedList();
        boolean test1 = demo.findMiddle() == null;
        demo.add("A");
        boolean test2 = demo.findMiddle().data.equals("A");
        demo.add("B");
        boolean test3 = demo.findMiddle().data.equals("A");
        demo.add("C");
        boolean test4 = demo.findMiddle().data.equals("B");
        demo.add("D");
        demo.add("E");
        boolean test5 = demo.findMiddle().data.equals("C");
        boolean success = test1 && test2 && test3 && test4 && test5;
        if (success) {
            System.out.println("Method findMiddle works as specified.");
        } else {
            System.out.println("Method findMiddle not working as specified.");
        }
        String left = demo.toString();
        String right = demo.invert().toString();
        boolean test10 = left.length() == right.length();
        for (int i = 0; i < left.length(); i++) {
            test10 = test10
                    && left.charAt(i) == right.charAt(right.length() - 1 - i);
        }
        if (test10) {
            System.out.println("Method invert works as specified.");
        } else {
            System.out.println("Method invert not working as specified.");
        }

    } // method SimpleLinkedList.main

} // class SimpleLinkedList
