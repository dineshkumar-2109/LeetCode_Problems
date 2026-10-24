1class Solution {
2    public int sortableIntegers(int[] nums) {
3        int n=nums.length;
4        int ans=0;
5        for(int k=1;k*k<=n;k++){
6            if(n%k==0){
7                if(find(nums,k)){
8                    ans=ans+k;
9                }
10                if((n/k)!=k&& find(nums,n/k)){
11                    ans=ans+n/k;
12                }
13            }
14        }
15        return ans;
16    }
17    public boolean find(int []nums,int k){
18        int n=nums.length,prev=0;
19        for(int i=0;i<n;i+=k){
20            int cnt=0;
21            int min=nums[i];
22            int max=nums[i];
23            for(int j=i;j<i+k-1;j++){
24                if(nums[j]>nums[j+1]){
25                    cnt++;
26                }
27                min=Math.min(min,nums[j+1]);
28                max=Math.max(max,nums[j+1]);
29            }
30            if(nums[i+k-1]>nums[i]){
31                cnt++;
32            }
33            if(cnt>1||prev>min){
34                return false;
35            }
36            prev=max;
37        }
38        return true;
39    }
40}