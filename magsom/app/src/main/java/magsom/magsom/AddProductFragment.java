package magsom.magsom;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText editBarcode;
    EditText editName;
    EditText editUsage;
    EditText editPolka;
    EditText editRegal;
    EditText editPrice;
    EditText editNumber;
    EditText editDescription;
    Button scanBtn;
    Button addProductBtn;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        editName = (EditText) view.findViewById(R.id.et_product_name);
        editUsage = (EditText) view.findViewById(R.id.et_product_usage);
        editPolka = (EditText) view.findViewById(R.id.et_product_polka);
        editRegal = (EditText) view.findViewById(R.id.et_product_regal);
        editPrice = (EditText) view.findViewById(R.id.et_product_price);
        editNumber = (EditText) view.findViewById(R.id.et_product_number);
        editDescription = (EditText) view.findViewById(R.id.et_product_description);
        editBarcode = (EditText) view.findViewById(R.id.et_product_barcode);
        scanBtn = (Button)view.findViewById(R.id.btn_product_barcode);
        addProductBtn = (Button) view.findViewById(R.id.btn_add_product);
        scanBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "EAN_CODE_MODE");
                startActivityForResult(intent, 0);
            }
        });
        addProductBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddProductRequest addProductRequest = new AddProductRequest();
                try {
                    addProductRequest.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                switchFragment(new ProductFragment());

            }
        });
        return view;
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                editBarcode.setText(intent.getStringExtra("SCAN_RESULT"));
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Log.d("Canceled", "Canceled");

            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    private class AddProductRequest extends AsyncTask <String, Void, Boolean>{


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                //Setup the parameters
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("barcode", editBarcode.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("productName", editName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("usage", editUsage.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("polka", editPolka.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("regal", editRegal.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("price", editPrice.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("number", editNumber.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("description", editDescription.getText().toString()));

                //Add more parameters as necessary

                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost("http://magsom.cba.pl/addproductandroid.php");
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
