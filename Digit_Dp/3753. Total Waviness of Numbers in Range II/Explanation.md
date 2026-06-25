
## 1. Problem Definition: What is "Waviness"?

The problem asks us to find the sum of "waviness" for all integers within a given range `[num1, num2]`. 

### Defining a Wave/Wiggle Point
An individual number's total waviness is determined by counting how many times its digits change direction between strictly increasing and strictly decreasing. A digit at index $i$ (where $0 < i < \text{length} - 1$) contributes **1** to the number's waviness if it forms a local peak or a local valley with its immediate neighbors:

1. **Local Peak (High Wave):** The current digit is strictly greater than both its predecessor and its successor.
   $$d_{i-1} < d_i > d_{i+1}$$
2. **Local Valley (Low Wave):** The current digit is strictly smaller than both its predecessor and its successor.
   $$d_{i-1} > d_i < d_{i+1}$$

### Examples
* **Number:** `13241`
  * Digits: `[1, 3, 2, 4, 1]`
  * Index 1 (`3`): $1 < 3 > 2 \rightarrow$ **Peak (+1)**
  * Index 2 (`2`): $3 > 2 < 4 \rightarrow$ **Valley (+1)**
  * Index 3 (`4`): $2 < 4 > 1 \rightarrow$ **Peak (+1)**
  * **Total Waviness for 13241 = 3**

* **Number:** `12345`
  * Digits: `[1, 2, 3, 4, 5]`
  * No peaks or valleys exist because the sequence is monotonically increasing.
  * **Total Waviness for 12345 = 0**

---

## 2. Solution Strategy: Digit DP

Iterating through every number from `num1` to `num2` one by one is far too slow ($O(N)$ complexity) when the numbers can be as large as $10^{10}$. To solve this efficiently, we use **Digit Dynamic Programming (Digit DP)**.

### Range Query Principle (Prefix Subtraction)
Instead of counting directly within the interval `[num1, num2]`, we calculate the total waviness for all numbers from `0` to `X` using a function $F(X)$. The answer for the range is then computed as:
$$\text{Total Waviness} = F(\text{num2}) - F(\text{num1} - 1)$$

### State Representation
The recursive function `find(i, tight, prev, prev_l)` builds valid numbers digit-by-digit from left to right. The state is defined by five parameters:

1. `i`: The current digit index we are placing (from $0$ to $\text{length}-1$).
2. `tight`: A boolean flag ($1$ or $0$). If $1$, the digits we place are restricted by the prefix of the upper bound array `arr`. If $0$, we can safely place any digit from $0$ to $9$.
3. `prev`: The digit chosen at index $i-1$. (Takes values $-1$ to $9$; $-1$ represents leading zeros).
4. `prev_l`: The digit chosen at index $i-2$. (Takes values $-1$ to $9$; $-1$ represents leading zeros).

### DP Memoization Array
The memoization table stores two pieces of information for every state to avoid redundant calculations:
* `dp[i][tight][prev][prev_l][0]`: The total count of valid suffix numbers that can be formed from this state.
* `dp[i][tight][prev][prev_l][1]`: The accumulated waviness contributed by all valid suffixes from this state.

### State Transitions and Wave Detection
When processing index `i` with a chosen digit `j`:
* **Leading Zeros Handling:** If we haven't started placing actual digits yet (`prev == -1` and `j == 0`), we keep the trackers at `-1` to ensure leading zeros don't falsely trigger a wave.
* **Wave Verification:** If we have at least two valid preceding digits (`prev_l != -1` and `prev != -1`), we check if the middle digit `prev` forms a peak or valley relative to `prev_l` and the new digit `j`:
