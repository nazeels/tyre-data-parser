import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class TreeFormatter {
    public Tree<String> getStringTree(List list) {
        Tree<String> parentNode = new Tree<String>("start");

        for (int idx = 0; idx < list.size(); idx++) {
            JSONObject jsonObjectItm = (JSONObject) list.get(idx);
            String catId = jsonObjectItm.get("cat_id").toString();
            Tree<String> catNode = null;
            if (null == parentNode.getTree(catId)) {
                catNode = parentNode.addLeaf("start", catId);
            } else {
                catNode = parentNode.getTree(catId);
            }
            //width
            String widthLabel1 = jsonObjectItm.get("widthLabel").toString();
            String heightLabel = jsonObjectItm.get("heightLabel").toString();
            String rimLabel = jsonObjectItm.get("rimLabel").toString();
            Tree<String> widthTree = catNode.getTree(widthLabel1);
            if (null == widthTree) {
                Tree<String> heightTree = getHeightTree(catNode, widthLabel1, heightLabel);
                //rimlabel
                heightTree.addLeaf(heightLabel, rimLabel);
            } else {
                Tree<String> heightTree = widthTree.getTree(heightLabel);
                if (null == heightTree) {
                    heightTree = widthTree.addLeaf(heightLabel);
                }
                heightTree.addLeaf(heightLabel, rimLabel);
            }
        }
        return parentNode;
    }

    public void printFormatted(Tree<String> input) {
        Collection<Tree<String>> widthTrees = input.getSubTrees();
        widthTrees.forEach(itWidth -> {
            JSONObject widthJson = this.getTemplate();
            JSONArray widthLookup = (JSONArray) widthJson.get("lookup");

            itWidth.getSubTrees().forEach(itHeight -> {
                widthLookup.add(this.getTemplateLookupItem(itHeight.getHead(), itHeight.getHead()));
                JSONObject heightJson = this.getTemplate();
                JSONArray heightLookup = (JSONArray) heightJson.get("lookup");
                itHeight.getSubTrees().forEach(itRim -> {
                    heightLookup.add(this.getTemplateLookupItem(itRim.getHead(), itRim.getHead()));

                    //rim
                    JSONObject rimJson = this.getTemplate();
                    JSONArray rimLookup = (JSONArray) rimJson.get("lookup");
                    itRim.getSubTrees().forEach(itLast -> {
                        rimLookup.add(this.getTemplateLookupItem(itLast.getHead(), itLast.getHead()));
                    });
                    String fileNameRim = "search_" + itWidth.getHead() + "_width_" + itHeight.getHead() + "_height_" + itRim.getHead() + "_rim";
                    generateJsonFIle(rimJson, fileNameRim);
                });
                String fileNameHeight = "search_" + itWidth.getHead() + "_width_" + itHeight.getHead() + "_height";
                generateJsonFIle(heightJson, fileNameHeight);
            });

            generateWidthFile(itWidth, widthJson);

        });
    }

    private void generateWidthFile(Tree<String> itWidth, JSONObject widthJson) {
        String fileNameWidth = "search_" + itWidth.getHead() + "_width";
        generateJsonFIle(widthJson, fileNameWidth);
    }

    private void generateJsonFIle(JSONObject widthJson, String fileNameWidth) {
        widthJson.put("lookupId", fileNameWidth);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileNameWidth + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(widthJson);
        printWriter.close();
    }

    private JSONObject getTemplate() {
        JSONObject obj = new JSONObject();
        obj.put("lookup", new JSONArray());
        return obj;
    }

    private JSONObject getTemplateLookupItem(String key, String value) {
        JSONObject obj = new JSONObject();
        obj.put("key", key);
        obj.put("value", value);
        return obj;
    }

    private Tree<String> getHeightTree(Tree<String> catNode, String widthLabel1, String heightLabel) {
        return catNode.addLeaf(widthLabel1, heightLabel);
    }
}
