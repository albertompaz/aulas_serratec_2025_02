package org.serratec.h2banco.converter;

import org.serratec.h2banco.domain.Combustivel;
import org.serratec.h2banco.exception.EnumValidationException;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CombustivelConverter implements AttributeConverter<Combustivel, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Combustivel combustivel) {
		return combustivel != null ? combustivel.getCodigo() : null;
	}

	@Override
	public Combustivel convertToEntityAttribute(Integer dbData) {
		try {
			return Combustivel.verifica(dbData);
		} catch (EnumValidationException e) {
			e.printStackTrace();
			return null;
		}
	}

}
