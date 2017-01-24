package abc.view;

import abc.RxJavaExample;
import abc.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTableGlobal;
    @FXML
    private TableColumn<Person, String> nickColumnGlobal;
    @FXML
    private TableColumn<Person, String> amountColumnGlobal;
    @FXML
    private ChoiceBox choiceGlobal;
    @FXML
    private Label tableTitleGlobal;
    @FXML
    private PieChart chartGlobal;

    private ListChangeListener<Person> changeListenerGlobal;
    private ObservableList<PieChart.Data> pieChartDataGlobal =
            FXCollections.observableArrayList();

    @FXML
    private TableView<Person> personTableLocal;
    @FXML
    private TableColumn<Person, String> nickColumnLocal;
    @FXML
    private TableColumn<Person, String> amountColumnLocal;
    @FXML
    private ChoiceBox choiceLocal;
    @FXML
    private Label tableTitleLocal;
    @FXML
    private PieChart chartLocal;

    @FXML
    public Label local;

    private ListChangeListener<Person> changeListenerLocal;
    private ObservableList<PieChart.Data> pieChartDataLocal =
            FXCollections.observableArrayList();

    private RxJavaExample mainApp;

    public PersonOverviewController() {
    }

    @FXML
    private void initialize() {
        local.setText("Local statistics");
        nickColumnGlobal.setCellValueFactory(cellData -> cellData.getValue().nickProperty());
        amountColumnGlobal.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        nickColumnLocal.setCellValueFactory(cellData -> cellData.getValue().nickProperty());
        amountColumnLocal.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        ObservableList<String> dane = FXCollections.observableArrayList();
        dane.add(new String("PushEvent"));
        dane.add(new String("CreateEvent"));
        dane.add(new String("WatchEvent"));
        dane.add(new String("PullEvent"));
        choiceGlobal.setItems(dane);
        choiceGlobal.setValue(dane.get(0));
        choiceLocal.setItems(dane);
        choiceLocal.setValue(dane.get(0));
        tableTitleGlobal.setText(dane.get(0));
        tableTitleLocal.setText(dane.get(0));

        choiceGlobal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.createGlobalCreate.removeListener(changeListenerGlobal);
                mainApp.createGlobalWatch.removeListener(changeListenerGlobal);
                mainApp.createGlobalPull.removeListener(changeListenerGlobal);
                mainApp.createGlobalPush.removeListener(changeListenerGlobal);
                pieChartDataGlobal.clear();
                if (choiceGlobal.getSelectionModel().getSelectedItem().toString().equals("CreateEvent")) {
                    tableTitleGlobal.setText("CreateEvent");
                    personTableGlobal.setItems(mainApp.createGlobalCreate);
                    mainApp.createGlobalCreate.addListener(changeListenerGlobal);
                    for (Person p : mainApp.createGlobalCreate) {
                        pieChartDataGlobal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                    }
                } else if (choiceGlobal.getSelectionModel().getSelectedItem().toString().equals("WatchEvent")) {
                    tableTitleGlobal.setText("WatchEvent");
                    personTableGlobal.setItems(mainApp.createGlobalWatch);
                    mainApp.createGlobalWatch.addListener(changeListenerGlobal);
                    for (Person p : mainApp.createGlobalWatch) {
                        pieChartDataGlobal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                    }
                } else if (choiceGlobal.getSelectionModel().getSelectedItem().toString().equals("PullEvent")) {
                    tableTitleGlobal.setText("PullEvent");
                    personTableGlobal.setItems(mainApp.createGlobalPull);
                    mainApp.createGlobalPull.addListener(changeListenerGlobal);
                    for (Person p : mainApp.createGlobalPull) {
                        pieChartDataGlobal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                    }
                } else if (choiceGlobal.getSelectionModel().getSelectedItem().toString().equals("PushEvent")) {
                    tableTitleGlobal.setText("PushEvent");
                    personTableGlobal.setItems(mainApp.createGlobalPush);
                    mainApp.createGlobalPush.addListener(changeListenerGlobal);
                    for (Person p : mainApp.createGlobalPush) {
                        pieChartDataGlobal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                    }
                }
            }
        });

        choiceLocal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.createLocalCreate.removeListener(changeListenerLocal);
                mainApp.createLocalWatch.removeListener(changeListenerLocal);
                mainApp.createLocalPull.removeListener(changeListenerLocal);
                mainApp.createLocalPush.removeListener(changeListenerLocal);
                pieChartDataLocal.clear();
                if (choiceLocal.getSelectionModel().getSelectedItem().toString().equals("CreateEvent")) {
                    tableTitleLocal.setText("CreateEvent");
                    personTableLocal.setItems(mainApp.createLocalCreate);
                    mainApp.createLocalCreate.addListener(changeListenerLocal);
                    for (Person p : mainApp.createLocalCreate) {
                        pieChartDataLocal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                    }
                } else if (choiceLocal.getSelectionModel().getSelectedItem().toString().equals("WatchEvent")) {
                    tableTitleLocal.setText("WatchEvent");
                    personTableLocal.setItems(mainApp.createLocalWatch);
                    mainApp.createLocalWatch.addListener(changeListenerLocal);
                    for (Person p : mainApp.createLocalWatch) {
                        pieChartDataLocal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                    }
                } else if (choiceLocal.getSelectionModel().getSelectedItem().toString().equals("PullEvent")) {
                    tableTitleLocal.setText("PullEvent");
                    personTableLocal.setItems(mainApp.createLocalPull);
                    mainApp.createLocalPull.addListener(changeListenerLocal);
                    for (Person p : mainApp.createLocalPull) {
                        pieChartDataLocal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                    }
                } else if (choiceLocal.getSelectionModel().getSelectedItem().toString().equals("PushEvent")) {
                    tableTitleLocal.setText("PushEvent");
                    personTableLocal.setItems(mainApp.createLocalPush);
                    mainApp.createLocalPush.addListener(changeListenerLocal);
                    for (Person p : mainApp.createLocalPush) {
                        pieChartDataLocal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                    }
                }
            }
        });
    }

    public void setMainApp(RxJavaExample mainApp) {
        this.mainApp = mainApp;

        if (mainApp.createGlobalPush != null) personTableGlobal.setItems(mainApp.createGlobalPush);
        changeListenerGlobal = new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Person p : change.getAddedSubList()) {
                            pieChartDataGlobal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                        }
                    } else if (change.wasRemoved()) {
                        for (Person p : change.getRemoved()) {
                            for (int i = 0; i < pieChartDataGlobal.size(); i++) {
                                PieChart.Data d = pieChartDataGlobal.get(i);
                                if (d.getName().equals(p.getNick())) {
                                    pieChartDataGlobal.remove(d);
                                    i--;
                                }
                            }
                        }
                    }
                }
            }
        };

        if (mainApp.createLocalPush != null) personTableLocal.setItems(mainApp.createLocalPush);
        changeListenerLocal = new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> change) {
                local.setText(mainApp.label);
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Person p : change.getAddedSubList()) {
                            pieChartDataLocal.add(new PieChart.Data(p.getNick(), Double.valueOf(p.getAmount())));
                        }
                    } else if (change.wasRemoved()) {
                        for (Person p : change.getRemoved()) {
                            for (int i = 0; i < pieChartDataLocal.size(); i++) {
                                PieChart.Data d = pieChartDataLocal.get(i);
                                if (d.getName().equals(p.getNick())) {
                                    pieChartDataLocal.remove(d);
                                    i--;
                                }
                            }
                        }
                    }
                }
            }
        };

        if (personTableGlobal != null) {
            mainApp.createGlobalPush.addListener(changeListenerGlobal);
            chartGlobal.setData(pieChartDataGlobal);
        }
        if (personTableLocal != null) {
            mainApp.createLocalPush.addListener(changeListenerLocal);
            chartLocal.setData(pieChartDataLocal);
        }
    }
}
