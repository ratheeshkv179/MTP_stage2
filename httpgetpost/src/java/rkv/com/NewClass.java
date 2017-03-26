/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rkv.com;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author ratheeshkv
 */
public class NewClass {

    public static void main(String args[]) {

        try {

//            Log log =  LogFactory.getLog(NewClass.class);
//            log.info("done");
            String[] request = new String[7];
            request[0] = "add-log";
            request[1] = "extra-add-log";
            request[2] = "get-image";
            request[3] = "ldap-auth";
            request[4] = "quiz";
            request[5] = "quiz-get";
            request[6] = "submit";

            for (String req : request) {

                DefaultHttpClient client = new DefaultHttpClient();
                String url = "http://localhost:8080/httpgetpost/add-log";// + req;
                System.out.println("\n" + url);
                //String url = "http://10.129.5.155:8080/serverplus/index.jsp";
                HttpPost httppost = new HttpPost(url);
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                List<? extends NameValuePair> nameValue;
                // httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = client.execute(httppost);
                int status = response.getStatusLine().getStatusCode();
                

                System.out.println("\nRequest: " + req + " STATUS : " + status);
            }
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


/*
https://tomcat.apache.org/tomcat-5.5-doc/config/valve.html
pattern="%h %l %u %t &quot;%r&quot; %s %b %m %D %T " />

<Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log" suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b %m %D %T [%a-%A]" />

 */
