import java.util.ArrayList;

/**
 * Created by Brandon on 11/15/14.
 */
public class Sorting {

    public static ArrayList<String> getInventions(ArrayList<String> data) {
        ArrayList<String> inventions = new ArrayList<String>();

        for (String words: data) {
            if ((words.contains("invented") || words.contains("invents")) && !words.contains("<ref>"))  {
                inventions.add(cleanText((words)));
            }
        }

        if (inventions.size() < 1) {
            inventions.add("* Nothing important was invented this year!");
        }
        return inventions;
    }

    public static ArrayList<String> getFounded(ArrayList<String> data) {
        ArrayList<String> founded = new ArrayList<String>();

        for (String words: data) {
            if ((words.contains("founded") || words.contains("found")) && !words.contains("<ref>")) {
                founded.add(cleanText((words)));
            }
        }

        if (founded.isEmpty())
            founded.add("* Nothing important was founded this year!");
        return founded;
    }

    public static ArrayList<String> getBirths(ArrayList<String> data) {
        ArrayList<String> births = new ArrayList<String>();
        boolean foundBirth = false;
        boolean foundDeath = false;
        for (String words: data) {
            if (words.equals("== Deaths ==")) {
                foundDeath = true;
            }
            if (foundBirth) {
                if (hasMonth(words) && !foundDeath && !words.contains("<ref>")) {
                    births.add(cleanText((words)));
                }
            }
            if (words.equalsIgnoreCase("== Births ==")) {
                foundBirth = true;
            }
        }
        return births;
    }

    public static ArrayList<String> getDeaths(ArrayList<String> data) {
        ArrayList<String> deaths = new ArrayList<String>();
        boolean foundDeath = false;
        for (String words: data) {
            if (foundDeath && hasMonth(words) && !words.contains("<ref>") && cleanText(words).length() > 20) {
                deaths.add(cleanText(words));
            }
            if (words.equals("== Deaths ==")) {
                foundDeath = true;
            }

        }

        return deaths;
    }

    private static boolean hasMonth(String text) {
        boolean hasIt = false;
        for (Month month: Month.values()) {
            if (text.toLowerCase().contains(month.toString().toLowerCase()) && !text.contains("==") && text.length() > 20
                    && !text.contains("Content|section|reason")) {
                hasIt = true;
            }
        }
        return hasIt;
    }

    public enum Month
    {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
        JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }

    public static String cleanText(String text) {
        text = text.replaceAll("&ndash;", "").replaceAll("\\[\\[", "").replaceAll("]]", "").replaceAll("\\*\\*", "*");
        if (text.contains("(") && text.contains(")")) {
            text = text.substring(0, text.indexOf("(")-1) + text.substring(text.indexOf(")")+1);
        }
        return text;
    }
}
