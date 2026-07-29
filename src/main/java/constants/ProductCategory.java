package constants;

public enum ProductCategory {
    MEN("mens"),
    WOMEN("womens"),
    KIDS("all-kids-outdoor-gear");

    private final String kind;

    ProductCategory(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }
}