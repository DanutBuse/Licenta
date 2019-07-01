package com.rab.licenta.AutoClinique;

import java.util.ArrayList;
import java.util.List;

import com.rab.licenta.AutoClinique.dto.MessageWrapperDTO;
import com.rab.licenta.AutoClinique.dto.OfferDTO;
import com.rab.licenta.AutoClinique.entity.OfferEntity;

public class OfferDtoMapper {

	public static List<OfferEntity> fromDTO(MessageWrapperDTO dto) {
		
		List<OfferEntity> ent = new ArrayList<OfferEntity>();
		
		dto.getListaOferte().stream()
							.forEach( oferta -> ent.add(OfferDtoMapper.fromDTO(oferta)));
		
		return ent;
		
	}
	
	public static OfferEntity fromDTO(OfferDTO dto) {
		return new OfferEntity(dto.getNumePiesa(), dto.getProducator(), dto.getPret(), null);
	}
	
	public static OfferDTO toDTO(OfferEntity ent) {
		return new OfferDTO(ent.getNumePiesa(), ent.getProducator(), ent.getPret());
	}
}
