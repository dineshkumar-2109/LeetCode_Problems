<h2><a href="https://leetcode.com/problems/minimum-operations-to-sort-a-string">3863. Minimum Operations to Sort a String</a></h2>

<p data-end="244" data-start="156">You are given a string <code>s</code> consisting of lowercase English letters.</p>

<p>In one operation, you can select any <strong><span data-keyword="substring-nonempty" class=" cursor-pointer relative text-dark-blue-s text-sm"><button type="button" aria-haspopup="dialog" aria-expanded="false" aria-controls="radix-_r_t_" data-state="closed" class="">substring</button></span></strong> of <code>s</code> that is <strong>not</strong> the entire string and <strong>sort</strong> it in <strong>non-descending alphabetical</strong> order.</p>

<p>Return the <strong>minimum</strong> number of operations required to make <code>s</code> sorted in <strong>non-descending</strong> order. If it is not possible, return -1.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "dog"</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong>​​​​​​​</p>

<ul>
	<li>Sort substring <code>"og"</code> to <code>"go"</code>.</li>
	<li>Now, <code>s = "dgo"</code>, which is sorted in ascending order. Thus, the answer is 1.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "card"</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Sort substring <code>"car"</code> to <code>"acr"</code>, so <code>s = "acrd"</code>.</li>
	<li>Sort substring <code>"rd"</code> to <code>"dr"</code>, making <code>s = "acdr"</code>, which is sorted in ascending order. Thus, the answer is 2.</li>
</ul>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "gf"</span></p>

<p><strong>Output:</strong> <span class="example-io">-1</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>It is impossible to sort <code>s</code> under the given constraints. Thus, the answer is -1.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 10<sup>5</sup></code></li>
	<li><code>s</code> consists of only lowercase English letters.</li>
</ul>


---

# 🛍️ Minimum-Operations-to-Sort-a-String | Explained

## Approach 1: Extremum Positioning & Case Analysis
### Intuition
Imagine you have a row of books on a narrow shelf, but your hands are only wide enough to pull out and alphabetize $n - 1$ books at a time. This leaves you with only two possible actions:
1. Sort the **first $n - 1$ books** (indices $0$ to $n - 2$), leaving the last book fixed.
2. Sort the **last $n - 1$ books** (indices $1$ to $n - 1$), leaving the first book fixed.

To sort the entire shelf with the fewest rearrangements, your goal is to place the smallest book at the front (index `0`) or the largest book at the back (index `n - 1`):
- If the smallest book is already at index `0`, you only need to sort the remaining $n - 1$ books once.
- If the largest book is already at index `n - 1`, you only need to sort the first $n - 1$ books once.
- If either the minimum or maximum element is somewhere in the middle, you can bring it to its correct boundary in one operation, and then sort the rest in a second operation (total: 2 operations).
- If the worst-case scenario occurs—where the minimum is trapped at the very end (`n - 1`) and the maximum is at the very beginning (`0`)—they block each other from reaching their final destinations immediately. You need one operation to kick one of them into the middle, a second to push it to the correct end, and a third to sort the rest (total: 3 operations).
- When $n = 2$, sorting a subarray of length $n - 1 = 1$ does nothing, making it impossible to fix an unsorted string.

---

### Algorithm Visualized

```mermaid
flowchart TD
    A[Start: Input String s] --> B{Is s already sorted?}
    B -- Yes --> C[Return 0]
    B -- No --> D{Is length n == 2?}
    D -- Yes --> E[Return -1<br/>Cannot swap 1-element subarrays]
    D -- No --> F[Find global min and max characters]
    F --> G{Is s[0] == min OR s[n-1] == max?}
    G -- Yes --> H[Return 1<br/>One sort of length n-1 suffices]
    G -- No --> I{Is min or max in the middle?<br/>Index 1 to n-2}
    I -- Yes --> J[Return 2<br/>Move extremum to boundary, then sort]
    I -- No --> K[Return 3<br/>Max is at 0 AND Min is at n-1]
```

---

### Approach
1. **Sorted Check:** Iterate through the string from index `1` to `n - 1`. If every character is greater than or equal to its predecessor, the string is already sorted. Return `0`.
2. **Impossibility Check ($n = 2$):** If the string has length `2` and is not sorted, any subarray of length $n - 1 = 1$ is already trivial and cannot change relative order. Return `-1`.
3. **Find Extremes:** Traverse the string to find the global minimum and maximum characters.
4. **Evaluate Boundaries (1 Operation):**
   - If the first character is already the global minimum (`s[0] == min`), sorting the suffix `[1, n - 1]` finishes the job.
   - If the last character is already the global maximum (`s[n - 1] == max`), sorting the prefix `[0, n - 2]` finishes the job.
   - In either case, return `1`.
5. **Evaluate Interior (2 Operations):**
   - If any occurrence of `min` or `max` lies strictly inside the string (between index `1` and `n - 2`), we can pull it to an outer boundary in 1 operation, then complete the sort in the 2nd operation. Return `2`.
6. **Worst-Case Inversion (3 Operations):**
   - If neither condition holds, the only remaining state is that all minimums are at index `n - 1` and all maximums are at index `0`. This requires 3 operations to untangle. Return `3`.

---

### Detailed Code Analysis

```java
class Solution {
    public int minOperations(String s) {
        int n = s.length();
        int min = s.charAt(0) - 'a';
        int max = s.charAt(0) - 'a';
        boolean sorted = true;
```
- **Lines 3–6:** Read the length `n` and initialize `min` and `max` using the integer value of the first character relative to `'a'`. Initialize a flag `sorted = true` to check if any work is required.

```java
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
                break;
            }
        }
        if (sorted) return 0;
```
- **Lines 7–13:** Traverse the string once to check if adjacent characters violate non-decreasing order. If no inversion is found, `sorted` remains `true`, and the function returns `0` operations immediately.

```java
        if (n == 2) return -1;
```
- **Line 14:** If `n == 2` and the string is not sorted, the only permitted subarray of length $n - 1$ has size $1$. Sorting a subarray of size $1$ does not rearrange elements, so sorting an unsorted pair like `"ba"` is impossible. Return `-1`.

```java
        for (int i = 1; i < n; i++) {
            min = Math.min(min, s.charAt(i) - 'a');
            max = Math.max(max, s.charAt(i) - 'a');
        }
```
- **Lines 15–18:** Find the global minimum and maximum characters across the entire string by scanning through indices `1` to `n - 1`.

```java
        if (s.charAt(0) - 'a' == min || s.charAt(n - 1) - 'a' == max) return 1;
```
- **Line 20:** If `s[0]` is already the minimum, sorting suffix `s[1...n-1]` puts everything in non-decreasing order. Alternatively, if `s[n - 1]` is the maximum, sorting prefix `s[0...n-2]` completes the sort. Return `1`.

```java
        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) - 'a' == min || s.charAt(i) - 'a' == max) return 2;
        }
        return 3;
    }
}
```
- **Lines 21–23:** Check the interior indices `[1, n - 2]`. If a `min` exists here, sorting `s[0...n-2]` moves `min` to index `0`, enabling a 2nd operation to sort `s[1...n-1]`. If a `max` exists here, sorting `s[1...n-1]` moves `max` to index `n - 1`, enabling a 2nd operation to sort `s[0...n-2]`. Return `2`.
- **Line 24:** If no `min` or `max` exists in the middle, then `s[0]` must be the maximum and `s[n - 1]` must be the minimum. Shuffling one to the middle takes 1 move, moving it to its final boundary takes a 2nd move, and sorting the remainder takes a 3rd move. Return `3`.

---

### Code

```java
class Solution {
    public int minOperations(String s) {
        int n = s.length();
        int min = s.charAt(0) - 'a';
        int max = s.charAt(0) - 'a';
        boolean sorted = true;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
                break;
            }
        }
        if (sorted) return 0;
        if (n == 2) return -1;

        for (int i = 1; i < n; i++) {
            min = Math.min(min, s.charAt(i) - 'a');
            max = Math.max(max, s.charAt(i) - 'a');
        }
        
        if (s.charAt(0) - 'a' == min || s.charAt(n - 1) - 'a' == max) return 1;

        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) - 'a' == min || s.charAt(i) - 'a' == max) return 2;
        }

        return 3;
    }
}
```

---

### Complexity
- **Time:** $\mathcal{O}(n)$, where $n$ is the length of the string. The algorithm performs at most three linear passes over the string:
  1. Checking if the string is already sorted ($\le n$ operations).
  2. Finding the minimum and maximum values ($n - 1$ operations).
  3. Scanning the interior characters ($n - 2$ operations).
  All passes run in linear time with $\mathcal{O}(1)$ operations per character.
- **Space:** $\mathcal{O}(1)$ auxiliary space. The algorithm only uses primitive integer and boolean variables (`n`, `min`, `max`, `sorted`, `i`) and does not allocate extra memory proportional to the input size.

---

## 🕵️‍♂️ Follow-up Questions

### 1. Can we combine the sorted check and the extremum search into a single pass?
**Answer:** Yes. In a single pass from `1` to `n - 1`, we can track whether `s[i] < s[i - 1]` while simultaneously updating `min` and `max`. If `sorted` remains true at the end of the pass, we return `0` immediately. This cuts the number of passes from three down to at most two without changing the asymptotic $\mathcal{O}(n)$ time complexity.

### 2. What would happen if the allowable sort operation had length $k$ where $k < n - 1$?
**Answer:** If $k < n - 1$, the problem turns into a graph reachability/permutation cycle problem. If $k = 2$ (adjacent swaps), the problem is equivalent to counting inversions or bubble sort swaps ($\mathcal{O}(n^2)$ operations). For general $k$, elements can only jump across windows of size $k$, which can lead to reachability barriers depending on modular arithmetic congruence of indices (similar to the Pancake Sorting or 15-puzzle problem).