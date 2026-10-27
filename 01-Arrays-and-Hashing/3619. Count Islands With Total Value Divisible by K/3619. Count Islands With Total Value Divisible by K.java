1class Solution {
2    private int direction[][]={{-1,0},{0,-1},{1,0},{0,1}};
3    public boolean bfs(int i,int j,int [][]grid,boolean visited[][],int k){
4        int n=grid.length;
5        int m=grid[0].length;
6        Queue<int[]> q=new LinkedList<>();
7        long sum=0;
8        q.add(new int[]{i,j});
9        visited[i][j]=true;
10        while(!q.isEmpty()){
11            
12            int []curr=q.poll();
13            sum+=grid[curr[0]][curr[1]];
14            for(int d[]:direction){
15                int ni=curr[0]+d[0];
16                int nj=curr[1]+d[1];
17                
18                
19                if(ni>=0&&nj>=0&&ni<n&&nj<m&&grid[ni][nj]!=0&&!visited[ni][nj]){
20                    
21                    q.offer(new int[]{ni,nj});
22                    visited[ni][nj]=true;
23                }
24            }
25        }
26
27        return sum%k==0;
28
29    }
30    public int countIslands(int[][] grid, int k) {
31        int n=grid.length;
32        int m=grid[0].length;
33        int island=0;
34        boolean visited[][]=new boolean[n][m]; 
35        for(int i=0;i<n;i++){
36            for(int j=0;j<m;j++){
37                if(grid[i][j]!=0&&!visited[i][j]&&bfs(i,j,grid,visited,k)){
38                    island++;
39                }
40            }
41        }
42        return island;
43    }
44}