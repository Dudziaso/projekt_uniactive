package magsom.magsom;

import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by Dudzias on 2015-01-02.
 */
public class EditProductRequest extends AsyncTask<String, Void, Boolean> {
    String barcode;
    String productName;
    String usage;
    String polka;
    String regal;
    String price;
    String number;
    String description;
    String idProduct;



    public  EditProductRequest(String barcode,String productName,String usage,String polka,
                               String regal,String price,String number,String description,String idProduct){
        this.barcode=barcode;
        this.productName=productName;
        this.usage=usage;
        this.polka=polka;
        this.regal=regal;
        this.price=price;
        this.number=number;
        this.description=description;
        this.idProduct=idProduct;

    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            //Setup the parameters
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("barcode",barcode));
            nameValuePairs.add(new BasicNameValuePair("productName", productName));
            nameValuePairs.add(new BasicNameValuePair("usage", usage));
            nameValuePairs.add(new BasicNameValuePair("polka", polka));
            nameValuePairs.add(new BasicNameValuePair("regal", regal));
            nameValuePairs.add(new BasicNameValuePair("price",price));
            nameValuePairs.add(new BasicNameValuePair("number",number ));
            nameValuePairs.add(new BasicNameValuePair("description",description));
            nameValuePairs.add(new BasicNameValuePair("idProduct",idProduct));

            //Add more parameters as necessary

            //Create the HTTP request
            HttpParams httpParameters = new BasicHttpParams();

            //Setup timeouts
            HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
            HttpConnectionParams.setSoTimeout(httpParameters, 15000);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost httppost = new HttpPost("http://magsom.cba.pl/editproduct.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
        }
        catch (Exception e){
            Log.e("ClientServerDemo", "Error:", e);
        }
        return true;

    }
}
