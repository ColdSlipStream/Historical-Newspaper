import java.util.ArrayList;

/**
 * Created by Kevin on 11/15/2014.
 */
public class ParseData {
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> warData = new ArrayList<String>();

    private ArrayList<String> presidentData = new ArrayList<String>();



    public void parseWikiData(String fullWeb)
    {

        data.add(fullWeb);

    }

    public void parseWarWikiData(ArrayList<String> warDataIn, int year)
    {
        boolean isDesignated = false;
        boolean isDesignated2 = false;
        boolean isEnding = false;
        boolean isBeginning = false;

        boolean checkedFirst = false;

        int derivedYear = 0;

        for (String s : warDataIn)
        {

            // Checks if second line contains year as well - -war beginning ending same year.
            if ((checkedFirst == true) && (isDesignated2 == false))
            {
                if (s.length() > 2)
                {
                    s = s.substring(1, s.length());

                    //Gets rid of spaces in front of year, if present
                    if (s.indexOf(' ') == 0)
                    {
                        s = s.substring(1, s.length());
                    }

                    //  System.out.println(s);
                    //Catches Number format exception
                    try {
                        derivedYear = Integer.parseInt(s);
                        // System.out.println(derivedYear);
                    }
                    catch (NumberFormatException e)
                    {
                        derivedYear = -1;
                    }
                    if(derivedYear == year)
                    {
                        //warData.add(s);
                        isDesignated2 = true;
                        isEnding = true;
                    }


                }

                checkedFirst = false;

            }


            if (isDesignated)
            {
                //Checks if valid number format
                if (s.length() > 2)
                {
                    s = s.substring(1, s.length());

                    //Gets rid of spaces in front of year, if present
                    if (s.indexOf(' ') == 0)
                    {
                        s = s.substring(1, s.length());
                    }
                }
                // System.out.println("Hits first check");
                //Catches Number format exception
                try {
                    derivedYear = Integer.parseInt(s);
                    //  System.out.println(derivedYear);
                }
                catch (NumberFormatException e)
                {
                    //  System.out.println("Fails numberFormat");
                    derivedYear = -1;
                }
                if (derivedYear == year)
                {
                    //warData.add(s);
                    //System.out.println(derivedYear);
                    isBeginning = true;

                }
                isDesignated = false;
                checkedFirst = true;

            }


            //ADDS STRING TO ARRAYLIST
            //ADDS BEGINNING/ENDING
            //REMOVES EXTRANEOUS SYMBOLS
            if ((isBeginning || isEnding) && (s.contains("[[")))
            {
                if ( s.length() < 200) {
                    //Strips html remnants
                    if ((s.indexOf('<') != -1) && (s.indexOf('>') != -1)) {
                        int initHTML = s.indexOf('<');
                        int endHTML = s.indexOf('>', initHTML + 1);

                        String cleanedVersion = s.substring(0, initHTML);
                        cleanedVersion += s.substring(endHTML + 1, s.length());
                        s = cleanedVersion;
                    }

                    //remove |[[ in front of line

                    if (s.indexOf("[[") != -1)
                    {
                        int bracketBegin = s.indexOf("[[");
                        s = s.substring(bracketBegin + 2, s.length());
                    }
                    else {
                        s = s.substring(3, s.length());
                    }
                    s = s.substring(0, s.length() - 2);
                    // System.out.println(isEnding);
                    if (isEnding) {
                        if (isBeginning) {

                            s += " begins and ends";
                        } else {
                            s += " ends";
                        }

                    } else {
                        if (isBeginning) {
                            s += " begins";
                        }

                    }

                    isDesignated = false;
                    isDesignated2 = false;
                    isEnding = false;
                    isBeginning = false;

                    warData.add("* " + s);
                }
            }

            //Determines if the symbol for year following
            if (s.contains("|-"))
            {
                // System.out.println("Hits");
                isDesignated = true;
            }
            else
            {
                isDesignated = false;
            }
        }
    }

    public void parsePresidents(ArrayList<String> overallData) {
        String president = "";
        for (String s : overallData) {
            if (s.contains("United States") || s.contains("America")) {
                if (s.contains(" President")) {
                    int index = s.indexOf("President");

                    if (s.charAt(index - 1) != '[') {
                        int secondIndex = s.indexOf("[[", index) + 2;
                        int finalBracket = s.indexOf("]]", secondIndex);

                        s = cleanHTML(s);
                        presidentData.add(s);




                    }
                } else if (s.contains("; [[President")) {
                    int initialIndex = s.indexOf("; [[President");

                    int secondSet = s.indexOf("[[", initialIndex + 5);

                    int thirdSet = s.indexOf("]]", secondSet);

                    s = cleanHTML(s);
                    presidentData.add(s);


                } else if (s.contains("[[President")) {
                    int initialIndex = s.indexOf("[[");
                    int secondSet = s.indexOf("]]", initialIndex + 2);

                    s = cleanHTML(s);
                    presidentData.add(s);
                }


            }
        }

    }
    private String cleanHTML(String data)
    {
        int index = data.indexOf("<");
        if (index != -1)
        {
            int secondIndex = data.indexOf("/");
            data = data.substring(0, index);
        }
        return data;
    }
    public ArrayList<String> getData()
    {
        return data;

    }

    public ArrayList<String> getWarData()
    {
        return warData;
    }

    public ArrayList<String> getPresidentData()
    {
        return presidentData;
    }


}
