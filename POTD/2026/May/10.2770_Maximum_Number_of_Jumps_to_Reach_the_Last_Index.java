class Solution {
    static Integer[] dp;
    public int maximumJumps(int[] nums, int target) {
        dp=new Integer[nums.length];
        int ans=find(nums,target,0);
        if(ans==Integer.MIN_VALUE) return -1;
        else return ans;
    }
    static int find(int[] nums,int tar,int i){
        if(i==nums.length-1){
            return 0;
        }
        if(dp[i]!=null) return dp[i];
        int ans=Integer.MIN_VALUE;
        int tem=Integer.MIN_VALUE;
        for(int j=i+1;j<nums.length;j++){
            if(Math.abs(nums[j]-nums[i])>=0&&Math.abs(nums[j]-nums[i])<=tar){
                tem=Math.max(tem,find(nums,tar,j));
                if(tem!=Integer.MIN_VALUE)
                        ans=Math.max(ans,tem+1);
                }
        }
        return dp[i]=ans;
    }
}
