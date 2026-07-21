# 3995. Minimum Cost to Convert String III

## Problem Understanding
The problem asks us to find the minimum total cost to transform a `source` string into a `target` string using a given set of `rules`. Each rule consists of a pattern (which can include wildcard `*` characters) and a replacement string, and applying it incurs a base cost plus an additional cost for every wildcard used. We must determine if a valid sequence of rule applications and character matches can achieve the transformation and return the minimum cost, or `-1` if it is impossible.

## Intuition & Approach
The problem can be modeled as finding the minimum cost path to reach the end of the string, making Dynamic Programming (DP) the perfect choice. I used a top-down DP approach with memoization. 

At any given index `i` of the `source` string, we evaluate two distinct choices:
1. **Apply a Rule:** We can attempt to apply every available rule starting at `i`. If the rule's pattern matches the `source` and its replacement perfectly matches the `target` (accounting for the `*` wildcards), we compute the cost (base cost + wildcards used) and recursively solve for the remaining suffix of the string.
2. **Skip a Character:** If the current character in `source` already matches the character in `target`, we can safely advance to the next index without applying any rule and incurring zero cost.

By exploring all valid paths and caching the minimum cost at each index, we avoid redundant calculations and ensure an optimal solution.

## Step-by-Step Explanation
* **Initialization:** The main `minCost` function initializes a global `dp` array of type `Integer[]` with the size of `source.length()`. It then triggers the recursive `find` function starting at index `0`.
* **Base Cases:** Inside `find`, if `i == source.length()`, we have successfully processed the entire string and return `0`. If `dp[i]` is not null, we immediately return the memoized result to save computation.
* **Rule Iteration:** We initialize `ans = -1` to track the minimal cost. We loop through all the available rules using `i1`. For each rule, `a` holds the pattern string and `b` holds the replacement string. If applying the rule would exceed the string's bounds (`a.length() + i > source.length()`), we `continue`.
* **Pattern Matching:** We use a `while` loop to scan the substring. `d` acts as the pointer for `source`/`target`, and `c1` is the pointer for the rule's strings `a`/`b`. `c2` counts how many wildcards (`*`) are consumed.
    * **Wildcard Check:** If `a.charAt(c1) == '*'` and `target.charAt(d) == b.charAt(c1)`, the wildcard is validly matched. We increment `d`, `c1`, and `c2`, then `continue`.
    * **Exact Match Check:** Otherwise, we strictly require `source.charAt(d) == a.charAt(c1)` AND `target.charAt(d) == b.charAt(c1)`. If this fails, we set `flag = 0` to mark the rule as invalid and `break` the loop.
* **Accumulating Rule Cost:** If the rule was fully valid (`flag == 1`), we recursively call `find` starting from the new index `d` and store the result in `tem`. If this path is valid (`tem != -1`), we update `ans` to store the minimum between its current value and the newly calculated cost (`tem + costs[i1] + c2`).
* **Character Skipping:** Independently of the rules, we check if `source.charAt(i) == target.charAt(i)`. If so, we recursively call `find` for `i + 1`. If the result `tem` is valid, we update `ans` with the minimum of itself and `tem`.
* **Memoization:** Finally, we save the computed `ans` into `dp[i]` and return it.

## Complexity Analysis
* **Time Complexity:** $\mathcal{O}(N \times R \times L)$, where $N$ is the length of the `source` string, $R$ is the total number of rules, and $L$ is the maximum length of a pattern. The recursive function `find` evaluates $N$ unique states (indices). At each state, we iterate through all $R$ rules, and for each rule, the `while` loop performs character comparisons taking up to $\mathcal{O}(L)$ time.
* **Space Complexity:** $\mathcal{O}(N)$ auxiliary space. The `dp` array takes $\mathcal{O}(N)$ memory to cache the minimum cost for each of the $N$ indices. Additionally, the maximum depth of the recursive call stack will be $N$ in the worst-case scenario (e.g., matching character by character).

## Code
```java
class Solution {
    static Integer[] dp;
    public int minCost(String source, String target, List<List<String>> rules, int[] costs) {
        dp=new Integer[source.length()];
        int ans=find(source,target,rules,costs,0);
        return ans;
    }
    static int find(String source, String target, List<List<String>> rules, int[] costs,int i){
        if(i==source.length()){
            return 0;
        }
        if(dp[i]!=null) return dp[i];
        int ans=-1;
        int tem=0;
        int flag=0;
        int d=i;
        for(int i1=0;i1<rules.size();i1++){
            String a=rules.get(i1).get(0);
            String b=rules.get(i1).get(1);
            int c1=0;
            d=i;
            flag=1;
            int c2=0;
            if(a.length()+i>source.length()) continue;
            while(d<source.length()&&c1<a.length()){
                if(target.charAt(d)==b.charAt(c1)&&a.charAt(c1)=='*'){
                    d++;
                    c1++;
                    c2++;
                    continue;
                }
                if(!(source.charAt(d)==a.charAt(c1)&&target.charAt(d)==b.charAt(c1))){
                    flag=0;
                    break;
                }
                d++;
                c1++;
            }
            if(flag==1){
                tem=find(source,target,rules,costs,d);
                if(tem!=-1){
                    if(ans!=-1)
                        ans=Math.min(ans,tem+costs[i1]+c2);
                    else
                        ans=tem+costs[i1]+c2;
                }
            }
        }
        if(source.charAt(i)==target.charAt(i)){
            tem=find(source,target,rules,costs,i+1);
            if(tem!=-1){
                if(ans!=-1)
                    ans=Math.min(ans,tem);
                else
                    ans=tem;
            }
        }
        dp[i]=ans;
        return ans;
    }
}
