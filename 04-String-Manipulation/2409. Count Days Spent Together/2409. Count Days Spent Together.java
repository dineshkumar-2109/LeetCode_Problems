1class Solution {
2    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
3        return Math.max(0,Math.min(day(leaveAlice),day(leaveBob))-Math.max(day(arriveAlice),day(arriveBob))+1);
4   }
5    public int day(String s){
6        int month=10*(s.charAt(0)-'0')+(s.charAt(1)-'0');
7        int date=10*(s.charAt(3)-'0')+(s.charAt(4)-'0');
8        return (275*month)/9-30-2*((month+9)/12)+date;
9    }
10}