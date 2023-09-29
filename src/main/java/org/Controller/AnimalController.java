package org.Controller;

import org.Animals.Cat;
import org.Animals.Dog;
import org.Animals.Hamster;
import org.System.*;

import java.util.*;
import java.util.logging.Logger;

public class AnimalController {
    private final AnimalList<Object> animalList = new AnimalList<>();
    private final Tools  tool = new Tools();

    // главное меню
    private final Map<String, String> menuMain = new HashMap<String, String>() {{
        put("1", "Добавить новое животное");
        put("2", "Добавить команду");
        put("3", "Показать список");
        put("4", "Представить команды");
        put("5", "Общее кол-во животных");
        put("0", "Выход");
    }};
    // меню второго уровня
    private final Map<String, String> menuAnimal = new HashMap<>() {{
        put("1", "Кот");
        put("2", "Собака");
        put("3", "Хомяк");
        put("0", "Отмена");
    }};

    private final Map<String, String> menuYesNo = new HashMap<>(){{
        put("1","ДА");
        put("0", "НЕТ");
    }};

    private enum ANIMALS {CAT, DOG, HAMSTER};

    public void Run() throws Exception {
        String menu;
        do {
            menu = getOperation();

            switch (menu) {
                case "11" -> addAnimal(ANIMALS.CAT);
                case "12" -> addAnimal(ANIMALS.DOG);
                case "13" -> addAnimal(ANIMALS.HAMSTER);
                case "21" -> addCommand(ANIMALS.CAT);
                case "22" -> addCommand(ANIMALS.DOG);
                case "23" -> addCommand(ANIMALS.HAMSTER);
                case "31" -> showAnimals(ANIMALS.CAT);
                case "32" -> showAnimals(ANIMALS.DOG);
                case "33" -> showAnimals(ANIMALS.HAMSTER);
                case "41" -> showCommands(ANIMALS.CAT);
                case "42" -> showCommands(ANIMALS.DOG);
                case "43" -> showCommands(ANIMALS.HAMSTER);
                case "5" -> showCountAnimals();
            }
        } while (!(menu.isEmpty() || menu.equals("0")));
    }
    private void showCountAnimals() throws Exception{
        try(Counter counter = new Counter()){
            Logger.getAnonymousLogger().info(counter.getCount().toString());
        }
    }
    private void showCommands(ANIMALS animal){
        String name = tool.getString("Имя животного: ");

        Object o = null;

        switch (animal){
            case CAT -> o = animalList.findCat(name);
            case DOG -> o = animalList.findDog(name);
            case HAMSTER -> o = animalList.findHamster(name);
        }

        if(o == null){
            Logger.getAnonymousLogger().info("Животное не найдено");
            return;
        }

        List<String> commands = null;

        switch (animal){
            case CAT -> commands = ((Cat)o).getCommandList();
            case DOG -> commands = ((Dog)o).getCommandList();
            case HAMSTER -> commands = ((Hamster)o).getCommandList();
        }

        StringBuilder strCommands = new StringBuilder();
        for (String c :commands) {
            strCommands.append(c).append(", ");
        }

        Logger.getAnonymousLogger().info(strCommands.toString());
    }
    private void showAnimals(ANIMALS animal){
        List<Object> animals = null;

        switch (animal){
            case CAT -> animals = animalList.getCats();
            case DOG -> animals = animalList.getDogs();
            case HAMSTER -> animals = animalList.getHamsters();
        }
        Logger logger = Logger.getAnonymousLogger();
        for (Object o : animals) {
            logger.info(o.toString());
        }
    }

    private void addCommand(ANIMALS animal){
        String name = tool.getString("Имя животного: ");
        Object objAnimal = null;
        switch (animal){
            case CAT -> objAnimal = animalList.findCat(name);
            case DOG -> objAnimal = animalList.findDog(name);
            case HAMSTER -> objAnimal = animalList.findHamster(name);
        }

        if(objAnimal == null){
            Logger.getAnonymousLogger().info("Нету такого животного");
        }
        else{
            String command = tool.getString("Новая команда: ");

            switch (animal){
                case CAT -> ((Cat)objAnimal).addCommand(command);
                case DOG -> ((Dog)objAnimal).addCommand(command);
                case HAMSTER -> ((Hamster)objAnimal).addCommand(command);
            }
        }
    }

    private void addAnimal(ANIMALS animal) throws Exception {
        // Счетчик
        try(Counter counter = new Counter()){
            counter.add();
        }
        String name = tool.getString("Имя животного: ");
        String color = tool.getString("Окрас: ");
        String date = tool.getString("Дата рождения: ");

        List<String> commands = new ArrayList<>();
        System.out.println("Добавить команды?");
        String menu = tool.menuShow(menuYesNo);
        while (menu.equals("1")){
            String command = tool.getString("Команда: ");
            commands.add(command);
            System.out.println("Продолжить?");
            menu = tool.menuShow(menuYesNo);
        }

        switch (animal){
            case CAT -> animalList.addAnimal(new Cat(name, color, date, commands));
            case DOG -> animalList.addAnimal(new Dog(name, color, date, commands));
            case HAMSTER -> animalList.addAnimal(new Hamster(name, color, date, commands));
        }
    }
    private String getOperation() {
        String menu = tool.menuShow(menuMain);
        if (!menu.isEmpty() && !menu.equals("0") && !menu.equals("5")) {
            menu += tool.menuShow(menuAnimal);
        }

        return menu;
    }
}
