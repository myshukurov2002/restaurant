package com.company.controller;

import com.company.dto.ApiResponse;
import com.company.dto.CheckDTO;
import com.company.service.CheckService;
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
@RequestMapping("/api/v1/check")
public class CheckController {
    @Autowired
    private CheckService checkService;

    @PostMapping("/create/{useCashback}")
    @Operation(summary = "create  ➕", description = "this api used for order creation")
    @PreAuthorize("hasAnyRole('STAFF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@PathVariable Boolean useCashback,
                                                 @RequestBody CheckDTO dto) {
        ApiResponse<?> response = checkService.create(useCashback,dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update  🛠️", description = "this api used for order  update")
    @PreAuthorize("hasAnyRole('STAFF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable String id,
                                                 @Valid @RequestBody CheckDTO dto) {
        ApiResponse<?> response = checkService.update(id, dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete  ❌", description = "this api used for order  delete")
    @PreAuthorize("hasAnyRole('STAFF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String id) {
        ApiResponse<?> response = checkService.delete(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getById/{id}")
    @Operation(summary = "getById  🤑", description = "this api used for order  getById")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable String id) {
        ApiResponse<?> response = checkService.getById(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getList")
    @Operation(summary = "getList  📄🤑", description = "this api used for order  getList")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(checkService.getList());
    }


    @GetMapping("/open/paging")
    @Operation(summary = "paging  📖🤑", description = "this api used for order  paging")
    public ResponseEntity<Page<?>> paging(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(checkService.paging(page, size));
    }
}
