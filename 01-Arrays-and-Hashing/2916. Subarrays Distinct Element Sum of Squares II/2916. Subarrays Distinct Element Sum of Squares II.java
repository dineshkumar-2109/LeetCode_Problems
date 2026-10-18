1class Solution {
2    long[] s, q, lz;
3
4    void app(int o, int len, long k) {
5        q[o] += 2 * k * s[o] + k * k * len;
6        s[o] += k * len;
7        lz[o] += k;
8    }
9
10    void upd(int o, int l, int r, int L, int R) {
11        if (L <= l && r <= R) { app(o, r - l + 1, 1); return; }
12        int m = (l + r) >> 1;
13        if (lz[o] != 0) {
14            app(2*o, m - l + 1, lz[o]);
15            app(2*o+1, r - m, lz[o]);
16            lz[o] = 0;
17        }
18        if (L <= m) upd(2*o, l, m, L, R);
19        if (R > m) upd(2*o+1, m+1, r, L, R);
20        s[o] = s[2*o] + s[2*o+1];
21        q[o] = q[2*o] + q[2*o+1];
22    }
23
24    public int sumCounts(int[] nums) {
25        final long MOD = 1_000_000_007L;
26        int n = nums.length;
27        s = new long[4*n]; q = new long[4*n]; lz = new long[4*n];
28        int[] last = new int[100001];
29        java.util.Arrays.fill(last, -1);
30        long ans = 0;
31        for (int i = 0; i < n; i++) {
32            upd(1, 0, n-1, last[nums[i]] + 1, i);
33            last[nums[i]] = i;
34            ans = (ans + q[1]) % MOD;
35        }
36        return (int) ans;
37    }
38}