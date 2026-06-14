# LeetCode 3960 - Frequency Balance Subarray

## Approach

For every subarray, we need to know the frequency of each value.

Instead of recomputing frequencies from scratch for every subarray, we expand the subarray one element at a time and maintain two hash maps.

### `map1`

Stores:

```text
value -> frequency
```

Example:

```text
1 -> 2
2 -> 3
```

### `map2`

Stores:

```text
frequency -> number of values having that frequency
```

For the above example:

```text
2 -> 1
3 -> 1
```

Meaning:

- One value appears 2 times.
- One value appears 3 times.

---

## Building the Subarray

We fix a starting index `i` and keep extending the ending index `j`.

```java
for(int i = 0; i < nums.length; i++) {
    for(int j = i; j < nums.length; j++) {
        ...
    }
}
```

Whenever `nums[j]` is added:

### Step 1

Get its old frequency.

```java
int r = map1.getOrDefault(nums[j], 0);
```

### Step 2

Remove its contribution from `map2`.

```java
if(r != 0){
    map2.put(r, map2.get(r) - 1);

    if(map2.get(r) == 0){
        map2.remove(r);
    }
}
```

### Step 3

Increase its frequency in `map1`.

```java
map1.put(nums[j], map1.getOrDefault(nums[j], 0) + 1);
```

### Step 4

Add the new frequency contribution into `map2`.

```java
r = map1.get(nums[j]);
map2.put(r, map2.getOrDefault(r, 0) + 1);
```

---

## Checking Whether a Subarray is Valid

### Case 1: Only One Distinct Value

```java
if(map1.size() == 1)
```

Example:

```text
[5,5,5]
```

Frequency:

```text
5 -> 3
```

This is always valid.

---

### Case 2: Exactly Two Frequencies Exist

```java
else if(map2.size() == 2)
```

Example:

```text
1 -> 2
2 -> 2
3 -> 4
```

Then:

```text
map2

2 -> 2
4 -> 1
```

The keys of `map2` are the two frequencies.

```java
int a1 = 0, a2 = 0;

for(Map.Entry<Integer,Integer> e : map2.entrySet()){
    if(a1 == 0)
        a1 = e.getKey();

    a2 = e.getKey();
}
```

Now `a1` and `a2` are the two frequencies.

The problem requires frequencies to be:

```text
f
and
2 * f
```

So we check:

```java
if(a1 == 2 * a2 || 2 * a1 == a2)
```

If true, the subarray is frequency balanced.

---

## Complexity Analysis

### Time Complexity

```text
O(n²)
```

- Outer loop chooses the starting index.
- Inner loop extends the ending index.
- HashMap operations are O(1).

### Space Complexity

```text
O(n)
```

for storing frequencies.

---

## Java Code

```java
class Solution {
    public int getLength(int[] nums) {
        HashMap<Integer,Integer> map1=new HashMap<>();
        HashMap<Integer,Integer> map2=new HashMap<>();
        int ans=0;

        for(int i=0;i<nums.length;i++){
            for(int j=i;j<nums.length;j++){

                int r=map1.getOrDefault(nums[j],0);

                if(r!=0){
                    map2.put(r,map2.get(r)-1);

                    if(map2.get(r)==0){
                        map2.remove(r);
                    }
                }

                map1.put(nums[j],map1.getOrDefault(nums[j],0)+1);

                r=map1.get(nums[j]);

                map2.put(r,map2.getOrDefault(r,0)+1);

                if(map1.size()==1){
                    ans=Math.max(ans,j-i+1);
                }
                else if(map2.size()==2){

                    int a1=0,a2=0;

                    for(Map.Entry<Integer,Integer> e : map2.entrySet()){
                        if(a1==0)
                            a1=e.getKey();

                        a2=e.getKey();
                    }

                    if(a1==2*a2||2*a1==a2)
                        ans=Math.max(ans,j-i+1);
                }
            }

            map1.clear();
            map2.clear();
        }

        return ans;
    }
}
```
