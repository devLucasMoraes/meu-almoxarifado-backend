package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.AcertoEstoqueDTO;
import com.example.meualmoxarifadobackend.controller.dto.request.MaterialDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.AutoCompleteDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowEstoqueDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowMaterialDTO;
import com.example.meualmoxarifadobackend.controller.filter.MaterialSearchFilter;
import com.example.meualmoxarifadobackend.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/materiais")
@CrossOrigin
public record MaterialController(MaterialService materialService) {

    @PostMapping("create")
    public ResponseEntity<ShowMaterialDTO> create(@RequestBody @Valid MaterialDTO materialDTO) {
        var material = materialService.create(materialDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(material.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowMaterialDTO(material));
    }

    @GetMapping
    public ResponseEntity<Page<ShowMaterialDTO>> getAll(Pageable pageable) {
        var materiais = materialService.findAll(pageable);
        var materiaisDTO = materiais.map(ShowMaterialDTO::new);
        return ResponseEntity.ok(materiaisDTO);
    }

    @GetMapping("estoque")
    public ResponseEntity<Page<ShowEstoqueDTO>> getEstoque(Pageable pageable) {
        var materiais = materialService.findAll(pageable);
        var materiaisDTO = materiais.map(ShowEstoqueDTO::new);
        return ResponseEntity.ok(materiaisDTO);
    }

    @PatchMapping("estoque/acerto")
    public  ResponseEntity<Void> acertoEstoque(@RequestBody @Valid AcertoEstoqueDTO acertoEstoqueDTO) {
        materialService.acertarEstoque(acertoEstoqueDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("query")
    public ResponseEntity<Page<ShowMaterialDTO>> dynamicGetAll(MaterialSearchFilter filters, Pageable pageable) {
        var materiais = materialService.dynamicFindAll(filters.toSpec(), pageable);
        var materiaisDTO = materiais.map(ShowMaterialDTO::new);
        return ResponseEntity.ok(materiaisDTO);
    }

    @GetMapping("autocomplete")
    public ResponseEntity<Page<AutoCompleteDTO>> searchTerm(MaterialSearchFilter filters, Pageable pageable) {
        var materiais = materialService.dynamicFindAll(filters.toSpec(), pageable);
        var materiaisDTO = materiais.map(AutoCompleteDTO::new);
        return ResponseEntity.ok(materiaisDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowMaterialDTO> getById(@PathVariable Long id) {
        var material = materialService.findById(id);
        return ResponseEntity.ok(new ShowMaterialDTO(material));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowMaterialDTO> updateById(@PathVariable Long id, @RequestBody @Valid MaterialDTO materialDTO) {
        var material = materialService.update(id, materialDTO.toModel());
        return ResponseEntity.ok(new ShowMaterialDTO(material));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
