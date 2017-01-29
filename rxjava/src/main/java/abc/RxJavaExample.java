package abc;

import abc.model.Person;
import abc.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
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
import java.util.zip.GZIPInputStream;

public class RxJavaExample extends Application {

    //time
    static public String labelLocal;
    static public int finish_hour = 0;
    static public int finish_minute = 0;
    static public int start_hour = 0;
    static public int start_minute = 0;
    static public String start_date = "2012-01-01";
    static public String finish_date = "";
    static int hour = 0;
    static public int timestamp = 23;
    static public String yesterday = "";
    static Calendar cal = null;
    static Calendar c;
    static DateFormat dateFormat;
    //GUI
    static public ObservableList<Person> createLocalPush = FXCollections.observableArrayList();
    static public ObservableList<Person> createLocalCreate = FXCollections.observableArrayList();
    static public ObservableList<Person> createLocalWatch = FXCollections.observableArrayList();
    static public ObservableList<Person> createLocalPull = FXCollections.observableArrayList();

    static public ObservableList<Person> createGlobalPush = FXCollections.observableArrayList();
    static public ObservableList<Person> createGlobalCreate = FXCollections.observableArrayList();
    static public ObservableList<Person> createGlobalWatch = FXCollections.observableArrayList();
    static public ObservableList<Person> createGlobalPull = FXCollections.observableArrayList();

    public TextInputDialog dialog;
    public Alert alert;

    public static void main(String... names) throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        yesterday = dateFormat.format(cal.getTime());
        c = Calendar.getInstance();
        c.setTime(dateFormat.parse(start_date));
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

    static public void runYourBackgroundTaskHere() throws ParseException, InterruptedException, IOException {

        MyObserver mo = new MyObserver();
        ArrayList<String> events = new ArrayList<String>();

        while (!start_date.equals(yesterday)) {
            while (hour < 24) {
                String url = "http://data.githubarchive.org/" + start_date + "-" + hour + ".json.gz";
                InputStream input = new URL(url).openStream();
                InputStreamReader isr = new InputStreamReader(new GZIPInputStream(input));
                BufferedReader in = new BufferedReader(isr);
                String content = null;
                while ((content = in.readLine()) != null)
                    events.add(content);
                finish_date = start_date;
                Observable<String> stringObservable = Observable.from(events);
                stringObservable.subscribe(mo);
                hour++;
                events.clear();
            }
            hour = 0;
            c.add(Calendar.DATE, 1);  // number of days to add
            start_date = dateFormat.format(c.getTime());
        }
    }
}