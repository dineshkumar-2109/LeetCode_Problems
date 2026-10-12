1class Solution {
2    private final int MOD=1000000007;
3    public int maxSum(List<Integer> nums, int k) {
4        int []bits=new int[31];
5        for(int num:nums){
6            for(int i=0;i<31;i++){
7                if((num&(1<<i))!=0){
8                    bits[i]++;
9                }
10            }
11        }
12        long ans[]=new long[k];
13        for(int i=0;i<k;i++){
14            long currNum=0;
15            for(int bit=30;bit>=0;bit--){
16                if(bits[bit]>0){
17                    currNum|=(1<<bit);
18                    bits[bit]--;
19                }
20            }
21            ans[i]=currNum;
22        }
23
24        long sum=0;
25        for(long num:ans){
26            sum=(sum+(num*num)%MOD)%MOD;
27        }
28        return (int)sum;
29    }
30}