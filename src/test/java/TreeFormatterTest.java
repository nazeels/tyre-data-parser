import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TreeFormatterTest {

    @org.junit.Test
    public void getStringTree() throws IOException, ParseException {
        String path = "/Users/nazeels/pjtWariyum/conversion/";

        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader(path + "test2.json"));
        List list = a.subList(0, a.size());

        TreeFormatter treeFormatter = new TreeFormatter();
        Tree<String> parentNode = treeFormatter.getStringTree(list);

        String content = FileUtils.readFileToString(new File(path + "test2_output.json"));

        assert parentNode.toString().replaceAll(" ", "").equals(content);
        System.out.println("parentNode = " + parentNode);
    }

    @Test
    public void printFormatted() throws IOException, ParseException {
        String path = "/Users/nazeels/pjtWariyum/conversion/";

        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader(path + "test2.json"));
        List list = a.subList(0, a.size());

        TreeFormatter treeFormatter = new TreeFormatter();
        Tree<String> parentNode = treeFormatter.getStringTree(list);

        treeFormatter.printFormatted(parentNode);
    }
}