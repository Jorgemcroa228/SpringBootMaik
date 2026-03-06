package com.jorge.prueba2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jorge.prueba2.dto.BuyRequestDTO;
import com.jorge.prueba2.dto.InventoryRequestDTO;
import com.jorge.prueba2.dto.InventoryResponseDTO;
import com.jorge.prueba2.dto.MessageResponseDTO;
import com.jorge.prueba2.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
}

@PostMapping()
public ResponseEntity<InventoryResponseDTO> createItem(@RequestBody InventoryRequestDTO inventoryRequestDTO) {

    
    InventoryResponseDTO response = inventoryService.createItem(inventoryRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

@GetMapping()
public ResponseEntity<List<InventoryResponseDTO>> getAllItems() {
    List<InventoryResponseDTO> response = inventoryService.getAllItems();
    return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

@GetMapping("/{id}")
public ResponseEntity<InventoryResponseDTO> getItemId(@PathVariable int id) {
        InventoryResponseDTO response = inventoryService.getItemId(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

@DeleteMapping("/{id}")
public ResponseEntity<MessageResponseDTO> deleteItem(@PathVariable int id) {
        MessageResponseDTO response = inventoryService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

@PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> updateItem(@PathVariable int id, @RequestBody InventoryRequestDTO inventoryRequestDTO) {
        InventoryResponseDTO response = inventoryService.updateItem(id, inventoryRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

@PostMapping("/{id}/buy")
    public ResponseEntity<MessageResponseDTO> buyItem(@PathVariable int id, @RequestBody BuyRequestDTO buyRequestDTO) {
        MessageResponseDTO response = inventoryService.buyItem(id, buyRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }  
}


