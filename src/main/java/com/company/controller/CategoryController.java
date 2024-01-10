package com.company.controller;

import com.company.dto.ApiResponse;
import com.company.dto.CategoryDTO;
import com.company.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @Operation(summary = "create category ➕", description = "this api used for category creation")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody CategoryDTO dto) {
        ApiResponse<?> response = categoryService.create(dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update category 🛠️", description = "this api used for category update")
    @CachePut(cacheNames = "category", key = "#id")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Integer id,
                                                 @Valid @RequestBody CategoryDTO dto) {
        ApiResponse<?> response = categoryService.update(id, dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete category ❌", description = "this api used for category delete")
    @CacheEvict(cacheNames = "category", key = "#id")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        ApiResponse<?> response = categoryService.delete(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getById/{id}")
    @Operation(summary = "getById category 📕", description = "this api used for category getById")
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Integer id) {
        ApiResponse<?> response = categoryService.getById(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @GetMapping("/open/getList")
    @Operation(summary = "getList category 📄📕", description = "this api used for category getList")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(categoryService.getList());
    }

    @GetMapping("/open/paging")
    @Operation(summary = "paging category 📖📕", description = "this api used for category paging")
    public ResponseEntity<Page<?>> paging(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(categoryService.paging(page, size));
    }

    @Scheduled(cron = "0 30 0 0 0 0")
    @CacheEvict(value = "category", allEntries = true)
    void cleanCaches() {
        log.info("cache category cleared");
    }
}
