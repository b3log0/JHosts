package org.b3log.jhosts.controller;

import org.b3log.jhosts.service.FileService;
import org.b3log.jhosts.service.impl.FileServiceImpl;

/**
 * Created by yaya on 17-9-7.
 */
public class BaseController {
    protected FileService fileService;
    public BaseController(){
        this.fileService = new FileServiceImpl();
    }
}
