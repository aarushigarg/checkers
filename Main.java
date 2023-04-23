package checkers;
import java.util.*;

public class Main
{
	public static void main (String[] args)
	{
		Scanner input = new Scanner (System.in);
		Board b = new Board ();
		
		String player = "B";
		boolean won = false;
		
		while (!won)
		{
			b.printBoard();
			
			Pieces piece = findMovePiece (b, player);
			
			int xNew = 0;
			int yNew = 0;
			
			int[] newSpot = findNewSpot (b, piece, player);
			xNew = newSpot[0];
			yNew = newSpot[1];
			
			boolean killed = false;
			if (Math.abs(piece.getX() - xNew) == 2 && Math.abs(piece.getY() - yNew) == 2)
			{
				kill (b, piece, xNew, yNew);
				killed = true;
			}
			
			b.movePiece(piece.getX(), piece.getY(), xNew, yNew);
			piece.setX (xNew);
			piece.setY (yNew);
			
			boolean killAvail = multipleKill (b, piece);
			while (killAvail && killed)
			{
				b.printBoard();
				System.out.println("Multiple kill available for player " + piece.getPlayer() + ". Input the new spot for the piece.");
				int[] newP = findNewSpot (b, piece, player);
				kill (b, piece, newP[0], newP[1]);
				b.movePiece (piece.getX(), piece.getY(), newP[0], newP[1]);
				piece.setX (newP[0]);
				piece.setY (newP[1]);
				killAvail = multipleKill (b, piece);
			}
			
			if (b.winner (player))
			{
				System.out.println("Player " + player + " has won!");
				won = true;
			}
			
			if (player.equals("B"))
				player = "W";
			else
				player = "B";
		}
	}
	
	//allowes the player to choose a piece to move
	public static Pieces findMovePiece (Board b, String player)
	{
		boolean chosen = false;
		Pieces piece = null;
		while (!chosen)
		{
			int xOrig = findXOrig (player);
			
			int yOrig = findYOrig (player);
			
			piece = b.getPiece(xOrig, yOrig);
			if (piece.getPlayer() == "_")
				System.out.println("That is a blank spot. Choose again");
			else if (!piece.getPlayer().toUpperCase().equals(player))
				System.out.println("That is not your pawn. Choose again");
			else
				chosen = true;
		}
		
		return piece;
	}
	
	//finds where the player wants to move a piece and makes sure it's valid
	public static int[] findNewSpot (Board b, Pieces piece, String player)
	{
		Scanner input = new Scanner (System.in);
		boolean valid = false;
		int[] point = {0, 0};
		while (!valid)
		{	
			int xNew = 0;
			int yNew = 0;

			System.out.println ("Where do you want to move that piece? (X coordinate)");
			xNew = input.nextInt();
			input.nextLine();
			
			System.out.println ("Where do you want to move that piece? (Y coordinate)");
			yNew = input.nextInt();
			input.nextLine();
			
			point[0] = xNew;
			point[1] = yNew;
		
			valid = validMove (b, piece, xNew, yNew);
			if (!valid)
			{
				System.out.println("That move is not valid. Type 1 to change piece. Type 2 to change move spot");
				int c = input.nextInt();
				input.nextLine();
				
				if (c == 2)
				{
					point = findNewSpot (b, piece, player);
					if (point[0] != 0)
						valid = true;
				}
				else
				{
					piece = findMovePiece (b, player);
					point = findNewSpot (b, piece, player);
					if (point[0] != 0)
						valid = true;
				}
			}
		}
		return point;
	}
	
	public static void kill (Board b, Pieces piece, int xNew, int yNew)
	{
		Pieces diag = b.getDiag(piece.getX(), piece.getY(), xNew, yNew);
		b.killPiece(diag.getX(), diag.getY());
	}
	
	//asks the player to say the x coordinate of the piece to move
	public static int findXOrig (String player)
	{
		Scanner input = new Scanner (System.in);
		System.out.println ("Player " + player + " what piece do you want to move? (X coordinate)");
		int xOrig = input.nextInt();
		input.nextLine();
		return xOrig;
	}
	
	//asks the player to say the y coordinate of the piece to move	
	public static int findYOrig (String player)
	{
		Scanner input = new Scanner (System.in);
		System.out.println ("Player " + player + " what piece do you want to move? (Y coordinate)");
		int yOrig = input.nextInt();
		input.nextLine();
		return yOrig;
	}
	
	//finds if double jump is possible and returns piece to jump to
	public static boolean multipleKill (Board b, Pieces piece)
	{
		if (!piece.kingStatus())
		{
			if (piece.getPlayer().equals("b"))
			{
				System.out.println("Got into b");
				if (piece.getX() < 7 && piece.getY() > 2)
					if (validMove(b, piece, piece.getX()+2, piece.getY()-2))
						return true;

				if (piece.getX() > 2 && piece.getY() > 2)
					if (validMove(b, piece, piece.getX()-2, piece.getY()-2))
						return true;
			}
			
			else
			{
				System.out.println("Got into w");
				if (piece.getX() < 7 && piece.getY() < 7)
					if (validMove(b, piece, piece.getX()+2, piece.getY()+2))
						return true;

				if (piece.getX() > 1 && piece.getY() < 7)
					if (validMove(b, piece, piece.getX()-2, piece.getY()+2))
						return true;
			}
		}
		
		else
		{
			if (piece.getX() < 7 && piece.getY() > 2)
				if (validMove(b, piece, piece.getX()+2, piece.getY()-2))
					return true;
	
			if (piece.getX() > 2 && piece.getY() > 2)
				if (validMove(b, piece, piece.getX()-2, piece.getY()-2))
					return true;

			if (piece.getX() < 7 && piece.getY() < 7)
				if (validMove(b, piece, piece.getX()+2, piece.getY()+2))
					return true;

			if (piece.getX() > 1 && piece.getY() < 7)
				if (validMove(b, piece, piece.getX()-2, piece.getY()+2))
					return true;
		}
		return false;
		
	}
	
	//checks if the player is making a valid move
	public static boolean validMove (Board b, Pieces p, int x, int y)
	{
		boolean valid = false;
		Pieces pos = b.getPiece(x, y);
		
		if (!p.kingStatus())
		{
			if (p.getPlayer().equals("b"))
			{
				if (Math.abs(p.getX() - x) == 1 && p.getY() - y == 1 && pos.getPlayer().equals("_"))
					valid = true;
				
				if (Math.abs(p.getX() - x) == 2 && p.getY() - y == 2 && pos.getPlayer().equals("_") && b.getDiag(p.getX(), p.getY(), x, y).getPlayer().toUpperCase().equals("W"))
					valid = true;
			}
			
			if (p.getPlayer().equals("w"))
			{
				if (Math.abs(p.getX() - x) == 1 && p.getY() - y == -1 && pos.getPlayer().equals("_"))
					valid = true;
				
				if (Math.abs(p.getX() - x) == 2 && p.getY() - y == -2 && pos.getPlayer().equals("_") && b.getDiag(p.getX(), p.getY(), x, y).getPlayer().toUpperCase().equals("B"))
					valid = true;
			}
		}
		
		else
		{
			if (Math.abs(p.getX() - x) == 1 && Math.abs(p.getY() - y) == 1 && pos.getPlayer().equals("_"))
				valid = true;
			
			if (Math.abs(p.getX() - x) == 2 && Math.abs(p.getY() - y) == 2 && pos.getPlayer().equals("_") && !b.getDiag(p.getX(), p.getY(), x, y).getPlayer().toUpperCase().equals(p.getPlayer()) && !b.getDiag(p.getX(), p.getY(), x, y).getPlayer().toUpperCase().equals("_"))
				valid = true;
		}
		
		return valid;
	}
}


