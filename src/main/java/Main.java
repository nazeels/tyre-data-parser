


import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String path = "/Users/nazeels/pjtWariyum/conversion/";
        JSONArray a = (JSONArray) parser.parse(new FileReader(path + "test.json"));
        List list = a.subList(0, a.size());

        TreeFormatter treeFormatter = new TreeFormatter();
        Tree<String> parentNode = treeFormatter.getStringTree(list);

        treeFormatter.printFormatted(parentNode);

        //file generation
        try (Stream<Path> walk = Files.walk(Paths.get("/Users/nazeels/pjtWariyum/conversion/jsonDatas"))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

            result.forEach(it->{
                System.out.println(it);

            });

        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
