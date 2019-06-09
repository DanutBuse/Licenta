package com.rab.licenta.AutoClinique;

import java.util.ArrayList;
import java.util.List;

import com.rab.licenta.AutoClinique.dto.MessageWrapperDTO;
import com.rab.licenta.AutoClinique.dto.OfertaDTO;
import com.rab.licenta.AutoClinique.entity.OfertaEntity;

public class OfertaDtoMapper {

	public static List<OfertaEntity> fromDTO(MessageWrapperDTO dto) {
		
		List<OfertaEntity> ent = new ArrayList<OfertaEntity>();
		
		dto.getListaOferte().stream()
							.forEach( oferta -> ent.add(OfertaDtoMapper.fromDTO(oferta)));
		
		return ent;
		
	}
	
	public static OfertaEntity fromDTO(OfertaDTO dto) {
		return new OfertaEntity(dto.getNumePiesa(), dto.getProducator(), dto.getPret(), null);
	}
	
	public static OfertaDTO toDTO(OfertaEntity ent) {
		return new OfertaDTO(ent.getNumePiesa(), ent.getProducator(), ent.getPret());
	}
}
