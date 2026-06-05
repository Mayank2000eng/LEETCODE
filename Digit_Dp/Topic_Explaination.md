# Digit DP (Digit Dynamic Programming)

## What is Digit DP?

Digit DP is a Dynamic Programming technique used when:

- We need to count numbers in a range
- Conditions depend on digits
- Brute force over all numbers is impossible

Instead of iterating through all numbers, we build numbers digit by digit.

---

# How to Identify Digit DP Questions?

Think about Digit DP if you see:

## 1. Large Range Constraints

Example:

```text
1 ≤ L,R ≤ 10^18
```

Brute force becomes impossible.

---

## 2. Question Involves Digits

Examples:

- Sum of digits
- Number of zeros
- Adjacent digits
- Distinct digits
- Contains digit X
- Increasing digits

---

## 3. Count Numbers in a Range

Examples:

```text
Count numbers having digit sum = K

Count numbers divisible by M

Count numbers with no repeated digits
```

---

## 4. Common Keywords

Questions containing:

```text
How many numbers...

Count valid integers...

Find total numbers...

Count beautiful integers...

Count special numbers...
```

often indicate Digit DP.

---

# Core Idea

Instead of:

```cpp
for(i=L;i<=R;i++)
```

We compute:

```text
Answer(R)-Answer(L-1)
```

Where:

```text
Answer(X)

=

Count valid numbers from [0,X]
```

---

# Standard DP State

Most Digit DP problems use:

```text
dp(position, tight, started, extra_state)
```

---

## Position

Current digit index.

Example:

```text
5238

position 0 -> 5

position 1 -> 2

position 2 -> 3

position 3 -> 8
```

---

## Tight

Controls restrictions.

```text
tight = 1

means:

current digit cannot exceed original digit
```

If we choose smaller digit:

```text
tight becomes 0
```

Then:

```text
future digits:

0 to 9 allowed
```

---

## Started

Handles leading zeros.

Example:

```text
00045
```

Use:

```text
started=0

number not started

started=1

number started
```

---

## Extra State

Depends on problem.

Examples:

### Sum of digits

```text
dp(pos,tight,sum)
```

### Divisibility

```text
dp(pos,tight,mod)
```

### Previous Digit

```text
dp(pos,tight,prevDigit)
```

### Counting occurrences

```text
dp(pos,tight,count)
```

---

# General Template

```cpp
long solve(pos,tight,state){

    if(pos==digits.size())
        return valid;

    if(dp exists)
        return dp;

    int limit =
    tight ? digits[pos] : 9;

    long ans=0;

    for(int d=0;d<=limit;d++){

        bool newTight=
        tight && (d==limit);

        update state

        ans+=solve(
            pos+1,
            newTight,
            newState
        );
    }

    return ans;
}
```

Range:

```cpp
answer=

solve(R)

-

solve(L-1)
```

---

# Tight Example

Suppose:

```text
N = 527
```

Position 0:

```text
Allowed:

0..5
```

Choose:

```text
3
```

Then:

```text
tight=0
```

Future:

```text
00 -> 99 possible
```

Choose:

```text
5
```

Then:

```text
tight remains 1
```

---

# Why Started State Needed?

Consider:

```text
00056
```

Without started:

```text
previous digit becomes 0

wrong transitions happen
```

Started state fixes this.

---

# Common Types of Digit DP

## Type 1: Count Valid Numbers

Examples:

```text
Count numbers with digit sum K

Count numbers containing digit 7

Count numbers with exactly X zeros
```

---

## Type 2: Modulo Problems

Examples:

```text
Count numbers divisible by K
```

State:

```text
newMod=(oldMod*10+digit)%K
```

---

## Type 3: Adjacent Digit Constraints

Examples:

```text
No adjacent equal digits

Increasing digits
```

Need:

```text
previous digit state
```

---

## Type 4: Bitmask Digit DP

Examples:

```text
All digits distinct
```

State:

```text
mask
```

Update:

```cpp
mask |= (1<<digit)
```

---

## Type 5: Sum Queries

Examples:

```text
Sum of digits from L to R

Sum of all valid numbers
```

Store:

```text
count

sum
```

---

# Complexity

Suppose:

```text
19 digits

sum state = 200
```

Complexity:

```text
19 × 2 × 200 × 10

≈ 76000
```

---

# Memoization Rule

Usually memoize only when:

```text
tight=0
```

Typical:

```cpp
if(!tight && dp exists)

return dp;
```

---

# Common Mistakes

## Mistake 1

Forget:

```text
Answer(R)-Answer(L-1)
```

---

## Mistake 2

Wrong Tight Update

Wrong:

```cpp
newTight=(d==limit)
```

Correct:

```cpp
newTight=

tight && (d==limit)
```

---

## Mistake 3

Ignoring Leading Zeros

Use:

```text
started state
```

---

## Mistake 4

Huge DP Arrays

Store only required states.

---

# Quick Identification Cheat Sheet

```text
Range Query

↓

Huge Constraints

↓

Digit Property

↓

Think Digit DP
```

---

# Mental Flow

```text
Range [L,R] ?

↓

Property depends on digits ?

↓

Constraints huge ?

↓

Compute:

F(R)-F(L-1)

↓

Build digit by digit

↓

Digit DP
```

---

# One Line Summary

```text
Digit DP:

Build numbers digit by digit while storing constraints as states.
```
