public class AVLNode {
    int sectorID;
    int height;
    SList tasks;
    AVLNode left;
    AVLNode right;

    public AVLNode(int sectorID, int height, SList tasks, AVLNode left, AVLNode right) {
        this.sectorID = sectorID;
        this.height = height;
        this.tasks = tasks;
        this.left = left;
        this.right = right;
    }

    toStringTODO
}