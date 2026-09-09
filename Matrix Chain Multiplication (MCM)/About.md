# Standard Matrix Chain Multiplication (MCM) Question

Given matrices:

$$
A_1, A_2, A_3, A_4
$$

with dimensions:

$$
A_1 = 10 \times 30
$$

$$
A_2 = 30 \times 5
$$

$$
A_3 = 5 \times 60
$$

$$
A_4 = 60 \times 20
$$

Find the **minimum number of scalar multiplications** required to compute:

$$
A_1A_2A_3A_4
$$

You can put parentheses in different ways, for example:

1. `((A_1A_2)A_3)A_4`
2. `(A_1(A_2A_3))A_4`
3. `(A_1A_2)(A_3A_4)`
4. `A_1((A_2A_3)A_4)`
5. `A_1(A_2(A_3A_4))`

**Your task:** Find which parenthesization gives the minimum cost and what that minimum cost is.

## Important Recurrence

If dimensions are stored as:

$$
p = [10, 30, 5, 60, 20]
$$

then:

$$
dp[i][j] = \min_{i \leq k < j}
\left(
dp[i][k] + dp[k+1][j] + p[i-1] \times p[k] \times p[j]
\right)
$$
