package com.example.meualmoxarifadobackend.controller;

import com.example.meualmoxarifadobackend.controller.dto.request.LocalDeAplicacaoDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.AutoCompleteDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowLocalDeAplicacaoDTO;
import com.example.meualmoxarifadobackend.controller.filter.LocalDeAplicacaoSearchFilter;
import com.example.meualmoxarifadobackend.service.LocalDeAplicacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("api/locais")
@CrossOrigin
public record LocalDeAplicacaoController(LocalDeAplicacaoService localDeAplicacaoService) {

    @PostMapping("create")
    public ResponseEntity<ShowLocalDeAplicacaoDTO> create(@RequestBody @Valid LocalDeAplicacaoDTO localDeAplicacaoDTO) {
        var localDeAplicacao = localDeAplicacaoService.create(localDeAplicacaoDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(localDeAplicacao.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowLocalDeAplicacaoDTO(localDeAplicacao));
    }

    @GetMapping
    public ResponseEntity<Page<ShowLocalDeAplicacaoDTO>> getAll(Pageable pageable) {
        var locaisDeAplicacao = localDeAplicacaoService.findAll(pageable);
        var locaisDeAplicacaoDTO = locaisDeAplicacao.map(ShowLocalDeAplicacaoDTO::new);
        return ResponseEntity.ok(locaisDeAplicacaoDTO);
    }

    @GetMapping("autocomplete")
    public ResponseEntity<Page<AutoCompleteDTO>> searchTerm(LocalDeAplicacaoSearchFilter filters, Pageable pageable) {
        var locaisDeAplicacao = localDeAplicacaoService.dynamicFindAll(filters.toSpec(), pageable);
        var locaisDeAplicacaoDTO = locaisDeAplicacao.map(AutoCompleteDTO::new);
        return ResponseEntity.ok(locaisDeAplicacaoDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowLocalDeAplicacaoDTO> getById(@PathVariable Long id){
        var localDeAplicacao = localDeAplicacaoService.findById(id);
        return ResponseEntity.ok(new ShowLocalDeAplicacaoDTO(localDeAplicacao));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowLocalDeAplicacaoDTO> updateById(@PathVariable Long id, @RequestBody @Valid LocalDeAplicacaoDTO localDeAplicacaoDTO) {
        var localDeAplicacao = localDeAplicacaoService.update(id, localDeAplicacaoDTO.toModel());
        return ResponseEntity.ok(new ShowLocalDeAplicacaoDTO(localDeAplicacao));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        localDeAplicacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
