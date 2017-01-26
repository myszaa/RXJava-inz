package abc;

import abc.json.Actor;
import abc.json.Repo;
import abc.model.Person;
import javafx.application.Platform;
import org.json.JSONObject;
import rx.Observer;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


public class JSONObserver implements Observer<JSONObject> {

    RxJavaExample rxJavaExample = new RxJavaExample();
    static HashSet<Actor> actors = new HashSet<Actor>();
    static HashSet<Repo> repos = new HashSet<Repo>();

    static ArrayList<Person> actorMapCreateEventSortedOutLocal = new ArrayList<Person>();
    static ArrayList<Person> actorMapWatchEventSortedOutLocal = new ArrayList<Person>();
    static ArrayList<Person> actorMapPushEventSortedOutLocal = new ArrayList<Person>();
    static ArrayList<Person> actorMapPullEventSortedOutLocal = new ArrayList<Person>();

    static HashMap<Integer, Integer> actorMapCreateEventLocal = new HashMap<Integer, Integer>();
    static LinkedHashMap<Integer, Integer> actorMapCreateEventSortedLocal = null;
    static HashMap<Integer, Integer> repoMapCreateEventLocal = new HashMap<Integer, Integer>();

    static HashMap<Integer, Integer> actorMapPushEventLocal = new HashMap<Integer, Integer>();
    static LinkedHashMap<Integer, Integer> actorMapPushEventSortedLocal = null;
    static HashMap<Integer, Integer> repoMapPushEventLocal = new HashMap<Integer, Integer>();

    static HashMap<Integer, Integer> actorMapWatchEventLocal = new HashMap<Integer, Integer>();
    static LinkedHashMap<Integer, Integer> actorMapWatchEventSortedLocal = null;
    static HashMap<Integer, Integer> repoMapWatchEventLocal = new HashMap<Integer, Integer>();

    static HashMap<Integer, Integer> actorMapPullEventLocal = new HashMap<Integer, Integer>();
    static LinkedHashMap<Integer, Integer> actorMapPullEventSortedLocal = null;
    static HashMap<Integer, Integer> repoMapPullEventLocal = new HashMap<Integer, Integer>();

    static ArrayList<Person> actorMapCreateEventSortedOutGlobal = new ArrayList<Person>();
    static ArrayList<Person> actorMapWatchEventSortedOutGlobal = new ArrayList<Person>();
    static ArrayList<Person> actorMapPushEventSortedOutGlobal = new ArrayList<Person>();
    static ArrayList<Person> actorMapPullEventSortedOutGlobal = new ArrayList<Person>();

    static HashMap<Integer, Integer> actorMapCreateEventGlobal = new HashMap<Integer, Integer>();
    static LinkedHashMap<Integer, Integer> actorMapCreateEventSortedGlobal = null;

    static HashMap<Integer, Integer> actorMapPushEventGlobal = new HashMap<Integer, Integer>();
    static LinkedHashMap<Integer, Integer> actorMapPushEventSortedGlobal = null;

    static HashMap<Integer, Integer> actorMapWatchEventGlobal = new HashMap<Integer, Integer>();
    static LinkedHashMap<Integer, Integer> actorMapWatchEventSortedGlobal = null;

    static HashMap<Integer, Integer> actorMapPullEventGlobal = new HashMap<Integer, Integer>();
    static LinkedHashMap<Integer, Integer> actorMapPullEventSortedGlobal = null;

    String startDate;
    String finishDate;
    String startHour;
    String finishHour;
    String startMinute;
    String finishMinute;

    public JSONObserver() throws FileNotFoundException {
    }

    public void onCompleted() {

        startDate = rxJavaExample.start_date;
        finishDate = rxJavaExample.finish_date;
        startHour = String.valueOf(rxJavaExample.start_hour);
        finishHour = String.valueOf(rxJavaExample.finish_hour);
        startMinute = String.valueOf(rxJavaExample.start_minute);
        finishMinute = String.valueOf(rxJavaExample.finish_minute);
        int licznik = 0;
        if (rxJavaExample.start_hour < 10)
            startHour = "0" + startHour;
        if (rxJavaExample.finish_hour < 10)
            finishHour = "0" + finishHour;
        if (rxJavaExample.start_minute < 10)
            startMinute = "0" + startMinute;
        if (rxJavaExample.finish_minute < 10)
            finishMinute = "0" + finishMinute;

        rxJavaExample.label = startDate + " " + startHour + ":" + startMinute + " -> " + finishDate + " " + finishHour + ":" + finishMinute;

        for (Map.Entry<Integer, Integer> entry : actorMapPushEventLocal.entrySet()) {
            if (!actorMapPushEventGlobal.containsKey(entry.getKey())) {
                actorMapPushEventGlobal.put(entry.getKey(), entry.getValue());
            } else {
                actorMapPushEventGlobal.put(entry.getKey(), actorMapPushEventGlobal.get(entry.getKey()) + entry.getValue());
            }
        }

        for (Map.Entry<Integer, Integer> entry : actorMapCreateEventLocal.entrySet()) {
            if (!actorMapCreateEventGlobal.containsKey(entry.getKey())) {
                actorMapCreateEventGlobal.put(entry.getKey(), entry.getValue());
            } else {
                actorMapCreateEventGlobal.put(entry.getKey(), actorMapCreateEventGlobal.get(entry.getKey()) + entry.getValue());
            }
        }

        for (Map.Entry<Integer, Integer> entry : actorMapWatchEventLocal.entrySet()) {
            if (!actorMapWatchEventGlobal.containsKey(entry.getKey())) {
                actorMapWatchEventGlobal.put(entry.getKey(), entry.getValue());
            } else {
                actorMapWatchEventGlobal.put(entry.getKey(), actorMapWatchEventGlobal.get(entry.getKey()) + entry.getValue());
            }
        }

        for (Map.Entry<Integer, Integer> entry : actorMapPullEventLocal.entrySet()) {
            if (!actorMapPullEventGlobal.containsKey(entry.getKey())) {
                actorMapPullEventGlobal.put(entry.getKey(), entry.getValue());
            } else {
                actorMapPullEventGlobal.put(entry.getKey(), actorMapPullEventGlobal.get(entry.getKey()) + entry.getValue());
            }
        }

        actorMapCreateEventSortedLocal = actorMapCreateEventLocal.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        licznik = 0;
        for (Map.Entry<Integer, Integer> entry : actorMapCreateEventSortedLocal.entrySet()) {
            if (licznik < 10) {
                for (Actor a : actors) {
                    if (a.getId().equals(entry.getKey()))
                        actorMapCreateEventSortedOutLocal.add(new Person(a.getLogin(), String.valueOf(entry.getValue())));
                }
                licznik++;
            } else break;
        }

        actorMapPushEventSortedLocal = actorMapPushEventLocal.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        licznik = 0;
        for (Map.Entry<Integer, Integer> entry : actorMapPushEventSortedLocal.entrySet()) {
            if (licznik < 10) {
                for (Actor a : actors) {
                    if (a.getId().equals(entry.getKey()))
                        actorMapPushEventSortedOutLocal.add(new Person(a.getLogin(), String.valueOf(entry.getValue())));
                }
                licznik++;
            } else break;
        }

        actorMapWatchEventSortedLocal = actorMapWatchEventLocal.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        licznik = 0;
        for (Map.Entry<Integer, Integer> entry : actorMapWatchEventSortedLocal.entrySet()) {
            if (licznik < 10) {
                for (Actor a : actors) {
                    if (a.getId().equals(entry.getKey()))
                        actorMapWatchEventSortedOutLocal.add(new Person(a.getLogin(), String.valueOf(entry.getValue())));
                }
                licznik++;
            } else break;
        }

        actorMapPullEventSortedLocal = actorMapPullEventLocal.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        licznik = 0;
        for (Map.Entry<Integer, Integer> entry : actorMapPullEventSortedLocal.entrySet()) {
            if (licznik < 10) {
                for (Actor a : actors) {
                    if (a.getId().equals(entry.getKey()))
                        actorMapPullEventSortedOutLocal.add(new Person(a.getLogin(), String.valueOf(entry.getValue())));
                }
                licznik++;
            } else break;
        }

        actorMapCreateEventSortedGlobal = actorMapCreateEventGlobal.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        licznik = 0;
        for (Map.Entry<Integer, Integer> entry : actorMapCreateEventSortedGlobal.entrySet()) {
            if (licznik < 10) {
                for (Actor a : actors) {
                    if (a.getId().equals(entry.getKey()))
                        actorMapCreateEventSortedOutGlobal.add(new Person(a.getLogin(), String.valueOf(entry.getValue())));
                }
                licznik++;
            } else break;
        }

        actorMapPushEventSortedGlobal = actorMapPushEventGlobal.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        licznik = 0;
        for (Map.Entry<Integer, Integer> entry : actorMapPushEventSortedGlobal.entrySet()) {
            if (licznik < 10) {
                for (Actor a : actors) {
                    if (a.getId().equals(entry.getKey()))
                        actorMapPushEventSortedOutGlobal.add(new Person(a.getLogin(), String.valueOf(entry.getValue())));
                }
                licznik++;
            } else break;
        }

        actorMapWatchEventSortedGlobal = actorMapWatchEventGlobal.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        licznik = 0;
        for (Map.Entry<Integer, Integer> entry : actorMapWatchEventSortedGlobal.entrySet()) {
            if (licznik < 10) {
                for (Actor a : actors) {
                    if (a.getId().equals(entry.getKey()))
                        actorMapWatchEventSortedOutGlobal.add(new Person(a.getLogin(), String.valueOf(entry.getValue())));
                }
                licznik++;
            } else break;
        }

        actorMapPullEventSortedGlobal = actorMapPullEventGlobal.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        licznik = 0;
        for (Map.Entry<Integer, Integer> entry : actorMapPullEventSortedGlobal.entrySet()) {
            if (licznik < 10) {
                for (Actor a : actors) {
                    if (a.getId().equals(entry.getKey()))
                        actorMapPullEventSortedOutGlobal.add(new Person(a.getLogin(), String.valueOf(entry.getValue())));
                }
                licznik++;
            } else break;
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rxJavaExample.createLocalCreate.clear();
                rxJavaExample.createLocalWatch.clear();
                rxJavaExample.createLocalPull.clear();
                rxJavaExample.createLocalPush.clear();
                ////////////////////////////////////
                rxJavaExample.createGlobalCreate.clear();
                rxJavaExample.createGlobalWatch.clear();
                rxJavaExample.createGlobalPull.clear();
                rxJavaExample.createGlobalPush.clear();

                rxJavaExample.createLocalPush.addAll(actorMapPushEventSortedOutLocal);
                rxJavaExample.createLocalWatch.addAll(actorMapWatchEventSortedOutLocal);
                rxJavaExample.createLocalCreate.addAll(actorMapCreateEventSortedOutLocal);
                rxJavaExample.createLocalPull.addAll(actorMapPullEventSortedOutLocal);
                /////////////////////////////////////////////////////////////////
                rxJavaExample.createGlobalPush.addAll(actorMapPushEventSortedOutGlobal);
                rxJavaExample.createGlobalWatch.addAll(actorMapWatchEventSortedOutGlobal);
                rxJavaExample.createGlobalCreate.addAll(actorMapCreateEventSortedOutGlobal);
                rxJavaExample.createGlobalPull.addAll(actorMapPullEventSortedOutGlobal);

                actorMapCreateEventSortedOutLocal.clear();
                actorMapWatchEventSortedOutLocal.clear();
                actorMapPushEventSortedOutLocal.clear();
                actorMapPullEventSortedOutLocal.clear();
                ////////////////////////////////////////////////////////////
                actorMapCreateEventSortedOutGlobal.clear();
                actorMapWatchEventSortedOutGlobal.clear();
                actorMapPushEventSortedOutGlobal.clear();
                actorMapPullEventSortedOutGlobal.clear();
            }
        });

        rxJavaExample.start_date = finishDate;
        rxJavaExample.start_hour = rxJavaExample.finish_hour;
        rxJavaExample.start_minute = rxJavaExample.finish_minute;
        clearBoxes();
    }

    public void onError(Throwable throwable) {
        System.out.println(throwable.toString());
    }

    public void onNext(JSONObject obj) {

        if (obj.getString("type").equals("CreateEvent")) {
            createEvent(obj);
        } else if (obj.getString("type").equals("PushEvent")) {
            pushEvent(obj);
        } else if (obj.getString("type").equals("WatchEvent")) {
            watchEvent(obj);
        } else if (obj.getString("type").equals("PullRequestEvent")) {
            pullEvent(obj);
        }
    }

    static void createEvent(JSONObject obj) {
        JSONObject object = obj.getJSONObject("actor");
        Actor actor = new Actor(object.getInt("id"), object.getString("login"));
        actors.add(actor);
        if (!actorMapCreateEventLocal.containsKey(actor.getId())) {
            actorMapCreateEventLocal.put(actor.getId(), 1);
        } else {
            actorMapCreateEventLocal.put(actor.getId(), actorMapCreateEventLocal.get(actor.getId()) + 1);
        }

        JSONObject object2 = obj.getJSONObject("repo");
        Repo repo = new Repo(object2.getInt("id"), object2.getString("name"));
        repos.add(repo);

        if (!repoMapCreateEventLocal.containsKey(repo.getId())) {
            repoMapCreateEventLocal.put(repo.getId(), 1);
        } else {
            repoMapCreateEventLocal.put(repo.getId(), repoMapCreateEventLocal.get(repo.getId()) + 1);
        }
    }

    static void pushEvent(JSONObject obj) {
        JSONObject object = obj.getJSONObject("actor");
        Actor actor = new Actor(object.getInt("id"), object.getString("login"));
        actors.add(actor);

        if (!actorMapPushEventLocal.containsKey(actor.getId())) {
            actorMapPushEventLocal.put(actor.getId(), 1);
        } else {
            actorMapPushEventLocal.put(actor.getId(), actorMapPushEventLocal.get(actor.getId()) + 1);
        }

        JSONObject object2 = obj.getJSONObject("repo");
        Repo repo = new Repo(object2.getInt("id"), object2.getString("name"));
        repos.add(repo);

        if (!repoMapPushEventLocal.containsKey(repo.getId())) {
            repoMapPushEventLocal.put(repo.getId(), 1);
        } else {
            repoMapPushEventLocal.put(repo.getId(), repoMapPushEventLocal.get(repo.getId()) + 1);
        }
    }

    static void watchEvent(JSONObject obj) {
        JSONObject object = obj.getJSONObject("actor");
        Actor actor = new Actor(object.getInt("id"), object.getString("login"));
        actors.add(actor);

        if (!actorMapWatchEventLocal.containsKey(actor.getId())) {
            actorMapWatchEventLocal.put(actor.getId(), 1);
        } else {
            actorMapWatchEventLocal.put(actor.getId(), actorMapWatchEventLocal.get(actor.getId()) + 1);
        }

        JSONObject object2 = obj.getJSONObject("repo");
        Repo repo = new Repo(object2.getInt("id"), object2.getString("name"));
        repos.add(repo);

        if (!repoMapWatchEventLocal.containsKey(repo.getId())) {
            repoMapWatchEventLocal.put(repo.getId(), 1);
        } else {
            repoMapWatchEventLocal.put(repo.getId(), repoMapWatchEventLocal.get(repo.getId()) + 1);
        }
    }

    static void pullEvent(JSONObject obj) {
        JSONObject object = obj.getJSONObject("actor");
        Actor actor = new Actor(object.getInt("id"), object.getString("login"));
        actors.add(actor);

        if (!actorMapPullEventLocal.containsKey(actor.getId())) {
            actorMapPullEventLocal.put(actor.getId(), 1);
        } else {
            actorMapPullEventLocal.put(actor.getId(), actorMapPullEventLocal.get(actor.getId()) + 1);
        }

        JSONObject object2 = obj.getJSONObject("repo");
        Repo repo = new Repo(object2.getInt("id"), object2.getString("name"));
        repos.add(repo);

        if (!repoMapPullEventLocal.containsKey(repo.getId())) {
            repoMapPullEventLocal.put(repo.getId(), 1);
        } else {
            repoMapPullEventLocal.put(repo.getId(), repoMapPullEventLocal.get(repo.getId()) + 1);
        }
    }

    static void clearBoxes() {
        actorMapCreateEventLocal.clear();
        actorMapCreateEventSortedLocal.clear();
        repoMapCreateEventLocal.clear();

        actorMapPushEventLocal.clear();
        actorMapPushEventSortedLocal.clear();
        repoMapPushEventLocal.clear();

        actorMapWatchEventLocal.clear();
        actorMapWatchEventSortedLocal.clear();
        repoMapWatchEventLocal.clear();

        actorMapPullEventLocal.clear();
        actorMapPullEventSortedLocal.clear();
        repoMapPullEventLocal.clear();
    }
}