package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.domain.model.Equipamento;
import com.example.meualmoxarifadobackend.domain.repository.EquipamentoRepository;
import com.example.meualmoxarifadobackend.service.EquipamentoService;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import com.example.meualmoxarifadobackend.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoServiceImpl(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    @Transactional(readOnly = true)
    public Page<Equipamento> findAll(Pageable pageable) {
        return this.equipamentoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Equipamento> dynamicFindAll(Specification<Equipamento> spec, Pageable pageable) {
        return this.equipamentoRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Equipamento findById(Long id) {
        return this.equipamentoRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipamento"));
    }

    @Transactional
    public Equipamento create(Equipamento equipamentoToCreate) {

        if(equipamentoRepository.existsByNome(equipamentoToCreate.getNome())){
            throw new BusinessException("O equipamento ja existe.");
        }

        equipamentoToCreate.setCreatedAt(LocalDateTime.now());
        equipamentoToCreate.setUpdatedAt(LocalDateTime.now());

        return this.equipamentoRepository.save(equipamentoToCreate);
    }

    @Transactional
    public Equipamento update(Long id, Equipamento equipamentoToUpdate) {
        Equipamento dbEquipamento = this.findById(id);

        if(!dbEquipamento.getId().equals(equipamentoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbEquipamento.setNome(equipamentoToUpdate.getNome());
        dbEquipamento.setUpdatedAt(LocalDateTime.now());

        return this.equipamentoRepository.save(dbEquipamento);
    }

    @Transactional
    public void delete(Long id) {
        Equipamento dbEquipamento = this.findById(id);
        this.equipamentoRepository.delete(dbEquipamento);
    }

    public Boolean existsById(Long id) {
        return this.equipamentoRepository.existsById(id);
    }
}
