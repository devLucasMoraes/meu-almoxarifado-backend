package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.RequisicaoEstoqueDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowRequisicaoEstoqueDTO;
import com.example.meualmoxarifadobackend.service.RequisicaoEstoqueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/requisicoes-estoque")
@CrossOrigin
public record RequisicaoEstoqueController(RequisicaoEstoqueService requisicaoEstoqueService) {


    @PostMapping("create")
    public ResponseEntity<ShowRequisicaoEstoqueDTO> create(@RequestBody @Valid RequisicaoEstoqueDTO requisicaoEstoqueDTO) {
        var requisicaoDeEstoque = requisicaoEstoqueService.create(requisicaoEstoqueDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(requisicaoDeEstoque.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowRequisicaoEstoqueDTO(requisicaoDeEstoque));
    }

    @GetMapping
    public ResponseEntity<Page<ShowRequisicaoEstoqueDTO>> getAll(Pageable pageable) {
        var requisicoesDeEstoque = requisicaoEstoqueService.findAll(pageable);
        var requisicoesDeEstoqueDTO = requisicoesDeEstoque.map(ShowRequisicaoEstoqueDTO::new);
        return ResponseEntity.ok(requisicoesDeEstoqueDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowRequisicaoEstoqueDTO> getById(@PathVariable Long id) {
        var requisicaoDeEstoque = requisicaoEstoqueService.findById(id);
        return ResponseEntity.ok(new ShowRequisicaoEstoqueDTO(requisicaoDeEstoque));
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ShowRequisicaoEstoqueDTO> updateById(@PathVariable Long id, @RequestBody @Valid RequisicaoEstoqueDTO requisicaoEstoqueDTO) {
        var requisicaoDeEstoque = requisicaoEstoqueService.update(id, requisicaoEstoqueDTO.toModel());
        return ResponseEntity.ok(new ShowRequisicaoEstoqueDTO(requisicaoDeEstoque));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        requisicaoEstoqueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
