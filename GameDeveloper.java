public class GameDeveloper
{
  private int developerID;
  private String developerName;
  GameDeveloper(int aDeveloperID, String aDeveloperName)
  {
    developerID = aDeveloperID;
	developerName = aDeveloperName;
  }
  public int getDeveloperID()
  {
    return developerID;
  }
  public String getDeveloperName()
  {
    return developerName;
  }
} 