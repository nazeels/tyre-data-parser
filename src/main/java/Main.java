
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String path = "/Users/nazeels/pjtWariyum/conversion/";
        JSONArray a = (JSONArray) parser.parse(new FileReader(path + "test.json"));
        List list = a.subList(0, a.size());

        TreeFormatter treeFormatter = new TreeFormatter();
        Tree<String> parentNode = treeFormatter.getStringTree(list);

        treeFormatter.printFormatted(parentNode);



    }


}
