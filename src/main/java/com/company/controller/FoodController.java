package com.company.controller;

import com.company.dto.ApiResponse;
import com.company.dto.FoodDTO;
import com.company.service.FoodService;
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
@RequestMapping("/api/v1/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping("/create")
    @Operation(summary = "create  ➕", description = "this api used for food creation")
    @PreAuthorize("hasAnyRole('CHEF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody FoodDTO dto) {
        ApiResponse<?> response = foodService.create(dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update  🛠️", description = "this api used for food  update")
    @PreAuthorize("hasAnyRole('CHEF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Integer id,
                                                 @Valid @RequestBody FoodDTO dto) {
        ApiResponse<?> response = foodService.update(id, dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete  ❌", description = "this api used for food  delete")
    @PreAuthorize("hasAnyRole('CHEF', 'WAITER', 'ADMINISTRATOR', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        ApiResponse<?> response = foodService.delete(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getById/{id}")
    @Operation(summary = "getById  🍖", description = "this api used for food  getById")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Integer id) {
        ApiResponse<?> response = foodService.getById(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/open/getList")
    @Operation(summary = "getList  📄🍖", description = "this api used for food  getList")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(foodService.getList());
    }


    @GetMapping("/open/paging")
    @Operation(summary = "paging  📖🍖", description = "this api used for food  paging")
    public ResponseEntity<Page<?>> paging(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(foodService.paging(page, size));
    }

    @GetMapping("/open/paging/by-categoryId/{categoryId}")
    @Operation(summary = "paging By Region closet 🪧📂", description = "this api used for closet paging By Region")
    public ResponseEntity<Page<?>> pagingByCategoryId(@PathVariable Integer categoryId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(foodService.pagingByCategoryId(categoryId, page, size));
    }
}
