import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Kevin on 11/15/2014.
 */
public class HTMLRetriever {
    private BufferedReader rd;
    private ArrayList<String> webLines = new ArrayList<String>();


    public HTMLRetriever(String webPage)
    {

        URL url = null;
        try
        {
            url = new URL(webPage);
        } catch (MalformedURLException e) {
            System.out.println("WebUrl Failure. HTMLRetriever Constructor");
            e.printStackTrace();
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println("Failure to Establish Connection. HTMLRetriever Constructor");
            e.printStackTrace();
        }

        try {
            rd = new BufferedReader (new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            System.out.println("Failure to open BufferedReader");
            e.printStackTrace();
        }

    }


    public void pullData()
    {
        String lineIn;
        try {
            //Checks if line is not null
            while (((lineIn = rd.readLine()) != null)) {

                webLines.add(lineIn);

            }

        }catch(IOException e){
            System.out.println("Read Line Error. scrapeHTML -- HTMLRetriever");
            e.printStackTrace();
        }


    }

    public ArrayList<String> getPulledData()
    {
        return webLines;
    }

    public void printData()
    {
        for (String s : webLines)
        {
            System.out.println(s);
        }


    }


}
