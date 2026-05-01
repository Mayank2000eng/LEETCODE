class Solution {
    public int maxRotateFunction(int[] nums) {
        int s=0;
        int f=0;
        for(int i=0;i<nums.length;i++){
            s+=nums[i];
            f+=nums[i]*i;
        }
        int ans=f;
        int j=nums.length-1;
        for(int i=0;i<nums.length;i++){
            f=f-(nums[j]*(nums.length-1));
            f+=(s-nums[j]);
            ans=Math.max(ans,f);
            j--;
        }
        return ans;
    }
}