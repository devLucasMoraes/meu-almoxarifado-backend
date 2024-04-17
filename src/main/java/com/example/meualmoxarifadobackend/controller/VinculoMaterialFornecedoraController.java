package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.VinculoMaterialFornecedoraDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowVinculoMaterialFornecedoraDTO;
import com.example.meualmoxarifadobackend.controller.filter.VinculoMaterialFornecedoraSearchFilter;
import com.example.meualmoxarifadobackend.service.VinculoMaterialFornecedoraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/vinculos")
@CrossOrigin
public record VinculoMaterialFornecedoraController(VinculoMaterialFornecedoraService vinculoMaterialFornecedoraService) {

    @PostMapping("create")
    public ResponseEntity<ShowVinculoMaterialFornecedoraDTO> create(@RequestBody @Valid VinculoMaterialFornecedoraDTO vinculoMaterialFornecedoraDTO) {
        var vinculo = vinculoMaterialFornecedoraService.create(vinculoMaterialFornecedoraDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vinculo.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowVinculoMaterialFornecedoraDTO(vinculo));
    }

    @GetMapping
    public ResponseEntity<Page<ShowVinculoMaterialFornecedoraDTO>> getAll(Pageable pageable) {
        var vinculoPage = vinculoMaterialFornecedoraService.findAll(pageable);
        var dtoPage = vinculoPage.map(ShowVinculoMaterialFornecedoraDTO::new);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("query")
    public ResponseEntity<Page<ShowVinculoMaterialFornecedoraDTO>> dynamicGetAll(VinculoMaterialFornecedoraSearchFilter filters, Pageable pageable) {
        var vinculoPage = vinculoMaterialFornecedoraService.dynamicFindAll(filters.toSpec(), pageable);
        var dtoPage = vinculoPage.map(ShowVinculoMaterialFornecedoraDTO::new);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("show/query")
    public ResponseEntity<ShowVinculoMaterialFornecedoraDTO> dynamicGetOne(VinculoMaterialFornecedoraSearchFilter filters) {
        var vinculo = vinculoMaterialFornecedoraService.dynamicFindOne(filters.toSpec());
        return ResponseEntity.ok(new ShowVinculoMaterialFornecedoraDTO(vinculo));
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowVinculoMaterialFornecedoraDTO> getById(@PathVariable Long id) {
        var material = vinculoMaterialFornecedoraService.findById(id);
        return ResponseEntity.ok(new ShowVinculoMaterialFornecedoraDTO(material));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowVinculoMaterialFornecedoraDTO> updateById(@PathVariable Long id, @RequestBody @Valid VinculoMaterialFornecedoraDTO vinculoMaterialFornecedoraDTO) {
        var material = vinculoMaterialFornecedoraService.update(id, vinculoMaterialFornecedoraDTO.toModel());
        return ResponseEntity.ok(new ShowVinculoMaterialFornecedoraDTO(material));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        vinculoMaterialFornecedoraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
