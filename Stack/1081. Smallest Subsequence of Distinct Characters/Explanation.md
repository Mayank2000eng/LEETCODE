# 1081. Smallest Subsequence of Distinct Characters

## Problem Understanding

The problem asks us to find the **lexicographically smallest subsequence** of a given string `s` that contains **every distinct character exactly once**.

The subsequence must:

- Contain every unique character exactly once.
- Preserve the relative order of characters from the original string.
- Be the smallest possible in lexicographical order.

---

# Intuition

The solution uses a **Greedy Algorithm** with a **Stack** (implemented using an array) and a **HashSet**.

The main idea is:

- Build the answer one character at a time.
- If the current character is smaller than the last character already chosen, we should replace the larger character **only if it appears again later**.
- This guarantees the smallest lexicographical answer while still keeping every distinct character.

To know whether a character can be removed safely, we first compute the **last occurrence** of every character.

---

# Approach

## 1. Store Last Occurrence of Every Character

Create an integer array of size `26`.

```java
int[] arr = new int[26];
```

Traverse the string once and store the last index of every character.

```java
for (int i = 0; i < s.length(); i++) {
    arr[s.charAt(i) - 'a'] = i;
}
```

---

## 2. Data Structures

### Character[] ans

Acts like a **stack** to store our subsequence.

```java
Character[] ans = new Character[s.length()];
```

---

### HashSet

Keeps track of characters already present in the answer.

```java
HashSet<Character> set = new HashSet<>();
```

This allows duplicate checking in **O(1)** time.

---

### Pointer `j`

Represents the top of the stack.

```java
int j = -1;
```

---

## 3. Process Every Character

For each character:

```java
char c = s.charAt(i);
```

### If already present

Simply skip it.

```java
if (set.contains(c))
    continue;
```

---

### Otherwise

Try removing larger characters from the stack.

```java
while (
    j >= 0 &&
    ans[j] > c &&
    arr[ans[j] - 'a'] >= i
)
```

The loop continues only if:

- Stack is not empty.
- Top character is lexicographically larger.
- Top character appears again later.

If all conditions are true:

```java
set.remove(ans[j]);
ans[j] = null;
j--;
```

This removes the larger character safely.

---

### Push Current Character

After removing all unnecessary characters:

```java
j++;
ans[j] = c;
set.add(c);
```

---

## 4. Construct the Answer

The valid stack lies between `0...j`.

```java
String ans1 = "";

for (int i = 0; i <= j; i++) {
    ans1 += ans[i];
}

return ans1;
```

---

# Dry Run

Input

```
s = "cbacdcbc"
```

Last occurrences

| Character | Last Index |
|-----------|------------|
| a | 2 |
| b | 6 |
| c | 7 |
| d | 4 |

Processing:

| Current | Stack |
|---------|-------|
| c | c |
| b | b |
| a | a |
| c | ac |
| d | acd |
| c | acd |
| b | acdb |
| c | acdb |

Final Answer

```
acdb
```

---

# Why Does the While Loop Work?

The while loop removes the top character only when:

1. The current character is smaller.
2. The top character appears again later.

Therefore:

- We never lose a required character.
- We always move toward a smaller lexicographical answer.

Each character is:

- Pushed at most once.
- Popped at most once.

Hence the algorithm remains linear.

---

# Complexity Analysis

### Time Complexity

- Computing last occurrences: **O(N)**
- Processing characters: **O(N)**
- Building final string: **O(N)**

Overall:

$$
\boxed{O(N)}
$$

Although there is a nested `while` loop, every character is pushed and popped at most one time.

---

### Space Complexity

- `Character[] ans` → **O(N)**
- `HashSet` → **O(26)**
- `arr` → **O(26)**

Overall:

$$
\boxed{O(N)}
$$

---

# Java Solution

```java
class Solution {
    public String smallestSubsequence(String s) {

        HashSet<Character> set = new HashSet<>();
        Character[] ans = new Character[s.length()];

        int[] arr = new int[26];

        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i) - 'a'] = i;
        }

        int j = -1;

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (!set.contains(c)) {

                while (
                    j >= 0 &&
                    ans[j] > c &&
                    arr[ans[j] - 'a'] >= i
                ) {
                    set.remove(ans[j]);
                    ans[j] = null;
                    j--;
                }

                j++;
                ans[j] = c;
                set.add(c);
            }
        }

        String ans1 = "";

        for (int i = 0; i <= j; i++) {
            ans1 += ans[i];
        }

        return ans1;
    }
}
```

---

# Key Takeaways

- Greedy + Stack is the optimal strategy.
- Store the last occurrence of every character.
- Remove larger characters only if they appear again later.
- Use a `HashSet` to avoid duplicates.
- Every character is pushed and popped at most once, giving an **O(N)** solution.
