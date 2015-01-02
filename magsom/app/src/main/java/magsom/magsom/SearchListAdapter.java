package magsom.magsom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import magsom.magsom.dummy.DummyContent;

/**
 * Created by Dudzias on 2014-11-23.
 */
public class SearchListAdapter extends ArrayAdapter
        implements Filterable {

    private Context context;
    private ItemsFilter mFilter;
    public  ArrayList<DummyContent.DummyItem> mItemsArray; // list of all products
    public  ArrayList<DummyContent.DummyItem> originalList;

    public SearchListAdapter(Context context, int resource, List<DummyContent.DummyItem> objects) {
        super(context, resource, objects);
        this.mItemsArray = (ArrayList<DummyContent.DummyItem>) objects;
        this.originalList =(ArrayList<DummyContent.DummyItem>) objects;
        this.context = context;
    }
    @Override
    public int getCount() {
        return mItemsArray.size();
    }

//    public ProductsListAdapter(Context context, int resource, int textViewResourceId,  List<DummyContent.DummyItem> objects) {
//        super(context, resource, textViewResourceId, objects);
//        this.mItemsArray = (ArrayList<DummyContent.DummyItem>) objects;
//        this.context = context;
//    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_list_item, parent, false);
//            convertView = inflater.inflate(R.id.productList, parent, false);
        }
        // Now we can fill the layout with the right values
        TextView tv = (TextView) convertView.findViewById(R.id.titleTextView);
//        TextView distView = (TextView) convertView.findViewById(R.id.dist);
        DummyContent.DummyItem p = mItemsArray.get(position);

        tv.setText((CharSequence) p.toString());
//        distView.setText("" + p.getDistance());
        Log.d("Rozmiar tablicy produktow", Integer.toString(mItemsArray.size()));
        return convertView;
    }

    @Override
    public android.widget.Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemsFilter();
        }
        return mFilter;
    }
    @Override
    public DummyContent.DummyItem getItem(int position){
        return mItemsArray.get(position);
    }

    @Override
    public long getItemId(int position){
        return Long.parseLong(mItemsArray.get(position).mProductId);
    }
    public void resetProductList(){
        mItemsArray = originalList;
    }


    /**
     * Created by Dudzias on 2014-11-23.
     */
    private class ItemsFilter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            resetProductList();
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = mItemsArray;
                results.count = mItemsArray.size();
                Log.d("Size of Items list",  Integer.toString(mItemsArray.size()));

            }
            else {
                // We perform filtering operation
                List<DummyContent.DummyItem> nProductsList = new ArrayList<DummyContent.DummyItem>();

                for (DummyContent.DummyItem p : mItemsArray) {
                    if (p.getItemBarcode().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        nProductsList.add(p);
                        Log.d("Inserted into new list object", p.toString());
                    }
                }
                results.values = nProductsList;
                results.count = nProductsList.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                mItemsArray = (ArrayList<DummyContent.DummyItem>) results.values;
                Log.d("lista nowa pierwszy produkt:", mItemsArray.get(0).mProductId);
                Log.d("2//Size of Items list",  Integer.toString(mItemsArray.size()));
                notifyDataSetChanged();
            }
        }
    }




}


