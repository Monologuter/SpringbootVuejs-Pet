package com.evan.aiu.controller;

import com.evan.aiu.dto.AnimalDTO;
import com.evan.aiu.entity.Animal;
import com.evan.aiu.result.Result;
import com.evan.aiu.result.ResultFactory;
import com.evan.aiu.service.AnimalService;
import com.evan.aiu.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class AnimalController {
    @Autowired
    AnimalService animalService;

    @CrossOrigin
    @GetMapping("/api/animal")
    public Result listAnimal() throws Exception {
        return ResultFactory.buildSuccessResult(animalService.list());
    }

    @CrossOrigin
    @GetMapping("/api/animalbystatus")
    public Result listAnimalByStatus(@RequestParam String status) throws Exception {
        return ResultFactory.buildSuccessResult(animalService.listByArea(Integer.valueOf(status)));
    }

    @CrossOrigin
    @GetMapping("/api/animalbyadoptname")
    public Result listAnimalByAoptname(@RequestParam String adoptname) throws Exception {
        return ResultFactory.buildSuccessResult(animalService.listByAdoptname(adoptname));
    }

    @CrossOrigin
    @PostMapping("/api/animal")
    public Result saveAnimal(@RequestBody Animal animal) throws Exception {
        animalService.addOrUpdate(animal);
        return ResultFactory.buildSuccessResult("发布成功！");
    }

    @CrossOrigin
    @PostMapping("/api/admin/content/animal")
    public Result addOrUpdateAnimal(@RequestBody @Valid Animal animal) throws Exception {
        animalService.addOrUpdate(animal);
        return ResultFactory.buildSuccessResult("修改成功");
    }

    @CrossOrigin
    @PostMapping("/api/admin/content/animal/delete")
    public Result deleteAnimal(@RequestBody @Valid Animal animal) throws Exception {
        animalService.deleteById(animal.getId());
        return ResultFactory.buildSuccessResult("删除成功");
    }

    @CrossOrigin
    @GetMapping("/api/area/{cid}/animal")
    public Result listByArea(@PathVariable("cid") int cid) throws Exception {
        return ResultFactory.buildSuccessResult(animalService.listByArea(cid));
    }

    @CrossOrigin
    @GetMapping("/api/search")
    public List<AnimalDTO> searchResult(@RequestParam("keywords") String keywords){
        if("".equals(keywords)) {
            return animalService.list();
        }else {
            return animalService.search(keywords);
        }
    }

    @PostMapping("api/admin/content/animal/covers")
    public String coversUpload(@RequestParam("file") MultipartFile file) throws Exception {
        String folder = "D:/项目仓库/Animals in University/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
