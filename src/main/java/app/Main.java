package app;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * Beispiel https://stackabuse.com/web-scraping-the-java-way/
 */
public class Main {

    private static final String PAGE = "https://www.capgemini.com/careers/career-paths/";
    private static final String ITEM_IDENTIFIER = "card_default__title";
    private static final String A = "a";
    private static final String HREF = "href";

    public static void main(String[] args) {
        try {
            // Website holen
            Document doc = Jsoup.connect(PAGE).get();

            // Titel ausgeben
            System.out.printf("Title: %s\n", doc.title());

            // Carreer Path 'Blöcke' holen
            Set<Element> carreerPaths = doc.getElementsByClass(ITEM_IDENTIFIER).stream()
                    .map(item -> item.getElementsByTag(A))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

            // Pfad Name und Link in der Konsole ausgeben
            carreerPaths.forEach(Main::printPath);

            // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printPath(Element path) {
        path.textNodes().forEach(System.out::println);
        System.out.println(path.attr(HREF));
    }
}
