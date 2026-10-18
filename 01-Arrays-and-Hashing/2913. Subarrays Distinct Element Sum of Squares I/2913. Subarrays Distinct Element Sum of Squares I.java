1class Solution {
2    public int sumCounts(List<Integer> nums) {
3        int ans=0;
4        int n=nums.size();
5        for(int i=0;i<n;i++){
6            Set<Integer> s=new HashSet<>();
7            for(int j=i;j<n;j++){
8                s.add(nums.get(j));
9                int val=s.size();
10                ans+=(val*val);
11            }
12        }
13        return ans;
14    }
15}