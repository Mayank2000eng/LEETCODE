# 1012. Numbers With Repeated Digits

## Problem Description

Given an integer `n`, find the total number of positive integers in the range `[1, n]` that contain **at least one repeated digit**.

### Examples

* **Example 1:**
  * **Input:** `n = 20`
  * **Output:** `1`
  * **Explanation:** The only positive number $\le 20$ with at least 1 repeated digit is `11`.
* **Example 2:**
  * **Input:** `n = 100`
  * **Output:** `10`
  * **Explanation:** The numbers are `11, 22, 33, 44, 55, 66, 77, 88, 99`, and `100`.
* **Example 3:**
  * **Input:** `n = 1000`
  * **Output:** `262`

### Constraints
* $1 \le n \le 10^9$

---

## My Solution Approach

To solve this efficiently within the given constraints ($n \le 10^9$), a brute-force check for every number will easily time out. Instead, I implemented a **Digit Dynamic Programming (Digit DP)** approach with memoization.

Instead of tracking unique numbers and subtracting them from `n` at the end, my code explicitly passes states down the recursion tree to track **both** the total valid numbers formed so far and how many of them have already encountered a duplicate digit.

### Key Concepts & State Representation

I break the integer `n` into an array of its constituent digits. The state of my DP is tracked using a 6-dimensional memoization array: `dp[i][lead][tight][rep][have][type]`.

* **`i`**: The current digit index we are processing (from left to right).
* **`lead`**: A boolean flag ($1$ or $0$) indicating if we are still processing leading zeros. If `lead == 1`, picking `0` keeps the number in a leading-zero state (i.e., we haven't actually started forming the digits of our number yet).
* **`tight`**: A boolean flag ($1$ or $0$) indicating whether the digits we are placing are constrained by the prefix of `n`. If `tight == 1`, our choice for the current digit is bounded up to `arr[i]`. If `tight == 0`, we can freely choose any digit from `0` to `9`.
* **`rep`**: A bitmask (an integer up to $2^{10} - 1 = 1023$) representing the set of digits we have already placed so far. For example, if we have placed `1` and `3`, the mask will be `(1 << 1) | (1 << 3)`.
* **`have`**: A boolean flag ($1$ or $0$) acting as a historical indicator. It becomes `1` the moment we place a digit that has already been placed before (i.e., a duplicate is found).
* **`type`**: A 2-element array tracking two independent values:
  * **`index 0`**: The total count of valid numbers formed under this state context.
  * **`index 1`**: The count of numbers that explicitly contain at least one repeated digit.

---


## Code

```java
class Solution {
    static Integer[][][][][][] dp;
    
    public int numDupDigitsAtMostN(int n) {
        int[] arr = create_arr(n);
        dp = new Integer[arr.length][2][2][1025][2][2];
        int[] ans = find(arr, 0, 1, 1, 0, 0);
        return ans[1];
    }
    
    static int[] create_arr(int num){
        int n = num;
        int c = 0;
        while(n > 0){
            c++;
            n /= 10;
        }
        int[] arr = new int[c];
        int i = c - 1;
        while(num > 0){
            arr[i--] = num % 10;
            num /= 10;
        }
        return arr;
    }
    
    static int[] find(int[] arr, int i, int lead, int tight, int rep, int have){
        if(i == arr.length){
            if(lead == 1) return new int[]{0, 0};
            return new int[]{1, have};
        }
        
        if(dp[i][lead][tight][rep][have][0] != null) 
            return new int[]{dp[i][lead][tight][rep][have][0], dp[i][lead][tight][rep][have][1]};
            
        int u = 9;
        if(tight == 1)
            u = arr[i];
            
        int[] ans = new int[2];
        int[] tem = new int[2];
        
        for(int i1 = 0; i1 <= u; i1++){
            int c = 0;
            if(have == 1) c = 1;
            int t1 = 0;
            if(tight == 1 && i1 == u) t1 = 1;
            
            if(lead == 1 && i1 == 0){
                tem = find(arr, i + 1, 1, 0, rep, c);
            }else{
                if((rep & (1 << i1)) != 0)
                    c = 1;
                tem = find(arr, i + 1, 0, t1, rep | (1 << i1), c);
            }
            ans[0] += tem[0];
            ans[1] += tem[1];
        }
        dp[i][lead][tight][rep][have][0] = ans[0];
                dp[i][lead][tight][rep][have][1] = ans[1];
                return ans;
    }
 }
```
## Detailed Code Walkthrough

### 1. Preprocessing (`create_arr`)

This converts the integer `n` into an array of individual digits (`arr`), allowing us to easily access the tight bounds at each position `i` from most significant digit to least significant digit.

### 2. The Recurrence Logic (`find`)

* **Base Case:** When we reach the end of the digit array (`i == arr.length`), we check if it's a valid positive integer.
  * If `lead == 1`, it means we only placed zeros (e.g., `0000`), which represents the number `0`. Since we only care about positive integers $\ge 1$, we return `{0, 0}`.
  * Otherwise, we return `{1, have}`. The `1` means we successfully constructed exactly one valid number, and `have` indicates whether it contains repeated digits (`1` if true, `0` if false).

* **Determining Upper Bound:** If `tight == 1`, the maximum digit we can place at position `i` is restricted to `arr[i]`. If `tight == 0`, we can go all the way up to `9`.

* **Transitions:** We iterate through all possible digit choices `i1` from `0` to `u`.
  * **Tight Propagation:** The new tight state `t1` stays restricted (`1`) if and only if the current state was restricted and we choose the maximum available digit (`i1 == u`).
  * **Handling Leading Zeros:** If `lead == 1` and we choose `i1 = 0`, the next state remains under `lead = 1`. We do not add this zero to our bitmask `rep` because leading zeros do not count as repeated digits in a number (e.g., `012` is just `12`, which has no duplicate digits).
  * **Duplicate Detection:** If it's a meaningful digit choice, we check if `i1` already exists in our mask using bitwise AND: `(rep & (1 << i1)) != 0`. If it does, we flag `c = 1`, meaning a duplicate digit has been detected. We also add the digit to our mask via bitwise OR: `rep | (1 << i1)`.
  * **Accumulation:** The returned values from the recursive branches are aggregated into `ans[0]` (total numbers) and `ans[1]` (numbers with repeated digits).

---

## Complexity Analysis

### Time Complexity

* **State Space:** The size of our DP table is governed by the state parameters:
  * `i`: $\approx 10$ positions (since $n \le 10^9$)
  * `lead`: $2$ states ($0$ or $1$)
  * `tight`: $2$ states ($0$ or $1$)
  * `rep`: $2^{10} = 1024$ combinations for the digit mask
  * `have`: $2$ states ($0$ or $1$)
* Total unique states $\approx 10 \times 2 \times 2 \times 1024 \times 2 = 81,920$ states.
* For each state, we iterate through at most $10$ possible digits ($0$ to $9$).
* Therefore, the maximum number of operations is roughly $81,920 \times 10 \approx 8.2 \times 10^5$, which executes in just a few milliseconds.
* **Final Time Complexity:** $O(\log_{10}(n) \times 2^D \times D)$ where $D = 10$ (the number of available digits). This is safely $O(1)$ relative to `n`.

### Space Complexity

* **Memory Overhead:** The space is dominated by the size of our multi-dimensional array `dp`.
  * **Size:** $10 \times 2 \times 2 \times 1025 \times 2 \times 2 \times 2$ (since the final dimension holds 2 integers).
* **Final Space Complexity:** $O(\log_{10}(n) \times 2^D)$ which translates to a constant memory footprint that easily fits well within standard heap limits.
        
