package com.company.controller;

import com.company.dto.ApiResponse;
import com.company.dto.MenuDTO;
import com.company.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping("/create")
    @Operation(summary = "create  ➕", description = "this api used for menu creation")
    @PreAuthorize("hasAnyRole('CHEF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody MenuDTO dto) {
        ApiResponse<?> response = menuService.create(dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update  🛠️", description = "this api used for menu  update")
    @PreAuthorize("hasAnyRole('CHEF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable String id,
                                                 @Valid @RequestBody MenuDTO dto) {
        ApiResponse<?> response = menuService.update(id, dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete  ❌", description = "this api used for menu  delete")
    @PreAuthorize("hasAnyRole('CHEF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String id) {
        ApiResponse<?> response = menuService.delete(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getById/{id}")
    @Operation(summary = "getById  📃", description = "this api used for menu  getById")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable String id) {
        ApiResponse<?> response = menuService.getById(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getList")
    @Operation(summary = "getList  📄📃", description = "this api used for menu  getList")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(menuService.getList());
    }


    @GetMapping("/open/paging")
    @Operation(summary = "paging  📖📃", description = "this api used for menu  paging")
    public ResponseEntity<Page<?>> paging(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(menuService.paging(page, size));
    }
}
