public class Node {
    private Term term;
    private Node next;

    public Node(Term term) {
        this.term = term;
        this.next = null;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public Term getTerm() {
        return term;
    }

    public void setExponentX(int exponentX) {
        term.setExponentX(exponentX);
    }

    public void setExponentY(int exponentY) {
        term.setExponentY(exponentY);
    }

    public void setExponentZ(int exponentZ) {
        term.setExponentZ(exponentZ);
    }

    public int getData() {
        return term.getCoefficient();
    }

    public int getExponentX() {
        return term.getExponentX();
    }

    public int getExponentY() {
        return term.getExponentY();
    }

    public int getExponentZ() {
        return term.getExponentZ();
    }

    @Override
    public String toString() {
        return term.toString();
    }
}
