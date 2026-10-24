1class Solution {
2    public int minOperations(String s) {
3        int n=s.length();
4        int min=s.charAt(0)-'a';
5        int max=s.charAt(0)-'a';
6        boolean sorted=true;
7        for(int i=1;i<n;i++){
8            if(s.charAt(i)<s.charAt(i-1)){
9                sorted=false;
10                break;
11            }
12        }
13        if(sorted)return 0;
14        if(n==2)return -1;
15        for(int i=1;i<n;i++){
16            min=Math.min(min,s.charAt(i)-'a');
17            max=Math.max(max,s.charAt(i)-'a');
18        }
19        
20        if(s.charAt(0)-'a'==min||s.charAt(n-1)-'a'==max) return 1;
21        for(int i=1;i<n-1;i++){
22            if(s.charAt(i)-'a'==min||s.charAt(i)-'a'==max) return 2;
23        }
24        return 3;
25    }
26}