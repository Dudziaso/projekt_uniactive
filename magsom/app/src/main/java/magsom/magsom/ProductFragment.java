package magsom.magsom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import magsom.magsom.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ProductFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText inputSearch;
    DoPost post = null;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;


    // TODO: Rename and change types of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        post = new DoPost(getActivity().getApplicationContext());
        try {
            post.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ProductsListAdapter(getActivity(),
                android.R.layout.simple_list_item_1, post.dummyContent.ITEMS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        inputSearch = (EditText) view.findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ((ArrayAdapter<?>) ProductFragment.this.mAdapter).getFilter().filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(post.dummyContent.ITEMS.get(position).mProductId);
//            Toast.makeText(getActivity(), post.dummyContent.ITEMS.get(position).mProductId + " Clicked!", Toast.LENGTH_SHORT).show();
//        }
//        Toast.makeText(getActivity(), post.dummyContent.ITEMS.get(position).mProductId + " Clicked!", Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), mAdapter.getItemId(position)+ " Clicked!", Toast.LENGTH_SHORT).show();
        ProductDetailsDialogFragment ireMissilesDialogFragment = new ProductDetailsDialogFragment((DummyContent.DummyItem) mAdapter.getItem(position));
        ireMissilesDialogFragment.show(getFragmentManager(), "New Dialog");

    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }


    public class ProductDetailsDialogFragment extends DialogFragment {
        DummyContent.DummyItem product;
        EditText editBarcode;
        EditText editName;
        EditText editUsage;
        EditText editPolka;
        EditText editRegal;
        EditText editPrice;
        EditText editNumber;
        EditText editDescription;
        Button scanBtn;

        public ProductDetailsDialogFragment(DummyContent.DummyItem product){
            this.product = product;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View modifyView = inflater.inflate(R.layout.dialog_edit_item_list, null);

            editName = (EditText) modifyView.findViewById(R.id.et_product_name);
            editUsage = (EditText) modifyView.findViewById(R.id.et_product_usage);
            editPolka = (EditText) modifyView.findViewById(R.id.et_product_polka);
            editRegal = (EditText) modifyView.findViewById(R.id.et_product_regal);
            editPrice = (EditText) modifyView.findViewById(R.id.et_product_price);
            editNumber = (EditText) modifyView.findViewById(R.id.et_product_number);
            editDescription = (EditText) modifyView.findViewById(R.id.et_product_description);
            editBarcode = (EditText) modifyView.findViewById(R.id.et_product_barcode);
            scanBtn = (Button)modifyView.findViewById(R.id.btn_product_barcode);
            scanBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "EAN_CODE_MODE");
                    startActivityForResult(intent, 0);
                }
            });



            editName.setText(product.mProductName);
            editUsage.setText(product.mProductType);
            editPolka.setText(product.mShelveNumber);
            editRegal.setText(product.regal);
            editPrice.setText(product.price);
            editNumber.setText(product.number);
            editDescription.setText(product.description);
            editBarcode.setText(product.barcode);

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(modifyView)
                    // Add action buttons
                    .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

//                            EditProductRequest(String barcode,String productName,String usage,String polka,
//                                    String regal,String price,String number,String description,String idProduct)
                            EditProductRequest editProductRequest = new EditProductRequest(editBarcode.getText().toString(),
                                  editName.getText().toString() ,editUsage.getText().toString(), editPolka.getText().toString(),
                                    editRegal.getText().toString(), editPrice.getText().toString(), editNumber.getText().toString(),
                                    editDescription.getText().toString(), product.mProductId);
                            try {
                                editProductRequest.execute().get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DeleteProductRequest deleteProductRequest = new DeleteProductRequest(product.mProductId);
                            try {
                                deleteProductRequest.execute().get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            switchFragment(new ProductFragment());

                            getDialog().cancel();
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getDialog().cancel();
                        }
                    });
            return builder.create();
        }

        public void onActivityResult(int requestCode, int resultCode, Intent intent) {

            if (requestCode == 0) {
                if (resultCode == getActivity().RESULT_OK) {
                    editBarcode.setText(intent.getStringExtra("SCAN_RESULT"));
                } else if (resultCode == getActivity().RESULT_CANCELED) {
                    Log.d("Canceled", "Canceled");

                }
            }
        }
        public void switchFragment(Fragment fragment) {
            //TextView helloText = (TextView) findViewById(R.id.hello_world);
            //helloText.setText("Klik");
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }


    private class DeleteProductRequest extends AsyncTask<String, Void, Boolean> {
       private String productId;

        public DeleteProductRequest(String productId){
            this.productId = productId;
        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                //Setup the parameters
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("productId", productId));


                //Add more parameters as necessary

                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost("http://magsom.cba.pl/deleteproduct.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpclient.execute(httppost);
            }
            catch (Exception e){
                Log.e("ClientServerDemo", "Error:", e);
            }
            return true;

        }
    }


}
