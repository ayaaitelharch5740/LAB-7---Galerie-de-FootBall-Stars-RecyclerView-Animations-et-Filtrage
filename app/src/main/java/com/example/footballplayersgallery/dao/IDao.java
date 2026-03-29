package com.example.footballplayersgallery.dao;


import java.util.List;

public interface IDao<T> {
    boolean add(T item);
    boolean modify(T item);
    boolean remove(T item);
    T fetchById(int id);
    List<T> fetchAll();
}