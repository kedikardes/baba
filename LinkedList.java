public class LinkedList {

    protected Node head;
    protected Node tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node getHead() {
        return head;
    }

    public void insertFirst(Node newNode) {
        if (isEmpty()) {
            tail = newNode;
        }
        newNode.setNext(head);
        head = newNode;
    }

    public void insertLast(Node newNode) {
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
    }

    public void insertMiddle(Node newNode, Node previous) {
        newNode.setNext(previous.getNext());
        previous.setNext(newNode);
    }
    public Node getNodeWithKey(int expX, int expY, int expZ) {
        Node current = head;
        while (current != null) {
            Term term = current.getTerm();
            if (term.getExponentX() == expX && term.getExponentY() == expY && term.getExponentZ() == expZ) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node current = head;
        while (current != null) {
            builder.append(current.getTerm()).append(" ");
            current = current.getNext();
        }
        return builder.toString();
    }

}
