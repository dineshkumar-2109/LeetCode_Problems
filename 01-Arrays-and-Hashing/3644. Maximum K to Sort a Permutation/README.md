<h2><a href="https://leetcode.com/problems/maximum-k-to-sort-a-permutation">3644. Maximum K to Sort a Permutation</a></h2>

<p>You are given an integer array <code>nums</code> of length <code>n</code>, where <code>nums</code> is a <strong><span data-keyword="permutation-array" class=" cursor-pointer relative text-dark-blue-s text-sm"><button type="button" aria-haspopup="dialog" aria-expanded="false" aria-controls="radix-_r_t_" data-state="closed" class="" fdprocessedid="wpbb3">permutation</button></span></strong> of the numbers in the range <code>[0..n - 1]</code>.</p>

<p>You may swap elements at indices <code>i</code> and <code>j</code> <strong>only if</strong> <code>nums[i] AND nums[j] == k</code>, where <code>AND</code> denotes the bitwise AND operation and <code>k</code> is a <strong>non-negative</strong> integer.</p>

<p>Return the <strong>maximum</strong> value of <code>k</code> such that the array can be sorted in <strong>non-decreasing</strong> order using any number of such swaps. If <code>nums</code> is already sorted, return 0.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [0,3,2,1]</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong></p>

<p>Choose <code>k = 1</code>. Swapping <code>nums[1] = 3</code> and <code>nums[3] = 1</code> is allowed since <code>nums[1] AND nums[3] == 1</code>, resulting in a sorted permutation: <code>[0, 1, 2, 3]</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [0,1,3,2]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>Choose <code>k = 2</code>. Swapping <code>nums[2] = 3</code> and <code>nums[3] = 2</code> is allowed since <code>nums[2] AND nums[3] == 2</code>, resulting in a sorted permutation: <code>[0, 1, 2, 3]</code>.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [3,2,1,0]</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong></p>

<p>Only <code>k = 0</code> allows sorting since no greater <code>k</code> allows the required swaps where <code>nums[i] AND nums[j] == k</code>.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n == nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= nums[i] &lt;= n - 1</code></li>
	<li><code>nums</code> is a permutation of integers from <code>0</code> to <code>n - 1</code>.</li>
</ul>


---

# 🛍️ Maximum-K-to-Sort-a-Permutation | Explained

## Approach 1: Bitwise AND of Displaced Elements (Cumulative Mask Reduction)

### Intuition
Think of sorting this permutation like a secure facility where items can only be swapped between rooms if both items hold a common set of security clearances (the bits in integer $k$). 

If an item is already in its designated room (`nums[i] == i`), it never needs to be touched or moved. However, any item that is in the wrong room (`nums[i] != i`) **must** be moved at least once. For two numbers $A$ and $B$ to swap, the rule requires $A \ \& \ B = k$. A fundamental property of the bitwise AND operator is that if $A \ \& \ B = k$, then every single bit that is set to `1` in $k$ **must** also be set to `1` in both $A$ and $B$. 

Extending this across all displaced elements: every element that needs to be moved must contain all the set bits of $k$. Therefore, $k$ cannot have any bit that is missing from *any* misplaced element. To maximize $k$, we simply find the intersection of all bits shared by every misplaced element, which is the cumulative bitwise AND of all elements where `nums[i] != i`.

### Algorithm Visualized

```mermaid
flowchart TD
    Start([Start: nums array]) --> Init[Initialize k = -1]
    Init --> Loop{i < nums.length?}
    
    Loop -- Yes --> CheckMatch{nums[i] == i?}
    CheckMatch -- Yes (Correct Position) --> NextIter[i++]
    CheckMatch -- No (Displaced) --> ApplyAND[k = k & nums[i]]
    
    ApplyAND --> CheckZero{k == 0?}
    CheckZero -- Yes --> EarlyExit([Return 0: No common bits remaining])
    CheckZero -- No --> NextIter
    
    NextIter --> Loop
    Loop -- No --> ReturnResult([Return Math.max k, 0])
```

### Approach
1. **Initialize the Bitmask:** Start with $k = -1$. In two's complement binary representation, $-1$ has all bits set to `1` (`0xFFFFFFFF`), making it the neutral identity element for the bitwise AND operation ($x \ \& \ -1 = x$).
2. **Filter and Accumulate:** Iterate through each element of the array from index `0` to `n - 1`:
   - If `nums[i] == i`, the element is already in its correct sorted position and requires no swaps. Skip it.
   - If `nums[i] != i`, the element must participate in at least one swap. Update $k$ by performing `k = k & nums[i]`.
3. **Prune Early:** If at any point $k$ becomes `0`, return `0` immediately. Because the bitwise AND operation is monotonically non-increasing (it can only turn `1`s to `0`s, never `0`s to `1`s), once $k$ hits $0$, it can never recover.
4. **Handle Edge Cases:** If the array was already sorted from the beginning, no elements enter the bitwise AND step, leaving $k = -1$. Return `Math.max(k, 0)` to guarantee the result is non-negative ($0$).

### Detailed Code Analysis

```java
class Solution {
    public int sortPermutation(int[] nums) {
        // Line 3: k is initialized to -1 (all 32 bits set to 1).
        // This acts as the identity element for bitwise AND.
        int k = -1;
        
        // Line 4: Iterate through all elements in the permutation.
        for (int i = 0; i < nums.length; i++) {
            // Line 5-7: Check if the current element is already in place.
            // Elements that are already sorted do not participate in swaps.
            if (nums[i] == i) {
                continue;
            }
            
            // Line 8: Intersect the set bits of k with the misplaced element nums[i].
            k = k & nums[i];
            
            // Line 9-11: Early exit optimization.
            // If all bits have been wiped out, k cannot grow back. 
            // The maximum possible value is definitively 0.
            if (k == 0) {
                return 0;
            }
        }
        
        // Line 15: If the array is already sorted, k remains -1.
        // Math.max(-1, 0) handles this by safely returning 0.
        return Math.max(k, 0);
    }
}
```

### Code
```java
class Solution {
    public int sortPermutation(int[] nums) {
        int k = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i) {
                continue;
            }
            k = k & nums[i];
            if (k == 0) {
                return 0;
            }
        }
        return Math.max(k, 0);
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of `nums`. We scan the array in a single pass. The early exit condition (`k == 0`) frequently terminates execution well before inspecting all $N$ elements in arbitrary inputs.
- **Space Complexity:** $\mathcal{O}(1)$. The algorithm operates in-place without allocating auxiliary data structures, using only a single primitive integer tracker `k`.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

1. **What happens if `nums` contains arbitrary values rather than a $0$-indexed permutation from $0$ to $n-1$?**
   - *Answer:* If `nums` contains arbitrary values, checking `nums[i] == i` is no longer valid. You would first need to create a sorted version of `nums` (or map values to their target sorted indices) to identify which elements are displaced relative to their target positions before applying the cumulative bitwise AND.

2. **Why does initializing $k = -1$ work regardless of the bit-width of integers in Java?**
   - *Answer:* In Java, integer types utilize two's complement representation. The value `-1` is represented as all `1` bits in binary (`32` ones for `int`). Since $X \ \& \ 1 = X$ for every individual bit, `-1` serves as the universal identity element for the bitwise AND operation across any number of bits.