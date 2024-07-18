package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiManager {
    public final String REGISTER = "https://app.seker.live/fm1/register";
    public final String GET_TASKS = "https://app.seker.live/fm1/get-tasks";
    public final String ADD_TASK = "https://app.seker.live/fm1/add-task";
    public final String SET_TASK_DONE = "https://app.seker.live/fm1/set-task-done";

    private CloseableHttpClient client;
    private URI uri;

    public ApiManager() {
        client = HttpClients.createDefault();
    }

    public void register(String id) throws URISyntaxException, IOException {
        uri = new URIBuilder(REGISTER).setParameter("id", id)
                .build();
        HttpPost post = new HttpPost(uri);
        CloseableHttpResponse response = client.execute(post);
        String response1 = EntityUtils.toString(response.getEntity());
        Response response2 = new ObjectMapper().readValue(response1, Response.class);
        System.out.println(response2.getSuccess() + "\n");
    }

    public void getTask(String id) throws URISyntaxException, IOException {
        uri = new URIBuilder(GET_TASKS).setParameter("id", id).build();
        HttpGet get = new HttpGet(uri);
        CloseableHttpResponse response = client.execute(get);
        String response1 = EntityUtils.toString(response.getEntity());
        Response response2 = new ObjectMapper().readValue(response1, Response.class);
        int counter = 1;
        for (int i = 0; i < response2.getTasks().size(); i++) {
            if (!response2.getTasks().get(i).isDone()) {
                System.out.println((counter) + ": " + response2.getTasks().get(i).getTitle());
                counter++;
            }
        }
        System.out.println("\n");
    }

    public void addTask(String id, String text) throws URISyntaxException, IOException {
        uri = new URIBuilder(ADD_TASK)
                .setParameter("id", id)
                .setParameter("text", text)
                .build();
        HttpPost post = new HttpPost(uri);
        CloseableHttpResponse response = client.execute(post);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    public void setTaskDone(String id, String task) throws URISyntaxException, IOException {
        uri = new URIBuilder(SET_TASK_DONE)
                .setParameter("id", id)
                .setParameter("text", task)
                .build();
        HttpPost post = new HttpPost(uri);
        CloseableHttpResponse response = client.execute(post);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
