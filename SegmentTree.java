/*
 * Template to use SegmentTree to find the minimum element in the array with a given range
 * to build tree - use constructor: SegmentTree sg = new SegmentTree(int[] arr);
 * to query - use sg.query(start index, end index);
 * to update - use sg.update(int index, int value);
 * 
 */
public class SegmentTree {
    int[] seg;
    int n;

    // constructor will build the tree
    SegmentTree(int[] arr) {
        n = arr.length;
        seg = new int[4*n+1];
        build(0, arr, 0, n-1);
    }

    public static void main(String[] args) {
        int arr[] = {2, 1, -8, 4 ,3, 7};
        int n = arr.length;

        SegmentTree sg1 = new SegmentTree(arr);
        
        System.out.println(sg1.query(0, 5));
        sg1.update(3, 10);
        System.out.println(sg1.query(0, 5));
    }

    // function to build the tree(will be called by constructor)
    private void build(int ind, int[] arr, int low, int high) {

        if(low == high) {
            seg[ind] = arr[low];
            return;
        }
        int mid = low + (high-low)/2;
        build(2*ind+1, arr, low, mid);
        build(2*ind+2, arr, mid+1, high);
        seg[ind] = Math.min(seg[2*ind+1], seg[2*ind+2]);

    }

    // function to be called to find the query
    public int query(int l, int r) {
        if(l<0 || r<0 || l>=n || r>=n || l>r) {
            System.out.println("Invalid index range for the given array");
            return -1;
        }
        return findQuery(0, 0, n-1, l, r);
    }

    // function to perform the query
    private int findQuery(int ind, int low, int high, int l ,int r) {
        // case1: no overlap - l r low high || low high l r
        if(r < low || high < l) return Integer.MAX_VALUE;

        // case2: complete overlap = l low high r
        if(low >= l && high <= r) return seg[ind];

        // case3: partial overlap
        int mid = low + (high - low)/2;
        int left = findQuery(2*ind+1, low, mid, l, r);
        int right = findQuery(2*ind+2, mid+1, high, l, r);
        return Math.min(left, right);
    }

    // function to be called for updating value in segment tree
    public void update(int index, int value) {
        if(index < 0 || index >= n) {
            System.out.println("Invalid index for the given array");
            return;
        }
        updateTree(0, 0, n-1, index, value);
    }

    // function to update the segment tree
    private void updateTree(int ind, int low, int high, int i, int val) {
        if(low == high) {
            seg[ind] = val;
            return;
        }
        int mid = low + (high-low)/2;
        if(i <= mid) updateTree(2*ind+1, low, mid, i, val);
        else updateTree(2*ind+2, mid+1, high, i, val);
        seg[ind] = Math.min(seg[2*ind+1], seg[2*ind+2]);
    }
}