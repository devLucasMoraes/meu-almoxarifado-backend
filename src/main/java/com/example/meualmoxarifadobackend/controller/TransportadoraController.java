package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.TransportadoraDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.AutoCompleteDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowTransportadoraDTO;
import com.example.meualmoxarifadobackend.controller.filter.TransportadoraSearchFilter;
import com.example.meualmoxarifadobackend.service.TransportadoraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/transportadoras")
@CrossOrigin
public record TransportadoraController(TransportadoraService transportadoraService) {

    @PostMapping("create")
    public ResponseEntity<ShowTransportadoraDTO> create(@RequestBody @Valid TransportadoraDTO transportadoraDTO) {
        var transportadora = transportadoraService.create(transportadoraDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transportadora.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowTransportadoraDTO(transportadora));
    }

    @GetMapping
    public ResponseEntity<Page<ShowTransportadoraDTO>> getAll(Pageable pageable) {
        var transportadoras = transportadoraService.findAll(pageable);
        var transportadorasDTO = transportadoras.map(ShowTransportadoraDTO::new);
        return ResponseEntity.ok(transportadorasDTO);
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<Page<AutoCompleteDTO>> searchTerm(TransportadoraSearchFilter filter, Pageable pageable) {
        var transportadoras = transportadoraService.dynamicFindAll(filter.toSpec(), pageable);
        var transportadorasDTO = transportadoras.map(AutoCompleteDTO::new);
        return ResponseEntity.ok(transportadorasDTO);
    }

    @GetMapping("/query")
    public ResponseEntity<Page<ShowTransportadoraDTO>> dynamicGetAll(TransportadoraSearchFilter filter, Pageable pageable) {
        var transportadoras = transportadoraService.dynamicFindAll(filter.toSpec(), pageable);
        var transportadorasDTO = transportadoras.map(ShowTransportadoraDTO::new);
        return ResponseEntity.ok(transportadorasDTO);
    }


    @GetMapping("show/{id}")
    public ResponseEntity<ShowTransportadoraDTO> getById(@PathVariable Long id) {
        var transportadora = transportadoraService.findById(id);
        return ResponseEntity.ok(new ShowTransportadoraDTO(transportadora));
    }

    @GetMapping("show/cnpj/{cnpj}")
    public ResponseEntity<ShowTransportadoraDTO> getByCnpj(@PathVariable String cnpj) {
        var transportadora = transportadoraService.getByCnpj(cnpj);
        return ResponseEntity.ok(new ShowTransportadoraDTO(transportadora));
    }


    @PutMapping("edit/{id}")
    public ResponseEntity<ShowTransportadoraDTO> updateById(@PathVariable Long id, @RequestBody @Valid TransportadoraDTO transportadoraDTO) {
        var transportadora = transportadoraService.update(id, transportadoraDTO.toModel());
        return ResponseEntity.ok(new ShowTransportadoraDTO(transportadora));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transportadoraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
