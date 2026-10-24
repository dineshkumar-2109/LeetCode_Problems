1class Solution {
2    public List<Integer> targetIndices(int[] nums, int target) {
3        List<Integer> ans=new ArrayList<>();
4        Arrays.sort(nums);
5        for(int i=0;i<nums.length;i++){
6            if(nums[i]==target){
7                ans.add(i);
8            }
9        }
10        return ans;
11    }
12}