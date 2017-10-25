/**
 * A method to handle the coordinates and the movement on the board
 * 
 * NOTE: There are two teams ( 1 and 2 ). Board: 0 - empty space 1 - team one 2 - team two 3 - possible move
 * 
 * @author Paulius Kuzmickas 
 * @version 1.0
 */
public class Board
{
    private int[][] board, theBoardOfChange;
    private final int ROWS = 8;
    private final int COLUMNS = 8;
    private int player1Pieces = 2;
    private int player2Pieces = 2;
    
    /**
     * A contructor to the board class.
     * 
     */
    public Board() {
        board = new int[ROWS][COLUMNS];
        theBoardOfChange = new int[ROWS][COLUMNS];
        for(int i=0; i<ROWS; i++)
        {
            for(int j=0; j<COLUMNS; j++)
            {
                board[i][j] = 0; 
            }
        }
        board[ROWS/2-1][COLUMNS/2-1]=1;
        board[ROWS/2][COLUMNS/2-1]=2;
        board[ROWS/2-1][COLUMNS/2]=2;
        board[ROWS/2][COLUMNS/2]=1;

    }
    /**
     * A method to return the number of rows.
     * 
     * @return returns the number of rows.
     */
    public int getRows()
    {
        return ROWS;
    }
    /**
     * A method to return the number of rows.
     * 
     * @return returns the number of columns.
     */
    public int getColumns()
    {
        return COLUMNS;
    }
    /**
     * A method to return the board 2d array
     * 
     * @return returns the game board 2d array
     */
    public int[][] getBoard()
    {
        return board;
    }
    /**
     * A method set the board 2d array to a specific one (mainly for loading)
     * 
     * @param Board a 2d array
     */
    public void setBoard(int[][] Board)
    {
        board = Board;
    }
    /**
     * A method to add a piece in a specific row, column and team.
     * 
     * @param row the row to put the piece in
     * @param column the column to put the piece in
     * @param team the team of the piece to put
     */
    public void addPiece(int row, int column, int team)
    {
        board[row][column]=team;
        if(team==1) {
             addPlayer1Piece();
        }
        if(team==2) {
             addPlayer2Piece();
        }
        for(int i=0; i<ROWS; i++)
        {
            for(int j=0; j<COLUMNS; j++)
            {
                if(board[i][j]==3) {
                    board[i][j]=0;
                    
                }
                theBoardOfChange[i][j] = 0;
            }
        }
        int end = 0;
        boolean change = false;
        if(column+1<COLUMNS && board[row][column+1]!=team && board[row][column+1]!=0 && board[row][column+1]!=3) { //right straight
            for(int i = column+1; i<COLUMNS; i++)
            {
                
                if(board[row][i]==team) {
                    change = true;
                    end = i;
                   
                    break;
                }
                if(board[row][i]==0) {
                    break;
                }
                
            }
            if(change) {
                for(int i=column+1; i<end; i++)
                {
                    board[row][i]=team;
                    if(team==1) {
                        addPlayer1Piece();
                        decPlayer2Piece();
                    }
                    if(team==2) {
                        addPlayer2Piece();
                        decPlayer1Piece();
                    }
                }
                change = false;
                end = 0;
            }
        }
        if(column-1>0 && board[row][column-1]!=team && board[row][column-1]!=0 && board[row][column-1]!=3) { //left straight
            for(int i = column-1; i>=0; i--)
            {
                
                if(board[row][i]==team) {
                    change = true;
                    end = i;
                    
                    break;
                }
                if(board[row][i]==0) {
                    break;
                }
                
            }
            if(change) {
                for(int i=column-1; i>end; i--)
                {
                    board[row][i]=team;
                    if(team==1) {
                        addPlayer1Piece();
                        decPlayer2Piece();
                    }
                    if(team==2) {
                        addPlayer2Piece();
                        decPlayer1Piece();
                    }
                }
                change = false;
                end = 0;
            }
        }
        if(row+1<ROWS && board[row+1][column]!=team && board[row+1][column]!=0 && board[row+1][column]!=3) { //down straight
            for(int i = row + 1; i<ROWS; i++)
            {
                if(board[i][column]==team) {
                    change = true;
                    end = i;
                    
                    break;
                }
                if(board[i][column]==0) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row + 1; i<end; i++)
                {
                    board[i][column]=team;
                    if(team==1) {
                        addPlayer1Piece();
                        decPlayer2Piece();
                    }
                    if(team==2) {
                        addPlayer2Piece();
                        decPlayer1Piece();
                    }
                }
                change = false;
                end = 0;
            }
        }
        if(row-1>0 && board[row-1][column]!=team && board[row-1][column]!=0 && board[row-1][column]!=3) { //up straight
            for(int i = row - 1; i>=0; i--)
            {
                if(board[i][column]==team) {
                    change = true;
                    end = i;
                    
                    break;
                }
                if(board[i][column]==0) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row - 1; i>end; i--)
                {
                    board[i][column]=team;
                    if(team==1) {
                        addPlayer1Piece();
                        decPlayer2Piece();
                    }
                    if(team==2) {
                        addPlayer2Piece();
                        decPlayer1Piece();
                    }
                }
                change = false;
                end = 0;
            }
        }
        if(row + 1<ROWS && column + 1 <COLUMNS && board[row+1][column+1]!=team && board[row+1][column+1]!=0 && board[row+1][column+1]!=3) { // right diag down
            for(int i=row+1, j=column+1; i<ROWS && j<COLUMNS; i++, j++)
            {
                if(board[i][j]==team) {
                    change = true;
                    end = i;
                    
                    break;
                }
                if(board[i][j]==0) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row+1, j=column+1; i<end; i++, j++)
                {
                    board[i][j]=team;
                    if(team==1) {
                        addPlayer1Piece();
                        decPlayer2Piece();
                    }
                    if(team==2) {
                        addPlayer2Piece();
                        decPlayer1Piece();
                    }
                }
                change = false;
                end = 0;
            }
        }
        if(row + 1<ROWS && column - 1 >0 && board[row+1][column-1]!=team && board[row+1][column-1]!=0 && board[row+1][column-1]!=3) { // left diag down
            for(int i=row+1, j=column-1; i<ROWS && j>=0; i++, j--)
            {
                if(board[i][j]==team) {
                    change = true;
                    end = i;
                   
                    break;
                }
                if(board[i][j]==0) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row+1, j=column-1; i<end; i++, j--)
                {
                    board[i][j]=team;
                    if(team==1) {
                        addPlayer1Piece();
                        decPlayer2Piece();
                    }
                    if(team==2) {
                        addPlayer2Piece();
                        decPlayer1Piece();
                    }
                }
                change = false;
                end = 0;
            }
        }
        if(row - 1>0 && column - 1 >0 && board[row-1][column-1]!=team && board[row-1][column-1]!=0 && board[row-1][column-1]!=3) { // left diag up
            for(int i=row-1, j=column-1; i>=0 && j>=0; i--, j--)
            {
                if(board[i][j]==team) {
                    change = true;
                    end = i;
                    
                    break;
                }
                if(board[i][j]==0) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row-1, j=column-1; i>end; i--, j--)
                {
                    board[i][j]=team;
                    if(team==1) {
                        addPlayer1Piece();
                        decPlayer2Piece();
                    }
                    if(team==2) {
                        addPlayer2Piece();
                        decPlayer1Piece();
                    }
                }
                change = false;
                end = 0;
            }
        }
        if(row - 1>0 && column + 1 <COLUMNS && board[row-1][column+1]!=team && board[row-1][column+1]!=0 && board[row-1][column+1]!=3) { // right diag up
            for(int i=row-1, j=column+1; i>=0 && j<COLUMNS; i--, j++)
            {
                if(board[i][j]==team) {
                    change = true;
                    end = i;
                    
                    break;
                }
                if(board[i][j]==0) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row-1, j=column+1; i>end; i--, j++)
                {
                    board[i][j]=team;
                    if(team==1) {
                        addPlayer1Piece();
                        decPlayer2Piece();
                    }
                    if(team==2) {
                        addPlayer2Piece();
                        decPlayer1Piece();
                    }
                }
                change = false;
                end = 0;
            }
        }
        
    }
    /**
     * A method to get the number of player 1 pieces
     * 
     * @return returns the number of player 1 pieces
     */
    public int getPlayer1Pieces()
    {
        return player1Pieces;
    }
    /**
     * A method to get the number of player 1 pieces
     * 
     * @param pieces the number of pieces to assign
     * @return returns the number of player 1 pieces
     */
    public void setPlayer1Pieces(int pieces)
    {
        player1Pieces = pieces;
    }
    /**
     * A method to add a piece to the player 1 piece number
     * 
     */
    public void addPlayer1Piece()
    {
        player1Pieces++;

    }
    /**
     * A method to subtract a piece to the player 1 piece number
     * 
     */
    public void decPlayer1Piece()
    {
        player1Pieces--;

    }
    /**
     * A method to add a piece to the player 2 piece number
     * 
     */
    public void addPlayer2Piece()
    {
        player2Pieces++;

    }
    /**
     * A method to subtract a piece to the player 2 piece number
     * 
     */
    public void decPlayer2Piece()
    {
        player2Pieces--;

    }
    /**
     * A method to get the number of player 2 pieces
     * 
     * @return returns the number of player 2 pieces
     */
    public int getPlayer2Pieces()
    {
        return player2Pieces;
    }
    /**
     * A method to get the number of player 2 pieces
     * 
     * @param pieces the number of pieces to assign
     * @return returns the number of player 2 pieces
     */
    public void setPlayer2Pieces(int pieces)
    {
        player2Pieces = pieces;
    }
    /**
     * A method to get the possible moves for a specific team, assign the number to 3 in the board 2d array
     * 
     * @param team the team to find the possible moves to
     */
    public void getPossibleMoves(int team)
    {
        boolean skip = false; // boolean to stop searching if already found (for possible moves)
        for(int i=0; i<ROWS; i++)
        {
            for(int j=0; j<COLUMNS; j++)
            {
                skip = false;
                if(board[i][j]==0) {
                    if(i+1<ROWS && board[i+1][j]!=0 && board[i+1][j]!=team && board[i+1][j]!=3) { //down check
                        for(int k=i+2; k<ROWS; k++) 
                        {
                            if(board[k][j]==team) {
                                board[i][j] = 3;
                                skip = true;
                                break;
                            }
                            if(board[k][j]==0 || board[k][j]==3) {
                                break;
                            }
                        }
                    }
                    if(skip) continue;
                    if(i-1>0 && board[i-1][j]!=0 && board[i-1][j]!=team && board[i-1][j]!=3) { //up check
                        for(int k=i-2; k>=0; k--) 
                        {
                            if(board[k][j]==team) {
                                board[i][j] = 3;
                                skip = true;
                                break;
                            }
                            if(board[k][j]==0 || board[k][j]==3) {
                                break;
                            }
                        }
                    }
                    if(skip) continue;
                    if(j+1<COLUMNS && board[i][j+1]!=0 && board[i][j+1]!=team && board[i][j+1]!=3) { //right check
                        for(int k=j+2; k<COLUMNS; k++) 
                        {
                            if(board[i][k]==team) {
                                board[i][j] = 3;
                                skip = true;
                                break;
                            }
                            if(board[i][k]==0 || board[i][k]==3) {
                                break;
                            }
                        }
                    }
                    if(skip) continue;
                    if(j-1>0 && board[i][j-1]!=0 && board[i][j-1]!=team && board[i][j-1]!=3) { //left check
                        for(int k=j-2; k>=0; k--) 
                        {
                            if(board[i][k]==team) {
                                board[i][j] = 3;
                                skip = true;
                                break;
                            }
                            if(board[i][k]==0 || board[i][k]==3) {
                                break;
                            }
                        }
                    }
                    if(skip) continue;
                    if(i+1<ROWS && j+1<COLUMNS && board[i+1][j+1]!=0 && board[i+1][j+1]!=team && board[i+1][j+1]!=3) { //diag right down
                        for(int k=i+2, l=j+2; k<ROWS && l<COLUMNS; k++, l++) 
                        {
                            if(board[k][l]==team) {
                                board[i][j] = 3;
                                skip = true;
                                break;
                            }
                            if(board[k][l]==0 || board[k][l]==3) {
                                break;
                            }
                        }
                    }
                    if(skip) continue;
                    if(i-1>0 && j-1>0 && board[i-1][j-1]!=0 && board[i-1][j-1]!=team && board[i-1][j-1]!=3) { //diag left up
                        for(int k=i-2, l=j-2; k>=0 && l>=0; k--, l--) 
                        {
                            if(board[k][l]==team) {
                                board[i][j] = 3;
                                skip = true;
                                break;
                            }
                            if(board[k][l]==0 || board[k][l]==3) {
                                break;
                            }
                        }
                    }
                    if(skip) continue;
                    if(i+1<ROWS && j-1>0 && board[i+1][j-1]!=0 && board[i+1][j-1]!=team && board[i+1][j-1]!=3) { //diag left down
                        for(int k=i+2, l=j-2; k<ROWS && l>=0; k++, l--) 
                        {
                            if(board[k][l]==team) {
                                board[i][j] = 3;
                                skip = true;
                                break;
                            }
                            if(board[k][l]==0 || board[k][l]==3) {
                                break;
                            }
                        }
                    }
                    if(skip) continue;
                    if(i-1>0 && j+1<COLUMNS && board[i-1][j+1]!=0 && board[i-1][j+1]!=team && board[i-1][j+1]!=3) { //diag right up
                        for(int k=i-2, l=j+2; k>=0 && l<COLUMNS; k--, l++) 
                        {
                            if(board[k][l]==team) {
                                board[i][j] = 3;
                                skip = true;
                                break;
                            }
                            if(board[k][l]==0 || board[k][l]==3) {
                                break;
                            }
                        }
                    }
                    if(skip) continue;
                    
                }
            }
        }
    }
    /**
     * A method to refresh the board of change. Set all the values to zero.
     */
    public void refreshBoardOfChange()
    {
        for(int i=0; i<ROWS; i++)
        {
            for(int j=0; j<COLUMNS; j++)
            {
                theBoardOfChange[i][j]=0;
            }
        }
    }
    /**
     * Gets a 2d array which shows all of the pieces that will be changed after a move in a specific place.
     * 
     * @param row the row from which to do the finding
     * @param column the column from which to do the finding
     * @param team the team for which to do the finding
     * @return returns a 2d array of the board of change
     */
    public int[][] getBoardOfChange(int row, int column, int team)
    {
        refreshBoardOfChange();
        int end = 0;
        boolean change = false;
        if(column+1<COLUMNS && board[row][column+1]!=team && board[row][column+1]!=0 && board[row][column+1]!=3) { //right straight
            for(int i = column+1; i<COLUMNS; i++)
            {
                
                if(board[row][i]==team) {
                    change = true;
                    end = i;
                    break;
                }
                if(board[row][i]==0 || board[row][i]==3) {
                    break;
                }
                
                
            }
            if(change) {
                for(int i=column+1; i<end; i++)
                {
                     theBoardOfChange[row][i] = 1;
                    
                }
                change = false;
                end = 0;
            }
        }
        if(column-1>0 && board[row][column-1]!=team && board[row][column-1]!=0 && board[row][column-1]!=3) { //left straight
            for(int i = column-1; i>=0; i--)
            {
                
                if(board[row][i]==team) {
                    change = true;
                    end = i;
                    break;
                }
                if(board[row][i]==0 || board[row][i]==3) {
                    break;
                }
                
            }
            if(change) {
                for(int i=column-1; i>end; i--)
                {
                    theBoardOfChange[row][i] = 1;
                    
                }
                change = false;
                end = 0;
            }
        }
        if(row+1<ROWS && board[row+1][column]!=team && board[row+1][column]!=0 && board[row+1][column]!=3) { //down straight
            for(int i = row + 1; i<ROWS; i++)
            {
                if(board[i][column]==team) {
                    change = true;
                    end = i;
                    break;
                }
                if(board[i][column]==0 || board[i][column]==3) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row + 1; i<end; i++)
                {
                    theBoardOfChange[i][column] = 1;
                    
                }
                change = false;
                end = 0;
            }
        }
        if(row-1>0 && board[row-1][column]!=team && board[row-1][column]!=0 && board[row-1][column]!=3) { //up straight
            for(int i = row - 1; i>=0; i--)
            {
                if(board[i][column]==team) {
                    change = true;
                    end = i;
                    break;
                }
                if(board[i][column]==0 || board[i][column]==3) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row - 1; i>end; i--)
                {
                    theBoardOfChange[i][column] = 1;
                    
                }
                change = false;
                end = 0;
            }
        }
        if(row + 1<ROWS && column + 1 <COLUMNS && board[row+1][column+1]!=team && board[row+1][column+1]!=0 && board[row+1][column+1]!=3) { // right diag down
            for(int i=row+1, j=column+1; i<ROWS && j<COLUMNS; i++, j++)
            {
                if(board[i][j]==team) {
                    change = true;
                    end = i;
                    break;
                }
                if(board[i][j]==0 || board[i][j]==3) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row+1, j=column+1; i<end; i++, j++)
                {
                    theBoardOfChange[i][j] = 1;
                    
                }
                change = false;
                end = 0;
            }
        }
        if(row + 1<ROWS && column - 1 >0 && board[row+1][column-1]!=team && board[row+1][column-1]!=0 && board[row+1][column-1]!=3) { // left diag down
            for(int i=row+1, j=column-1; i<ROWS && j>=0; i++, j--)
            {
                if(board[i][j]==team) {
                    change = true;
                    end = i;
                    break;
                }
                if(board[i][j]==0 || board[i][j]==3) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row+1, j=column-1; i<end; i++, j--)
                {
                    theBoardOfChange[i][j] = 1;
                    
                }
                change = false;
                end = 0;
            }
        }
        if(row - 1>0 && column - 1 >0 && board[row-1][column-1]!=team && board[row-1][column-1]!=0 && board[row-1][column-1]!=3) { // left diag up
            for(int i=row-1, j=column-1; i>=0 && j>=0; i--, j--)
            {
                if(board[i][j]==team) {
                    change = true;
                    end = i;
                    break;
                }
                if(board[i][j]==0 || board[i][j]==3) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row-1, j=column-1; i>end; i--, j--)
                {
                    theBoardOfChange[i][j] = 1;
                    
                }
                change = false;
                end = 0;
            }
        }
        if(row - 1>0 && column + 1 <COLUMNS && board[row-1][column+1]!=team && board[row-1][column+1]!=0 && board[row-1][column+1]!=3) { // right diag up
            for(int i=row-1, j=column+1; i>=0 && j<COLUMNS; i--, j++)
            {
                if(board[i][j]==team) {
                    change = true;
                    end = i;
                    break;
                }
                if(board[i][j]==0 || board[i][j]==3) {
                    break;
                }
                
            }
            if(change) {
                for(int i=row-1, j=column+1; i>end; i--, j++)
                {
                    theBoardOfChange[i][j] = 1;
                    
                }
                change = false;
                end = 0;
            }
        }
        return theBoardOfChange;
    }
 
}
