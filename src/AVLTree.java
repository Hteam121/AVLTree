// AVL Binary search tree implementation in Java
// Author: AlgorithmTutor

// data structure that represents a node in the tree

/** changed all the code to support the book object specifically**/

import java.text.DecimalFormat;

class Node {
    Book data;
    Node parent;
    Node left;
    Node right;
    double key; //Seperated the key from the data object to help with reading code
    int bf;

    public Node(Book data) {
        this.data = data;
        this.key = data.isbn;

        this.parent = null;
        this.left = null;
        this.right = null;
        this.bf = 0;
    }
}

public class AVLTree {
    private Node root;

    public AVLTree() {
        root = null;
    }

    private void printHelper(Node currPtr, String indent, boolean last) {
        // print the tree structure on the screen
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            System.out.println(currPtr.data.toString() + "(BF = " + currPtr.bf + ")");

            printHelper(currPtr.left, indent, false);
            printHelper(currPtr.right, indent, true);
        }
    }

    private Node searchTreeHelper(Node node, int key) {
        if (node == null || key == node.key) {
            return node;
        }

        if (key < node.key) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }

    private Node deleteNodeHelper(Node node, double key) {
        // search the key
        if (node == null) return node;
        else if (key < node.key) node.left = deleteNodeHelper(node.left, key);
        else if (key > node.key) node.right = deleteNodeHelper(node.right, key);
        else {
            // the key has been found, now delete it

            // case 1: node is a leaf node
            if (node.left == null && node.right == null) {
                node = null;
            }

            // case 2: node has only one child
            else if (node.left == null) {
                Node temp = node;
                node = node.right;
            }

            else if (node.right == null) {
                Node temp = node;
                node = node.left;
            }

            // case 3: has both children
            else {
                Node temp = minimum(node.right);
                node.key = temp.key;
                node.right = deleteNodeHelper(node.right, temp.key);
            }

        }

        // Write the update balance logic here
        // YOUR CODE HERE

        return node;
    }

    // update the balance factor the node
    private void updateBalance(Node ogNode, Node node) { //Added the ogNode parameter to help with printing insert value
        if (node.bf < -1 || node.bf > 1) {
            DecimalFormat format = new DecimalFormat("0");
            System.out.print("Imbalance condition occurred at inserting " + format.format(ogNode.key) ); //This is where I find out what node I'm using
            rebalance(node);
            return;
        }

        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.bf -= 1;
            }

            if (node == node.parent.right) {
                node.parent.bf += 1;
            }

            if (node.parent.bf != 0) {
                updateBalance(ogNode, node.parent);
            }
        }
    }

    // rebalance the tree
    void rebalance(Node node) {

        DecimalFormat format = new DecimalFormat("0"); //formats decimal value
        if (node.bf > 0) {

            //RL
            if (node.right.bf < 0) {

                System.out.print("; RL case fixed at " + format.format(node.key) + "\n"); //RL case along with the problem node
                rightRotate(node.right);
                leftRotate(node);

                //LL
            } else {
                System.out.print("; LL case fixed at " + format.format(node.key) + "\n"); //LL case along with the problem node

                leftRotate(node);
            }
        } else if (node.bf < 0) {

            //LR
            if (node.left.bf > 0) {

                System.out.print("; LR case fixed at " + format.format(node.key) + "\n"); //LR case along with the problem node

                leftRotate(node.left);
                rightRotate(node);

                //RR
            } else {
                System.out.print("; RR case fixed at " + format.format(node.key) + "\n"); //RR case along with the problem node
                rightRotate(node);
            }
        }
    }

    private void preOrderHelper(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    private void inOrderHelper(Node node) {
        if (node != null) {
            inOrderHelper(node.left);
            System.out.print(node.data + " ");
            inOrderHelper(node.right);
        }
    }

    private void postOrderHelper(Node node) {
        if (node != null) {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(node.data + " ");
        }
    }

    // Pre-Order traversal
    // Node.Left Subtree.Right Subtree
    public void preorder() {
        preOrderHelper(this.root);
    }

    // In-Order traversal
    // Left Subtree . Node . Right Subtree
    public void inorder() {
        inOrderHelper(this.root);
    }

    // Post-Order traversal
    // Left Subtree . Right Subtree . Node
    public void postorder() {
        postOrderHelper(this.root);
    }

    // search the tree for the key k
    // and return the corresponding node
    public Node searchTree(int k) {
        return searchTreeHelper(this.root, k);
    }

    // find the node with the minimum key
    public Node minimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // find the node with the maximum key
    public Node maximum(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // find the successor of a given node
    public Node successor(Node x) {
        // if the right subtree is not null,
        // the successor is the leftmost node in the
        // right subtree
        if (x.right != null) {
            return minimum(x.right);
        }

        // else it is the lowest ancestor of x whose
        // left child is also an ancestor of x.
        Node y = x.parent;
        while (y != null && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // find the predecessor of a given node
    public Node predecessor(Node x) {
        // if the left subtree is not null,
        // the predecessor is the rightmost node in the
        // left subtree
        if (x.left != null) {
            return maximum(x.left);
        }

        Node y = x.parent;
        while (y != null && x == y.left) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    // rotate left at node x
    void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;

        // update the balance factor
        x.bf = x.bf - 1 - Math.max(0, y.bf);
        y.bf = y.bf - 1 + Math.min(0, x.bf);
    }

    // rotate right at node x
    void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;

        // update the balance factor
        x.bf = x.bf + 1 - Math.min(0, y.bf);
        y.bf = y.bf + 1 + Math.max(0, x.bf);
    }


    // insert the key to the tree in its appropriate position
    public void insert(Book book) {
        // PART 1: Ordinary BST insert
        Node node = new Node(book);
        Node y = null;
        Node x = this.root;

        while (x != null) {
            y = x;
            if (node.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        // y is parent of x
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.key < y.key) {
            y.left = node;
        } else {
            y.right = node;
        }

        // PART 2: re-balance the node if necessary
        updateBalance(node, node);
    }

    // delete the node from the tree
    Node deleteNode(int data) {
        return deleteNodeHelper(this.root, data);
    }

    // print the tree structure on the screen
    public void prettyPrint() {
        printHelper(this.root, "", true);
    }

    /**Test Code
     * Won't work anymore since it is specific to Book
     * Used this code to test out where insertion, and problem node is detected for problem node
     * then went back and adjusted for Book
     * **/
    public static void main(String [] args) {
//        AVLTree bst = new AVLTree();
//        bst.insert(1);
//        bst.insert(2);
//        bst.insert(3);
//        bst.insert(4);
//        bst.insert(5);
//        bst.insert(6);
//        bst.insert(7);
//        bst.insert(8);
//        bst.prettyPrint();
    }
}