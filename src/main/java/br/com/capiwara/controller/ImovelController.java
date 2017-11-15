package br.com.capiwara.controller;

import br.com.capiwara.entity.Imovel;
import br.com.capiwara.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @RequestMapping(value = "/imovel/{id}")
    public ResponseEntity addImovel(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(imovelService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            LinkedHashMap<String, String> response = new LinkedHashMap<>();
            response.put("mensagem", e.getMessage());
            response.put("status", "NOT_FOUND");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/imovel/combobox/{id}")
    public ResponseEntity fillCombobox(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(imovelService.calcularParcelas(id), HttpStatus.OK);
        } catch (Exception e) {
            LinkedHashMap<String, String> response = new LinkedHashMap<>();
            response.put("mensagem", e.getMessage());
            response.put("status", "NOT_FOUND");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
