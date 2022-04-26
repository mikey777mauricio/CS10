public class SimpleSLL {
    private Element head;        // The first element of the list
    /**
     * A private class inner representing the elements in the list.
     */
    private class Element {
        public int data;
        public Element next;
        /**
         * Constructor for a linked list element, given an object.
         *
         */
        public Element(int theData) {
            data = theData;
            next = null;
        } }
    /**
     * Constructor to create an empty singly linked list.
     */
    public void SLL() {
        head = null; }
    /**
     * Is this list empty?
     */
    public boolean isEmpty() {
        return (head == null);
    }

    public int sum(Element elt){
        if(elt == null) return 0;
        else return elt.data + sum(elt.next);

    }

    public void append(int value){
        if(head == null) head = new Element(value);
        else {
            Element e = head;
            while(e.next != null){
                e = e.next;
            }
            e.next = new Element(value);
        }

    }

}