package org.b3log.jhosts.controller;

import io.datafx.controller.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.b3log.jhosts.Host;
import org.b3log.jhosts.service.FileService;
import org.b3log.jhosts.service.impl.FileServiceImpl;

import java.util.List;

@ViewController(value = "/hxml/ui/TreeTableView.fxml", title = "Material Design Example")
public class ProdHostController extends BaseController {
    public ProdHostController() {
        super();
        this.title = "Product Hosts";
    }

    /*@Override
    ObservableList<FXHost> getLocalHosts() {
        FileService fileService = new FileServiceImpl();
        List<Host> allHosts = fileService.getAllHosts().subList(10, 50);
        ObservableList<FXHost> fxHosts = FXCollections.observableArrayList();
        for (Host host : allHosts) {
            fxHosts.add(new FXHost(id, host.isEnable(),host.getIpAddress(), host.getDomainName()));
        }
        return fxHosts;
    }*/
}
