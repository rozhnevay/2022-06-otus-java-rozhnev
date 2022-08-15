package ru.otus.model.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import ru.otus.enums.NominalEnum;
import ru.otus.exceptions.BucketNotFoundException;
import ru.otus.exceptions.NotEnoughMoneyException;
import ru.otus.exceptions.NotEnoughNominalException;
import ru.otus.model.Banknote;
import ru.otus.model.Bucket;
import ru.otus.model.MoneyStorage;

@Getter
public class MoneyStorageImpl implements MoneyStorage {
    private final Set<Bucket> buckets = new HashSet<>();

    public MoneyStorageImpl(List<NominalEnum> supportedNominalEnums) {
        supportedNominalEnums.forEach(supportedNominal -> buckets.add(new BucketImpl(supportedNominal)));
    }

    @Override
    public void topUp(Banknote banknote) {
        topUp(banknote, 1);
    }

    @Override
    public void topUp(Banknote banknote, Integer countBanknotes) {
        for (var i = 0; i < countBanknotes; i++) {
            getBucketByNominal(banknote.getNominal()).add(banknote);
        }
    }

    @Override
    public List<Banknote> withdraw(Integer amount) {
        if (amount > getBalance()) {
            throw new NotEnoughMoneyException(amount);
        }
        List<Banknote> result = new ArrayList<>();

        List<NominalEnum> suitableNominals = getAvailableNominals()
            .stream()
            .filter(nominalEnum -> nominalEnum.getValue() <= amount)
            .sorted(Comparator.comparingInt(NominalEnum::getValue).reversed())
            .collect(Collectors.toList());

        int remainingAmount = amount;
        int currentNominalLevel = 0;
        while (remainingAmount > 0) {
            if (currentNominalLevel == suitableNominals.size()) {
                throw new NotEnoughNominalException(amount);
            }

            NominalEnum currentNominal = suitableNominals.get(currentNominalLevel);
            int neededCount = remainingAmount / currentNominal.getValue();

            int nominalAmountInBucket = getBucketByNominal(currentNominal).getBalance();
            if (neededCount > nominalAmountInBucket) {
                for (var i = 0; i < nominalAmountInBucket; i++) {
                    result.add(new BanknoteImpl(currentNominal));
                }
                remainingAmount -= nominalAmountInBucket*currentNominal.getValue();
            } else {
                for (var i = 0; i < neededCount; i++) {
                    result.add(new BanknoteImpl(currentNominal));
                }
                remainingAmount -= neededCount*currentNominal.getValue();
            }

            currentNominalLevel++;
        }

        result.forEach(banknote -> getBucketByNominal(banknote.getNominal()).remove(banknote));

        return result;
    }

    @Override
    public int getBalance() {
        return buckets
            .stream()
            .mapToInt(Bucket::getBalance)
            .sum();
    }

    public Set<NominalEnum> getAvailableNominals() {
        return buckets.stream()
            .map(Bucket::getNominal)
            .collect(Collectors.toSet());
    }

    private Bucket getBucketByNominal(NominalEnum nominalEnum) {
        return buckets.stream()
            .filter(bucket -> bucket.getNominal().equals(nominalEnum))
            .findFirst()
            .orElseThrow(() -> new BucketNotFoundException(nominalEnum));
    }
}
