package com.lf.tree;

public class RedBlackTreeNode {
    private int val;
    private RedBlackTreeNode left;
    private RedBlackTreeNode right;

    private boolean isRed;

    private RedBlackTreeNode parent;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public RedBlackTreeNode getLeft() {
        return left;
    }

    public void setLeft(RedBlackTreeNode left) {
        this.left = left;
    }

    public RedBlackTreeNode getRight() {
        return right;
    }

    public void setRight(RedBlackTreeNode right) {
        this.right = right;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    public RedBlackTreeNode getParent() {
        return parent;
    }

    public void setParent(RedBlackTreeNode parent) {
        this.parent = parent;
    }
}
