package ru.otus.model.impl;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.enums.NominalEnum;
import ru.otus.model.Banknote;

@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor
@EqualsAndHashCode
public class BanknoteImpl implements Banknote {
    private final NominalEnum nominalEnum;

    @Override
    public NominalEnum getNominal() {
        return nominalEnum;
    }

    @Override
    public int getNominalValue() {
        return nominalEnum.getValue();
    }
}
