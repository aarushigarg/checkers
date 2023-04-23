package checkers;

public class Pieces
{
  private int xCor;
  private int yCor;
  private boolean king;
  private String player;
  
  public Pieces (int x, int y)
  {
	xCor = x;
	yCor = y;
	king = false;
	player = "_";
  }
  
  public Pieces (int x, int y, String p)
  {
    xCor = x;
    yCor = y;
    player = p;
  }
  
  public int getX ()
  {
    return xCor;
  }
  
  public int getY ()
  {
    return yCor;
  }
  
  public boolean kingStatus ()
  {
    return king;
  }
  
  public String getPlayer ()
  {
	  return player;
  }
  
  public void setX (int x)
  {
    xCor = x;
  }
  
  public void setY (int y)
  {
    yCor = y;
  }
  
  public void setPlayer (String p)
  {
	  player = p;
  }
  
  public void makeKing (boolean k)
  {
    king = k;
    player = player.toUpperCase();
  }
  
  public String toString ()
  {
	return player;
  }
}