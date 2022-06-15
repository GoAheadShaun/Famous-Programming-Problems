class SudokuSolver {
    private int n = 9;
    private int N = n*n;
    
    public void solveSudoku(char[][] board) {
        int[] rows = new int[n], cols = new int[n], boxes = new int[n];
        
        // populate rows, cols and boxes with present values in the grid
        for (int r=0; r<n; r++) {
            for (int c=0; c<n; c++) {
                if (board[r][c] == '.') continue;
                int pos = board[r][c] - '1';
                int boxI = (r / 3) * 3 + (c / 3);
                int mask = 1 << pos;

                rows[r] |= mask;
                cols[c] |= mask;
                boxes[boxI] |= mask;
            }
        }
        
        // begin backtracking at cell 0
        backtrack(board, 0, rows, cols, boxes);
    }
    
    private boolean backtrack(char[][] board, int x, int[] rows, int[] cols, int[] boxes) {
        if (x >= N) return true; // backtrack successful if we've reached the end!
        int r = x/n;
        int c = x%n;

        // skipping numbers until we find a '.' which is what we can work with
        while (board[r][c] != '.') {
            if (x >= N) return true; 
            r = x/n;
            c = x%n;
            x++;
        }
        
        // in case we skipped a '.'
        if (x > 0 && board[(x-1)/n][(x-1)%n] == '.') x--;
        
        // now that we're at a '.', check through all numbers from 1 to 9 
        // the idea is that we check each valid number one-by-one.
        // if a future backtrack reveals that '1' doesn't work, we can then try '2' and so on.
        for (int i=1; i<=n; i++) {
            int mask = 1 << (i-1);
            int boxI = (r / 3) * 3 + (c / 3);
            
            // if the number isn't valid, skip it
            if (!isValid(r, c, boxI, mask, rows, cols, boxes)) continue;
            
            // update the board and other arrays accommodating for the number we're currently checking: i
            board[r][c] = (char) (i + '0'); 
            rows[r] |= mask;
            cols[c] |= mask;
            boxes[boxI] |= mask;
            
            // backtrack. if this backtrack was successful that means we found the solution and we 
            // don't need to backtrack anymore
            if (backtrack(board, x+1, rows, cols, boxes)) return true;
            
            // otherwise, we know our current number, i, didn't work
            // thus, we undo our change and try again
            board[r][c] = '.';
            rows[r] ^= mask;
            cols[c] ^= mask;
            boxes[boxI] ^= mask;
        }
        
        // if no numbers from 1-9 are successful, we reached a dead-end 
        return false;
    }
    
    private boolean isValid(int r, int c, int boxI, int mask, int[] rows, int[] cols, int[] boxes) {
        if ((rows[r] & mask) != 0) 
            return false;
        
        if ((cols[c] & mask) != 0) 
            return false;
        
        if ((boxes[boxI] & mask) != 0) 
            return false;
        
        return true;
    }
}
