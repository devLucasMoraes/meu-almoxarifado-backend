package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.CategoriaDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.AutoCompleteDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowCategoriaDTO;
import com.example.meualmoxarifadobackend.controller.filter.CategoriaSearchFilter;
import com.example.meualmoxarifadobackend.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/categorias")
@CrossOrigin
public record CategoriaController(CategoriaService categoriaService) {

    @PostMapping("create")
    public ResponseEntity<ShowCategoriaDTO> create(@RequestBody @Valid CategoriaDTO categoriaDTO) {
        var categoria = categoriaService.create(categoriaDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoria.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowCategoriaDTO(categoria));
    }

    @GetMapping
    public ResponseEntity<Page<ShowCategoriaDTO>> getAll(Pageable pageable) {
        var categorias = categoriaService.findAll(pageable);
        var categoriasDTO = categorias.map(ShowCategoriaDTO::new);
        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("autocomplete")
    public ResponseEntity<Page<AutoCompleteDTO>> searchTerm(CategoriaSearchFilter filters, Pageable pageable) {
        var categorias = categoriaService.dynamicFindAll(filters.toSpec(), pageable);
        var categoriasDTO = categorias.map(AutoCompleteDTO::new);
        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowCategoriaDTO> getById(@PathVariable Long id){
        var categoria = categoriaService.findById(id);
        return ResponseEntity.ok(new ShowCategoriaDTO(categoria));
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowCategoriaDTO> updateById(@PathVariable Long id, @RequestBody @Valid CategoriaDTO categoriaDTO) {
        var categoria = categoriaService.update(id, categoriaDTO.toModel());
        return ResponseEntity.ok(new ShowCategoriaDTO(categoria));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
