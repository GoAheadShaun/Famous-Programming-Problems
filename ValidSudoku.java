class ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        int n = 9;
        
        int[] rows = new int[n];
        int[] cols = new int[n];
        int[] boxes = new int[n];

        
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (board[i][j] == '.') continue;
                
                int pos = board[i][j] - '1';
                int mask = 1 << pos;
                
                if ((rows[i] & mask) != 0)
                    return false;
                rows[i] |= mask;
                
                if ((cols[j] & mask) != 0)
                    return false;
                cols[j] |= mask;
                
                int boxI = (i / 3) * 3 + (j / 3);
                if ((boxes[boxI] & mask) != 0)
                    return false;
                boxes[boxI] |= mask;
            }
        }
        
        return true;
    }
}
