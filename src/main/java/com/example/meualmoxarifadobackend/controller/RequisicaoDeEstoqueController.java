package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.RequisicaoDeEstoqueDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowRequisicaoDeEstoqueDTO;
import com.example.meualmoxarifadobackend.service.RequisicaoDeEstoqueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/requisicoes")
@CrossOrigin
public record RequisicaoDeEstoqueController(RequisicaoDeEstoqueService requisicaoDeEstoqueService) {


    @PostMapping("create")
    public ResponseEntity<ShowRequisicaoDeEstoqueDTO> create(@RequestBody @Valid RequisicaoDeEstoqueDTO requisicaoDeEstoqueDTO) {
        var requisicaoDeEstoque = requisicaoDeEstoqueService.create(requisicaoDeEstoqueDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(requisicaoDeEstoque.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowRequisicaoDeEstoqueDTO(requisicaoDeEstoque));
    }

    @GetMapping
    public ResponseEntity<Page<ShowRequisicaoDeEstoqueDTO>> getAll(Pageable pageable) {
        var requisicoesDeEstoque = requisicaoDeEstoqueService.findAll(pageable);
        var requisicoesDeEstoqueDTO = requisicoesDeEstoque.map(ShowRequisicaoDeEstoqueDTO::new);
        return ResponseEntity.ok(requisicoesDeEstoqueDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowRequisicaoDeEstoqueDTO> getById(@PathVariable Long id) {
        var requisicaoDeEstoque = requisicaoDeEstoqueService.findById(id);
        return ResponseEntity.ok(new ShowRequisicaoDeEstoqueDTO(requisicaoDeEstoque));
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ShowRequisicaoDeEstoqueDTO> updateById(@PathVariable Long id, @RequestBody @Valid RequisicaoDeEstoqueDTO requisicaoDeEstoqueDTO) {
        var requisicaoDeEstoque = requisicaoDeEstoqueService.update(id, requisicaoDeEstoqueDTO.toModel());
        return ResponseEntity.ok(new ShowRequisicaoDeEstoqueDTO(requisicaoDeEstoque));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        requisicaoDeEstoqueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
