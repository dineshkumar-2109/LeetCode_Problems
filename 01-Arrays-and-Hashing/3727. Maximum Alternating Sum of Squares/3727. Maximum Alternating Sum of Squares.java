1class Solution {
2    public long maxAlternatingSum(int[] nums) {
3        int l=0;
4        int r=nums.length-1;
5        long sum=0;
6        for(int i=0;i<nums.length;i++){
7            nums[i]=Math.abs(nums[i]);
8        }
9        Arrays.sort(nums);
10        boolean turn=true;
11        while(l<=r){
12            if(turn){
13                sum+=(nums[r]*nums[r]);
14                r--;
15            }else{
16                sum-=(nums[l]*nums[l]);
17                l++;
18            }
19            turn=!turn;
20        }
21        return sum;
22    }
23}