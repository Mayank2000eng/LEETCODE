class Solution {
    static Integer[] dp;
    public int maxSumAfterPartitioning(int[] arr, int k) {
        dp=new Integer[arr.length];
        return find(arr,0,k);
    }
    static int find(int[] arr,int i,int k){
        if(i>=arr.length) return 0;
        if(dp[i]!=null) return dp[i];
        int ans=0;
        int max=arr[i];
        for(int j=0;j<k;j++){
            if(j+i<arr.length){
                max=Math.max(max,arr[i+j]);
                int ans1=find(arr,i+j+1,k);
                ans1=ans1+((j+1)*max);
                ans=Math.max(ans,ans1);
            }else break;
        }
        dp[i]=ans;
        return ans;
    }
}
