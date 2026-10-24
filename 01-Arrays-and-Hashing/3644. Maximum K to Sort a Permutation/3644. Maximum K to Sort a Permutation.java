1class Solution {
2    public int sortPermutation(int[] nums) {
3        int k=-1;
4        for(int i=0;i<nums.length;i++){
5            if(nums[i]==i){
6                continue;
7            }
8            k=k&nums[i];
9            if(k==0){
10                return 0;
11            }
12            
13
14        }
15        return Math.max(k,0);
16
17    }
18}