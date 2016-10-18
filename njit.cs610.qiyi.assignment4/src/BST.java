

/**
 * <p>
 * BST
 * </p>
 *
 * @author qiyi
 * @version 2015-12-3
 */
public class BST<Key extends Comparable<Key>, Value> {
    private class TreeNode{
        private Key key;
        private Value value;
        private TreeNode left = null;
        private TreeNode right = null;
        private TreeNode(Key key, Value value){
            this.key = key;
            this.value = value;
        }
    }
    private TreeNode root;
    public void insert(Key key, Value value){
        if (root == null) root = new TreeNode(key, value);
        else insert(root, key, value);
    }
    private void insert(TreeNode root, Key key, Value value){
        if (key.compareTo(root.key) == 0) System.out.println("Insert "+ root.key + " FAILED (already in the list)"); 
        if (key.compareTo(root.key) < 0){
            // go right subtree
            if (root.left == null) root.left = new TreeNode(key, value);
            else insert(root.left, key, value);
        }else{
            // go left subtree
            if (root.right == null) root.right = new TreeNode(key, value);
            else insert(root.right, key, value);
        }
    }
    public void delete(Key key){
        if (root == null)  System.out.println("Delete " + key + " FAILED (not in the list)");
        else this.root = delete(root, key);
        }
    private TreeNode delete(TreeNode root, Key key){
        if (key.compareTo(root.key) == 0) {
            if ((root.left == null) && (root.right == null)) return null;
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            // find the rightmost node of left subtree
            TreeNode rightmost = root.left;
            TreeNode parent = root.left; 
            while (rightmost.right != null) {
                parent = rightmost;
                rightmost = rightmost.right;
            }
            // if have intermediate parent
            if (parent != root.left) {
                // if have left subtree
                if (rightmost.left != null) parent.right = rightmost.left;
                rightmost.left = root.left;
            }
            rightmost.right = root.right;
            root = rightmost;
        }
        else if (key.compareTo(root.key) < 0){
            // go right subtree
            if (root.left == null) {
                System.out.println("Delete " + key + " FAILED (not in the list)");
            }
            else root.left = delete(root.left, key);
        }else{
            // go left subtree
            if (root.right == null) {
                System.out.println("Delete " + key + " FAILED (not in the list)");
            }
            else root.right = delete(root.right, key);
        }
        return root;
    }
    public Value search(Key key){
        if (root == null) return null;
        return search(root, key);
    }
    private Value search(TreeNode root, Key key){
        if (key.compareTo(root.key) == 0) return root.value; 
        if (key.compareTo(root.key) < 0){
            // go right subtree
            if (root.left == null) return null;
            else return search(root.left, key);
        }else{
            // go left subtree
            if (root.right == null) return null;
            else return search(root.right, key);
        }
    }
    
    public void printInorderTraverse(){
        System.out.print("(");
        if (root != null) printInorderTraverse(root);
        System.out.print(")");
        System.out.println();
    }
    private void printInorderTraverse(TreeNode root){
        if (root.left != null){
            System.out.print("(");
            printInorderTraverse(root.left);
            System.out.print(")");
        }
        System.out.print(root.key);
        if (root.right != null){
            System.out.print("(");
            printInorderTraverse(root.right);
            System.out.print(")");  
        }
    }
}
