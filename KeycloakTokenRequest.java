import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

public class KeycloakTokenRequest {
    
    public static void main(String[] args) throws IOException {
        
        String keycloakUrl = "http://localhost:8080/realms/TestRealm/protocol/openid-connect/token";
        String client_id = "java-client";
        String username = "vscardel";
        String password = "password";

        String credentials = client_id + ":" + "";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        URL url = new URL(keycloakUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String postData = "grant_type=password&username=" + username + "&password=" + password;
        conn.getOutputStream().write(postData.getBytes("UTF-8"));

        Scanner responseScanner = new Scanner(conn.getInputStream());
        StringBuilder responseBuilder = new StringBuilder();

        while (responseScanner.hasNext()) {
            responseBuilder.append(responseScanner.next());
        }

        System.out.println(responseBuilder.toString());

        responseScanner.close();
    }
}