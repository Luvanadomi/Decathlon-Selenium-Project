package constants;

public final class AppUrls {

    public static final String BASE_URL = "https://www.decathlon.com/";

    public static final String COLLECTION_URL = BASE_URL + "collections/";
    
    public static final String TARGET_PRODUCT_URL = BASE_URL + "products/quechua-hiking-10l-backpack-arpenaz-nh100-344147?variant=40129897988158";

    public static final String CART_URL = BASE_URL + "cart";

    private AppUrls() {
    }

    public static String getUrl(ProductCategory category) {
        return COLLECTION_URL + category.getKind();
    }
}