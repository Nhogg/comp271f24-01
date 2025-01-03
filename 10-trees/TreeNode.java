public class TreeNode implements Comparable<TreeNode> {

    /** The data payload of the node */
    private String word;



    /** Its left and right pointers */
    private TreeNode left;

    private TreeNode right;

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    /**
     * Basic constructor creates a simple node with a payload and two null children.
     *
     * @param word
     */
    public TreeNode(String word) {
        this.word = word;
        this.left = null;
        this.right = null;
    } // basic constructor

    public String getWord() {
        return word;
    }

    /** Implementation of Comparable using the nodes' content strings as basis for comparison. */
    public int compareTo(TreeNode other) {
        int result = 1;
        if (other != null)
            result = this.getWord().compareTo(other.getWord());
        return result;
    } // method compareTo

    public int numChildren() {
        int cnt = 0;
        if (this.getLeft() != null) {
            cnt++;
        }
        if (this.getRight() != null) {
            cnt++;
        }
        return cnt;
    }

}