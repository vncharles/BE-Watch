package com.charles.website.controller;

import com.charles.website.domain.Product;
import com.charles.website.model.MessageResponse;
import com.charles.website.model.ProductRequest;
import com.charles.website.service.FileService;
import com.charles.website.service.ProductService;
import com.charles.website.utils.Authen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<Product> list = productService.getAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestParam("image") MultipartFile image,
                                    @RequestParam("data") String data) throws JsonProcessingException {
        Authen.check();
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest req = mapper.readValue(data, ProductRequest.class);

        productService.create(req, image);

        return ResponseEntity.ok(new MessageResponse("Create product is success"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@RequestParam Long id,
                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                    @RequestParam("data") String data) throws JsonProcessingException {
        Authen.check();
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest req = mapper.readValue(data, ProductRequest.class);

        productService.update(id, req, image);

        return ResponseEntity.ok(new MessageResponse("Update product is success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable("id")Long id) {
        Product product = productService.getDetail(id);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        Authen.check();
        productService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Delete product is success"));
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
