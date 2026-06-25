class Solution {
    static Long[][][][][] dp;
    public long totalWaviness(long num1, long num2) {
        int[] arr1=make(num1-1);
        int[] arr2=make(num2);
        dp=new Long[arr1.length+1][2][11][11][2];
        long[] ans1=find(arr1,0,1,-1,-1);
        dp=new Long[arr2.length+1][2][11][11][2];
        long[] ans2=find(arr2,0,1,-1,-1);
        return ans2[1]-ans1[1];
    }
    static int[] make(long n){
        if(n==0){
            return new int[]{0};
        }
        int c=0;
        long num=n;
        while(num>0){
            c++;
            num/=10;
        }
        int[] arr=new int[c];
        int i=c-1;
        while(i>=0){
            arr[i--]=(int)(n%10);
            n/=10;
        }
        return arr;
    }
    static long[] find(int[] arr,int i,int tight,int prev,int prev_l){
        if(i==arr.length){
            return new long[]{1,0};
        }
        if(dp[i][tight][prev+1][prev_l+1][0]!=null){
            return new long[]{
                dp[i][tight][prev+1][prev_l+1][0],
                dp[i][tight][prev+1][prev_l+1][1]
            };
        }
        int u=9;
        if(tight==1){
            u=arr[i];
        }
        long[] ans=new long[2];
        long[] tem=new long[2];
        for(int j=0;j<=u;j++){
            int r=j;
            int d=prev;
            long c=0;
            if(prev==-1&&j==0){
                r=-1;
                d=-1;
            }
            int t=tight;
            if(tight==1&&j==arr[i])
                t=tight;
            else
                t=0;
            if(prev_l!=-1){
                if(prev<prev_l&&prev<j) c=1;
                if(prev>prev_l&&prev>j) c=1;
            }
            tem=find(arr,i+1,t,r,d);
            ans[0]=ans[0]+tem[0];
            ans[1]=ans[1]+tem[1]+(c*tem[0]);
        }
        dp[i][tight][prev+1][prev_l+1][0]=ans[0];
        dp[i][tight][prev+1][prev_l+1][1]=ans[1];
        return ans;
    }   
}
