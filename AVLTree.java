public class AVLTree {
    AVLNode root;
    int size;
    //the pdf suggests we will need more attributes
    /*
    TODO:
    implement the 4 rotations 
    run through the entire logic of the tree to see if it makes sense */
    public AVLTree() {
        root = null;
        size = 0;
    }

    //might need a helper class for balancing
    public void Insert(int sectorID) {
        if (root == null) {
            root = AVLNode(sectorID, 1, new SList, null, null);
        }
        if(root.sectorID > sectorID) {
            Insert(root.left, sectorID, 2);
        }
        if(root.sectorID < sectorID) {
            Insert(root.right, sectorID, 2);
        }
    }       

    //I intentionally used method overloading a lot in the AVL tree
    private void Insert(AVLNode currentNode, int sectorID, int height) {
        if(currentNode == null) {
            currentNode = AVLNode(sectorID, height, new SList, null, null);
        }
        if(currentNode.sectorID > sectorID) {
            Insert(currentNode.left, sectorID, height + 1);
        }
        if(currentNode.sectorID < sectorID) {
            Insert(currentNode.right, sectorID, height + 1);
        }
        Balance(currentNode); //or maybe we need to call balance on the whole tree idk
    }

    public void AVLNode Remove(int sectorID) {
        Remove(root, sectorID);
    }
    
    private AVLNode Remove(AVLNode currentNode, int sectorID) {
        if(currentNode == null) return null;
        if(currentNode.sectorID > sectorID) {
            currentNode.left = Remove(currentNode.left, sectorID);
        }
        else if(currentNode.sectorID < sectorID) {
            currentNode.right = Remove(currentNode.right, sectorID);
        }
        else {
            if(currentNode.left == null) return currentNode.right;
            if(currentNode.right == null) return currentNode`.left;

            AVLNode replacer = findMin(currentNode.right);
            currentNode.sectorID = replacer.sectorID;
            currentNode.tasks = replacer.tasks;
            currentNode.right = Remove(currentNode.right, replacer.sectorID);

            updateHeight(currentNode);

            currentNode = Balance(currentNode);
            return currentNode;
        }
    }

    private AVLNode findMin(AVLNode node) {
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    private int returnHeight(AVLNode node) {
        if(node == null) return 0;
        return node.height;
    }
    private void updateHeight(AVLNode node) {
        //instead of doing multiple ifs
        //math.max does it in one line
        node.height = 1 + Math.max(returnHeight(node.left), returnHeight(node.right));
    }

    //we will use LVR 
    public AVLNode Search(int sectorID) {
        return Search(root, sectorID);
    }

    private AVLNode Search(AVLNode currentNode, int sectorID) {
        if (currentNode == null) return null;

        if(currentNode.sectorID == sectorID) {
            return currentNode;
        }
        if(currentNode.sectorID > sectorID) {
            return Search(currentNode.left, sectorID);
        }
        if(currentNode.sectorID < sectorID) {
            return Search(currentNode.right, sectorID);
        }
        return null; //I put this just in case but I think the first line does the same thing
    }

    private void Traverse(AVLNode currentNode) {
        if(currentNode == null) return;
        Traverse(currentNode.left);
        System.out.println(currentNode); 
        Traverse(currentNode.right);
    }

    private AVLNode Balance(AVLNode currentNode) {
        int balanceFactor = returnHeight(currentNode.left) - returnHeight(currentNode.right);
        if(balanceFactor >= 2) {
            int lowerBalanceFactor = returnHeight(currentNode.left.left) - returnHeight(currentNode.left.right);
            if(lowerBalanceFactor <= -1) {
                //leftright case
                currentNode.left = RotateTreeLeft(currentNode.left);
                return RotateTreeRight(currentNode);
            }
            if(lowerBalanceFactor >= 1) {
                //leftleft case
                return RotateTreeRight(currentNode);
            }
        }

        if(balanceFactor <= -2) {
            int lowerBalanceFactor = returnHeight(currentNode.right.left) - returnHeight(currentNode.right.right);
            if(lowerBalanceFactor <= -1) {
                //rightright case
                return RotateTreeLeft(currentNode);
            }
            if(lowerBalanceFactor >= 1) {
                //rightleft case
                currentNode.right = RotateTreeRight(currentNode.right);
                return RotateTreeLeft(currentNode);
            }
        }
        return currentNode;
    }
    //this is for the leftleft and leftright cases
    public TaskNode RotateTreeRight(AVLNode node) {
        AVLNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    //this is for the rightright and rightleft cases
    public TaskNode RotateTreeLeft(AVLNode node) {
        AVLNode newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }
    public TaskNode completeTask(TaskNode task) {
        AVLNode sectorNode = search(task.sectorID);
        if(sectorNode != null) {
            return sectorNode.tasks.completeTask(task);
        }
    }
    //The pdf suggests we may need more methods, there might be one in tasks
}