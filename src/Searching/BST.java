package Searching;

import Fundamentals.api.Queue;
import Fundamentals.imp.QueueByLinkedList;

import java.util.Objects;

/**
 * 二叉查找树.
 * 查找时:输入的key和当前节点的key比较,输入key小了就往左走,打了往右走.
 * 增加时:找到了key,替换掉,遍历后仍为找到,就new后替换.
 * 删除(最麻烦):...
 *
 * @param <Key>
 * @param <Value>
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
    
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int N;
        
        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
        
        @Override
        public String toString() {
            return key + "-->" + val;
        }
    }
    
    //返回表中的键值对数量,即节点数量
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
        
    }
    
    public int size() {
        return size(root);
    }
    
    public Value get(Key key) {
        return get(root, key);
    }
    
    public void put(Key key, Value value) {
        root = put(root, key, value);
        
    }
    
    private Node put(Node x, Key key, Value value) {
        //存在就更新,不存在就新建.
        if (x == null) {
            return new Node(key, value, 1);
        }
        
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            //这里把当前节点的左节点指向新节点
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            //同理
            x.right = put(x.right, key, value);
        } else {
            x.val = value;
        }
        
        x.N = size(x.left) + size(x.right) + 1;
        
        return x;
    }
    
    
    private Value get(Node x, Key key) {
        if (x == null) {
            return null;
        }
        
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }
    
    
    public Key min() {
        return min(root).key;
    }
    
    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }
    
    public Key max() {
        return max(root).key;
    }
    
    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        
        return max(x.right);
    }
    
    
    private Node floor(Node root, Key key) {
        if (root == null) {
            return null;
        }
        
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root;
        }
        if (cmp < 0) {
            return floor(root.left, key);
        }
        Node bigger = floor(root.right, key);
        return Objects.requireNonNullElse(bigger, root);
    }
    
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }
    
    //找出上确界,即比key大的集合中,最小的那个
    public Key ceiling(Key key) {
        Node node = ceiling(root, key);
        if (node == null) {
            return null;
        }
        return node.key;
    }
    
    private Node ceiling(Node root, Key key) {
        if (root == null) {
            return null;
        }
        
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root;
        }
        
        if (cmp > 0) {
            return ceiling(root.right, key);
        }
        Node smaller = ceiling(root.left, key);
        return Objects.requireNonNullElse(smaller, root);
    }
    
    public Key select(int k) {
        return select(root, k).key;
    }
    
    private Node select(Node x, int k) {
        if (x == null) {
            return null;
        }
        int t = size(x.left);
        
        if (t > k) {
            return select(x.left, k);
        } else if (t < k) {
            return select(x.right, k - t - 1);
        } else {
            return x;
        }
    }
    
    /**
     * 输入的key和root的key相等-->把左子树的所有节点返回.
     * 输入的key比root小,返回在左子树中的排名.
     * 输入的key比root大,返回根节点和右节点的排名.
     *
     * @param key
     * @param x
     * @return
     */
    private int rank(Key key, Node x) {
        if (x == null) {
            return 0;
        }
        
        int cmp = key.compareTo(x.key);
        
        if (cmp < 0) {
            return rank(key, x.left);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(key, x.right);
        } else {
            return size(x.left);
        }
    }
    
    //如果浪费时间是愉快的,那么就不算浪费时间.
    public int rank(Key key) {
        return rank(key, root);
    }
    
    
    public void deleteMin() {
        root = deleteMin(root);
    }
    
    private Node deleteMin(Node x) {
        //x为要删除的节点,把x的右节点交出来
        if (x.left == null) {
            //right上位.
            return x.right;
        }
        
        //沿着左节点往下找
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        
        return x;
    }
    
    
    /**
     * 一种不错的想法,编码有点复杂.
     * 首先找到要删除的节点t,删除链表的方式删除掉.
     * 删除后用右子树x的最小节点,我们删除掉它
     * 用x替代原本t的位置,从而保证了二叉树的有序性.
     *
     * @param x   在根为x的子树中做删除.
     * @param key 要删掉的key.
     * @return 删除后树的根节点.
     */
    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        
        int cmp = key.compareTo(x.key);
        
        if (cmp < 0) {
            //老规矩,小了往左走
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            
            //大了去右边.
            x.right = delete(x.right, key);
        } else {
            //当找到了
            //当前节点的右节点为空,返回左节点填补位置.
            if (x.right == null) {
                return x.left;
            }
            
            //当前节点的左节点为空,返回右节点地址填补位置.
            if (x.left == null) {
                return x.right;
            }
            
            Node t = x;
            
            //找出右子树的最小节点.
            x = min(t.right);
            
            //干掉右子树的最小节点.即删除最小节点的连接,断开连接.
            x.right = deleteMin(t.right);
            
            //x调整好了
            x.left = t.left;
            
        }
        
        //调整更节点的节点个数.
        x.N = size(x.left) + size(x.right) + 1;
        
        return x;
    }
    
    public void delete(Key key) {
        root = delete(root, key);
    }
    
    public void print(Node x) {
        if (x == null) {
            return;
        }
        
        print(x.left);
        System.out.println(x.key);
        print(x.right);
    }
    
    public Iterable<Key> keys() {
        return keys(min(), max());
    }
    
    public Iterable<Key> keys(Key low, Key high) {
        Queue<Key> queue = new QueueByLinkedList<>();
        keys(root, queue, low, high);
        return queue;
    }
    
    //把合格的数据插入队列.注意剔除不合要求的.
    private void keys(Node x, Queue<Key> queue, Key low, Key high) {
        if (x == null) {
            return;
        }
        
        int cmplow = low.compareTo(x.key);
        int cmphigh = high.compareTo(x.key);
        
        if (cmplow < 0) {
            keys(x.left, queue, low, high);
        }
        
        
        //同时满足下界不大于x,上界不小于x时才会满足.
        if (cmplow <= 0 && cmphigh >= 0) {
            queue.enqueue(x.key);
        }
        
        if (cmphigh > 0) {
            keys(x.right, queue, low, high);
        }
    }
    
    
    public boolean contains(Key key) {
        return get(key) != null;
    }
    
    
    public static void main(String[] args) {
        BST<String, String> bst = new BST<>();
        bst.put("a", "b");
        bst.put("e", "d");
        bst.put("f", "d");
        bst.put("c", "d");
        bst.put("d", "d");

//        bst.print(bst.root);
        
        System.out.println(bst.floor("b"));
        System.out.println(bst.ceiling("b"));
        Iterable<String> keys = bst.keys();
        
        for (String key : keys) {
            System.out.println(key);
        }
    }
}

