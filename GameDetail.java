public class GameDetail
{
  private int    gameID;
  private String gameTitle;
  private int    genreID;
  private int    developerID;
  private double gamePrice;
  
  GameDetail(int aGameID, String aGameTitle,
             int aGenreID, int aDeveloperID,
			 double aGamePrice)
  {
    gameID      = aGameID;
	gameTitle   = aGameTitle;
	genreID     = aGenreID;
	developerID = aDeveloperID;
	gamePrice   = aGamePrice;
  }
  public int getGameID()
  {
    return gameID;
  }
  public String getGameTitle()
  {
    return gameTitle;
  }
  public int getGenreID()
  {
    return genreID;
  }
  public int getDeveloperID()
  {
    return developerID;
  }
  public double getGamePrice()
  {
    return gamePrice;
  }
} 