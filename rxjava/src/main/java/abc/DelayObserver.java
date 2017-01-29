package abc;

import org.json.JSONObject;
import rx.Observer;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DelayObserver implements Observer<JSONObject> {

    ArrayList<JSONObject> time_package = new ArrayList<JSONObject>();
    JSONObserver jsonObserver = new JSONObserver();

    public DelayObserver() throws FileNotFoundException {
    }

    @Override
    public void onCompleted() {

        rx.Observable<JSONObject> jsonObservable = rx.Observable.from(time_package);
        jsonObservable.subscribe(jsonObserver);
        time_package.clear();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onNext(JSONObject jsonObject) {
        for (int i = 0; i < 300000; i++) {
            Math.random();
        }
        time_package.add(jsonObject);
    }
}
