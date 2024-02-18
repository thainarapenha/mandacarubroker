package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;

import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(String id) {
        return stockRepository.findById(id);
    }

    public Stock createStock(RequestStockDTO data) {
        Stock novaAcao = new Stock(data);
        validateRequestStockDTO(data);
        return stockRepository.save(novaAcao);
    }

    public Stock updateStock(String id, Stock updatedStock) {
        Optional<Stock> optionalStock = stockRepository.findById(id);

        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            stock.setSymbol(updatedStock.getSymbol());
            stock.setCompanyName(updatedStock.getCompanyName());
            stock.setPrice(updatedStock.getPrice());

            return stockRepository.save(stock);
        } else {
            return null; // Ou você pode lançar uma exceção indicando que a ação não foi encontrada.
        }
    }

    public void deleteStock(String id) {
        stockRepository.deleteById(id);
    }

    public static void validateRequestStockDTO(RequestStockDTO data) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

            for (ConstraintViolation<RequestStockDTO> violation : violations) {
                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
            }

            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
    }

    public void validateAndCreateStock(RequestStockDTO data) {
        validateRequestStockDTO(data);

        Stock novaAcao = new Stock(data);
        stockRepository.save(novaAcao);
    }
}
