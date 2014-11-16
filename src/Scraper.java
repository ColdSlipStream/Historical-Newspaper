import java.util.ArrayList;

/**
 * Created by Kevin on 11/15/2014.
 */
public class Scraper {
    private static ArrayList<String> data = new ArrayList<String>();

    public static ArrayList<String> retrieveOverallData(String webURL)
    {
        HTMLRetriever retriever = new HTMLRetriever(webURL);
        retriever.pullData();
        data = retriever.getPulledData();
       // retriever.printData();
        return data;


    }


}
