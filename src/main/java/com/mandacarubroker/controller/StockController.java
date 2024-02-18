package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.service.StockService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    /**
     * Construtor que injeta o serviço de ações
     * @param stockService Serviço de ações a ser injetado.
     */
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Retorna a lista de todas as ações.
     * @return Lista de objetos Stock.
     */
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    /**
     * Retorna uma ação pelo ID.
     * @return ResponseEntity contendo o objeto Stock se encontrado e o código de status respectivamente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable String id) {
        Stock stock = stockService.getStockById(id).orElse(null);
        return stock != null ? ResponseEntity.ok(stock) : ResponseEntity.notFound().build();
    }

    /**
     * Cria uma nova ação com base nos dados fornecidos.
     * @return ResponseEntity contendo a ação criada e código de status HTTP apropriado.
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody RequestStockDTO data) {
        Stock createdStock = stockService.createStock(data);
        return ResponseEntity.ok(createdStock);
    }

    /**
     * Atualiza uma ação existente com base no ID.
     * @return ResponseEntity contendo o objeto Stock atualizado e o código de status respectivamente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable String id, @RequestBody Stock updatedStock) {
        try {
            Stock result = stockService.updateStock(id, updatedStock);

            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Exclui uma ação com base no ID.
     * @param id ID da ação a ser excluída.
     */@DeleteMapping("/{id}")
    public void deleteStock(@PathVariable String id) {
        stockService.deleteStock(id);
    }

}
