class Solution {
    public int minFlips(String s) {
        if(s.length()<3) return 0;
        int c=0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='1') c++;
        }
        int n=s.length();
        if(c==n||c==0||c==1) return 0;
        if(s.charAt(0)=='1'&&s.charAt(n-1)=='1'){
            return Math.min(c-2,n-2-(c-2));
        }else{
            return Math.min(c-1,n-c);
        }
    }
}
