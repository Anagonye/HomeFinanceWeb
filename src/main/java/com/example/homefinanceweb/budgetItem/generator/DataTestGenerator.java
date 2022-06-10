package com.example.homefinanceweb.budgetItem.generator;

import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataTestGenerator {
    private static String[] foodNames = {"Tomato", "Bread", "Water", "Chocolate", "Ketchup", "butter", "eggs"};
    private static String[] billNames = {"Electricity bill", "Water bill", "Bill for the Internet"};
    private static String[] rentNames = {"Rent for an apartment"};
    private static String[] entertainmentNames = {"Netflix subscription", "Cinema ticket", "Harry Potter book", "Aqua park"};
    private static String[] transportNames = {"Fuel", "Train ticket"};
    private static String[] healthNames = {"Slices", "Painkiller"};
    private static String[] clothesNames = {"Shirt", "Pants", "Hat"};
    private static String[] giftNames = {"Socks", "Flowers"};
    private static String[] investmentNames = {"Bitcoin", "Gold", "Treasury bonds"};
    private static String[] debtNames = {"Debt to a friend"};
    private static String[] personalNames = {"Haircut"};


    private static List<ExpenseDto> generateExpensesInCategory(ExpenseCategory category, String[] namesPool, int minValue, int maxValue, int numberOf, LocalDate startDate, LocalDate endDate){
        List<ExpenseDto> result = new ArrayList<>();


        for(int i = 0; i<numberOf; i++){
            int randomIndex = (int)(Math.random()*namesPool.length);
            int randomValue =(int)(Math.random()*(maxValue - minValue)+minValue);
            LocalDate localDate = randomLocalDate(startDate, endDate);
            result.add(new ExpenseDto(namesPool[randomIndex], BigDecimal.valueOf(randomValue),  category, localDate));
        }
        return result;
    }

    private static LocalDate randomLocalDate(LocalDate startDate, LocalDate endDate){
        long minDay = startDate.toEpochDay();
        long maxDay = endDate.toEpochDay();
        long randomDate = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDate);

    }
    private static final LocalDate start = LocalDate.of(2022,1,1);
    private static final LocalDate end = LocalDate.of(2022,4,5);

    private final static List<ExpenseDto> foodExpenses = generateExpensesInCategory(ExpenseCategory.FOOD, foodNames, 1, 15, 30, start, end);
    private final static List<ExpenseDto> billExpenses = generateExpensesInCategory(ExpenseCategory.BILL, billNames, 15, 100, 5, start, end);
    private final static List<ExpenseDto> rentExpenses = generateExpensesInCategory(ExpenseCategory.RENT, rentNames, 1000, 1000, 3, start, end);
    private final static List<ExpenseDto> entertainmentExpenses = generateExpensesInCategory(ExpenseCategory.ENTERTAINMENT, entertainmentNames, 10, 50, 10, start, end);
    private final static List<ExpenseDto> transportExpenses = generateExpensesInCategory(ExpenseCategory.TRANSPORT, transportNames, 1, 25, 10, start, end);
    private final static List<ExpenseDto> healthExpenses = generateExpensesInCategory(ExpenseCategory.HEALTH, healthNames, 5, 10, 2, start, end);
    private final static List<ExpenseDto> clothesExpenses = generateExpensesInCategory(ExpenseCategory.CLOTHES, clothesNames, 15, 75, 5, start, end);
    private final static List<ExpenseDto> investmentExpenses = generateExpensesInCategory(ExpenseCategory.CLOTHES, clothesNames, 50, 500, 5, start, end);
    private final static List<ExpenseDto> debtExpenses = generateExpensesInCategory(ExpenseCategory.DEBT, debtNames, 10, 50, 2, start, end);
    private final static List<ExpenseDto> personalExpenses = generateExpensesInCategory(ExpenseCategory.PERSONAL, personalNames, 10, 15, 3, start, end);

    public static List<ExpenseDto> generatedExpenses(){

        Stream<ExpenseDto> combined = Stream.of(foodExpenses,billExpenses,rentExpenses,entertainmentExpenses,transportExpenses,healthExpenses,clothesExpenses,investmentExpenses,debtExpenses,personalExpenses)
                .flatMap(Collection::stream);
        return combined.collect(Collectors.toList());
    }


}
