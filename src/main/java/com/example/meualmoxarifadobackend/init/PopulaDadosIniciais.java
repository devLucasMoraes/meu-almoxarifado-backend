package com.example.meualmoxarifadobackend.init;

import com.example.meualmoxarifadobackend.controller.dto.request.CategoriaDTO;
import com.example.meualmoxarifadobackend.controller.dto.request.EquipamentoDTO;
import com.example.meualmoxarifadobackend.controller.dto.request.InsumoDTO;
import com.example.meualmoxarifadobackend.controller.dto.request.RequisitanteDTO;
import com.example.meualmoxarifadobackend.domain.model.Unidade;
import com.example.meualmoxarifadobackend.service.CategoriaService;
import com.example.meualmoxarifadobackend.service.EquipamentoService;
import com.example.meualmoxarifadobackend.service.InsumoService;
import com.example.meualmoxarifadobackend.service.RequisitanteService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("dev")
public class PopulaDadosIniciais {

    private final CategoriaService categoriaService;

    private final InsumoService insumoService;

    private final RequisitanteService requisitanteService;

    private final EquipamentoService equipamentoService;

    public PopulaDadosIniciais(CategoriaService categoriaService, InsumoService insumoService, RequisitanteService requisitanteService, EquipamentoService equipamentoService) {
        this.categoriaService = categoriaService;
        this.insumoService = insumoService;
        this.requisitanteService = requisitanteService;
        this.equipamentoService = equipamentoService;
    }

    @PostConstruct
    public void populaDadosIniciais(){
        CategoriaDTO alcoois = new CategoriaDTO(
                null,
                "ALCOOIS"
        );
        CategoriaDTO papelaoParana = new CategoriaDTO(
                null,
                "PAPELAO PARANA"
        );

        CategoriaDTO vernizes = new CategoriaDTO(
                null,
                "VERNIZES"
        );

        InsumoDTO vernizUv = new InsumoDTO(
                null,
                "VERNIZ UV-8010/11 CALANDRA BRILHO",
                new BigDecimal(5),
                false,
                Unidade.KG,
                new BigDecimal(30),
                3L
        );

        InsumoDTO ipa7030 = new InsumoDTO(
                null,
                "IPA 70/30",
                new BigDecimal(5),
                false,
                Unidade.LITRO,
                new BigDecimal(150),
                1L
        );
        InsumoDTO ipa100 = new InsumoDTO(
                null,
                "IPA 100",
                new BigDecimal(0),
                false,
                Unidade.LITRO,
                new BigDecimal(150),
                1L
        );
        InsumoDTO papelao08100015 = new InsumoDTO(
                null,
                "PAPELAO 800 x 1000mm 1.5mm",
                new BigDecimal(0),
                false,
                Unidade.FOLHA,
                new BigDecimal(100),
                2L
        );

        RequisitanteDTO requisitanteDTO = new RequisitanteDTO(
                null,
                "ROGERIO",
                "(xx)x xxxx-xxxx"
        );

        EquipamentoDTO localDeAplicacaoDTO = new EquipamentoDTO(
                null,
                "SM102"
        );

        categoriaService.create(alcoois.toModel());
        categoriaService.create(papelaoParana.toModel());
        categoriaService.create(vernizes.toModel());
        insumoService.create(ipa7030.toModel());
        insumoService.create(ipa100.toModel());
        insumoService.create(vernizUv.toModel());
        insumoService.create(papelao08100015.toModel());
        requisitanteService.create(requisitanteDTO.toNewModel());
        equipamentoService.create(localDeAplicacaoDTO.toNewModel());
    }
}
