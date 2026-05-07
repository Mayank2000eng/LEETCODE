
// Greedy Approach : 

class Solution {
    public int[] maxValue(int[] nums) {
         int[] pref=new int[nums.length];
         int[] suf=new int[nums.length];
         int[] ans=new int[nums.length];
         suf[nums.length-1]=nums[nums.length-1];
         pref[0]=nums[0];
         for(int i=nums.length-2;i>=0;i--){
            suf[i]=Math.min(suf[i+1],nums[i]);
         }
         for(int i=1;i<nums.length;i++){
            pref[i]=Math.max(pref[i-1],nums[i]);
         }
        int far=nums.length-1;
        int i=nums.length-2;
        ans[i+1]=pref[i+1];
        while(i>=0){
            int l=pref[i];
            int r=suf[i];
            if(nums[far]<l&&nums[far]>=r) ans[i]=ans[far];
            else{
                ans[i]=pref[i];
            }
            if(nums[i]<nums[far]) far=i;
            i--;
        }
        return ans;
    }
}
