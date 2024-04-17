package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.RequisitanteDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.AutoCompleteDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowRequisitanteDTO;
import com.example.meualmoxarifadobackend.controller.filter.RequisitanteSearchFilter;
import com.example.meualmoxarifadobackend.service.RequisitanteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/requisitantes")
@CrossOrigin
public record RequisitanteController(RequisitanteService requisitanteService) {

    @PostMapping("create")
    public ResponseEntity<ShowRequisitanteDTO> create(@RequestBody @Valid RequisitanteDTO requisitanteDTO) {
        var requisitante = requisitanteService.create(requisitanteDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(requisitante.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowRequisitanteDTO(requisitante));
    }

    @GetMapping
    public ResponseEntity<Page<ShowRequisitanteDTO>> getAll(Pageable pageable) {
        var requisitantes = requisitanteService.findAll(pageable);
        var requisitantesDTO = requisitantes.map(ShowRequisitanteDTO::new);
        return ResponseEntity.ok(requisitantesDTO);
    }

    @GetMapping("autocomplete")
    public ResponseEntity<Page<AutoCompleteDTO>> searchTerm(RequisitanteSearchFilter filters, Pageable pageable) {
        var requisitantes = requisitanteService.dynamicFindAll(filters.toSpec(), pageable);
        var requisitantesDTO = requisitantes.map(AutoCompleteDTO::new);
        return ResponseEntity.ok(requisitantesDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowRequisitanteDTO> getById(@PathVariable Long id){
        var requisitante = requisitanteService.findById(id);
        return ResponseEntity.ok(new ShowRequisitanteDTO(requisitante));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowRequisitanteDTO> updateById(@PathVariable Long id, @RequestBody @Valid RequisitanteDTO requisitanteDTO) {
        var requisitante = requisitanteService.update(id, requisitanteDTO.toModel());
        return ResponseEntity.ok(new ShowRequisitanteDTO(requisitante));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        requisitanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
