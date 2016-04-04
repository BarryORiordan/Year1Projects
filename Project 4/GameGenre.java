public class GameGenre
{
  private int genreID;
  private String genreTitle;
  GameGenre(int aGenreID, String aGenreTitle)
  {
    genreID = aGenreID;
	genreTitle = aGenreTitle;
  }
  public int getGenreID()
  {
    return genreID;
  }
  public String getGenreTitle()
  {
    return genreTitle;
  }
} 