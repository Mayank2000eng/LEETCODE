3116. Kth Smallest Amount With Single Denomination Combination
Problem

Given an array coins containing different coin denominations and an integer k.

We have an infinite supply of every coin, but we cannot combine different denominations.

For every coin c, we can generate:

c, 2c, 3c, 4c, ...

We need to return the kth smallest distinct amount that can be generated.

Example
coins = [5, 2], k = 7

Multiples of 5:

5, 10, 15, 20, ...

Multiples of 2:

2, 4, 6, 8, 10, 12, ...

Distinct amounts:

2, 4, 5, 6, 8, 10, 12, ...

Therefore:

Answer = 12
Approach

The solution uses two main techniques:

Binary Search on the answer
Inclusion-Exclusion Principle
1. Binary Search on Answer

Suppose we want to check:

How many valid amounts are <= m?

For a single coin c, the number of multiples of c less than or equal to m is:

m / c

For example:

m = 20
c = 5

20 / 5 = 4

The multiples are:

5, 10, 15, 20

So if we can calculate:

count(m) = number of distinct valid amounts <= m

then:

If count(m) >= k, answer is <= m
If count(m) < k, answer is > m

Therefore, count(m) is monotonic:

m increases → count(m) never decreases

This allows us to use binary search.

2. Inclusion-Exclusion

The difficult part is calculating:

count(m)

If we simply add:

m / coin1 + m / coin2 + ...

then common multiples are counted multiple times.

For example:

coins = [3, 6]
m = 18

Multiples of 3:

3, 6, 9, 12, 15, 18

Count = 6

Multiples of 6:

6, 12, 18

Count = 3

If we add them:

6 + 3 = 9

But the actual distinct numbers are only:

3, 6, 9, 12, 15, 18

Count = 6

The common multiples were counted twice.

Inclusion-Exclusion Formula

For two coins:

count = multiples(a)
      + multiples(b)
      - multiples(lcm(a,b))

For three coins:

count =
    multiples(a)
  + multiples(b)
  + multiples(c)
  - multiples(lcm(a,b))
  - multiples(lcm(a,c))
  - multiples(lcm(b,c))
  + multiples(lcm(a,b,c))

In general:

Odd number of selected coins → ADD
Even number of selected coins → SUBTRACT

This is exactly what the check() function does.

check() Function
static long check(int[] arr, long m) {
    long total = 0;

    for (int i = 1; i <= arr.length; i++) {
        if (i % 2 != 0) {
            total += find(arr, 0, 0, i, 1, m);
        } else {
            total -= find(arr, 0, 0, i, 1, m);
        }
    }

    return total;
}

Here i represents:

Number of coins selected in the current subset.

For example:

i = 1 → choose every subset containing 1 coin
i = 2 → choose every subset containing 2 coins
i = 3 → choose every subset containing 3 coins
...

According to inclusion-exclusion:

1 coin  → +
2 coins → -
3 coins → +
4 coins → -
...

Therefore:

if (i % 2 != 0)
    total += ...
else
    total -= ...
Generating Subsets Using Recursion

The find() function generates all possible subsets of a particular size.

static long find(
    int[] arr,
    int i,
    int d,
    int dk,
    long lcm,
    long m
)

Meaning:

Parameter	Meaning
arr	Coin array
i	Current index
d	Number of coins currently selected
dk	Required number of selected coins
lcm	LCM of selected coins
m	Number whose multiples we are counting
Two Choices at Every Index

For every coin, we have two choices:

Choice 1: Select the coin
if (d < dk) {
    long lcm1 = lcm(lcm, arr[i]);

    ans = find(
        arr,
        i + 1,
        d + 1,
        dk,
        lcm1,
        m
    );
}

The number of selected coins increases:

d → d + 1

and the LCM is updated.

Choice 2: Don't Select the coin
ans = ans + find(
    arr,
    i + 1,
    d,
    dk,
    lcm,
    m
);

Here:

d remains same
LCM remains same
Base Case
if (i == arr.length) {
    if (d == dk) {
        return m / lcm;
    } else {
        return 0;
    }
}

When all coins have been processed:

If required number of coins was selected
d == dk

then:

m / lcm

gives the number of common multiples.

Why?

If selected coins are:

3 and 6

then their common multiples are multiples of:

LCM(3,6) = 6

So:

m / 6

counts them.

LCM Calculation

The LCM is calculated using GCD:

static long gcd(long a, long b) {
    if (a == 0) return b;

    return gcd(b % a, a);
}

And:

static long lcm(long a, long b) {
    long g = gcd(a, b);
    long ans = a * b;

    return ans / g;
}

Mathematically:

LCM(a,b) = (a × b) / GCD(a,b)
Binary Search

The main function:

public long findKthSmallest(int[] coins, int k) {
    long l = 1;
    long h = Long.MAX_VALUE;
    long ans = 0;

    while (l <= h) {
        long m = l + (h - l) / 2;

        long ans1 = check(coins, m);

        if (ans1 >= k) {
            ans = m;
            h = m - 1;
        } else {
            l = m + 1;
        }
    }

    return ans;
}

At every step:

m = middle value

Then:

check(m)

calculates how many valid amounts are <= m.

Case 1
check(m) >= k

There are already at least k amounts.

So the answer could be m or something smaller.

ans = m
h = m - 1
Case 2
check(m) < k

There are not enough amounts.

Therefore we need a larger value:

l = m + 1
Example

Consider:

coins = [3, 6, 9]
k = 3

The distinct amounts are:

3, 6, 9, 12, 15, ...

The answer is:

9

Suppose we check:

m = 9
Multiples of 3
9 / 3 = 3
Multiples of 6
9 / 6 = 1
Multiples of 9
9 / 9 = 1

But simply adding gives:

3 + 1 + 1 = 5

Some values are duplicated.

Inclusion-exclusion removes those duplicates using pairwise and higher-order LCMs.

Finally:

count(9) = 3

Therefore:

count(9) >= k
3 >= 3

So 9 can be the answer, and binary search continues toward smaller values.

Complexity

There are at most:

n = 15

coins.

For every check(m), we generate all non-empty subsets.

Number of subsets:

2^n - 1

With n <= 15:

2^15 - 1 ≈ 32767

So one check() takes approximately:

O(2^n)

Binary search over the answer requires approximately:

O(log(MAX_ANSWER))

Therefore:

Time Complexity = O(2^n × log(MAX_ANSWER))

and recursion uses:

Space Complexity = O(n)

because the recursion depth is at most n.

Key Idea

The entire solution can be summarized as:

                 Kth Smallest Amount
                         │
                         ▼
                Binary Search on Answer
                         │
                         ▼
              How many amounts <= mid?
                         │
                         ▼
               Inclusion-Exclusion
                         │
                         ▼
              Generate all subsets
                         │
                         ▼
             Calculate LCM of subset
                         │
                         ▼
                 mid / LCM
Final Takeaway

The most important observation is:

For a given m, count how many distinct amounts can be generated up to m using Inclusion-Exclusion. Since this count is monotonic, binary search can find the smallest m for which the count is at least k.
