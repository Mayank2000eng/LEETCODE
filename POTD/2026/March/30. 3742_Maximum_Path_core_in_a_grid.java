class Solution {

    static Integer[][][] dp;

    public int maxPathScore(int[][] grid, int k) {
        dp=new Integer[grid.length][grid[0].length][k+1];
        int ans= find(grid,0,0,0,k);
        return ans;
    }

    static int find(int[][] grid,int i,int j,int k,int K){
        if(grid.length-1==i&&j==grid[0].length-1){
            int c=0;
            if(grid[i][j]!=0) c=1;
            if(k+c>K) return -1;
            return grid[i][j];
        }
        if(i==grid.length||j==grid[0].length) return -1;
        if(dp[i][j][k]!=null) return dp[i][j][k];
        int ans1=-1,ans2=-1;
        int c=0;
        if(grid[i][j]!=0) c=1;
        if(c+k<=K){
            ans1=find(grid,i+1,j,k+c,K);
            ans2=find(grid,i,j+1,k+c,K);
        }
        if(ans1==-1&&ans2==-1) return dp[i][j][k]=-1;
        return dp[i][j][k]=grid[i][j]+Math.max(ans1,ans2);
    }
}