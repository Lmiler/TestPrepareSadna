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
    public final String REGISTER = "https://app.seker.live/fm1/register";   //קבוע שבו שמור שמור הקובץ עם הנתיב של המשימה להירשם
    public final String GET_TASKS = "https://app.seker.live/fm1/get-tasks";  //קבוע שבו שמור שמור הקובץ עם הנתיב של המשימה לקבל את כל המשימות
    public final String ADD_TASK = "https://app.seker.live/fm1/add-task";   //קבוע שבו שמור שמור הקובץ עם הנתיב של המשימה להוסיף משימה
    public final String SET_TASK_DONE = "https://app.seker.live/fm1/set-task-done";  //קבוע שבו שמור שמור הקובץ עם הנתיב של המשימה לסמן משימה כdone

    private CloseableHttpClient client;  //ליצור את "הלקוח" שיצור את הקשר עם הapi
    private URI uri;    //אובייקט של הקישור שישלח לשרת עבור המשימה המתאימה

    public ApiManager() {
        client = HttpClients.createDefault();  //הבנאי של הapi יוצר את האובייקט של הלקוח עם חבילת ברירת מחדש הקיימת בתוך המחלקה שלו
    }

    public void register(String id) throws URISyntaxException, IOException {
        uri = new URIBuilder(REGISTER).setParameter("id", id)   //הוספת "פרמטרים" לקישור על פי ההוראות
                .build();    //הפקודה שהופכת את הסטריג שבתוך הבילדר לקישור
        HttpPost post = new HttpPost(uri);   //יצירת אובייקט על פי סוג הבקשה שצריך לפי ההוראות. במקרה זה סוג הבקשה היא "post"
        CloseableHttpResponse response = client.execute(post);   //בקשה ושמירה של התגובה של הapi למשימה ששלחנו לו
        String response1 = EntityUtils.toString(response.getEntity());   //המרה של התגובה למשתנה מסוג סטריג
        Response response2 = new ObjectMapper().readValue(response1, Response.class);  //יצירת אובייקט מהמחלקה ששומרת ביותר סדר כל חלק בתגובה של השרת
        System.out.println(response2.isSuccess() + "\n");  //הדפסת התגובה
        if (!response2.isSuccess()){
            System.out.println(response2.getErrorCode());  //הדפסת קוד שגיאה במידה והמשימה לא הצליחה
        }
    }

    public void getTask(String id) throws URISyntaxException, IOException {
        uri = new URIBuilder(GET_TASKS).setParameter("id", id)   //הוספת "פרמטרים" לקישור על פי ההוראות
                .build();   //הפקודה שהופכת את הסטריג שבתוך הבילדר לקישור
        HttpGet get = new HttpGet(uri);   //יצירת אובייקט על פי סוג הבקשה שצריך לפי ההוראות. במקרה זה סוג הבקשה היא "get"
        CloseableHttpResponse response = client.execute(get);   //בקשה ושמירה של התגובה של הapi למשימה ששלחנו לו
        String response1 = EntityUtils.toString(response.getEntity());   //המרה של התגובה למשתנה מסוג סטריג
        Response response2 = new ObjectMapper().readValue(response1, Response.class);  //יצירת אובייקט מהמחלקה ששומרת ביותר סדר כל חלק בתגובה של השרת
        int counter = 1;
        for (int i = 0; i < response2.getTasks().size(); i++) {  //הדפסה מסודרת של כל משימה בעזרת מיספור של מונה חיצוני
            if (!response2.getTasks().get(i).isDone()) {
                System.out.println(counter + ": " + response2.getTasks().get(i).getTitle());
                counter++;
            }
        }
        System.out.println("\n");
    }

    public void addTask(String id, String text) throws URISyntaxException, IOException {
        uri = new URIBuilder(ADD_TASK)
                .setParameter("id", id)   //הוספת "פרמטרים" לקישור על פי ההוראות
                .setParameter("text", text)
                .build();   //הפקודה שהופכת את הסטריג שבתוך הבילדר לקישור
        HttpPost post = new HttpPost(uri);   //יצירת אובייקט על פי סוג הבקשה שצריך לפי ההוראות. במקרה זה סוג הבקשה היא "post"
        CloseableHttpResponse response = client.execute(post);   //בקשה ושמירה של התגובה של הapi למשימה ששלחנו לו
        String response1 = EntityUtils.toString(response.getEntity());   //המרה של התגובה למשתנה מסוג סטריג
        if (!response1.isEmpty()){
            //יצירת אובייקט מהמחלקה ששומרת ביותר סדר כל חלק בתגובה של השרת. יצירה זו מתרחשת רק במקרה והמשימה ששלחנו לא הצליחה
            Response response2 = new ObjectMapper().readValue(response1, Response.class);
            System.out.println(false + "Error code: " + response2.getErrorCode()); //הדפסה שקראה שגיאה וקוד השגיאה
        } else {
            System.out.println(true);  //אם לא הייתה שגיאה, הדפסה שהמשימה עברה בהצלחה
        }
    }

    public void setTaskDone(String id, String task) throws URISyntaxException, IOException {
        uri = new URIBuilder(SET_TASK_DONE)
                .setParameter("id", id)     //הוספת "פרמטרים" לקישור על פי ההוראות
                .setParameter("text", task)
                .build();   //הפקודה שהופכת את הסטריג שבתוך הבילדר לקישור
        HttpPost post = new HttpPost(uri);   //יצירת אובייקט על פי סוג הבקשה שצריך לפי ההוראות. במקרה זה סוג הבקשה היא "post"
        CloseableHttpResponse response = client.execute(post);   //בקשה ושמירה של התגובה של הapi למשימה ששלחנו לו
        String response1 = EntityUtils.toString(response.getEntity());   //המרה של התגובה למשתנה מסוג סטריג
        Response response2 = new ObjectMapper().readValue(response1, Response.class);  //יצירת אובייקט מהמחלקה ששומרת ביותר סדר כל חלק בתגובה של השרת
        System.out.println(response2.isSuccess());  //הדפסת התגובה
        if (!response2.isSuccess()){
            System.out.println(response2.getErrorCode());   //הדפסת קוד שגיאה במידה והמשימה לא הצליחה
        }
    }
}
