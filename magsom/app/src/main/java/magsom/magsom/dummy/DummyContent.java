package magsom.magsom.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();



    public void addItem(DummyItem item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        //        public String id;
//        public String content;
        public String mProductName;
        public String mProductId;
        public String mProductType;
        public String mShelveNumber;
        public String number;
        public String regal;
        public String price;
        public String description;
        public String barcode;

        //        public DummyItem(String id, String content) {
//            this.id = id;
//            this.content = content;
//        }
        public DummyItem(String mProductName, String mProductId, String mProductType,String mShelveNumber,
                         String number, String regal, String price, String description, String barcode ) {
            this.mProductName = mProductName;
            this.mProductId = mProductId;
            this.mProductType = mProductType;
            this.mShelveNumber = mShelveNumber;
            this. number = number;
            this. regal = regal;
            this. price = price;
            this. description = description;
            this. barcode = barcode;

        }
        @Override
        public String toString() {
            return mProductName + " "+ price;
        }

        public String getItemName() {
            return mProductName;
        }
        public String getItemBarcode(){
            return barcode;
        }
    }
}