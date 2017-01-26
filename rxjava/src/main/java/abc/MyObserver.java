package abc;

import org.json.JSONObject;
import rx.Observer;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class MyObserver implements Observer<String> {

    //static int timestamp = 23; //timesloty co 23 min

    ArrayList<JSONObject> time_package = new ArrayList<JSONObject>();
    JSONObserver jsonObserver = new JSONObserver();
    RxJavaExample rxJavaExample = new RxJavaExample();
    int temp_timestamp = rxJavaExample.timestamp;

    public MyObserver() throws FileNotFoundException {
    }

    public void onCompleted() {
    }

    public void onError(Throwable throwable) {
        System.out.println(throwable.toString());
    }

    public void onNext(String line) {
        JSONObject obj = new JSONObject(line);
        String date = obj.getString("created_at");
        String[] date_splited = date.split("T");
        String[] time_splited = date_splited[1].split(":"); //minuta
        Integer minute = Integer.valueOf(time_splited[1]);
        if (minute == temp_timestamp) {
            rxJavaExample.finish_minute = temp_timestamp;
            rxJavaExample.finish_hour = Integer.valueOf(time_splited[0]);
            rx.Observable<JSONObject> jsonObservable = rx.Observable.from(time_package);
            jsonObservable.subscribe(jsonObserver);
            temp_timestamp += rxJavaExample.timestamp;
            if (temp_timestamp > 59) temp_timestamp -= 60;
            time_package.clear();
        }
        time_package.add(obj);

        for (int i = 0; i < 800000; i++) {
            Math.random();
        }
    }
}
