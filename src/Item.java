import java.util.ArrayList;

class Item {
    private String name;

    public String getName() {
        return name;
    }

    private String category;

    public String getCategory() {
        return category;
    }

    private double rating;

    public double getRating() {
        return rating;
    }

    public Item(String name, String category, double rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
    }

    public String toString() {
        return "Name: " + name + ", "
                + "Category: " + category + ", "
                + "Rating" + String.valueOf(rating);
    }

    // Uses Quick Sort
    public static void orderByRating(ArrayList<Item> vector) {
        Item.quickSort(vector, 0, vector.size() - 1);
        return;
    }

    private static void quickSort(ArrayList<Item> vector, int start, int end) {
        if (start < end) {
            int pivot = Item.partition(vector, start, end);
            Item.quickSort(vector, start, pivot - 1);
            Item.quickSort(vector, pivot + 1, end);
        }
    }

    private static int partition(ArrayList<Item> vector, int start, int end) {
        Item pivotItem = vector.get(end);
        int index = start;
        for (int j = start; j < end; j++) {
            if (vector.get(j).getRating() <= pivotItem.getRating()) {
                Item aux = vector.get(j);
                vector.set(j, vector.get(index));
                vector.set(index, aux);
                index++;
            }
        }
        Item aux = vector.get(end);
        vector.set(end, vector.get(index));
        vector.set(index, aux);
        return index;
    }

    // Uses Insertion Sort
    public static void orderByCategory(ArrayList<Item> vector) {
        int length = vector.size();
        for (int index = 1; index < length; index++) {
            Item value = vector.get(index);
            int previousIndex = index - 1;
            while (previousIndex >= 0
                    && vector.get(previousIndex).category.compareToIgnoreCase(value.getCategory()) > 0) {
                vector.set(previousIndex + 1, vector.get(previousIndex));
                previousIndex -= 1;
            }
            vector.set(previousIndex + 1, value);
        }
        return;
    }
}