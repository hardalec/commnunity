package com.jing.community.controller;

import com.jing.community.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    @RequestMapping("/file/upload")
//    @RequestBody
    public FileDto upload(){
        FileDto fileDTO = new FileDto();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/erweima.jpg");
        return fileDTO;
    }
}
