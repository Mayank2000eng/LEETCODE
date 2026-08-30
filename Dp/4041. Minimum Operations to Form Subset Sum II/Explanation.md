# 1. Question — What are we asked to do?

You have an array `nums` and a target `sum`.

For every `nums[i]`, you can perform an operation:

> `x → floor(x / 2)`

Each operation costs `1`.

You need to choose some resulting values from the elements such that their total is exactly `sum`, using the **minimum number of operations**.

You can also **skip an element** without using it.

## Example

Suppose:

```text
nums = [4, 3]
sum = 5

From 4, we can get:

4       cost 0
2       cost 1
1       cost 2

From 3:

3       cost 0
1       cost 1

We can choose:

4 + 1 = 5

The 4 costs 0 operations and 3 → 1 costs 1.

So:

answer = 1
2. Your Recursive Approach

Your main function:

int ans = find(nums, 0, 0, sum);

Here:

i     = current index
sum1  = sum formed so far
sum   = target

So:

find(nums, i, sum1, sum)

means:

Starting from index i, if I have already formed sum1, what is the minimum number of operations required to reach sum?

3. Base Cases

You have:

if (sum1 == sum) return 0;

This means target is already achieved.

No more operations are required.

For example:

sum1 = 5
target = 5

answer = 0

Then:

if (i == nums.length || sum1 > sum) {
    return Integer.MAX_VALUE;
}

If we have used all elements and haven't reached the target, it's impossible.

Similarly, if:

sum1 > sum

we have crossed the target, so this path is invalid.

Integer.MAX_VALUE is being used as:

IMPOSSIBLE
4. DP

You use:

dp = new Integer[nums.length][sum];

And:

if (dp[i][sum1] != null)
    return dp[i][sum1];

The state is:

(i, sum1)

because once we know:

which index we're at
how much sum we've already formed

the previous decisions don't matter anymore.

So we store the answer for that state.

5. First Choice — Don't Use nums[i]

You do:

int ans = find(nums, i + 1, sum1, sum);

This means:

What if I completely skip the current element?

For example:

nums = [4, 3]
          ↑
        current

You can simply ignore 3 and move forward.

6. Second Choice — Use nums[i]

This is the important part of your approach.

You have:

int tar1 = nums[i];
int c1 = 0;

Initially:

tar1 = nums[i]
c1 = 0

Then:

while(tar1 > 0)

You are generating all the values that can be obtained by repeatedly dividing by 2.

For example, if:

nums[i] = 10

your outer loop generates:

10
5
2
1

because:

10 / 2 = 5
5 / 2 = 2
2 / 2 = 1
1 / 2 = 0
7. Why Do You Need c1?

This is the clever part of your implementation.

For:

10

we have:

10 → cost 0
5  → cost 1
2  → cost 2
1  → cost 3

So c1 represents the number of operations needed to obtain tar1.

But then you have another loop:

while (tar <= sum)

and:

tar *= 2;
c++;

This allows you to consider using multiple copies of the same reduced value.

For example, if:

nums[i] = 10

after reducing it to:

5

you can potentially use:

5

with cost 1.

Or:

5 + 5

with cost:

1 + 1 = 2

Your loop handles these possibilities.

8. Inner Loop

You have:

int tar = tar1;
int c = c1;

while (tar <= sum) {

Suppose:

tar1 = 2
c1 = 2

Then:

tar = 2
c = 2

First:

find(nums, i + 1, sum1 + 2, sum)

Then:

tar *= 2;
c++;

so:

tar = 4
c = 3

Then try:

find(nums, i + 1, sum1 + 4, sum)

So you're trying different amounts that can be contributed by the current element.

9. Why c + tem?

Suppose:

int tem = find(...);

returns:

3

That means the remaining elements need 3 operations.

If the current choice costs:

c = 2

then total:

2 + 3 = 5

Hence:

ans = Math.min(ans, c + tem);
10. Why This Condition?

You have:

if (tar > sum / 2) break;

Before doing:

tar *= 2;

you check whether doubling will exceed the target.

For example:

sum = 10
tar = 6

Doubling gives:

12

which is already greater than the target.

So there is no point trying it.

11. Your Complete Code With Comments
class Solution {

    static Integer[][] dp;

    public int minOperations(int[] nums, int sum) {

        dp = new Integer[nums.length][sum];

        int ans = find(nums, 0, 0, sum);

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    static int find(int[] nums, int i, int sum1, int sum) {

        // Target achieved
        if (sum1 == sum) {
            return 0;
        }

        // No elements left or target exceeded
        if (i == nums.length || sum1 > sum) {
            return Integer.MAX_VALUE;
        }

        // Already calculated
        if (dp[i][sum1] != null) {
            return dp[i][sum1];
        }

        // Option 1: Don't use nums[i]
        int ans = find(nums, i + 1, sum1, sum);

        int tar1 = nums[i];
        int c1 = 0;

        // Try different values obtained by repeatedly dividing by 2
        while (tar1 > 0) {

            int tar = tar1;
            int c = c1;

            // Try using tar, 2*tar, 4*tar, ...
            while (tar <= sum) {

                int tem = find(
                    nums,
                    i + 1,
                    sum1 + tar,
                    sum
                );

                if (tem != Integer.MAX_VALUE) {
                    ans = Math.min(ans, c + tem);
                }

                // Doubling tar would exceed the target
                if (tar > sum / 2) {
                    break;
                }

                tar *= 2;
                c++;
            }

            // One more division by 2
            tar1 /= 2;
            c1++;
        }

        return dp[i][sum1] = ans;
    }
}
