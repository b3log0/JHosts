package org.b3log.jhosts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.layout.StackPane;
import org.b3log.jhosts.Host;
import org.b3log.jhosts.service.FileService;
import org.b3log.jhosts.service.impl.FileServiceImpl;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@ViewController(value = "/hxml/ui/TreeTableView.fxml", title = "Material Design Example")
public class BaseController {
    protected String title = "Default Hosts";
    protected int count = 0;
    protected FileService fileService = new FileServiceImpl();
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private static final String CONTENT_PANE = "ContentPane";
    // editable table view
    @FXMLViewFlowContext
    protected ViewFlowContext context;
    @FXML
    private Label profile;
    @FXML
    private JFXTreeTableView<FXHost> hostsDataTable;
    @FXML
    private JFXTreeTableColumn<FXHost, String> hostIpAddress;
    @FXML
    private JFXTreeTableColumn<FXHost, String> hostDomainName;
    @FXML
    private JFXButton hostAdd;
    @FXML
    private JFXButton hostRemove;
    @FXML
    private Label hostsCount;
    @FXML
    private JFXTextField hostSearch;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton reset;
    @FXML
    private JFXDialog dialog;
    @FXML
    private JFXButton acceptButton;

    /**
     * init fxml when loaded.
     */
    @PostConstruct
    public void init() {
        this.profile.setText(this.title);
        setupEditableTableView();
        save.setOnMouseClicked((e) -> {
            List<Host> hostList = new ArrayList<>();
            int i;
            for (i = 0; i < hostsDataTable.getCurrentItemsCount(); i++) {
                Host host = new Host();
                host.setIpAddress(hostDomainName.getTreeTableView().getTreeItem(i).getValue().ipAddress.getValue());
                host.setDomainName(hostDomainName.getTreeTableView().getTreeItem(i).getValue().domainName.getValue());
                hostList.add(host);
            }
            this.fileService.testWrite(hostList);
            dialog.setTransitionType(DialogTransition.TOP);
            dialog.show((StackPane) context.getRegisteredObject(CONTENT_PANE));
        });
        reset.setOnMouseClicked((e) -> {
            dialog.setTransitionType(DialogTransition.TOP);
            dialog.show((StackPane) context.getRegisteredObject(CONTENT_PANE));
        });
        acceptButton.setOnMouseClicked((e) -> dialog.close());
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<FXHost, T> column, Function<FXHost, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<FXHost, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void setupEditableTableView() {
        setupCellValueFactory(hostIpAddress, FXHost::ipAddressProperty);
        setupCellValueFactory(hostDomainName, FXHost::domainNameProperty);

        // add editors
        hostIpAddress.setCellFactory((TreeTableColumn<FXHost, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        hostIpAddress.setOnEditCommit((CellEditEvent<FXHost, String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().ipAddress.set(t.getNewValue());
        });
        hostDomainName.setCellFactory((TreeTableColumn<FXHost, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        hostDomainName.setOnEditCommit((CellEditEvent<FXHost, String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().domainName.set(t.getNewValue());
        });

        final ObservableList<FXHost> dummyData = getLocalHosts();
        hostsDataTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        hostsDataTable.setShowRoot(false);
        hostsDataTable.setEditable(true);
        hostsCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + hostsDataTable.getCurrentItemsCount() + POSTFIX,
                        hostsDataTable.currentItemsCountProperty()));
        hostSearch.textProperty()
                .addListener(setupSearchField(hostsDataTable));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<FXHost> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(hostProp -> {
                    final FXHost host = hostProp.getValue();
                    return host.ipAddress.get().contains(newVal)
                            || host.domainName.get().contains(newVal);
                });
    }

    ObservableList<FXHost> getLocalHosts() {
        FileService fileService = new FileServiceImpl();
        List<Host> allHosts = fileService.getAllHosts();
        this.count = allHosts.size();
        ObservableList<FXHost> fxHosts = FXCollections.observableArrayList();
        for (Host host : allHosts) {
            fxHosts.add(new FXHost(host.getIpAddress(), host.getDomainName()));
        }
        return fxHosts;
    }

    /*
     * data class
     */
    static final class FXHost extends RecursiveTreeObject<FXHost> {
        final StringProperty ipAddress;
        final StringProperty domainName;

        FXHost(String ipAddress, String domainName) {
            this.ipAddress = new SimpleStringProperty(ipAddress);
            this.domainName = new SimpleStringProperty(domainName);
        }

        StringProperty ipAddressProperty() {
            return ipAddress;
        }

        StringProperty domainNameProperty() {
            return domainName;
        }
    }
}
