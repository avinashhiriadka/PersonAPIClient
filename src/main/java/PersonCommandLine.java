import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonCommandLine {

    public static void main(String[] args) throws Exception{

        StringBuffer json = new StringBuffer();
        Map<String, String> headers = new LinkedHashMap<String, String>();

        try {
           if (!args[0].equals("list")) {
               File file = new File(args[0]);
               BufferedReader br = new BufferedReader(new FileReader(file));

               String line;
               while ((line = br.readLine()) != null)
                   json.append(line);
           } else {
               String url = "http://localhost:8080/persons/findAll";
               RestResponse response = performGetRequest(url, headers);
               System.out.println("Http status code after get :" + response.getStatusCode());

               if (response.getStatusCode() == HttpStatus.SC_OK) {
                   System.out.println("The following persons are in DB: " + response.getResponseBody());
               }
               System.exit(0);
           }

           headers.put("Accept", "application/json");
           headers.put("Content-Type", "application/json");

           if (args[1].equals("add")) {
               String url = "http://localhost:8080/persons/add";
               RestResponse response = preformPostRequest(url, json.toString(), ContentType.APPLICATION_JSON, headers);
               System.out.println("Http status code after post :" + response.getStatusCode());
               if (response.getStatusCode() == HttpStatus.SC_CREATED) {
                   System.out.println("Successfully added the following persons: " + response.getResponseBody());
               }
           } else if (args[1].equals("delete")) {
               String url = "http://localhost:8080/persons/delete";
               RestResponse response = preformPostRequest(url, json.toString(), ContentType.APPLICATION_JSON, headers);
               System.out.println("Http status code after post :" + response.getStatusCode());

               if (response.getStatusCode() == HttpStatus.SC_ACCEPTED) {
                   System.out.println("The following persons are deleted: " + response.getResponseBody());
               }

           } else if (args[1].equals("update")) {
               String url = "http://localhost:8080/persons/update";
               RestResponse response = preformPostRequest(url, json.toString(), ContentType.APPLICATION_JSON, headers);
               System.out.println("Http status code after post :" + response.getStatusCode());

               if (response.getStatusCode() == HttpStatus.SC_ACCEPTED) {
                   System.out.println("The following persons are updated: " + response.getResponseBody());
               }
           } else {
               System.out.println("Please use only add, delete, update or list options ");
           }
       }catch(java.lang.ArrayIndexOutOfBoundsException ex){
           System.out.println("Incorrect arguments used to run the program ");
       }
    }

    private static RestResponse preformPostRequest(String url, String body, ContentType applicationJson, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        if(headers != null) {
            Header[] customHeaders = new Header[headers.size()];
            int i = 0;
            for (String key : headers.keySet()) {
                customHeaders[i++] = new BasicHeader(key, headers.get(key));
            }
        }
        try{
            CloseableHttpClient client = HttpClientBuilder.create().build();
            post.setEntity(new StringEntity(body,applicationJson));
            response = client.execute(post);
            ResponseHandler<String> responseBody = new BasicResponseHandler();
            return new RestResponse(response.getStatusLine().getStatusCode(), responseBody.handleResponse(response));
        }catch(Exception e){
            if(e instanceof HttpResponseException)
                return new RestResponse(response.getStatusLine().getStatusCode(),e.getMessage());
            throw new RuntimeException(e.getMessage(), e);

        }
    }

    private static RestResponse performGetRequest(String url, Map<String, String> headers) {
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try{
            CloseableHttpClient client = HttpClientBuilder.create().build();
            response = client.execute(get);
            ResponseHandler<String> responseBody = new BasicResponseHandler();
            return new RestResponse(response.getStatusLine().getStatusCode(), responseBody.handleResponse(response));
        }catch(Exception e){
            if(e instanceof HttpResponseException)
                return new RestResponse(response.getStatusLine().getStatusCode(),e.getMessage());
            throw new RuntimeException(e.getMessage(), e);

        }
    }
}


