package com.antonenko.xenia.racoons;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.antonenko.xenia.racoons.entities.Racoon;
 
  
public class RacoonsClient {
  
    public static final String REST_SERVICE_URI = "http://localhost:8080";
  
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    public HttpHeaders getHeaders(){
        String plainCredentials="admin:qwe123";
//        String plainCredentials="user:user";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
         
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
     
    /*
     * Send a GET request to get list of all racoons.
     */
    public List<Racoon> listAllRacoons(){

        RestTemplate restTemplate = new RestTemplate(); 
         
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        
		ResponseEntity<List<Racoon>> response = restTemplate.exchange(REST_SERVICE_URI + "/racoon/", HttpMethod.GET,
				request, new ParameterizedTypeReference<List<Racoon>>(){});
		List<Racoon> racoons = response.getBody();
        return racoons;
    }
      
    /*
     * Send a GET request to get a specific racoon.
     */
    public Racoon getRacoon(long id){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<Racoon> response = restTemplate.exchange(REST_SERVICE_URI+"/racoon/" + id, HttpMethod.GET, request, Racoon.class);
        Racoon racoon = response.getBody();
        return racoon;
    }

	
    /*
     * Send a POST request to create a new racoon.
     */
    public String createRacoon(String name, int weight) {
        RestTemplate restTemplate = new RestTemplate();
        Racoon racoon = new Racoon(name, weight);
        HttpEntity<Object> request = new HttpEntity<Object>(racoon, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/racoon/", request, Racoon.class);
        return uri.toASCIIString();
    }
  
    /*
     * Send a PUT request to update an existing racoon.
     */
    public Racoon updateRacoon(long id, String name, int weight) {
        RestTemplate restTemplate = new RestTemplate();
        Racoon racoon  = new Racoon(name, weight);
        HttpEntity<Object> request = new HttpEntity<Object>(racoon, getHeaders());
        ResponseEntity<Racoon> response = restTemplate.exchange(REST_SERVICE_URI+"/racoon/" + id, HttpMethod.PUT, request, Racoon.class);
        return response.getBody();
    }
  
    /*
     * Send a DELETE request to delete a specific racoon.
     */
    public void deleteRacoon(long id) {
        System.out.println("\nTesting delete racoon API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/racoon/" + id, HttpMethod.DELETE, request, Racoon.class);
    }

    public static void main(String args[]){
    	
    	RacoonsClient client = new RacoonsClient();
    	
        System.out.println("\nTesting listAllRacoons API-----------");
    	List<Racoon> racoons = client.listAllRacoons();
        if(racoons != null) {
        	System.out.println(racoons);
        }else{
            System.out.println("No racoons =( ------------");
        }
        
        System.out.println("\nTesting create racoon API----------");
        String uri = client.createRacoon("Cory", 15);
        System.out.println("Location : " + uri);
        uri = client.createRacoon("Letho", 13);
        System.out.println("Location : " + uri);
        uri = client.createRacoon("Sonya", 14);
        System.out.println("Location : " + uri);
 
        System.out.println("\nTesting getRacoon API----------");
        Racoon racoon = client.getRacoon(2);
        System.out.println(racoon);
        
 
        racoons = client.listAllRacoons();
        if(racoons != null) {
        	System.out.println(racoons);
        }else{
            System.out.println("No racoons =( ------------");
        }
 
        System.out.println("\nTesting update racoon API----------");
        racoon = client.updateRacoon(1, "Corey", 18);
        System.out.println(racoon);

        client.deleteRacoon(2);
        racoons = client.listAllRacoons();
        System.out.println(racoons);

    }
}
