public class Term {
    private int coefficient;
    private int exponentX;
    private int exponentY;
    private int exponentZ;

    public Term(int coefficient, int exponentX, int exponentY, int exponentZ) {
        this.coefficient = coefficient;
        this.exponentX = exponentX;
        this.exponentY = exponentY;
        this.exponentZ = exponentZ;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setExponentX(int exponentX) {
        this.exponentX = exponentX;
    }

    public int getExponentX() {
        return exponentX;
    }

    public void setExponentY(int exponentY) {
        this.exponentY = exponentY;
    }

    public int getExponentY() {
        return exponentY;
    }

    public void setExponentZ(int exponentZ) {
        this.exponentZ = exponentZ;
    }

    public int getExponentZ() {
        return exponentZ;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (coefficient != 0) {
            if (coefficient > 0 && builder.length() > 0) {
                builder.append("+");
            }
            builder.append(coefficient);
            if (exponentX > 0) {
                builder.append("x");
                if (exponentX > 1) {
                    builder.append("^").append(exponentX);
                }
            }
            if (exponentY > 0) {
                builder.append("y");
                if (exponentY > 1) {
                    builder.append("^").append(exponentY);
                }
            }
            if (exponentZ > 0) {
                builder.append("z");
                if (exponentZ > 1) {
                    builder.append("^").append(exponentZ);
                }
            }
        }
        return builder.toString();
    }
}
