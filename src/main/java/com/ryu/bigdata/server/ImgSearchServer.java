package com.ryu.bigdata.server;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ImgSearchServer {
	//private static String SERVER_IP="112.170.13.73";
	//private static int PORT=6700;
	//private static String SERVER_URL=SERVER_IP+":"+PORT;
	private String serverIp="http://112.170.13.73:6700";
	private HttpClient httpclient;
	private HttpPost httpPost;
	
    public ImgSearchServer() {
        httpclient = new DefaultHttpClient();
        
    }
    
    // 기존소스 보호용
    public JSONObject imageSearchByUrl(String image_url, String image_name, String x, String y, String width, String height, String retsize) {
    	
        return imageSearchByUrl(image_url, image_name, x, y, width, height, retsize, "Y");
    }
    
    public JSONObject imageSearchByUrl(String image_url, String image_name, String x, String y, String width, String height, String retsize, String ownYn) {
    	httpPost = new HttpPost(serverIp+"/searchurl");

    	JSONObject jsonObject =null;
    	JSONParser jsonParser = new JSONParser();
    	// set the parameters
        List <NameValuePair> nvps = new ArrayList<NameValuePair>();
        
        nvps.add(new BasicNameValuePair("image_url",image_url));
        nvps.add(new BasicNameValuePair("image_name", image_name));
        nvps.add(new BasicNameValuePair("x", x));
        nvps.add(new BasicNameValuePair("y", y));
        nvps.add(new BasicNameValuePair("width", width));
        nvps.add(new BasicNameValuePair("height", height));
        nvps.add(new BasicNameValuePair("retsize", retsize));
        nvps.add(new BasicNameValuePair("ownYn", ownYn));

        // set the encoding
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        // send the http request and get the http response
        try {
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            jsonObject = (JSONObject) jsonParser.parse(EntityUtils.toString(resEntity));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    // 기존소스 보호용
    public JSONObject imageSearchByData(String image, String image_name, String x, String y, String width, String height, String retsize) {
    	return imageSearchByData(image, image_name, x, y, width, height, retsize, "Y");
    }
    
    public JSONObject imageSearchByData(String image, String image_name, String x, String y, String width, String height, String retsize, String ownYn) {
    	httpPost = new HttpPost(serverIp+"/search");

    	JSONObject jsonObject =null;
    	JSONParser jsonParser = new JSONParser();
    	// set the parameters
        List <NameValuePair> nvps = new ArrayList<NameValuePair>();
        
        nvps.add(new BasicNameValuePair("image",image));
        nvps.add(new BasicNameValuePair("image_name", image_name));
        nvps.add(new BasicNameValuePair("x", x));
        nvps.add(new BasicNameValuePair("y", y));
        nvps.add(new BasicNameValuePair("width", width));
        nvps.add(new BasicNameValuePair("height", height));
        nvps.add(new BasicNameValuePair("retsize", retsize));
        nvps.add(new BasicNameValuePair("retsize", retsize));
        nvps.add(new BasicNameValuePair("ownYn", ownYn));

        // set the encoding
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        // send the http request and get the http response
        try {
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            jsonObject = (JSONObject) jsonParser.parse(EntityUtils.toString(resEntity));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    public JSONObject imageToVector(String image_url, String image_name, String x, String y, String width, String height, String retsize) {
    	httpPost = new HttpPost(serverIp+"/searchurl");

    	JSONObject jsonObject =null;
    	JSONParser jsonParser = new JSONParser();
    	// set the parameters
        List <NameValuePair> nvps = new ArrayList<NameValuePair>();
        
        nvps.add(new BasicNameValuePair("image_url",image_url));
        nvps.add(new BasicNameValuePair("image_name", image_name));
        nvps.add(new BasicNameValuePair("x", x));
        nvps.add(new BasicNameValuePair("y", y));
        nvps.add(new BasicNameValuePair("width", width));
        nvps.add(new BasicNameValuePair("height", height));
        nvps.add(new BasicNameValuePair("retsize", retsize));

        // 벡터 저장 경로 JSON형태로 리턴
        // set the encoding
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        // send the http request and get the http response
        try {
            //HttpResponse response = httpclient.execute(httpPost);
            //HttpEntity resEntity = response.getEntity();
            //jsonObject = (JSONObject) jsonParser.parse(EntityUtils.toString(resEntity));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    public JSONObject detroi(String image, String image_name)
    {
    	httpPost = new HttpPost(serverIp+"/detroi");

    	JSONObject jsonObject =null;
    	JSONParser jsonParser = new JSONParser();
    	// set the parameters
        List <NameValuePair> nvps = new ArrayList<NameValuePair>();
        
        nvps.add(new BasicNameValuePair("image",image));
        nvps.add(new BasicNameValuePair("image_name", image_name));

        // set the encoding
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        // send the http request and get the http response
        try {
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            jsonObject = (JSONObject) jsonParser.parse(EntityUtils.toString(resEntity));
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return jsonObject;
    }
}