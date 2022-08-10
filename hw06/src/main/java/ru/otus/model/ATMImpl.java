package ru.otus.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import ru.otus.enums.NominalEnum;
import ru.otus.exceptions.NotEnoughMoneyException;
import ru.otus.exceptions.NotEnoughNominalException;

public class ATMImpl implements ATM {
    private final Map<NominalEnum, Integer> buckets;

    public ATMImpl() {
        this.buckets = new HashMap<>();
        Arrays.stream(NominalEnum.values()).forEach(nominalEnum -> this.buckets.put(nominalEnum, 0));
    }

    @Override
    public void topUp(List<NominalEnum> banknotes) {
        banknotes.forEach(this::topUpBucket);
    }

    @Override
    public List<Pair<NominalEnum, Integer>> withdraw(int amount) {
        if (amount > getBalance()) {
            throw new NotEnoughMoneyException(amount);
        }
        List<Pair<NominalEnum, Integer>> result = new ArrayList<>();

        List<NominalEnum> suitableNominalEnums = Arrays.stream(NominalEnum.values()).toList()
            .stream()
            .filter(nominalEnum -> nominalEnum.getValue() <= amount)
            .sorted(Comparator.comparingInt(NominalEnum::getValue).reversed())
            .collect(Collectors.toList());

        int remainingAmount = amount;
        int currentNominalLevel = 0;
        while (remainingAmount > 0) {
            if (currentNominalLevel == suitableNominalEnums.size()) {
                throw new NotEnoughNominalException(amount);
            }

            NominalEnum currentNominal = suitableNominalEnums.get(currentNominalLevel);
            int neededCount = remainingAmount / currentNominal.getValue();

            int nominalAmountInBucket = buckets.get(currentNominal);
            if (neededCount > nominalAmountInBucket) {
                result.add(Pair.of(currentNominal, nominalAmountInBucket));
                remainingAmount -= nominalAmountInBucket*currentNominal.getValue();
            } else {
                result.add(Pair.of(currentNominal, neededCount));
                remainingAmount -= neededCount*currentNominal.getValue();
            }

            currentNominalLevel++;
        }

        result.forEach(pair -> withdrawFromBucket(pair.getKey(), pair.getValue()));

        return result;
    }

    @Override
    public int getBalance() {
        return buckets
            .entrySet()
            .stream()
            .mapToInt(
                (entry) ->
                entry.getKey().getValue() * entry.getValue()
            ).sum();
    }

    private void topUpBucket(NominalEnum bucketNominal) {
        buckets.put(bucketNominal, buckets.get(bucketNominal) + 1);
    }

    private void withdrawFromBucket(NominalEnum bucketNominal, Integer countToWithdraw) {
        buckets.put(bucketNominal, buckets.get(bucketNominal) - countToWithdraw);
    }
}
