package magsom.magsom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
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
        FireMissilesDialogFragment ireMissilesDialogFragment = new FireMissilesDialogFragment((DummyContent.DummyItem) mAdapter.getItem(position));
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


    public class FireMissilesDialogFragment extends DialogFragment {
        DummyContent.DummyItem product;
        EditText editBarcode;

        public FireMissilesDialogFragment(DummyContent.DummyItem product){
            this.product = product;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View modifyView = inflater.inflate(R.layout.dialog_edit_item_list, null);

            EditText editName = (EditText) modifyView.findViewById(R.id.et_product_name);
            EditText editUsage = (EditText) modifyView.findViewById(R.id.et_product_usage);
            EditText editPolka = (EditText) modifyView.findViewById(R.id.et_product_polka);
            EditText editRegal = (EditText) modifyView.findViewById(R.id.et_product_regal);
            EditText editPrice = (EditText) modifyView.findViewById(R.id.et_product_price);
            EditText editNumber = (EditText) modifyView.findViewById(R.id.et_product_number);
            EditText editDescription = (EditText) modifyView.findViewById(R.id.et_product_description);
            editBarcode = (EditText) modifyView.findViewById(R.id.et_product_barcode);
            Button scanBtn = (Button)modifyView.findViewById(R.id.btn_product_barcode);
            scanBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
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
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           getDialog().cancel();
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                     });
            return builder.create();
        }

        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            Log.d("dzialam", "cos chce zwrocic");
            editBarcode.setText(intent.getStringExtra("SCAN_RESULT"));
//            if (requestCode == 0) {
//                if (resultCode == RESULT_OK) {
//                    String contents = intent.getStringExtra("SCAN_RESULT");
//                    String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
//                    // Handle successful scan
////                    Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format , Toast.LENGTH_LONG);
////                    toast.setGravity(Gravity.TOP, 25, 400);
////                    toast.show();
//                } else if (resultCode == RESULT_CANCELED) {
//                    // Handle cancel
////                    Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
////                    toast.setGravity(Gravity.TOP, 25, 400);
////                    toast.show();
//
//                }
//            }
        }

    }


}
