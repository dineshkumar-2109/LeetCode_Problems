1class Solution {
2    private final int MOD=1000000007;
3    private long fact[];
4    private long invFact[];
5    public long solve(List<Integer> numbers){
6        int n=numbers.size();
7        if(n<=2){
8            return 1;
9        }
10        int root=numbers.get(0);
11        List<Integer> left=new ArrayList<>();
12        List<Integer> right=new ArrayList<>();
13        for(int i=1;i<n;i++){
14            if(numbers.get(i)<root){
15                left.add(numbers.get(i));
16            }else{
17                right.add(numbers.get(i));
18            }
19        }
20        int leftSize=left.size();
21        int rightSize=right.size();
22        long combinations=fact[n-1]*invFact[leftSize]%MOD*invFact[rightSize]%MOD;
23        long leftWays=solve(left);
24        long rightWays=solve(right);
25        return combinations*leftWays%MOD*rightWays%MOD;
26    }
27    public int numOfWays(int[] nums) {
28        int n=nums.length;
29        fact=new long [n+1];
30        invFact=new long [n+1];
31        fact[0]=1;
32        for(int i=1;i<=n;i++){
33            fact[i]=(fact[i-1]*i)%MOD;
34        }
35        invFact[n]=pow(fact[n],MOD-2);
36        for(int i=n;i>0;i--){
37            invFact[i-1]=invFact[i]*i%MOD;
38        }
39
40        List<Integer> numbers=new ArrayList<>();
41        for(int num:nums){
42            numbers.add(num);
43        }
44
45        return (int)(solve(numbers)-1+MOD)%MOD;
46    }
47    public long pow(long base,long exp){
48        long result=1;
49        while(exp>0){
50            if((exp&1)==1){
51                result=(result*base)%MOD;
52            }
53            base=(base*base)%MOD;
54            exp>>=1;
55        }
56        return result;
57    }
58}