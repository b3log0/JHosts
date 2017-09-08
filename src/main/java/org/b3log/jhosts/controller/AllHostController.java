package org.b3log.jhosts.controller;

import io.datafx.controller.ViewController;

@ViewController(value = "/hxml/ui/TreeTableView.fxml", title = "Material Design Example")
public class AllHostController extends BaseController {
    public AllHostController() {
        super();
        this.title = "All Hosts";
    }
}
