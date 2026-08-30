# 115. Distinct Subsequences

## 1. Question — What are we asked to do?

You are given two strings:

```text
s
t

You need to find how many distinct subsequences of s are equal to t.

A subsequence is formed by deleting some characters from s while keeping the remaining characters in the same order.

Example
s = "rabbbit"
t = "rabbit"

There are 3 different ways to choose characters from s to form "rabbit".

So the answer is:

3
2. How do we think recursively?

We use two pointers:

i → current character of s
j → current character of t

So:

find(s, t, i, j)

means:

How many ways can we form t[j...] using s[i...]?

3. First Base Case
if(j==t.length()) return 1;

If j reaches the end of t, it means we have successfully formed the complete target string.

For example:

t = "rabbit"

             j
             ↓
rabbit
      ↑
      end

There is exactly 1 valid way.

So:

return 1;
4. Second Base Case
if(i==s.length()) return 0;

If s is finished but t is still remaining, then we cannot form t.

So:

return 0;
5. Memoization

We use:

static Integer[][] dp;

and initialize it:

dp=new Integer[s.length()][t.length()];

dp[i][j] stores the answer for:

find(s, t, i, j)

Before calculating a state, we check:

if(dp[i][j]!=null) return dp[i][j];

If we have already calculated this state, we directly return the stored answer.

This prevents the same state from being calculated again and again.

6. Main Case — Characters are Equal

Suppose:

s.charAt(i) == t.charAt(j)

For example:

s = "rabbbit"
     ↑
     i

t = "rabbit"
     ↑
     j

Both characters are 'r'.

Now we have two choices.

Choice 1 — Use the character

We use s[i] to match t[j].

Therefore both pointers move:

find(s,t,i+1,j+1)

Because:

i → i + 1
j → j + 1
Choice 2 — Skip the character

We don't use s[i].

Only i moves:

find(s,t,i+1,j)

j stays the same because we still need to match t[j].

Therefore, when:

s.charAt(i)==t.charAt(j)

we add both possibilities:

return dp[i][j]=find(s,t,i+1,j+1)+find(s,t,i+1,j);
7. Main Case — Characters are Different

If:

s.charAt(i) != t.charAt(j)

then s[i] cannot be used to match t[j].

So we have only one choice:

Skip s[i].

Therefore:

find(s,t,i+1,j)

Your code does:

int ans=find(s,t,i+1,j);
return dp[i][j]=ans;
8. Your Complete Solution
class Solution {

    static Integer[][] dp;

    public int numDistinct(String s, String t) {

        dp=new Integer[s.length()][t.length()];

        return find(s,t,0,0);
    }

    static int find(String s,String t,int i,int j){

        if(j==t.length()) return 1;

        if(i==s.length()) return 0;

        if(dp[i][j]!=null) return dp[i][j];

        if(s.charAt(i)==t.charAt(j)){

           return dp[i][j]=find(s,t,i+1,j+1)+find(s,t,i+1,j);
        }

        int ans=find(s,t,i+1,j);

        return dp[i][j]=ans;
    }
}
9. Complete Logic in Short

At every position:

If characters are equal:
              s[i] == t[j]
                    |
             ----------------
             |              |
            USE            SKIP
             |              |
         i+1, j+1         i+1, j

So:

find(i,j) =
    find(i+1,j+1)
  + find(i+1,j)
If characters are different:
             s[i] != t[j]
                    |
                  SKIP
                    |
                  i+1,j

So:

find(i,j) = find(i+1,j)
10. Complexity

There are at most:

s.length() × t.length()

different (i, j) states.

Each state is calculated only once because of memoization.

Therefore:

Time Complexity  = O(s.length() × t.length())
Space Complexity = O(s.length() × t.length())

The extra recursion stack can go up to O(s.length()).
