package com.lf.tree;

import com.alibaba.fastjson.JSON;

public class BinaryTree {
    private TreeNode root;
    private int size;

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.addNode(6);
        binaryTree.addNode(7);
        binaryTree.addNode(3);
        binaryTree.addNode(2);
        binaryTree.addNode(8);
        binaryTree.addNode(1);
        binaryTree.addNode(9);
        System.out.println(JSON.toJSONString(binaryTree));
        System.out.println(binaryTree.maxDeep1(binaryTree.root));
        System.out.println(binaryTree.search(2));
        System.out.println(binaryTree.search(4));
        System.out.println(binaryTree.search(1));
    }

    public void addNode(int val) {
        TreeNode node = new TreeNode(val);
        addNode(node);
    }



    public boolean search(int val) {
        if (root == null) {
            return false;
        }
        TreeNode currentNode = root;
        while (currentNode != null) {
            if (currentNode.val == val) {
                return true;
            } else if (currentNode.val > val) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }

    public TreeNode searchNode(TreeNode node) {
        if (root == null || node == null) {
            return null;
        }
        TreeNode currentNode = root;
        while (currentNode != null) {
            if (currentNode.val == node.val) {
                return currentNode;
            } else if (currentNode.val > node.val) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return null;
    }

    public void addNode(TreeNode addNode) {
        if (root == null) {
            this.root = addNode;
        }

        TreeNode currNode = this.root;
        TreeNode parent = null;
        while (currNode != null) {

            if (addNode.val == currNode.val) {
                break;
            }

            parent = currNode;
            if (addNode.val < currNode.val) {
                currNode = parent.left;
                if (currNode == null) {
                    parent.left = addNode;
                    return;
                }

            } else {
                currNode = parent.right;
                if (currNode == null) {
                    parent.right = addNode;
                    return;
                }
            }
        }

        this.size++;
    }

    public void delNode(TreeNode node) {
        if (root == null || node == null) {
            return;
        }

        TreeNode treeNode = searchNode(node);
        if(treeNode == null){
            return;
        }
        //根节点
        if(treeNode.parent == null){
            if(this.root.right != null){
                this.root = this.root.right;
                this.root.left = treeNode.left;
            }else if(this.root.left != null){

            }
        }


    }

    public int maxDeep1(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int left = maxDeep1(root.left);
            int right = maxDeep1(root.right);
            return 1 + Math.max(left, right);
        }
    }

    public void minDeep() {

    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static class TreeNode {
        private int val;
        private TreeNode parent;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getLeft() {
            return left;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }
}
