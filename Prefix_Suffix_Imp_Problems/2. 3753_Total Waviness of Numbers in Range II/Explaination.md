# Earliest Finish Time — Explanation

## Problem Understanding

We have two categories of rides:

- Land rides
- Water rides

We must perform:

```text
One ride from each category
```

The order can be:

```text
Land → Water
OR
Water → Land
```

We need to find the **minimum possible finishing time**.

---

## Key Observation

Suppose we fix the order:

```text
Land → Water
```

For every land ride:

```text
land finishes at:

finishLand = landStart + landDuration
```

Now we must choose the best water ride.

There are two possibilities:

### Case 1: Water ride already started

```text
waterStart <= finishLand
```

Then when land ride finishes, we can immediately board.

Therefore:

```text
finish time = finishLand + waterDuration
```

We need:

```text
minimum water duration
among all rides with:

waterStart <= finishLand
```

---

### Case 2: Water ride starts later

```text
waterStart > finishLand
```

Now we must wait.

Therefore:

```text
finish time = waterStart + waterDuration
```

We need:

```text
minimum value of:

waterStart + waterDuration
```

among all future rides.

---

# Approach

## Step 1: Sort water rides

Create:

```java
[start , duration]
```

Sort by:

```text
start time
```

Why?

Because later we want binary search.

---

## Step 2: Prefix Minimum

Create:

```text
prefixMinDuration[i]
```

Meaning:

```text
minimum duration
from index 0 to i
```

Example:

```text
Durations:

5 3 8 2

Prefix Min:

5 3 3 2
```

Now we can quickly answer:

```text
minimum duration among rides
whose start time <= X
```

---

## Step 3: Suffix Minimum

Create:

```text
suffixMinFinish[i]
```

Store:

```text
minimum value of:

start + duration

from i → n−1
```

Example:

```text
start+duration:

12 15 11 20

Suffix Min:

11 11 11 20
```

Now we can quickly answer:

```text
minimum finish time among rides
whose start time > X
```

---

## Step 4: Binary Search

For each land ride:

```text
finishLand = landStart + landDuration
```

Binary search:

```text
Find first water ride with:

waterStart > finishLand
```

This creates:

```text
[ rides starting <= finishLand ]
[ rides starting > finishLand ]
```

Suppose index is:

```text
ans
```

Then:

### If no future ride exists

```text
answer:

finishLand
+
minimum duration among all rides
```

which becomes:

```text
finishLand + prefixMin[last]
```

---

### If ans == 0

All rides start later.

Answer:

```text
suffixMin[0]
```

---

### Otherwise

Take minimum of:

```text
finishLand + prefixMin[ans-1]
```

and

```text
suffixMin[ans]
```

---

# Why Run Twice?

We only solved:

```text
Land → Water
```

But order can also be:

```text
Water → Land
```

So:

```java
answer = min(

find(Land,Water),

find(Water,Land)

)
```

---

# Complexity Analysis

Let:

```text
n = land rides

m = water rides
```

Sorting:

```text
O(m log m)
```

For each land ride:

```text
Binary Search

O(log m)
```

Total:

```text
O(m log m + n log m)
```

Space:

```text
O(m)
```

---

# Techniques Used

```text
✓ Sorting

✓ Binary Search

✓ Prefix Minimum

✓ Suffix Minimum

✓ Greedy Observation
```

This is a classic example of combining multiple standard competitive programming techniques together.
