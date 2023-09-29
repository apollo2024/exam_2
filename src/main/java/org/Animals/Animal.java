package org.Animals;

import java.util.List;

public interface Animal {
    String getName();

    void setDateBirth(String date);

    String getDateBirth();

    void setName(String name);

    void addCommand(String newCommand);

    void removeCommand(String command);

    List<String> getCommandList();

    String getColor();

    int getCommandCount();

    void setColor(String color);
}

