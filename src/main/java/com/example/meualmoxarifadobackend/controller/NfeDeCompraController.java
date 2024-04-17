package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.NfeDeCompraDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowNfeDeCompraDTO;
import com.example.meualmoxarifadobackend.service.NfeDeCompraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/nfe/compra")
@CrossOrigin
public record NfeDeCompraController(NfeDeCompraService nfeDeCompraService) {


    @PostMapping("/create")
    public ResponseEntity<ShowNfeDeCompraDTO> create(@RequestBody @Valid NfeDeCompraDTO nfeDeCompraDTO) {
        var nfeDeCompra = nfeDeCompraService.create(nfeDeCompraDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nfeDeCompra.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowNfeDeCompraDTO(nfeDeCompra));
    }

    @GetMapping
    public ResponseEntity<Page<ShowNfeDeCompraDTO>> getAll(Pageable pageable) {
        var nfesDeCompra = nfeDeCompraService.findAll(pageable);
        var nfesDeCompraDTO = nfesDeCompra.map(ShowNfeDeCompraDTO::new);
        return ResponseEntity.ok(nfesDeCompraDTO);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<ShowNfeDeCompraDTO> getById(@PathVariable Long id) {
        var nfeDeCompra = nfeDeCompraService.findById(id);
        return ResponseEntity.ok(new ShowNfeDeCompraDTO(nfeDeCompra));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ShowNfeDeCompraDTO> updateById(@PathVariable Long id, @RequestBody @Valid NfeDeCompraDTO nfeDeCompraDTO) {
        var nfeDeCompra = nfeDeCompraService.update(id, nfeDeCompraDTO.toModel());
        return ResponseEntity.ok(new ShowNfeDeCompraDTO(nfeDeCompra));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        nfeDeCompraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
