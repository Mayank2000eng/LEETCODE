class Solution {
    public char[][] rotateTheBox(char[][] box) {
        for(int i=0;i<box.length;i++){
            for(int j=box[0].length-1;j>=0;j--){
                if(box[i][j]=='#'){
                int jj=j;
                while(jj<box[0].length-1&&box[i][jj+1]=='.'){
                    box[i][jj]='.';
                    box[i][jj+1]='#';
                    jj++;
                }
                }
            }
        }
        char[][] new_box=new char[box[0].length][box.length];
        for(int i=0;i<box.length;i++){
            for(int j1=0;j1<box[0].length;j1++){
                int i_new=j1;
                int j_new=box.length-1-i;
                new_box[i_new][j_new]=box[i][j1];
            }
        }
        return new_box;
    }
}
