1class Solution {
2    public int sumOfSquares(int[] nums) {
3        int sum=0,n=nums.length;
4        for(int i=1;i<=n;i++){
5            if(n%i==0){
6                sum+=(nums[i-1]*nums[i-1]);
7            }
8        }
9        return sum;
10    }
11}