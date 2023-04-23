package checkers;

public class Board
{
	private Pieces[][] b = new Pieces[9][9];
	
	//creates the board and fills it out. b is b[y][x]
	public Board ()
	{
		for (int i = 1; i < 9; i++)
		{
			b[0][i] = new Pieces (0, i-1, String.valueOf(i));
		}
		
		for (int i = 1; i < 9; i++)
		{
			b[i][0] = new Pieces (i-1, 0, String.valueOf(i));
		}
		
		b[0][0] = new Pieces (0, 0, "0");
		
		
		for (int i = 1; i < 9; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				Pieces p = new Pieces(j, i);
				b[i][j] = p;
			}
		}
		
		for (int i = 1; i < 4; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				if (i%2 != 0 || j%2 != 0)
				{
					if (i%2 != j%2)
					{
						Pieces p = new Pieces(j, i, "w");
						b[i][j] = p;
					}
				}
			}
		}
		
		for (int i = 6; i < 9; i++)
		{
			for (int j = 1; j < 9; j++)
			{
				if (i%2 != 0 || j%2 != 0)
				{
					if (i%2 != j%2)
					{
						Pieces p = new Pieces(j, i, "b");
						b[i][j] = p;
					}
				}
			}
		}
	}
	
	public void printBoard ()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				System.out.print(b[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public Pieces getPiece (int x, int y)
	{
		return b[y][x];
	}
	
	public Pieces getDiag (int x1, int y1, int x2, int y2)
	{
		return b[(y1+y2)/2][(x1+x2)/2];
	}
	
	public void movePiece (int xOrig, int yOrig, int xNew, int yNew)
	{
		Pieces piece = getPiece(xOrig, yOrig);
		if (yNew == 1 || yNew == 8)
			piece.makeKing(true);
		Pieces temp = b[yNew][xNew];
		piece.setX(xNew);
		piece.setY(yNew);
		b[yNew][xNew] = piece;
		temp.setX(xOrig);
		temp.setY(yOrig);
		b[yOrig][xOrig] = temp;
	}
	
	public void killPiece (int x, int y)
	{
		b[y][x].setPlayer("_");
	}
	
	public boolean winner (String player)
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (!b[i][j].getPlayer().toUpperCase().equals(player))
				{
					return false;
				}
			}
		}
		return true;
	}
}



