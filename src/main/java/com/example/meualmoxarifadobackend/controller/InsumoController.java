package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.AcertoEstoqueDTO;
import com.example.meualmoxarifadobackend.controller.dto.request.InsumoDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.AutoCompleteDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowInsumoDTO;
import com.example.meualmoxarifadobackend.controller.filter.InsumoSearchFilter;
import com.example.meualmoxarifadobackend.service.InsumoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/insumos")
@CrossOrigin
public record InsumoController(InsumoService insumoService) {

    @PostMapping("create")
    public ResponseEntity<ShowInsumoDTO> create(@RequestBody @Valid InsumoDTO insumoDTO) {
        var material = insumoService.create(insumoDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(material.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowInsumoDTO(material));
    }

    @GetMapping
    public ResponseEntity<Page<ShowInsumoDTO>> getAll(Pageable pageable) {
        var materiais = insumoService.findAll(pageable);
        var materiaisDTO = materiais.map(ShowInsumoDTO::new);
        return ResponseEntity.ok(materiaisDTO);
    }

//    @GetMapping("estoque")
//    public ResponseEntity<Page<ShowEstoqueDTO>> getEstoque(Pageable pageable) {
//        var materiais = insumoService.findAll(pageable);
//        var materiaisDTO = materiais.map(ShowEstoqueDTO::new);
//        return ResponseEntity.ok(materiaisDTO);
//    }

    @PatchMapping("estoque/acerto")
    public  ResponseEntity<Void> acertoEstoque(@RequestBody @Valid AcertoEstoqueDTO acertoEstoqueDTO) {
        insumoService.acertarEstoque(acertoEstoqueDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("query")
    public ResponseEntity<Page<ShowInsumoDTO>> dynamicGetAll(InsumoSearchFilter filters, Pageable pageable) {
        var materiais = insumoService.dynamicFindAll(filters.toSpec(), pageable);
        var materiaisDTO = materiais.map(ShowInsumoDTO::new);
        return ResponseEntity.ok(materiaisDTO);
    }

    @GetMapping("autocomplete")
    public ResponseEntity<Page<AutoCompleteDTO>> searchTerm(InsumoSearchFilter filters, Pageable pageable) {
        var materiais = insumoService.dynamicFindAll(filters.toSpec(), pageable);
        var materiaisDTO = materiais.map(AutoCompleteDTO::new);
        return ResponseEntity.ok(materiaisDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowInsumoDTO> getById(@PathVariable Long id) {
        var material = insumoService.findById(id);
        return ResponseEntity.ok(new ShowInsumoDTO(material));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowInsumoDTO> updateById(@PathVariable Long id, @RequestBody @Valid InsumoDTO insumoDTO) {
        var material = insumoService.update(id, insumoDTO.toModel());
        return ResponseEntity.ok(new ShowInsumoDTO(material));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        insumoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
