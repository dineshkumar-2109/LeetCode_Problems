1class Solution {
2    private int direction[][]={{-1,0},{0,-1},{1,0},{0,1}};
3    public boolean bfs(int i,int j,int [][]grid,boolean visited[][]){
4        int n=grid.length;
5        int m=grid[0].length;
6        Queue<int[]> q=new LinkedList<>();
7        q.add(new int[]{i,j});
8        visited[i][j]=true;
9        boolean isClosed=true;
10        while(!q.isEmpty()){
11            
12            int []curr=q.poll();
13            for(int d[]:direction){
14                int ni=curr[0]+d[0];
15                int nj=curr[1]+d[1];
16                if (ni < 0 || ni >= n || nj < 0 || nj >= m) {
17                    continue;
18                }
19                
20                if(grid[ni][nj]!=1&&!visited[ni][nj]){
21                    if(ni==0||ni==n-1||nj==0||nj==m-1){
22                        isClosed=false;
23                        
24                    }
25                    q.offer(new int[]{ni,nj});
26                    visited[ni][nj]=true;
27                }
28            }
29        }
30        return isClosed;
31
32    }
33    public int closedIsland(int[][] grid) {
34        int n=grid.length;
35        int m=grid[0].length;
36        int island=0;
37        boolean visited[][]=new boolean[n][m]; 
38        for(int i=1;i<n-1;i++){
39            for(int j=1;j<m-1;j++){
40                if(grid[i][j]==0&&!visited[i][j]&&bfs(i,j,grid,visited)){
41                    island++;
42                }
43            }
44        }
45        return island;
46    }
47}