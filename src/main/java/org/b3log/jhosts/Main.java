package org.b3log.jhosts;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
public class Main extends Application {
    public static void main(String[] args){
//        FileUtil.readHostFile();
//        FileUtil.writeHostFile();
        Main.launch(args);
    }

    private static final String COMPUTER_DEPARTMENT = "Computer Department";
    private static final String SALES_DEPARTMENT = "Sales Department";
    private static final String IT_DEPARTMENT = "IT Department";
    private static final String HR_DEPARTMENT = "HR Department";

    @Override
    public void start(Stage primaryStage) throws Exception {

        JFXTreeTableColumn<Main.User, String> deptColumn = new JFXTreeTableColumn<>("Department");
        deptColumn.setPrefWidth(150);
        deptColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Main.User, String> param) -> {
            if (deptColumn.validateValue(param)) {
                return param.getValue().getValue().department;
            } else {
                return deptColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Main.User, String> empColumn = new JFXTreeTableColumn<>("Employee");
        empColumn.setPrefWidth(150);
        empColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Main.User, String> param) -> {
            if (empColumn.validateValue(param)) {
                return param.getValue().getValue().userName;
            } else {
                return empColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Main.User, String> ageColumn = new JFXTreeTableColumn<>("Age");
        ageColumn.setPrefWidth(150);
        ageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Main.User, String> param) -> {
            if (ageColumn.validateValue(param)) {
                return param.getValue().getValue().age;
            } else {
                return ageColumn.getComputedValue(param);
            }
        });


        ageColumn.setCellFactory((TreeTableColumn<Main.User, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        ageColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Main.User, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().age.set(t.getNewValue()));

        empColumn.setCellFactory((TreeTableColumn<Main.User, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        empColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Main.User, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().userName.set(t.getNewValue()));

        deptColumn.setCellFactory((TreeTableColumn<Main.User, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        deptColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Main.User, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().department.set(t.getNewValue()));


        // data
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new Main.User(COMPUTER_DEPARTMENT, "23", "CD 1"));
        users.add(new Main.User(SALES_DEPARTMENT, "22", "Employee 1"));
        users.add(new Main.User(SALES_DEPARTMENT, "24", "Employee 2"));
        users.add(new Main.User(SALES_DEPARTMENT, "25", "Employee 4"));
        users.add(new Main.User(SALES_DEPARTMENT, "27", "Employee 5"));
        users.add(new Main.User(IT_DEPARTMENT, "42", "ID 2"));
        users.add(new Main.User(HR_DEPARTMENT, "21", "HR 1"));
        users.add(new Main.User(HR_DEPARTMENT, "28", "HR 2"));

        for (int i = 0; i < 40000; i++) {
            users.add(new Main.User(HR_DEPARTMENT, Integer.toString(i % 10), "HR 3" + i));
        }
        for (int i = 0; i < 40000; i++) {
            users.add(new Main.User(COMPUTER_DEPARTMENT, Integer.toString(i % 20), "CD 2" + i));
        }

        for (int i = 0; i < 40000; i++) {
            users.add(new Main.User(IT_DEPARTMENT, Integer.toString(i % 5), "HR 4" + i));
        }

        // build tree
        final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);

        JFXTreeTableView<Main.User> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        treeView.getColumns().setAll(deptColumn, ageColumn, empColumn);

        FlowPane main = new FlowPane();
        main.setPadding(new Insets(10));
        main.getChildren().add(treeView);


        JFXButton groupButton = new JFXButton("Group");
        groupButton.setOnAction((action) -> new Thread(() -> treeView.group(empColumn)).start());
        main.getChildren().add(groupButton);

        JFXButton unGroupButton = new JFXButton("unGroup");
        unGroupButton.setOnAction((action) -> treeView.unGroup(empColumn));
        main.getChildren().add(unGroupButton);

        JFXTextField filterField = new JFXTextField();
        main.getChildren().add(filterField);

        Label size = new Label();

        filterField.textProperty().addListener((o, oldVal, newVal) -> {
            treeView.setPredicate(userProp -> {
                final Main.User user = userProp.getValue();
                return user.age.get().contains(newVal)
                        || user.department.get().contains(newVal)
                        || user.userName.get().contains(newVal);
            });
        });

        size.textProperty()
                .bind(Bindings.createStringBinding(() -> String.valueOf(treeView.getCurrentItemsCount()),
                        treeView.currentItemsCountProperty()));
        main.getChildren().add(size);

        Scene scene = new Scene(main, 475, 500);
        scene.getStylesheets().add(Main.class.getResource("/css/jfoenix-components.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static final class User extends RecursiveTreeObject<Main.User> {
        final StringProperty userName;
        final StringProperty age;
        final StringProperty department;

        User(String department, String age, String userName) {
            this.department = new SimpleStringProperty(department);
            this.userName = new SimpleStringProperty(userName);
            this.age = new SimpleStringProperty(age);
        }
    }
}
