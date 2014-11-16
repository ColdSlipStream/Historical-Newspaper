import java.util.ArrayList;

/**
 * Created by Kevin on 11/15/2014.
 */
public class DeriveData {
    private ArrayList<String> overallData = new ArrayList<String>();

    private ArrayList<String> warData = new ArrayList<String>();

    private ArrayList<String> cleanedWarData = new ArrayList<String>();

    private ArrayList<String> presidentData= new ArrayList<String>();



    private ParseData parser = new ParseData();



    public DeriveData (int year)
    {
        Scraper scrape = new Scraper();
        String url = wikipediaURL(Integer.toString(year));

        overallData = scrape.retrieveOverallData(url);

        if ((year <= 1899) && (year >= 1800))
        {
            url = wikipediaURL("List_of_wars_1800–99");
            warData = scrape.retrieveOverallData(url);
        }
        else if ((year <= 1944) && (year >= 1900))
        {
            url = wikipediaURL("List_of_wars_1900–44");
            warData = scrape.retrieveOverallData(url);
        }
        else if ((year <= 1989) && (year >= 1945))
        {
            url = wikipediaURL("List_of_wars_1945–89");
            warData = scrape.retrieveOverallData(url);
        }
        else if ((year <= 2002) && (year >= 1990))
        {
            url = wikipediaURL("List_of_wars_1990–2002");
            warData = scrape.retrieveOverallData(url);
        }
        else if ((year <= 2010) && (year >= 2003))
        {
            url = wikipediaURL("List_of_wars_2003–10");
            warData = scrape.retrieveOverallData(url);
        }
        else if ((year <= 2014) && year >= 2011)
        {
            url = wikipediaURL("List_of_wars_2011-present");
            warData = scrape.retrieveOverallData(url);
        }

        parseWarData(year);

        parser.parsePresidents(overallData);

        parsePresidentData();





    }

    public ArrayList<String> getOverallData()
    {
        return overallData;
    }

    public ArrayList<String> getWarData()
    {
        return warData;
    }

    public ArrayList<String> getCleanedWarData()
    {
        return cleanedWarData;
    }

    private void parseWarData(int year) {
        parser.parseWarWikiData(warData, year);

        cleanedWarData = parser.getWarData();

    }

    private void parsePresidentData()
    {
        presidentData = parser.getPresidentData();
    }

    public ArrayList<String> getPresidentData()
    {
        return presidentData;
    }

    private void printPresidentData()
    {
        for (String s : presidentData)
        {
            System.out.println(s);
        }
    }




    private void printWarData()
    {
        for (String s : warData)
        {
            System.out.println(s);
        }
    }

    private void printOverallData()
    {
        for (String s : overallData)
        {
            System.out.println(s);
        }
    }

    private void printCleanedWarData()
    {
        for (String s : cleanedWarData)
        {
            System.out.println(s);
        }
    }




    private String wikipediaURL(String title)
    {
        String url = "http://en.wikipedia.org/w/index.php?title=" + title + "&action=raw";
        return url;
    }

    public static void main (String[] args)
    {
        DeriveData derive = new DeriveData(1930);
        //derive.printCleanedWarData();

      //  derive.printPresidentData();

       // derive.printWarData();

    }
}
