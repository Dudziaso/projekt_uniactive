package magsom.magsom;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import magsom.magsom.dummy.DummyContent;


/**
 * Created by Dudzias on 2014-11-14.
 */


public class DoPost extends AsyncTask<String, Void, Boolean> {

    Context mContext = null;
    String strNameToSearch = "";
    DummyContent dummyContent = new DummyContent();

    //Result data
    String productName;
    String strLastName;
    int productId;
    int intPoints;
    public ArrayList<MyDataHolder> dataArray = new ArrayList<MyDataHolder>();

    public DoPost(Context context){
        this.mContext = context;
    }

    Exception exception = null;


    @Override
    protected Boolean doInBackground(String... arg0) {

        try{

            //Setup the parameters
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
            //Add more parameters as necessary

            //Create the HTTP request
            HttpParams httpParameters = new BasicHttpParams();

            //Setup timeouts
            HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
            HttpConnectionParams.setSoTimeout(httpParameters, 15000);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost httppost = new HttpPost("http://magsom.cba.pl/getproducts.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity);
            //--------------------------------------------------------
            //Toast.makeText(mContext, "chuj", Toast.LENGTH_LONG).show();
          //  Log.d("Json string: ", result);

           String json = result.toString().substring(result.indexOf("[{"), result.lastIndexOf("}]") + 2);
            Log.d("Json edited string: ", json);
            // Create a JSON object from the request response
            //JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonarray = new JSONArray(json);
            //Retrieve the data from the JSON object

            for (int i = 0; i < jsonarray.length(); i++) {

                JSONObject jsonobject = jsonarray.getJSONObject(i);
                MyDataHolder data = new MyDataHolder();
                DummyContent.DummyItem dummy = new DummyContent.DummyItem(jsonobject .getString("nazwa"), jsonobject .getString("idalternator"),
                        jsonobject .getString("typ"),jsonobject .getString("pulka"),jsonobject .getString("numer"),jsonobject .getString("regal"),
                        jsonobject .getString("cena"), jsonobject . getString("opis") );
                dummyContent.addItem(dummy);
//                dummy.mProductId = jsonobject .getString("idalternator");
//                dummy.mProductName = jsonobject .getString("nazwa");
//                dummy.mProductType = jsonobject .getString("typ");
//                dummy.mShelveNumber = jsonobject .getString("pulka");
//                dummy.regal = jsonobject .getString("regal");
//                dummy.number = jsonobject .getString("numer");
//                dummy.price = jsonobject .getString("cena");
//                dummy.description = jsonobject . getString("opis");

//                data.mProductId = jsonobject .getString("idalternator");
//                data.mProductName = jsonobject .getString("nazwa");
//                data.mProductType = jsonobject .getString("typ");
//                data.mShelveNumber = jsonobject .getString("pulka");
//                data.regal = jsonobject .getString("regal");
//                data.number = jsonobject .getString("numer");
//                data.price = jsonobject .getString("cena");
//                data.description = jsonobject . getString("opis");

                //Log.d(jsonobject .getString("idalternator"), jsonobject .getString("nazwa"));
//                dataArray.add(data);

            }

        }catch (Exception e){
            Log.e("ClientServerDemo", "Error:", e);
            exception = e;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean valid){
        //Update the UI
//        textViewFirstName.setText("Nazwa produktu: " + productName);
//        textViewLastName.setText("Last Name: " + strLastName);
//        textViewAge.setText("Age: " + productId);
//        textViewPoints.setText("Points: " + intPoints);
//
//        buttonGetData.setEnabled(true);

//        if(exception != null){
//            Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
//        }
        Toast.makeText(mContext, "Downloading completed !", Toast.LENGTH_LONG).show();
    }

    public class MyDataHolder {
        public String mProductName;
        public String mProductId;
        public String mProductType;
        public String mShelveNumber;
        public String number;
        public String regal;
        public String price;
        public String description;

        @Override
        public String toString(){
            return this.mProductId +": "+this.mProductName +" "+ this.description;
        }

    }


}
