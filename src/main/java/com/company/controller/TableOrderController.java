package com.company.controller;

import com.company.dto.ApiResponse;
import com.company.dto.TableOrderDTO;
import com.company.service.TableOrderService;
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
@RequestMapping("/api/v1/table")
public class TableOrderController {
    @Autowired
    private TableOrderService tableOrderService;

    @PostMapping("/create")
    @Operation(summary = "create table ➕", description = "this api used for table creation")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody TableOrderDTO dto) {
        ApiResponse<?> response = tableOrderService.create(dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update table 🛠️", description = "this api used for table update")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Integer id,
                                                 @Valid @RequestBody TableOrderDTO dto) {
        ApiResponse<?> response = tableOrderService.update(id, dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete table ❌", description = "this api used for table delete")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        ApiResponse<?> response = tableOrderService.delete(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getById/{id}")
    @Operation(summary = "getById table 📂", description = "this api used for table getById")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Integer id) {
        ApiResponse<?> response = tableOrderService.getById(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getList")
    @Operation(summary = "getList table 📄📂", description = "this api used for table getList")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(tableOrderService.getList());
    }


    @GetMapping("/open/paging")
    @Operation(summary = "paging table 📖📂", description = "this api used for table paging")
    public ResponseEntity<Page<?>> paging(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(tableOrderService.paging(page, size));
    }
}
