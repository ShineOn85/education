package my.tree;

public class Tree {
    private Node root;

    class Node {
        Node leftNode;
        Node rightNode;
        Node parent;
        final int value;
        boolean isRed = true;

        public Node(int value) {
            this.value = value;
        }
    }

    public void add(int value) {
        Node node = new Node(value);
        if (root == null) {
            root = node;
            root.isRed = false;
            return;
        }

        Node n = root;

        while (!(n.value == value)) {
            if (value < n.value) {
                if (n.leftNode != null) {
                    n = n.leftNode;
                } else {
                    n.leftNode = node;
                    n.leftNode.parent = n;
                    fixAfterInsert(n.leftNode);
                    return;
                }
            } else {
                if (n.rightNode != null) {
                    n = n.rightNode;
                } else {
                    n.rightNode = node;
                    n.rightNode.parent = n;
                    fixAfterInsert(n.rightNode);
                    return;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "[" + toString(root).trim().replaceAll("[,]$", "") + "]";
    }

    private String toString(Node node) {
        StringBuilder builder = new StringBuilder();
        if (node == null) {
            return "";
        }
        builder.append(toString(node.leftNode));
        builder.append(node.value);
        builder.append(", ");
        builder.append(toString(node.rightNode));
        return builder.toString();
    }

    /*public String toString(Node node) {
        String result = "";
        if (node == null) {
            return "";
        }
        result += toString(node.leftNode);
        result += node.value + ", ";
        result += toString(node.rightNode);

        return result;
    }*/

    private void fixAfterInsert(Node node) {
        if (node.parent == null) {
            node.isRed = false;
            return;
        }

        Node parent = node.parent;
        Node grandParent = parent.parent;

        if (grandParent == null) {
            parent.isRed = false;
            return;
        }

        Node uncle = grandParent.leftNode == parent ? grandParent.rightNode : grandParent.leftNode;

        if (node.isRed && (uncle != null && uncle.isRed) && parent.isRed) {
            flipColor(parent);
        }

        if (node.isRed && parent.isRed) {
            if (grandParent.leftNode == parent && parent.rightNode == node) {
                smallLeftRotate(parent);
                return;
            }
            if (grandParent.rightNode == parent && parent.leftNode == node) {
                smallRightRotate(parent);
                return;
            }
            if ((uncle == null || !uncle.isRed) && parent.leftNode == node) {
                rightRotate(parent);
                return;
            }
            if ((uncle == null || !uncle.isRed) && parent.rightNode == node) {
                leftRotate(parent);
                return;
            }
        }
        fixAfterInsert(node.parent);
    }

    private void flipColor(Node node) {
        node.parent.isRed = true;
        if (node.parent.rightNode != null) {
            node.parent.rightNode.isRed = false;
        }
        if (node.parent.leftNode != null) {
            node.parent.leftNode.isRed = false;
        }
        if (node.parent == root) node.parent.isRed = false;
    }

    private void rightRotate(Node node) {
        Node n = node.parent;
        node.isRed = false;
        n.isRed = true;
        if (n.parent != null && n.parent.rightNode != n) {
            n.parent.leftNode = node;
        } else if (n.parent != null && n.parent.leftNode != n) {
            n.parent.rightNode = node;
        }
        n.leftNode = node.rightNode;
        node.parent = n.parent;
        n.parent = node;
        node.leftNode.parent = node;
        if (node.rightNode != null) {
            node.rightNode.parent = n;
        }
        node.rightNode = n;
        if (node.parent == null) {
            root = node;
        }
        fixAfterInsert(node.leftNode);
    }

    private void leftRotate(Node node) {
        Node n = node.parent;
        node.isRed = false;
        n.isRed = true;
        if (n.parent != null && n.parent.leftNode != n) {
            n.parent.rightNode = node;
        } else if (n.parent != null && n.parent.rightNode != n) {
            n.parent.leftNode = node;
        }
        n.rightNode = node.leftNode;
        node.parent = n.parent;
        n.parent = node;
        node.rightNode.parent = node;
        if (node.leftNode != null) {
            node.leftNode.parent = n;
        }
        node.leftNode = n;
        if (node.parent == null) {
            root = node;
        }
        fixAfterInsert(node.rightNode);
    }

    private void smallRightRotate(Node node) {
        Node n = node.leftNode;
        node.leftNode = node.leftNode.rightNode;
        n.rightNode = node;
        n.parent = node.parent;
        node.parent.rightNode = n;
        leftRotate(n);
    }

    private void smallLeftRotate(Node node) {
        Node n = node.rightNode;
        node.rightNode = node.rightNode.rightNode;
        n.leftNode = node;
        n.parent = node.parent;
        node.parent.leftNode = n;
        rightRotate(n);
    }

    private Node search(int value) {
        Node node = root;
        while (node != null && node.value != value) {
            node = value < node.value ? node.leftNode : node.rightNode;
        }
        return node;
    }

    public void remove(int value) {
        Node removeNode = search(value);
        if (removeNode == null) return;
        remove(removeNode);
    }

    private void remove(Node removeNode) {
        if (removeNode.leftNode != null && removeNode.rightNode != null) {
            Node swapNode = maxLeftSubTree(removeNode);
            Node node = swap(removeNode, swapNode);
            remove(node);
            return;
        }

        if (removeNode.leftNode == null && removeNode.rightNode == null) {
            if (removeNode == root) {
                root = null;
                return;
            }
            if (removeNode.parent.leftNode == removeNode) {
                removeNode.parent.leftNode = null;
            } else {
                removeNode.parent.rightNode = null;
            }
            if (!removeNode.isRed) {
                Node sibling = removeNode.parent.leftNode == null ? removeNode.parent.rightNode : removeNode.parent.leftNode;
                fixAfterRemove(sibling);
            }
        }
        if (removeNode.leftNode != null || removeNode.rightNode != null) {
            if (removeNode.rightNode != null) {
                if (removeNode == root) {
                    root = removeNode.rightNode;
                    root.isRed = false;
                    return;
                }
                if (removeNode.parent.rightNode == removeNode) {
                    removeNode.parent.rightNode = removeNode.rightNode;
                } else {
                    removeNode.parent.leftNode = removeNode.rightNode;
                }
                removeNode.rightNode.parent = removeNode.parent;
                removeNode.rightNode.isRed = false;
            } else {
                if (removeNode == root) {
                    root = removeNode.leftNode;
                    root.isRed = false;
                    return;
                }
                if (removeNode.parent.leftNode == removeNode) {
                    removeNode.parent.leftNode = removeNode.leftNode;
                } else {
                    removeNode.parent.rightNode = removeNode.leftNode;
                }
                removeNode.leftNode.parent = removeNode.parent;
                removeNode.leftNode.isRed = false;
            }
        }
    }

    private Node maxLeftSubTree(Node node) {
        Node max = node.leftNode;
        while (max.rightNode != null) {
            max = max.rightNode;
        }
        return max;
    }

    private Node swap(Node n1, Node n2) {
        Node parent = n1.parent;
        Node left = n1.leftNode == n2 ? n1 : n1.leftNode;
        Node right = n1.rightNode;
        Node child = n2.leftNode;
        boolean red = n1.isRed;
        Node n2Parent = n2.parent;
        boolean n2Red = n2.isRed;

        n1.leftNode = null;
        n1.rightNode = null;
        n1.parent = n2.parent == n1 ? n2 : n2.parent;
        n1.isRed = n2.isRed;

        right.parent = n2;
        left.parent = n2;

        n2.parent = parent;
        n2.leftNode = n1 == left && child != null ? child : left;
        n2.rightNode = right;
        n2.isRed = red;

        if (child != null) {
            n1.isRed = child.isRed;
            child.isRed = n2Red;
            child.parent = left == n1 ? n2 : n2Parent;
            child.rightNode = n1;
            n1.parent = child;
            n2Parent.rightNode = n2Parent == n1 ? null : child;
        }

        if (parent == null) {
            root = n2;
        } else if (parent.leftNode.value == n1.value) {
            parent.leftNode = n2;
        } else parent.rightNode = n2;
        return n1;
    }

    private void fixAfterRemove(Node node) {
        if (node == node.parent.rightNode) {
            if (node.isRed) {
                leftRotate(node);
                node.isRed = false;
                node.leftNode.isRed = true;
                fixAfterRemove(node.leftNode.rightNode);
                return;
            }
            if (node.leftNode != null && node.leftNode.isRed) {
                smallRightRotate(node);
                fixAfterInsert(node.parent);
                return;
            }
            if (node.rightNode != null && node.rightNode.isRed) {
                leftRotate(node);
                node.leftNode.isRed = false;
                node.rightNode.isRed = false;
                return;
            }
        } else {
            if (node.isRed) {
                rightRotate(node);
                node.isRed = false;
                node.rightNode.isRed = true;
                fixAfterRemove(node.rightNode.leftNode);
                return;
            }
            if (node.rightNode != null && node.rightNode.isRed) {
                smallLeftRotate(node);
                node.parent.leftNode.isRed = false;
                node.parent.rightNode.isRed = false;
                node.parent.isRed = true;
                fixAfterInsert(node.parent);
                return;
            }
            if (node.leftNode != null && node.leftNode.isRed) {
                rightRotate(node);
                node.leftNode.isRed = false;
                node.rightNode.isRed = false;
                node.isRed = true;
                return;
            }
        }
        Node parent = node.parent;
        boolean parentRed = parent.isRed;
        node.parent.isRed = false;
        node.isRed = true;
        if (!parentRed && parent != root) {
            Node sibling = parent.parent.rightNode;
            if (sibling.leftNode.isRed || sibling.rightNode.isRed) {
                leftRotate(node.parent.parent.rightNode);
                flipColor(node.parent.parent);
            } else {
                parent.parent.isRed = false;
                parent.parent.rightNode.isRed = true;
            }
        }
    }
}

