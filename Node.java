public class Node {
    private Term term;
    private Node next;

    public Node(Term term) {
        this.term = term;
        this.next = null;
    }

    public Node getNext() {
        return next;
    }

    public Term getTerm() {
        return term;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    public void setCoefficient(int coefficient) {
        term.setCoefficient(coefficient);
    }
}
