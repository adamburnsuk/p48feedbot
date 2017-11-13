package com.walmart.qe.mobilebot.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.walmart.qe.mobilebot.exceptions.StorageFileNotFoundException;
import com.walmart.qe.mobilebot.service.StorageService;

/**
 * This class is a controller for working with files in the application.
 * 
 * @author a2burns
 *
 */
@Controller
public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Controller for upload form.  Loads route for upload form and passes model.
     * 
     * @param model the model object for upload form
     * @return
     * @throws IOException
     */
    @GetMapping(value="/files")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList())); 

        return "files/uploadForm";
    }

    /**
     * 
     * @param filename
     * @return
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
    /**
     * 
     * @param filename
     * @return
     */
    @GetMapping("/files/logs/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveLogFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource("logs/" + filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/files")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/files";
    }
    
    @PostMapping(value="/svc/files",produces = "application/json")
    public ResponseEntity<?> svcHandleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);     

        return ResponseEntity.ok().build();
    }

    /**
     * Handles exceptions thrown by controller
     * 
     * @param exc StorageFileNotFoundException object
     * @return
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}

