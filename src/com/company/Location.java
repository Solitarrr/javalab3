package com.company;

/**
 * Этот класс представляет конкретное место на 2D-карте.
 * Координаты целочисленные значения.
 **/
public class Location
{
    /** X координата. **/
    public int xCoord;

    /** Y координата. **/
    public int yCoord;


    /** Создает новое местоположение с указанными целыми координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создаем новую локацию с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }
    /** Тут сравниваем это местоположение с другим **/
    public boolean equals(Object obj) {
        // Представляет ли из себя obj Location
        if (obj instanceof Location) {
            // Тут приводится другой объект к типу местоположения, затем сравнить. Вернуть true в случае, если равно
            Location other = (Location) obj;
            if (xCoord == other.xCoord && yCoord == other.yCoord) {
                return true;
            }
        }
        // В случае если не равны, то вернуть false
        return false;
    }

    /** Предоставление хэш-кода для каждого местоположения **/
    public int hashCode() {
        int result = 17; // Некое основное значение
        // Тут используется другое простое значение для объединения
        result = 37 * result + xCoord;
        result = 37 * result + yCoord;
        return result;
    }
}