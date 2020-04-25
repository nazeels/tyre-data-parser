import org.json.simple.JSONObject;

import java.util.List;

public class TreeFormatter {
    public  Tree<String> getStringTree(List list) {
        Tree<String> parentNode = new Tree<String>("start");

        for (int idx = 0; idx < list.size(); idx++) {
            JSONObject jsonObjectItm = (JSONObject) list.get(idx);
            String catId = jsonObjectItm.get("cat_id").toString();
            Tree<String> catNode = null;
            if(null == parentNode.getTree(catId)) {
                catNode = parentNode.addLeaf("start", catId);
            }
            else {
                catNode = parentNode.getTree(catId);
            }
            //width
            String widthLabel1 = jsonObjectItm.get("widthLabel").toString();
            String heightLabel = jsonObjectItm.get("heightLabel").toString();
            String rimLabel = jsonObjectItm.get("rimLabel").toString();
            Tree<String> widthTree = catNode.getTree(widthLabel1);
            if( null == widthTree)
            {
                Tree<String> heightTree = getHeightTree(catNode, widthLabel1, heightLabel);
                //rimlabel
                heightTree.addLeaf(heightLabel,rimLabel);
            }
            else {
                Tree<String> heightTree = widthTree.getTree(heightLabel);
                if(null == heightTree){
                    heightTree = widthTree.addLeaf(heightLabel);
                }
                heightTree.addLeaf(heightLabel,rimLabel);
            }
        }
        return parentNode;
    }

    private Tree<String> getHeightTree(Tree<String> catNode, String widthLabel1, String heightLabel) {
        return catNode.addLeaf(widthLabel1, heightLabel);
    }
}
