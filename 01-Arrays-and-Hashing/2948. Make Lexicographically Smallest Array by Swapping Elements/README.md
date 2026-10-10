<h2><a href="https://leetcode.com/problems/make-lexicographically-smallest-array-by-swapping-elements">2948. Make Lexicographically Smallest Array by Swapping Elements</a></h2>

<p>You are given a <strong>0-indexed</strong> array of <strong>positive</strong> integers <code>nums</code> and a <strong>positive</strong> integer <code>limit</code>.</p>

<p>In one operation, you can choose any two indices <code>i</code> and <code>j</code> and swap <code>nums[i]</code> and <code>nums[j]</code> <strong>if</strong> <code>|nums[i] - nums[j]| &lt;= limit</code>.</p>

<p>Return <em>the <strong>lexicographically smallest array</strong> that can be obtained by performing the operation any number of times</em>.</p>

<p>An array <code>a</code> is lexicographically smaller than an array <code>b</code> if in the first position where <code>a</code> and <code>b</code> differ, array <code>a</code> has an element that is less than the corresponding element in <code>b</code>. For example, the array <code>[2,10,3]</code> is lexicographically smaller than the array <code>[10,2,3]</code> because they differ at index <code>0</code> and <code>2 &lt; 10</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,5,3,9,8], limit = 2
<strong>Output:</strong> [1,3,5,8,9]
<strong>Explanation:</strong> Apply the operation 2 times:
- Swap nums[1] with nums[2]. The array becomes [1,3,5,9,8]
- Swap nums[3] with nums[4]. The array becomes [1,3,5,8,9]
We cannot obtain a lexicographically smaller array by applying any more operations.
Note that it may be possible to get the same result by doing different operations.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [1,7,6,18,2,1], limit = 3
<strong>Output:</strong> [1,6,7,18,1,2]
<strong>Explanation:</strong> Apply the operation 3 times:
- Swap nums[1] with nums[2]. The array becomes [1,6,7,18,2,1]
- Swap nums[0] with nums[4]. The array becomes [2,6,7,18,1,1]
- Swap nums[0] with nums[5]. The array becomes [1,6,7,18,1,2]
We cannot obtain a lexicographically smaller array by applying any more operations.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> nums = [1,7,28,19,10], limit = 3
<strong>Output:</strong> [1,7,28,19,10]
<strong>Explanation:</strong> [1,7,28,19,10] is the lexicographically smallest array we can obtain because we cannot apply the operation on any two indices.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= limit &lt;= 10<sup>9</sup></code></li>
</ul>


---

# 🛍️ Make-Lexicographically-Smallest-Array-by-Swapping-Elements | Explained

## Approach 1: Sorting, Connected Components (Transitivity), and Greedy Placement

### Intuition
The problem allows us to swap any two elements $nums[i]$ and $nums[j]$ if $|nums[i] - nums[j]| \le limit$. 

A critical observation is the **transitive property of swaps**:
If element $A$ can swap with element $B$, and element $B$ can swap with element $C$, then $A$ can swap with $C$ indirectly through $B$ (e.g., swap $A$ with $B$, then swap the new position with $C$). 

**Real-World Analogy:**
Imagine a group of friends trading cards. Alice can only trade with Bob because their card values are close. Bob can trade with Charlie. Even though Alice and Charlie cannot trade directly, Alice can pass her card to Bob, who passes it to Charlie. Ultimately, all cards within this connected circle of friends can be shuffled and distributed however they like.

Because any numbers belonging to the same connected component can freely exchange positions among their original index locations, we can solve this greedily:
1. Identify all elements that belong to the same component (group).
2. For each group, we want the smallest numbers placed at the earliest positions where elements of that group originally appeared.

Sorting the numbers makes finding these connected components trivial: consecutive numbers in the sorted order with a difference $\le limit$ must belong to the same group.

---

### Algorithm Visualized

```mermaid
flowchart TD
    subgraph Step 1: Input & Sort
        A["Original nums: [1, 5, 3, 9, 8], limit = 2"] --> B["Clone & Sort: [1, 3, 5, 8, 9]"]
    end

    subgraph Step 2: Group by Adjacency Difference <= limit
        B --> C{"Check sorted[i] - sorted[i-1] <= limit"}
        C -->|1, 3, 5: diff <= 2| D["Group 0: [1, 3, 5]"]
        C -->|8, 9: diff <= 2, but 8 - 5 > 2| E["Group 1: [8, 9]"]
    end

    subgraph Step 3: Map Values to Group ID
        D --> F["Map: 1->0, 3->0, 5->0"]
        E --> G["Map: 8->1, 9->1"]
    end

    subgraph Step 4: Greedy Reconstruction in Original Indices
        H["Index 0: nums[0] = 1 (Group 0) -> pop 1"]
        I["Index 1: nums[1] = 5 (Group 0) -> pop 3"]
        J["Index 2: nums[2] = 3 (Group 0) -> pop 5"]
        K["Index 3: nums[3] = 9 (Group 1) -> pop 8"]
        L["Index 4: nums[4] = 8 (Group 1) -> pop 9"]
    end

    F --> H
    G --> K
    H --> I --> J --> K --> L
    L --> M["Result: [1, 3, 5, 8, 9]"]
```

---

### Approach

1. **Clone and Sort:** Create a copy of `nums` and sort it in ascending order.
2. **Cluster into Groups:** 
   - Iterate through the sorted array. If the difference between the current element and the previous element is greater than `limit` (or if it is the first element), start a new group.
   - Otherwise, append the current element to the current group.
   - Map each value to its assigned `group id` using a hash map.
3. **Greedy Reassignment:**
   - Maintain a pointer/index `idx[group_id]` tracking which element from each group has been consumed.
   - Iterate through the original `nums` array from left to right.
   - For each number `nums[i]`, look up its `group id`. Replace `nums[i]` with the next smallest unused number from that group, and increment the group's pointer.

---

### Detailed Code Analysis

#### 1. Sorting a Clone
```java
int []sortedArray = nums.clone();
Arrays.sort(sortedArray);
```
- We clone `nums` so we preserve the original order of elements and their indices.
- Sorting takes $O(N \log N)$ and aligns numbers so that elements with differences $\le limit$ are immediately adjacent.

#### 2. Group Partitioning and Lookup Construction
```java
List<List<Integer>> groups = new ArrayList<>();
Map<Integer, Integer> map = new HashMap<>();
int id = -1;
for(int i = 0; i < sortedArray.length; i++) {
    if(i == 0 || sortedArray[i] - sortedArray[i - 1] > limit) {
        groups.add(new ArrayList<>());
        id++;
    }
    groups.get(id).add(sortedArray[i]);
    map.put(sortedArray[i], id);
}
```
- `id` tracks the current connected component index.
- If `sortedArray[i] - sortedArray[i - 1] > limit`, the chain is broken; we must start a new group by incrementing `id` and initializing a new list.
- Each element in `groups.get(id)` is naturally added in non-decreasing order because `sortedArray` is sorted.
- `map.put(sortedArray[i], id)` records the group identifier for each value. Duplicate values overwrite with the same `id`, which is valid since identical values belong to the exact same group.

#### 3. Reconstructing the Smallest Array
```java
int idx[] = new int[groups.size()];
for(int i = 0; i < nums.length; i++) {
    int curr = map.get(nums[i]);
    nums[i] = groups.get(curr).get(idx[curr]);
    idx[curr]++;
}
return nums;
```
- `idx` acts as an array of heads/pointers for each group (simulating a Queue without queue overhead).
- Traversing `nums` from left to right ensures we visit indices greedily (from most significant index to least significant).
- `nums[i]` is reassigned to `groups.get(curr).get(idx[curr])`, which is guaranteed to be the smallest remaining available value for that component.
- Incrementing `idx[curr]` ensures each value is used exactly once.

---

### Code

```java
class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int []sortedArray = nums.clone();
        Arrays.sort(sortedArray);
        List<List<Integer>> groups = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        int id = -1;
        
        for(int i = 0; i < sortedArray.length; i++){
            if(i == 0 || sortedArray[i] - sortedArray[i - 1] > limit){
                groups.add(new ArrayList<>());
                id++;
            }
            groups.get(id).add(sortedArray[i]);
            map.put(sortedArray[i], id);
        }
        
        int idx[] = new int[groups.size()];
        for(int i = 0; i < nums.length; i++){
            int curr = map.get(nums[i]);
            nums[i] = groups.get(curr).get(idx[curr]);
            idx[curr]++;
        }
        
        return nums;
    }
}
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}(N \log N)$
  - Cloning the array takes $\mathcal{O}(N)$.
  - Sorting `sortedArray` takes $\mathcal{O}(N \log N)$.
  - Building the groups and hash map requires iterating through `sortedArray` once: $\mathcal{O}(N)$ average time (assuming $\mathcal{O}(1)$ average hash map operations).
  - Rebuilding `nums` requires a single pass of length $N$: $\mathcal{O}(N)$.
  - **Total Time:** $\mathcal{O}(N \log N)$.

- **Space Complexity:** $\mathcal{O}(N)$
  - `sortedArray` consumes $\mathcal{O}(N)$ space.
  - `groups` stores all $N$ elements across the sublists: $\mathcal{O}(N)$ space.
  - `map` stores up to $N$ unique entries: $\mathcal{O}(N)$ space.
  - `idx` array has size equal to the number of groups, bounded by $\mathcal{O}(N)$.
  - **Total Space:** $\mathcal{O}(N)$.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

1. **Why does an array pointer `idx[]` work instead of using `Queue<Integer>` for each group?**
   - Using a primitive array `idx[]` to index into the `ArrayList` acts just like dequeueing from a `Queue`, but avoids the memory overhead and boxing/unboxing costs associated with Java's `LinkedList` or `ArrayDeque`.

2. **Can this problem be solved using Disjoint Set Union (DSU / Union-Find)?**
   - Yes, DSU can group indices together based on value differences. However, checking pairs naively takes $\mathcal{O}(N^2)$. Sorting is still required to only union adjacent elements within `limit`, making the linear grouping approach used here simpler, faster, and having less constant-factor overhead than DSU.