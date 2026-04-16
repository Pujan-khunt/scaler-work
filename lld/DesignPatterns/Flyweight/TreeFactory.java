import java.util.HashMap;
import java.util.Map;

// This factory ensures that the flyweights are shared properly.
public class TreeFactory {
    private static final Map<String, TreeType> treeTypes = new HashMap<>();

    public static TreeType getTreeType(String name, String color, String texture) {
        TreeType result = treeTypes.get(name);
        if (result == null) {
            result = new TreeType(name, color, texture);
            treeTypes.put(name, result);
            System.out.println(TreeFactory.class.toString() + " created a new TreeType: { " + name
                    + ", " + color + ", " + texture + " }");
        }
        return result;
    }
}
