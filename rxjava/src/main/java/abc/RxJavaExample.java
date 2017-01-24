package abc;

import abc.model.Person;
import abc.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rx.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;

public class RxJavaExample extends Application {

    //time
    static public String label;
    static String dt = "2012-01-01";
    static public int finish_hour = 0;
    static public int finish_minute = 0;
    static public int start_hour = 0;
    static public int start_minute = 0;
    static public String start_date = dt;
    static public String finish_date = "";
    static int hour = 0;
    static int timestamp;
    //GUI
    static public ObservableList<Person> createLocalPush = FXCollections.observableArrayList();
    static public ObservableList<Person> createLocalCreate = FXCollections.observableArrayList();
    static public ObservableList<Person> createLocalWatch = FXCollections.observableArrayList();
    static public ObservableList<Person> createLocalPull = FXCollections.observableArrayList();

    static public ObservableList<Person> createGlobalPush = FXCollections.observableArrayList();
    static public ObservableList<Person> createGlobalCreate = FXCollections.observableArrayList();
    static public ObservableList<Person> createGlobalWatch = FXCollections.observableArrayList();
    static public ObservableList<Person> createGlobalPull = FXCollections.observableArrayList();

    public static void main(String... names) {

        Runnable r = new Runnable() {
            public void run() {
                try {
                    runYourBackgroundTaskHere();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(r);
        launch(names);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RxJavaExample.class.getResource("/abc/view/layout.fxml"));

        Parent root = loader.load();

        PersonOverviewController controller = loader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("GitHub Analysis");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    static void runYourBackgroundTaskHere() throws ParseException, InterruptedException, IOException {

        MyObserver mo = new MyObserver();
        ArrayList<String> events = new ArrayList<String>();
        String yesterday = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        yesterday = dateFormat.format(cal.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(dateFormat.parse(dt));

        while (!dt.equals(yesterday)) {
            while (hour < 24) {
                String url = "http://data.githubarchive.org/" + dt + "-" + hour + ".json.gz";
                InputStream input = new URL(url).openStream();
                InputStreamReader isr = new InputStreamReader(new GZIPInputStream(input));
                BufferedReader in = new BufferedReader(isr);
                String content = null;
                while ((content = in.readLine()) != null)
                    events.add(content);
                finish_date = dt;
                Observable<String> stringObservable = Observable.from(events);
                stringObservable.subscribe(mo);
                hour++;
                events.clear();
            }
            hour = 0;
            c.add(Calendar.DATE, 1);  // number of days to add
            dt = dateFormat.format(c.getTime());
        }
    }
}