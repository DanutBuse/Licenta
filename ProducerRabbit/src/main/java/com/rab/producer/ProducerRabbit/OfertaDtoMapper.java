package com.rab.producer.ProducerRabbit;

import java.util.ArrayList;
import java.util.List;

import com.rab.producer.ProducerRabbit.dto.MessageWrapperDTO;
import com.rab.producer.ProducerRabbit.dto.OfertaDTO;
import com.rab.producer.ProducerRabbit.entity.OfertaEntity;

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
