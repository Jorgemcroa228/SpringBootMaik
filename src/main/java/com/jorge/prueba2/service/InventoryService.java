package com.jorge.prueba2.service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jorge.prueba2.constants.MessageConstants;
import com.jorge.prueba2.dto.BuyRequestDTO;
import com.jorge.prueba2.dto.InventoryRequestDTO;
import com.jorge.prueba2.dto.InventoryResponseDTO;
import com.jorge.prueba2.dto.MessageResponseDTO;
import com.jorge.prueba2.repository.InventoryRepository;

@Service
public class InventoryService {
    
    private JdbcTemplate jdbcTemplate;

    public InventoryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<InventoryResponseDTO> itemMapper = (rs, rowNum) -> {
        InventoryResponseDTO inventoryResponseDTO = new InventoryResponseDTO();
        inventoryResponseDTO.setId(rs.getInt("id"));
        inventoryResponseDTO.setName(rs.getString("name"));
        inventoryResponseDTO.setCantidad(rs.getInt("cantidad"));
        inventoryResponseDTO.setMarca(rs.getString("marca"));
        inventoryResponseDTO.setModelo(rs.getString("modelo"));
        return inventoryResponseDTO;
    };

    public InventoryResponseDTO createItem(InventoryRequestDTO inventoryRequestDTO) {
    
        if (inventoryRequestDTO.getName() == null || inventoryRequestDTO.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_NAME_REQUIRED);
        }
        
        if (inventoryRequestDTO.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_CANTIDAD_INVALID);
        }
        
        if (inventoryRequestDTO.getMarca() == null || inventoryRequestDTO.getMarca().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_MARCA_REQUIRED);
        }
        
        if (inventoryRequestDTO.getModelo() == null || inventoryRequestDTO.getModelo().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_MODELO_REQUIRED);
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                InventoryRepository.INSERT_ITEM, 
                Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, inventoryRequestDTO.getName());
            preparedStatement.setInt(2, inventoryRequestDTO.getCantidad());
            preparedStatement.setString(3, inventoryRequestDTO.getMarca());
            preparedStatement.setString(4, inventoryRequestDTO.getModelo());

            return preparedStatement;
        }, keyHolder);

        InventoryResponseDTO response = new InventoryResponseDTO();
        response.setId(keyHolder.getKey().intValue());
        response.setName(inventoryRequestDTO.getName());
        response.setCantidad(inventoryRequestDTO.getCantidad());
        response.setMarca(inventoryRequestDTO.getMarca());
        response.setModelo(inventoryRequestDTO.getModelo());

        return response;
    }

    public List<InventoryResponseDTO> getAllItems() {
        return jdbcTemplate.query(InventoryRepository.GET_ALL_ITEMS, itemMapper);
    }

    public InventoryResponseDTO getItemId(int id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_ID_CERO_O_NEGATIVE);
        }
        
        try {
            return jdbcTemplate.queryForObject(InventoryRepository.GET_ITEM_ID, itemMapper, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(MessageConstants.MESSAGE_ITEM_NOT_FOUND, id));
        }
    }

    public MessageResponseDTO deleteItem(int id) {
        
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_ID_CERO_O_NEGATIVE);
        }
        
        try {
            jdbcTemplate.queryForObject(InventoryRepository.GET_ITEM_ID, itemMapper, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(MessageConstants.MESSAGE_ITEM_NOT_FOUND, id));
        }
        
        jdbcTemplate.update(InventoryRepository.DELETE_ITEM, id);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage(MessageConstants.MESSAGE_ITEM_DELETE_ID);

        return response;
    }

    public InventoryResponseDTO updateItem(int id, InventoryRequestDTO inventoryRequestDTO) {

        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_ID_CERO_O_NEGATIVE);
        }
        
        if (inventoryRequestDTO.getName() == null || inventoryRequestDTO.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_NAME_REQUIRED);
        }
        
        if (inventoryRequestDTO.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_CANTIDAD_INVALID);
        }
        
        if (inventoryRequestDTO.getMarca() == null || inventoryRequestDTO.getMarca().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_MARCA_REQUIRED);
        }
        
        if (inventoryRequestDTO.getModelo() == null || inventoryRequestDTO.getModelo().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_MODELO_REQUIRED);
        }
        
        try {
            jdbcTemplate.queryForObject(InventoryRepository.GET_ITEM_ID, itemMapper, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(MessageConstants.MESSAGE_ITEM_NOT_FOUND, id));
        }
        
        jdbcTemplate.update(InventoryRepository.UPDATE_ITEM,
            inventoryRequestDTO.getName(),
            inventoryRequestDTO.getCantidad(),
            inventoryRequestDTO.getMarca(),
            inventoryRequestDTO.getModelo(),
            id
        );

        InventoryResponseDTO response = new InventoryResponseDTO();
        response.setId(id);
        response.setName(inventoryRequestDTO.getName());
        response.setCantidad(inventoryRequestDTO.getCantidad());
        response.setMarca(inventoryRequestDTO.getMarca());
        response.setModelo(inventoryRequestDTO.getModelo());

        return response;
    }

    public MessageResponseDTO buyItem(int id, BuyRequestDTO buyRequestDTO) {
        
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_ID_CERO_O_NEGATIVE);
        }
        
       
        if (buyRequestDTO.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstants.MESSAGE_CANTIDAD_INVALID);
        }
        
        
        InventoryResponseDTO item;
        try {
            item = jdbcTemplate.queryForObject(InventoryRepository.GET_ITEM_ID, itemMapper, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(MessageConstants.MESSAGE_ITEM_NOT_FOUND, id));
        }
        
        
        if (item.getCantidad() < buyRequestDTO.getCantidad()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                String.format(MessageConstants.MESSAGE_BUY_INSUFFICIENT_STOCK, item.getCantidad(), buyRequestDTO.getCantidad()));
        }
        
        
        jdbcTemplate.update(InventoryRepository.BUY_ITEM, buyRequestDTO.getCantidad(), id);
        
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage(String.format(MessageConstants.MESSAGE_BUY_SUCCESS, buyRequestDTO.getCantidad()));
        
        return response;
    }
}
