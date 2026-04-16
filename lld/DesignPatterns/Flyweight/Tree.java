public class Tree {
    private final int x;
    private final int y;
    private final TreeType type;

    public Tree(int x, int y, TreeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw() {
        // Pass extrinsic state (x, y) to the flyweight's method
        type.draw(this.x, this.y);
    }
}
