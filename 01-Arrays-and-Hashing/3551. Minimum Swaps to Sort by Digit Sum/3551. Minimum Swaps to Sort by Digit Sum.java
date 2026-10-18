1import java.util.Arrays;
2
3class Solution {
4    public int findSum(int num){
5        int sum=0;
6        while(num>0){
7            sum+=num%10;
8            num=num/10;
9        }
10        return sum;
11    }
12    public int minSwaps(int[] nums) {
13        int n=nums.length;
14        int[][] arr=new int[n][3];
15        for(int i=0;i<n;i++){
16            arr[i][0]=findSum(nums[i]);
17            arr[i][1]=nums[i];
18            arr[i][2]=i;
19        }
20        Arrays.sort(arr,(a,b)->
21        {
22            if(a[0]!=b[0]){
23                return Integer.compare(a[0],b[0]);
24            }
25            return Integer.compare(a[1],b[1]);
26        });
27        boolean[] visited=new boolean[n];
28        int swaps=0;
29        for(int i=0;i<n;i++){
30            if(visited[i]||arr[i][2]==i){
31                continue;
32            }
33            int cycleSize=0;
34            int j=i;
35            while(!visited[j]){
36                visited[j]=true;
37                j=arr[j][2];
38                cycleSize++;
39            }
40            if(cycleSize>1){
41                swaps+=(cycleSize-1);
42            }
43        }
44        return swaps;
45    }
46}