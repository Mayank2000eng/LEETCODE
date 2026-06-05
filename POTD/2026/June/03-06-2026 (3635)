class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        return Math.min(find(landStartTime,landDuration,waterStartTime,waterDuration),find( waterStartTime,waterDuration,landStartTime,landDuration));
    }
    static int find(int[] land,int[] land_d,int[] water,int[] water_d){
        int[][] mat=new int[water.length][4];
        for(int i=0;i<water.length;i++){
            mat[i][0]=water[i];
            mat[i][1]=water_d[i];
        }
        Arrays.sort(mat,(x,y)->Integer.compare(x[0],y[0]));
        mat[0][2]=mat[0][1];
        for(int i=1;i<water.length;i++){
            mat[i][2]=Math.min(mat[i][1],mat[i-1][2]);
        }
        mat[mat.length-1][3]=mat[mat.length-1][0]+mat[mat.length-1][1];
        for(int i=water.length-2;i>=0;i--){
            mat[i][3]=Math.min(mat[i][1]+mat[i][0],mat[i+1][3]);
        }
        int min=Integer.MAX_VALUE;
        int min1=Integer.MAX_VALUE;
        for(int i=0;i<land.length;i++){
            int tar=land[i]+land_d[i];
            int l=0,h=water.length-1;
            int ans=-1;
            while(l<=h){
                int m=(h+l)/2;
                if(mat[m][0]>tar){
                    ans=m;
                    h=m-1;
                }else{
                    l=m+1;
                }
            }
            if(ans==-1){
                min=tar+mat[mat.length-1][2];
            }else{
                if(ans==0){
                    min=mat[0][3];
                }else{
                    min=Math.min(tar+mat[ans-1][2],mat[ans][3]);
                }
            }
            min1=Math.min(min,min1);
        }
        return min1;
    }
}
