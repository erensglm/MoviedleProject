package vcs.testvcs1;

public class Movie {
    //No;Title;Year;Genre;Origin;Director;Star;
    private final int No;
    private final String Title;
    private final int Year;
    private final String Genre;
    private final String Origin;
    private final String Director;
    private final String Star;

    public Movie(int no, String title, int year, String genre, String origin, String director, String star) {
        No = no;
        Title = title;
        Year = year;
        Genre = genre;
        Origin = origin;
        Director = director;
        Star = star;
    }


    public int getNo() {
        return No;
    }

    public String getTitle() {
        return Title;
    }

    public int getYear() {
        return Year;
    }

    public String getGenre() {
        return Genre;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getDirector() {
        return Director;
    }

    public String getStar() {
        return Star;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "No=" + No +
                ", Title='" + Title + '\'' +
                ", Year=" + Year +
                ", Genre='" + Genre + '\'' +
                ", Origin='" + Origin + '\'' +
                ", Director='" + Director + '\'' +
                ", Star='" + Star + '\'' +
                '}';
    }

}
