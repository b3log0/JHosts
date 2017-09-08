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
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import demos.gui.uicomponents.SVGLoaderController;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.paint.Color;
import org.apache.commons.lang3.StringUtils;
import org.b3log.jhosts.GlyphViewer;
import org.b3log.jhosts.Host;
import org.b3log.jhosts.service.FileService;
import org.b3log.jhosts.service.impl.FileServiceImpl;
import org.b3log.jhosts.util.GlyphSet;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@ViewController(value = "/hxml/ui/TreeTableView.fxml", title = "Material Design Example")
public class BaseController {
    protected String title = "Default Hosts";
    protected volatile int count = 0;
    protected FileService fileService = new FileServiceImpl();
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private static final String CONTENT_PANE = "ContentPane";
    private final String svgFileName = "icomoon.svg";
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
    private Label dialogLabel;
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
     * 只允许三种修改操作：
     * 1. 激活或取消激活某一行
     * 2. 删除一行
     * 3. 添加一行
     */
    @PostConstruct
    public void init() {
        this.profile.setText(this.title);
        //TODO 行可以放Label，label可以设置Graphic
        SVGGlyph restGlyph = GlyphSet.getGlyph("repeat",Color.WHITE);
        restGlyph.setSize(20, 20);
        reset.setGraphic(restGlyph);
        SVGGlyph saveGlyph = GlyphSet.getGlyph("save",Color.WHITE);
        saveGlyph.setSize(20,20);
        save.setGraphic(saveGlyph);
        SVGGlyph labelGlyph = GlyphSet.getGlyph("test",Color.BLACK);
        labelGlyph.setSize(20,20);
        dialogLabel.setText("");
        dialogLabel.setGraphic(labelGlyph);
        setupEditableTableView();
        save.setOnMouseClicked((e) -> {
            //获取当前列表中的元素并提交保存
//            List<Host> hostList = new ArrayList<>();
//            for (int i = 0; i < Integer.parseInt(StringUtils.substringBetween(hostsCount.getText(), PREFIX, POSTFIX)); i++) {
//                FXHost fxHost = hostDomainName.getTreeTableView().getTreeItem(i).getValue();
//                Host host = new Host();
//                host.setIpAddress(fxHost.ipAddress.getValue());
//                host.setDomainName(fxHost.domainName.getValue());
//                hostList.add(host);
//            }
//            this.fileService.writeToHostFile(hostList);
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
            fxHosts.add(new FXHost(host.isEnable(),host.getIpAddress(), host.getDomainName()));
        }
        return fxHosts;
    }

    /*
     * data class
     */
    static final class FXHost extends RecursiveTreeObject<FXHost> {
        final BooleanProperty enable;
        final StringProperty ipAddress;
        final StringProperty domainName;

        FXHost(Boolean enable, String ipAddress, String domainName) {
            this.enable = new SimpleBooleanProperty(enable);
            this.ipAddress = new SimpleStringProperty(ipAddress);
            this.domainName = new SimpleStringProperty(domainName);
        }

        BooleanProperty enableProperty() {
            return enable;
        }

        StringProperty ipAddressProperty() {
            return ipAddress;
        }

        StringProperty domainNameProperty() {
            return domainName;
        }
    }
}
