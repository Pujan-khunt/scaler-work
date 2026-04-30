public class Main {
    public static void main(String[] args) {
        Forest forest = new Forest();

        // Plant 5 Oak trees
        for (int i = 0; i < 5; i++) {
            forest.plantTree(i, i * 2, "Oak", "Green", "OakTexture.png");
        }

        // Plant 5 Pine trees
        for (int i = 0; i < 5; i++) {
            forest.plantTree(10 + i, 20 + i, "Pine", "Dark Green", "PineTexture.png");
        }

        // Plant 5 Birch trees
        for (int i = 0; i < 5; i++) {
            forest.plantTree(50 + i, 50 + i, "Birch", "Yellow", "BirchTexture.png");
        }

        System.out.println("\n--- Drawing the Forest ---");
        forest.draw();

        int numTrees = 15;
        int numTypes = 3;

        System.out.println("\nTotal Tree objects (Contexts) created: " + numTrees);
        System.out.println("Total TreeType objects (Flyweights) created: " + numTypes);
        System.out.println(
                "Memory saved! Instead of storing redundant strings/textures 15 times, we only stored them 3 times.");
    }
}
