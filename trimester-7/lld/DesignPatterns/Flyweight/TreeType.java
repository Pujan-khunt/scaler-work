// This class is a flyweight to the `Tree` class
// It represents the intrinsic state/data of `Tree`.
// intrinsic class is generally immutable.
public class TreeType {
    private final String name;
    private final String color;
    private final String textureFilePath;

    public TreeType(String name, String color, String textureFilePath) {
        this.name = name;
        this.color = color;
        this.textureFilePath = textureFilePath;
    }

    public void draw(int x, int y) {
        System.out.println(
                "Drawing [" + name + "] tree at (" + x + ", " + y + ") with color " + color);
    }

    public String getTexture() {
        return textureFilePath;
    }

    // ... getters are added here
    // setters are avoided due to the immutable nature of intrinsic classes.
}
