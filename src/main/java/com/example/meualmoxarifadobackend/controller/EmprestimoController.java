package com.example.meualmoxarifadobackend.controller;


import com.example.meualmoxarifadobackend.controller.dto.request.EmprestimoAPagarDTO;
import com.example.meualmoxarifadobackend.controller.dto.response.ShowEmprestimoAPagarDTO;
import com.example.meualmoxarifadobackend.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/emprestimos")
@CrossOrigin
public record EmprestimoController(EmprestimoService emprestimoService) {


    @PostMapping("a-pagar/create")
    public ResponseEntity<ShowEmprestimoAPagarDTO> create(@RequestBody @Valid EmprestimoAPagarDTO emprestimoAPagarDTO) {
        var emprestimoTroca = emprestimoService.create(emprestimoAPagarDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprestimoTroca.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowEmprestimoAPagarDTO(emprestimoTroca));
    }

    @GetMapping("a-pagar")
    public ResponseEntity<Page<ShowEmprestimoAPagarDTO>> getAll(Pageable pageable) {
        var emprestimoTroca = emprestimoService.findAll(pageable);
        var emprestimoETrocaDTO = emprestimoTroca.map(ShowEmprestimoAPagarDTO::new);
        return ResponseEntity.ok(emprestimoETrocaDTO);
    }

    @GetMapping("a-pagar/show/{id}")
    public ResponseEntity<ShowEmprestimoAPagarDTO> getById(@PathVariable Long id) {
        var emprestimoTroca = emprestimoService.findById(id);
        return ResponseEntity.ok(new ShowEmprestimoAPagarDTO(emprestimoTroca));
    }

    @PutMapping("a-pagar/edit/{id}")
    public ResponseEntity<ShowEmprestimoAPagarDTO> updateById(@PathVariable Long id, @RequestBody @Valid EmprestimoAPagarDTO emprestimoAPagarDTO) {
        var emprestimoTroca = emprestimoService.update(id, emprestimoAPagarDTO.toModel());
        return ResponseEntity.ok(new ShowEmprestimoAPagarDTO(emprestimoTroca));
    }

    @DeleteMapping("a-pagar/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        emprestimoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
