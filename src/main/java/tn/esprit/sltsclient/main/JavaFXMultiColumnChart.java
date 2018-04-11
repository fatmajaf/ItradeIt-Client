package tn.esprit.sltsclient.main;


import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
 
/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFXMultiColumnChart extends Application {
     
    public class Record{
        private SimpleStringProperty fieldDay;
        private SimpleDoubleProperty fieldValue1;
        private SimpleDoubleProperty fieldValue2;
         
        Record(String fDay, double fValue1, double fValue2){
            this.fieldDay = new SimpleStringProperty(fDay);
            this.fieldValue1 = new SimpleDoubleProperty(fValue1);
            this.fieldValue2 = new SimpleDoubleProperty(fValue2);
        }
         
        public String getFieldDay() {
            return fieldDay.get();
        }
         
        public double getFieldValue1() {
            return fieldValue1.get();
        }
         
        public double getFieldValue2() {
            return fieldValue2.get();
        }
         
        public void setFieldDay(String fDay) {
            fieldDay.set(fDay);
        }
      
        public void setFieldValue1(Double fValue1) {
            fieldValue1.set(fValue1);
        }
         
        public void setFieldValue2(Double fValue2) {
            fieldValue2.set(fValue2);
        }
    }
     
    class MyList {
         
        ObservableList<Record> dataList;
        ObservableList<PieChart.Data> pieChartData1;
        ObservableList<XYChart.Data> xyList2;
         
        MyList(){
            dataList = FXCollections.observableArrayList();
            pieChartData1 = FXCollections.observableArrayList();
            xyList2 = FXCollections.observableArrayList();
        }
         
        public void add(Record r){
            dataList.add(r);
            pieChartData1.add(new PieChart.Data(r.getFieldDay(), r.getFieldValue1()));
            xyList2.add(new XYChart.Data(r.getFieldDay(), r.getFieldValue2()));
        }
         
        public void update1(int pos, Double val){
            pieChartData1.set(pos, new PieChart.Data(pieChartData1.get(pos).getName(), val));
        }
         
        public void update2(int pos, Double val){
            xyList2.set(pos, new XYChart.Data(xyList2.get(pos).getXValue(), val));
        }
    }
     
    MyList myList;
     
    private TableView<Record> tableView = new TableView<>();
     
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("java-buddy.blogspot.com");
         
        //prepare myList
        myList = new MyList();
        myList.add(new Record("Sunday", 100, 120));
        myList.add(new Record("Monday", 200, 210));
        myList.add(new Record("Tuesday", 50, 70));
        myList.add(new Record("Wednesday", 75, 50));
        myList.add(new Record("Thursday", 110, 120));
        myList.add(new Record("Friday", 300, 200));
        myList.add(new Record("Saturday", 111, 100));
         
        Group root = new Group();
         
        tableView.setEditable(true);
 
        Callback<TableColumn, TableCell> cellFactory = 
                new Callback<TableColumn, TableCell>() {
                           
                          @Override
                          public TableCell call(TableColumn p) {
                              return new EditingCell();
                          }
                      };
         
        TableColumn columnDay = new TableColumn("Day");
        columnDay.setCellValueFactory(
                new PropertyValueFactory<Record,String>("fieldDay"));
        columnDay.setMinWidth(60);
         
        TableColumn columnValue1 = new TableColumn("Value 1");
        columnValue1.setCellValueFactory(
                new PropertyValueFactory<Record,Double>("fieldValue1"));
        columnValue1.setMinWidth(60);
         
        TableColumn columnValue2 = new TableColumn("Value 2");
        columnValue2.setCellValueFactory(
                new PropertyValueFactory<Record,Double>("fieldValue2"));
        columnValue2.setMinWidth(60);
      
        //--- Add for Editable Cell of Value field, in Double
        columnValue1.setCellFactory(cellFactory);
        columnValue1.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Record, Double>>() {
                     
                    @Override public void handle(TableColumn.CellEditEvent<Record, Double> t) {
                        ((Record)t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setFieldValue1(t.getNewValue());
                         
                        int pos = t.getTablePosition().getRow();
                        myList.update1(pos, t.getNewValue());
                    }
                });
         
        columnValue2.setCellFactory(cellFactory);
        columnValue2.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Record, Double>>() {
                     
                    @Override public void handle(TableColumn.CellEditEvent<Record, Double> t) {
                        ((Record)t.getTableView()
                                .getItems()
                                .get(t.getTablePosition().getRow())).setFieldValue2(t.getNewValue());
                         
                        int pos = t.getTablePosition().getRow();
                        myList.update2(pos, t.getNewValue());
                    }
                });
       
        //--- Prepare StackedBarChart
         
        List<String> dayLabels = Arrays.asList(
                "Sunday", 
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday");
 
        final PieChart pieChart1 = new PieChart(myList.pieChartData1);
        pieChart1.setPrefWidth(200);
        pieChart1.setTitle("Pie Chart 1");
         
        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Day");
        xAxis2.setCategories(FXCollections.<String> observableArrayList(dayLabels));
        yAxis2.setLabel("Value 2");
        XYChart.Series XYSeries2 = new XYChart.Series(myList.xyList2);
        XYSeries2.setName("XYChart.Series 2");
         
        final LineChart<String,Number> lineChart2 = 
                new LineChart<>(xAxis2,yAxis2);
        lineChart2.setTitle("Line Chart 2");
        lineChart2.setPrefWidth(250);
        lineChart2.getData().add(XYSeries2);
 
        //---
        tableView.setItems(myList.dataList);
        tableView.getColumns().addAll(columnDay, columnValue1, columnValue2);
        tableView.setPrefWidth(200);
         
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(tableView, pieChart1, lineChart2);
  
        root.getChildren().add(hBox);
      
      primaryStage.setScene(new Scene(root, 750, 400));
      primaryStage.show();
    }
 
    class EditingCell extends TableCell<Record, Double> {
        private TextField textField;
         
        public EditingCell() {}
         
        @Override
        public void startEdit() {
            super.startEdit();
             
            if (textField == null) {
                createTextField();
            }
             
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }
         
        @Override
        public void cancelEdit() {
            super.cancelEdit();
             
            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
         
        @Override
        public void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
          
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                     
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }
         
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
              
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(Double.parseDouble(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
      
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
     
}