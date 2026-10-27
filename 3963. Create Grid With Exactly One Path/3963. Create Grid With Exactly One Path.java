1class Solution {
2    public String[] createGrid(int m, int n) {
3        String[] res = new String[m];
4        
5        Arrays.fill(res, 0, m - 1, . + #.repeat(n - 1));
6        res[m - 1] = ..repeat(n);
7        
8        return res;
9    }
10}