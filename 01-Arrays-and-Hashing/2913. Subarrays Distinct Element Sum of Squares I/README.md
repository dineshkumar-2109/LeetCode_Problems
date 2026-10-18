<h2><a href="https://leetcode.com/problems/subarrays-distinct-element-sum-of-squares-i">2913. Subarrays Distinct Element Sum of Squares I</a></h2>

<p>You are given a <strong>0-indexed </strong>integer array <code>nums</code>.</p>

<p>The <strong>distinct count</strong> of a subarray of <code>nums</code> is defined as:</p>

<ul>
	<li>Let <code>nums[i..j]</code> be a subarray of <code>nums</code> consisting of all the indices from <code>i</code> to <code>j</code> such that <code>0 &lt;= i &lt;= j &lt; nums.length</code>. Then the number of distinct values in <code>nums[i..j]</code> is called the distinct count of <code>nums[i..j]</code>.</li>
</ul>

<p>Return <em>the sum of the <strong>squares</strong> of <strong>distinct counts</strong> of all subarrays of </em><code>nums</code>.</p>

<p>A subarray is a contiguous <strong>non-empty</strong> sequence of elements within an array.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,2,1]
<strong>Output:</strong> 15
<strong>Explanation:</strong> Six possible subarrays are:
[1]: 1 distinct value
[2]: 1 distinct value
[1]: 1 distinct value
[1,2]: 2 distinct values
[2,1]: 2 distinct values
[1,2,1]: 2 distinct values
The sum of the squares of the distinct counts in all subarrays is equal to 1<sup>2</sup> + 1<sup>2</sup> + 1<sup>2</sup> + 2<sup>2</sup> + 2<sup>2</sup> + 2<sup>2</sup> = 15.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [1,1]
<strong>Output:</strong> 3
<strong>Explanation:</strong> Three possible subarrays are:
[1]: 1 distinct value
[1]: 1 distinct value
[1,1]: 1 distinct value
The sum of the squares of the distinct counts in all subarrays is equal to 1<sup>2</sup> + 1<sup>2</sup> + 1<sup>2</sup> = 3.</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 100</code></li>
</ul>


---

# 🛍️ Subarrays-Distinct-Element-Sum-of-Squares-I | Explained

## Approach 1: Incremental Subarray Enumeration with Hash Set
### Intuition
Imagine you are walking down an aisle in a grocery store where each shelf contains an item. You pick a starting shelf $i$ with an empty basket. As you step forward shelf by shelf to position $j$, you place whatever item you find into your basket. 

Because your basket automatically ignores duplicates (like a magical compartment that only counts distinct product types), you can look inside at any step $j$, see the number of unique items you have gathered so far, square that count, and add it to your score. Once you reach the end of the aisle, you clear your basket, step to the next starting shelf $i + 1$, and repeat the process.

This approach works because every contiguous subarray can be uniquely identified by its start boundary $i$ and end boundary $j$. By incrementally expanding $j$ from $i$ to $n-1$, we avoid recalculating distinct elements from scratch for every subarray, taking advantage of the state built from the previous step.

### Algorithm Visualized

```mermaid
flowchart TD
    Start([Start: sumCounts]) --> Init[Initialize ans = 0, n = nums.size]
    Init --> OuterLoop{i < n ?}
    
    OuterLoop -- Yes --> NewSet[Create empty HashSet 's']
    OuterLoop -- No --> ReturnAns([Return ans])
    
    NewSet --> InnerLoop{j < n ?}
    
    InnerLoop -- Yes --> AddElem[Add nums[j] to HashSet 's']
    AddElem --> CalcSize[val = s.size]
    CalcSize --> Accumulate[ans += val * val]
    Accumulate --> NextJ[j++]
    NextJ --> InnerLoop
    
    InnerLoop -- No --> NextI[i++]
    NextI --> OuterLoop
```

### Approach
1. **Initialize Accumulator:** Maintain an integer `ans = 0` to store the cumulative sum of squares of distinct counts across all subarrays.
2. **Fix Left Boundary ($i$):** Iterate the starting pointer `i` from index `0` to `n - 1`.
3. **Reset State:** For each new starting position `i`, initialize a new `HashSet<Integer>`. This set tracks unique numbers encountered in the current subarray family starting at `i`.
4. **Expand Right Boundary ($j$):** Iterate the ending pointer `j` from `i` to `n - 1`.
   - Insert `nums.get(j)` into the set.
   - The number of distinct elements in the subarray `nums[i...j]` is simply `s.size()`.
   - Square this count (`val * val`) and add it to `ans`.
5. **Return Result:** Once all pairs $(i, j)$ have been evaluated, return `ans`.

### Detailed Code Analysis

- **Lines 2–4:**
  ```java
  public int sumCounts(List<Integer> nums) {
      int ans = 0;
      int n = nums.size();
  ```
  We initialize our running total `ans` and capture the length of the list `n`. Using primitive `int` is sufficient here because the constraints for Version I ($n \le 100$) ensure that the final result will not exceed standard 32-bit signed integer limits.

- **Line 5:**
  ```java
  for (int i = 0; i < n; i++) {
  ```
  The outer loop defines the starting index `i` of the subarray. Every possible starting point from $0$ to $n-1$ is considered.

- **Line 6:**
  ```java
  Set<Integer> s = new HashSet<>();
  ```
  Creating the `HashSet` inside the outer loop is crucial. It guarantees that for every new starting point `i`, we begin with a clean slate. A `HashSet` provides average $O(1)$ time complexity for insertions and lookups.

- **Lines 7–8:**
  ```java
  for (int j = i; j < n; j++) {
      s.add(nums.get(j));
  ```
  Instead of recomputing the distinct count for subarray `[i...j]` by rescanning from $i$ to $j$ (which would cost $O(n^3)$ total time), the inner loop extends the previous subarray `[i...j-1]` by just one element `nums.get(j)`. The set handles deduplication automatically.

- **Lines 9–10:**
  ```java
  int val = s.size();
  ans += (val * val);
  ```
  `s.size()` retrieves the count of distinct elements in $O(1)$ time. We compute the square of this count (`val * val`) and immediately accumulate it into `ans`.

- **Lines 13–14:**
  ```java
      }
  }
  return ans;
  ```
  After checking all $\frac{n(n + 1)}{2}$ contiguous subarrays, we return the accumulated result.

### Code
```java
class Solution {
    public int sumCounts(List<Integer> nums) {
        int ans = 0;
        int n = nums.size();
        for (int i = 0; i < n; i++) {
            Set<Integer> s = new HashSet<>();
            for (int j = i; j < n; j++) {
                s.add(nums.get(j));
                int val = s.size();
                ans += (val * val);
            }
        }
        return ans;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(n^2)$ on average.  
  There are $\frac{n(n + 1)}{2}$ total subarrays. For each step of the inner loop, we invoke `s.add()` and `s.size()`, which execute in $\mathcal{O}(1)$ average time. Therefore, the total running time scales quadratically with $n$. Given $n \le 100$, the operations are around $\approx 5,000$, well within the standard 1-second limit ($10^8$ operations).
- **Space Complexity:** $\mathcal{O}(n)$ auxiliary space.  
  In the worst-case scenario where all elements in `nums` are unique, the `HashSet` stores up to $n$ integers during an outer loop iteration. Garbage collection reclaims this memory across iterations, maintaining an upper bound of $\mathcal{O}(n)$ extra space at any given moment.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

1. **How would you scale this solution if $n \le 10^5$ (as in Subarrays Distinct Element Sum of Squares II)?**
   - The $\mathcal{O}(n^2)$ approach will result in a Time Limit Exceeded (TLE) verdict. We must transition from an iterative brute-force approach to a dynamic contribution model using a **Segment Tree with Lazy Propagation** or a **Fenwick Tree**. By tracking the last seen index of each element, we can compute how adding `nums[j]` changes the distinct counts of all ending subarrays ending at $j$ in $\mathcal{O}(n \log n)$ time.

2. **Can we optimize constant factor performance for this $\mathcal{O}(n^2)$ approach in Java?**
   - Yes. `HashSet<Integer>` introduces overhead due to autoboxing (`int` to `Integer`), object allocations, and hash collisions. If the range of values in `nums` is bounded (e.g., $1 \le \text{nums}[i] \le 100$), a fixed-size `boolean[] visited` array or a bitset can replace the `HashSet`, cutting memory allocations down to zero within the inner loops and speeding up runtime significantly.