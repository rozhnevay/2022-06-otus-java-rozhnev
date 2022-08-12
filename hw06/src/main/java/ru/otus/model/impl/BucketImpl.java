package ru.otus.model.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.enums.NominalEnum;
import ru.otus.exceptions.NominalNotSupportedInBucketException;
import ru.otus.model.Banknote;
import ru.otus.model.Bucket;

@RequiredArgsConstructor
public class BucketImpl implements Bucket {

    private final NominalEnum nominalEnum;

    private final List<Banknote> banknotes = new ArrayList<>();

    @Override
    public void add(Banknote banknote) {
        if (!banknote.getNominal().equals(nominalEnum)) {
            throw new NominalNotSupportedInBucketException(nominalEnum);
        }
        banknotes.add(banknote);
    }

    @Override
    public void remove(Banknote banknote) {
        banknotes.remove(banknote);
    }

    @Override
    public int getBalance() {
        return banknotes.size()*nominalEnum.getValue();
    }

    @Override
    public NominalEnum getNominal() {
        return nominalEnum;
    }
}
