// Client's class

import java.util.ArrayList;
import java.util.List;

public class Forest {
    private final List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture) {
        // Create the flyweight using the factory to avoid duplication
        TreeType type = TreeFactory.getTreeType(name, color, texture);

        // Create context object (`Tree`) and link it to the flyweight (`TreeType`).
        Tree tree = new Tree(x, y, type);
        trees.add(tree);
    }

    public void draw() {
        for (Tree tree : trees) {
            tree.draw();
        }
    }

}
