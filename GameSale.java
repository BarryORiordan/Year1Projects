public class GameSale
{
  private int    gameID;
  private int    gameSaleUnits;
  private int    monthID;
  
  GameSale(int aGameID, int aSaleUnitValue, int aMonthID)
  {
    gameID = aGameID;
	gameSaleUnits = aSaleUnitValue;
	monthID = aMonthID;
  }
  public int getGameID()
  {
    return gameID;
  }
  public int getSaleUnits()
  {
    return gameSaleUnits;
  }
  public int getMonthID()
  {
    return monthID;
  }
} 