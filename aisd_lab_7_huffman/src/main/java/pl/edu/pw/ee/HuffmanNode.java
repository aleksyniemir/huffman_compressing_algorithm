package pl.edu.pw.ee;

public class HuffmanNode {
    private int freq, integer;
    private HuffmanNode left, right;

    public HuffmanNode(Integer integer, int freq, HuffmanNode right, HuffmanNode left) {
        this.integer = integer;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        if (left == null && right == null) {
            return true;
        } else {
            return false;
        }
    }

    public int compareTo(HuffmanNode that) {
        return this.freq - that.freq;
    }

    public void increaseFrequency() {
        this.freq++;
    }

    public int getInteger() {
        return this.integer;
    }

    public int setInteger(int integer) {
        this.integer = integer;
        return this.integer;
    }

    public int getFrequency() {
        return this.freq;
    }

    public HuffmanNode getLeft() {
        return this.left;
    }

    
    public HuffmanNode getRight() {
        return this.right;
    }

    @Override
    public boolean equals(Object object) {
        HuffmanNode node = (HuffmanNode) object;
        if (node.getInteger() == this.getInteger()) {
            return true;
        } else {
            return false;
        }
    }
}
