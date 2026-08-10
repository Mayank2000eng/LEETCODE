Approach

For every index i, the amount of water that can be trapped depends on:

The maximum height on the left of i
The maximum height on the right of i

The water level at index i is:

min(leftMax, rightMax)

Therefore:

water = min(leftMax, rightMax) - height[i]

If either side is not taller than the current bar, no water can be trapped.

Prefix and Suffix Arrays

I use two arrays:

1. left[]

left[i] stores the maximum height from index 0 to i.

For example:

height = [4,2,0,3,2,5]

left = [4,4,4,4,4,5]

We calculate it from left to right:

for(int i=0;i<height.length;i++){
    left[i]=Math.max(max,height[i]);
    max=left[i];
}
2. right[]

right[i] stores the maximum height from index i to the last index.

right = [5,5,5,5,5,5]

We calculate it from right to left:

max=0;

for(int i=height.length-1;i>=0;i--){
    right[i]=Math.max(max,height[i]);
    max=right[i];
}
Calculating Trapped Water

For every index except the first and last:

if(i!=0 && i!=height.length-1)

I check whether both sides have a taller boundary.

if(left[i-1] <= height[i] || right[i+1] <= height[i])
    continue;

If both sides are taller, then:

ans += Math.min(left[i-1],right[i+1]) - height[i];

Here:

left[i-1]  = maximum height on the left
right[i+1] = maximum height on the right

So the water trapped is:

min(maxLeft, maxRight) - currentHeight
Dry Run

For:

height = [4,2,0,3,2,5]

Consider index 2:

height[2] = 0

left[1]  = 4
right[3] = 5

Therefore:

water = min(4,5) - 0
      = 4

Similarly, water is trapped at other positions.

Total:

4 + 1 + 4 = 9

Therefore:

Answer = 9
Code
class Solution {
    public int trap(int[] height) {
        int[] left = new int[height.length];
        int[] right = new int[height.length];

        int max = 0;

        // Prefix maximum
        for(int i = 0; i < height.length; i++) {
            left[i] = Math.max(max, height[i]);
            max = left[i];
        }

        max = 0;

        // Suffix maximum
        for(int i = height.length - 1; i >= 0; i--) {
            right[i] = Math.max(max, height[i]);
            max = right[i];
        }

        int ans = 0;

        // Calculate trapped water
        for(int i = 0; i < height.length; i++) {

            if(i != 0 && i != height.length - 1) {

                if(left[i - 1] <= height[i] ||
                   right[i + 1] <= height[i])
                    continue;

                ans += Math.min(left[i - 1], right[i + 1])
                       - height[i];
            }
        }

        return ans;
    }
}
Complexity
Time Complexity
O(n)

We traverse the array three times.

O(n) + O(n) + O(n) = O(n)
Space Complexity
O(n)
