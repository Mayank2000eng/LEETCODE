# 3911. K-th Smallest Remaining Even Integer in Subarray Queries

## Problem Understanding

You are given a strictly increasing array of integers and multiple queries. Each query specifies a subarray range and a target position `k`.

The goal is to determine the **k-th positive even number** from the infinite sequence of even numbers:

```
2, 4, 6, 8, 10, ...
```

after removing any even numbers that are present in the specified subarray.

---

# Intuition & Approach

To efficiently process multiple queries without simulating the infinite sequence, I utilized a combination of **Prefix Sum arrays** and **Nested Binary Search**.

### Prefix Sums

By precomputing the counts of even numbers up to every index, we can answer how many even numbers fall within any subarray in **O(1)** time.

### Binary Search on Answer

The sequence of even numbers is strictly increasing, meaning the count of valid remaining even numbers up to any given value is monotonic. This makes it a perfect candidate for **Binary Search**.

### Secondary Binary Search

While guessing a potential answer, we need to know how many removed elements from the subarray are smaller than or equal to our guess.

A secondary binary search directly on the subarray provides this count quickly, allowing us to find the exact offset required to locate the k-th remaining even number.

---

# Step-by-Step Explanation

### 1. Prefix Array Initialization

We loop through the `nums` array to construct `arr`.

A variable `c` tracks the cumulative count of even integers seen so far, and:

```java
arr[i] = c;
```

stores the prefix count of even numbers.

---

### 2. Processing Queries

For each query, we extract:

- `l` → left index
- `r` → right index
- `k` → required position

We also define:

```java
a = nums[l];
la = nums[r];
```

---

### 3. Base Calculation

Without removing anything, the k-th positive even number would simply be:

```java
kth = 2 + (k - 1) * 2;
```

---

### 4. Conditional Check

If

```java
kth >= nums[l]
```

then the desired even number may lie inside the removal range and therefore might have been deleted.

In that case we invoke:

```java
find(...)
```

to compute the adjusted answer.

---

## Primary Binary Search (`find`)

We binary search over all possible positive even numbers.

```java
l = 2
h = Integer.MAX_VALUE
```

For every midpoint:

### Step 1

Compute

```java
mid
```

which represents the current candidate answer.

---

### Step 2

If

```java
mid >= a
```

we call

```java
find2(...)
```

to locate the largest index whose value is

```
<= mid
```

inside the query range.

---

### Step 3

Using that index, we compute:

```java
abs
```

which represents how many removed even numbers are

```
<= mid
```

inside the query window.

```java
abs = arr[d];

if (l1 > 0)
    abs -= arr[l1 - 1];
```

---

### Step 4

The total positive even numbers up to `mid` are

```java
((mid - 2) / 2) + 1
```

After removing deleted even numbers,

```java
count = totalEvenNumbers - abs;
```

---

### Step 5

Compare `count` with `k`.

- If

```java
count < k
```

search higher.

```java
l = mid + 2;
```

- If

```java
count > k
```

search lower.

```java
h = mid - 2;
```

- Otherwise we found exactly `k` remaining even numbers.

Now verify that `mid` itself wasn't removed.

If

```java
mid == nums[ind]
```

then this candidate is invalid, so continue searching lower.

Otherwise,

```java
mid
```

is the required answer.

---

## Secondary Binary Search (`find2`)

This helper function performs a binary search inside the query range:

```java
[l, r]
```

and returns the largest index satisfying

```
nums[index] <= mid
```

This allows us to quickly determine how many removed values lie before or at `mid`.

---

# Complexity Analysis

### Time Complexity

- Building the prefix array:

```
O(N)
```

- Each query performs:

  - One binary search over the answer space:

  ```
  O(log M)
  ```

  where `M = Integer.MAX_VALUE`.

  - Each iteration performs another binary search over the array:

  ```
  O(log N)
  ```

Overall complexity:

```
O(N + Q × log(M) × log(N))
```

---

### Space Complexity

- Prefix sum array:

```
O(N)
```

- Answer array:

```
O(Q)
```

Overall:

```
O(N + Q)
```

---

# Code (Java)

```java
class Solution {
    public int[] kthRemainingInteger(int[] nums, int[][] queries) {
        int[] ans = new int[queries.length];
        int[] arr = new int[nums.length];
        int c = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 0) {
                c++;
            }
            arr[i] = c;
        }

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            int k = queries[i][2];

            int a = nums[l];
            int la = nums[r];

            int kth = 2 + (k - 1) * 2;

            if (kth >= nums[l]) {
                int dig = find(arr, k, a, la, nums, l, r);
                kth = dig;
            }

            ans[i] = kth;
        }

        return ans;
    }

    static int find(int[] arr, int k, int a, int la, int[] nums, int l1, int r1) {
        int l = 2, h = Integer.MAX_VALUE;
        int ans = -1;

        while (l <= h) {
            int mid = l + (h - l) / 2;

            int abs = 0;
            int ind = -1;

            if (mid >= a) {
                int d = find2(nums, mid, l1, r1);

                abs = arr[d];

                if (l1 > 0)
                    abs -= arr[l1 - 1];

                ind = d;
            }

            int count = ((mid - 2) / 2) + 1;
            count -= abs;

            if (count < k) {
                l = mid + 2;
            } else if (k < count) {
                h = mid - 2;
            } else {
                if (ind != -1 && mid == nums[ind]) {
                    h = mid - 2;
                } else {
                    ans = mid;
                    break;
                }
            }
        }

        return ans;
    }

    static int find2(int[] nums, int mid, int l, int r) {
        int ans = 0;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (nums[m] <= mid) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return ans;
    }
}
```
