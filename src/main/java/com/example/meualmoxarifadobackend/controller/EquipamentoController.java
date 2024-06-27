package com.example.meualmoxarifadobackend.controller;

import com.example.meualmoxarifadobackend.controller.dto.request.EquipamentoDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.AutoCompleteDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowEquipamentoDTO;
import com.example.meualmoxarifadobackend.controller.filter.EquipamentoSearchFilter;
import com.example.meualmoxarifadobackend.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("api/equipamentos")
@CrossOrigin
public record EquipamentoController(EquipamentoService equipamentoService) {

    @PostMapping("create")
    public ResponseEntity<ShowEquipamentoDTO> create(@RequestBody @Valid EquipamentoDTO localDeAplicacaoDTO) {
        var localDeAplicacao = equipamentoService.create(localDeAplicacaoDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(localDeAplicacao.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowEquipamentoDTO(localDeAplicacao));
    }

    @GetMapping
    public ResponseEntity<Page<ShowEquipamentoDTO>> getAll(Pageable pageable) {
        var locaisDeAplicacao = equipamentoService.findAll(pageable);
        var locaisDeAplicacaoDTO = locaisDeAplicacao.map(ShowEquipamentoDTO::new);
        return ResponseEntity.ok(locaisDeAplicacaoDTO);
    }

    @GetMapping("autocomplete")
    public ResponseEntity<Page<AutoCompleteDTO>> searchTerm(EquipamentoSearchFilter filters, Pageable pageable) {
        var locaisDeAplicacao = equipamentoService.dynamicFindAll(filters.toSpec(), pageable);
        var locaisDeAplicacaoDTO = locaisDeAplicacao.map(AutoCompleteDTO::new);
        return ResponseEntity.ok(locaisDeAplicacaoDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowEquipamentoDTO> getById(@PathVariable Long id){
        var localDeAplicacao = equipamentoService.findById(id);
        return ResponseEntity.ok(new ShowEquipamentoDTO(localDeAplicacao));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowEquipamentoDTO> updateById(@PathVariable Long id, @RequestBody @Valid EquipamentoDTO localDeAplicacaoDTO) {
        var localDeAplicacao = equipamentoService.update(id, localDeAplicacaoDTO.toModel());
        return ResponseEntity.ok(new ShowEquipamentoDTO(localDeAplicacao));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        equipamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
