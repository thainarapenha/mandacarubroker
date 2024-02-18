package com.mandacarubroker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests {

    // Injeção de dependências necessárias para os testes
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Método executado antes de cada teste para inicializar o repositório com dados de exemplo
     */
    @BeforeEach
    public void initRepository() {
        stockRepository.save(new Stock(new RequestStockDTO("MGLU3", "Magazine Luiza", 2.04)));
        stockRepository.save(new Stock(new RequestStockDTO("ABEV3", "Ambev", 12.76)));
        stockRepository.save(new Stock(new RequestStockDTO("ITSA4", "Itaúsa", 10.66)));
    }

    /**
     * Limpa repositório ao final de cada teste
     */
    @AfterEach
    public void clearRepository() {
        stockRepository.deleteAll();
    }

    /**
     * Testa se a requisição para obter todas as ações retorna um status HTTP OK (200)
     */
    @Test
    void checkStatusGetAllStocks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Testa se a requisição para obter uma ação por ID inexistente retorna um status HTTP Not Found (404)
     */
    @Test
    void checksIfGetIdStocksExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stocks/{id}", "id não existe"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Testa se a criação de uma nova ação retorna um status HTTP OK (200) e verifica os detalhes da ação criada
     */
    @Test
    void checkIfCreatedNewStock() throws Exception {
        RequestStockDTO newStock = new RequestStockDTO("MGLU3", "Magazine Luiza", 2.04);

        String requestJson = objectMapper.writeValueAsString(newStock);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/stocks")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(jsonPath("$.symbol").value(newStock.symbol()))
                .andExpect(jsonPath("$.companyName").value(newStock.companyName()))
                .andExpect(jsonPath("$.price").value(newStock.price()));

    }

    /**
     * Testa se a atualização de uma ação retorna um status HTTP OK (200) e verifica os detalhes da ação atualizada
     */
    @Test
    void checkUpdateStockById() throws Exception {
        Stock targetUpdatingStock = stockRepository.findAll().get(0);

        targetUpdatingStock.setSymbol("MGLU5");

        String requestJson = objectMapper.writeValueAsString(targetUpdatingStock);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/stocks/{id}", targetUpdatingStock.getId())
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(jsonPath("$.symbol").value(targetUpdatingStock.getSymbol()))
                .andExpect(jsonPath("$.companyName").value(targetUpdatingStock.getCompanyName()))
                .andExpect(jsonPath("$.price").value(targetUpdatingStock.getPrice()));

    }

    /**
     * Testa se a exclusão de uma ação por ID retorna um status HTTP OK (200)
     */
    @Test
    void checkDeleteStockById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/stocks/{id}", "6c0c7a08-8313-45db-97ef-236d29bf940b"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Testa se a exclusão de uma ação com ID inexistente retorna um status HTTP No Content (204)
     */
    @Test
    void checksIfDeleteWithNotExistentId() throws Exception  {
        String nonexistentId = "1a2b3c4d5e7f8g9h";

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/stocks/{id}", nonexistentId);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    /**
     * Testa se a tentativa de excluir todas as ações retorna um status HTTP Method Not Allowed (405)
     */
    @Test
    void canNotDeleteAllStocks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/stocks"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}