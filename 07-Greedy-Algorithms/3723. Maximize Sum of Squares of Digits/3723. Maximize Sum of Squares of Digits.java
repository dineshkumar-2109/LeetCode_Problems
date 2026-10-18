1class Solution {
2    public String maxSumOfSquares(int num, int sum) {
3        if(num*9<sum){
4            return ;
5        }
6        
7        int val=sum/9;
8        sum=sum%9;
9        StringBuilder sb=new StringBuilder(9.repeat(val));
10        if(sum>0){
11            sb.append(sum);
12        }
13        sb.append(0.repeat(num-sb.length()));
14        return sb.toString();
15    }
16}
17/*
18class Solution {
19    public String maxSumOfSquares(int num, int sum) {
20        StringBuilder sb=new StringBuilder();
21        while(num>0){
22            int val=Math.min(sum,9);
23            sb.append(val);
24            num--;
25            sum-=val;
26        }
27        return sum>0?:sb.toString();
28    }
29}
30*/