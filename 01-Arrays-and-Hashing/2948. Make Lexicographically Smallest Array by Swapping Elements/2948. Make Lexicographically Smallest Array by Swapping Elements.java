1class Solution {
2    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
3        int []sortedArray=nums.clone();
4        Arrays.sort(sortedArray);
5        List<List<Integer>> groups = new ArrayList<>();
6        Map<Integer, Integer> map = new HashMap<>();
7        int id = -1;
8        for(int i=0;i<sortedArray.length;i++){
9            if(i==0||sortedArray[i]-sortedArray[i-1]>limit){
10                groups.add(new ArrayList<>());
11                id++;
12            }
13            groups.get(id).add(sortedArray[i]);
14            map.put(sortedArray[i],id);
15        }
16        int idx[]=new int[groups.size()];
17        for(int i=0;i<nums.length;i++){
18            int curr=map.get(nums[i]);
19            nums[i]=groups.get(curr).get(idx[curr]);
20            idx[curr]++;
21        }
22        return nums;
23    }
24}