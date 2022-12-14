package pl.edu.pw.ee;

public class BinaryNode {
    private String s;
    private int integer;
    private int lengthOfChar;
    private String binaryCode;

    public BinaryNode(String s, int integer, int lengthOfChar) {
        this.integer = integer;
        this.s = s;
        this.lengthOfChar = lengthOfChar;
        createBinaryCode();
    }

    public String getString() {
        return this.s;
    }

    public int getInteger() {
        return this.integer;
    }

    public int getLengthOfChar() {
        return this.lengthOfChar;
    }

    public String getBinaryCode() {
        return this.binaryCode;
    }

    private void createBinaryCode() {
        this.binaryCode = this.getString();
        while(this.binaryCode.length() < this.getLengthOfChar()) {
            this.binaryCode = "0" + this.binaryCode;
        }
    }

    public boolean compareIntegers(int integer) {
        if (integer == this.getInteger()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean compareStrings(String s) {
        if (s.equals(this.binaryCode)) {
            return true;
        } else {
            return false;
        }
    }
}