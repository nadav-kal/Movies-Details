import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        String API_KEY = "761a2560035557be488309c5018170dc";
        List<Movie> movies = new ArrayList<>();

        while(true){
            System.out.println("Enter IMDB ID");
            Scanner input = new Scanner(System.in);
            String IMDB_ID = input.nextLine();
            if(IMDB_ID.equals("0")) {
                break;
            }

            try {
                URL url = new URL("http://api.themoviedb.org/3/find/" +
                        IMDB_ID + "?api_key=" + API_KEY + "&language=en-US&external_source=imdb_id");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                String output;
                while ((output = br.readLine()) != null) {
                    String title = findAttribute(output,"\"title\"");
                    System.out.println("Movie title: " + title);

                    String posterPath = findAttribute(output,"\"poster_path\"");
                    URL posterURL = new URL("https://image.tmdb.org/t/p/original" + posterPath);
                    BufferedImage img = ImageIO.read(posterURL);
                    File file = new File("C:/temp" + posterPath);
                    ImageIO.write(img, "jpg", file);

                    double popularity = Double.parseDouble(findAttribute(output,"\"popularity\""));

                    Movie movie = new Movie(title, posterPath, popularity);
                    movies.add(movie);
                }
            }
            catch (Exception e) {
                System.out.println("Movie id not found");
            }
        }

        movies.sort(Comparator.comparingDouble(Movie::getPopularity).reversed());
        if(movies.size() > 0)
            System.out.println("The most popular movie from your search is: " + movies.get(0).getTitle());
        else
            System.out.println("You movies list is empty");
    }

    public static String findAttribute(String movieDetails, String attribute) {
        int i = movieDetails.indexOf(attribute) + attribute.length();
        String foundAttribute = movieDetails.substring(i);
        foundAttribute = foundAttribute.substring(foundAttribute.indexOf(":") + 1, foundAttribute.indexOf(","));
        foundAttribute = foundAttribute.replaceAll("\"", "");
        return foundAttribute;
    }
}