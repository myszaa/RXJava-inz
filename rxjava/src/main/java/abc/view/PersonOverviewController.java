package abc.view;

import abc.RxJava;
import abc.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @FXML
    public MenuItem setTimeboxItem;

    @FXML
    public MenuItem startAnalysisItem;

    @FXML
    public MenuItem startDateItem;

    private ListChangeListener<Person> changeListenerLocal;
    private ObservableList<PieChart.Data> pieChartDataLocal =
            FXCollections.observableArrayList();

    private RxJava mainApp;
    public DatePicker datePicker = new DatePicker();

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

    public void setMainApp(RxJava mainApp) {
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
                local.setText(mainApp.labelLocal);
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

    public void closeExit(ActionEvent event) {
        System.exit(0);
    }

    public void setTimebox(ActionEvent event) {
        try {
            mainApp.dialog = new TextInputDialog(String.valueOf(mainApp.timestamp));
            mainApp.dialog.setTitle("Timebox");
            mainApp.dialog.setHeaderText("Timebox between 0 - 60 minutes");
            mainApp.dialog.setContentText("Please enter your timebox:");
            Optional<String> result = mainApp.dialog.showAndWait();
            if (result.isPresent()) {
                if (Integer.valueOf(result.get()) <= 0 || Integer.valueOf(result.get()) > 59) {
                    mainApp.alert = new Alert(Alert.AlertType.ERROR);
                    mainApp.alert.setTitle("Error");
                    mainApp.alert.setContentText("Please entry correct value!");
                    if (Integer.valueOf(result.get()) <= 0) mainApp.alert.setHeaderText("You entry too small value");
                    else mainApp.alert.setHeaderText("You entry too large value");
                    mainApp.alert.showAndWait();
                    setTimebox(null);
                } else
                    mainApp.timestamp = Integer.valueOf(result.get());
            }
        } catch (NumberFormatException e) {
            mainApp.alert = new Alert(Alert.AlertType.ERROR);
            mainApp.alert.setTitle("Error");
            mainApp.alert.setContentText("Please entry correct value!");
            mainApp.alert.setHeaderText("You didn't entry number");
            mainApp.alert.showAndWait();
            setTimebox(null);
        }
    }

    public void startAnalysis(ActionEvent event) {
        setTimeboxItem.setDisable(true);
        startAnalysisItem.setVisible(false);
        startDateItem.setDisable(true);

        Runnable r = new Runnable() {
            public void run() {
                try {
                    mainApp.runYourBackgroundTaskHere();
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
    }

    public void setStartDate(ActionEvent event) throws ParseException {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.DECORATED);
        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 50, 50, 50));
        layout.setSpacing(20);
        Label lbl = new Label("Set start date");
        lbl.setFont(Font.font("Amble CN", FontWeight.BOLD, 20));
        layout.getChildren().add(lbl);

        Scene scene = new Scene(layout, 250, 150);
        dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.setTitle("Start date");
        dialog.show();
        datePicker = new DatePicker();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(mainApp.start_date);
        Date yesterday = sdf.parse(mainApp.yesterday);
        int days = Days.daysBetween(new DateTime(start), new DateTime(yesterday)).getDays();

        layout.getChildren().add(datePicker);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate actuallStartDate = LocalDate.parse(mainApp.start_date, formatter);

        datePicker.setValue(actuallStartDate);
        Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        LocalDate.of(2012, 1, 1).plusDays(0))
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }

                                if (item.isAfter(
                                        LocalDate.of(2012, 1, 1).plusDays(days))
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.setOnAction(event1 -> {
            LocalDate date = datePicker.getValue();
            mainApp.start_date = date.toString();
        });

        Button btn = new Button();
        btn.setText("OK");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        layout.getChildren().add(btn);
        layout.setAlignment(Pos.CENTER);
    }
}
