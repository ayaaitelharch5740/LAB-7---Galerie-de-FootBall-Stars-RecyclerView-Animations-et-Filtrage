package com.example.footballplayersgallery.service;

import com.example.footballplayersgallery.beans.Player;
import com.example.footballplayersgallery.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class PlayerService implements IDao<Player> {

    private final List<Player> catalog;
    private static PlayerService instance;

    private PlayerService() {
        catalog = new ArrayList<>();
        loadPlayers();
    }

    public static PlayerService getInstance() {
        if (instance == null) instance = new PlayerService();
        return instance;
    }

    private void loadPlayers() {
        // Yassine Bounou - Gardien de but
        catalog.add(new Player("Yassine Bounou",
                "https://upload.wikimedia.org/wikipedia/commons/5/53/Yassine_Bono_%28cropped%29.jpg",
                "Gardien de but", "Al-Hilal FC", 4.9f));

        // Achraf Hakimi - Défenseur
        catalog.add(new Player("Achraf Hakimi",
                "https://upload.wikimedia.org/wikipedia/commons/f/f1/Achraf_Hakimi_vs_Niger%2C_5_Sept_2025.jpg",
                "Défenseur", "Paris Saint-Germain", 4.8f));

        // Nayef Aguerd - Défenseur
        catalog.add(new Player("Nayef Aguerd",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/AguerdYoussef_%28cropped%29.jpg/640px-AguerdYoussef_%28cropped%29.jpg",
                "Défenseur", "Real Sociedad", 4.7f));


        // Azzedine Ounahi - Milieu de terrain
        catalog.add(new Player("Azzedine Ounahi",
                "https://tse4.mm.bing.net/th/id/OIP.VmzRNJQBJSjyg5GKtBHCugHaHa?w=2500&h=2500&rs=1&pid=ImgDetMain&o=7&rm=3",
                "Milieu de terrain", "Panathinaïkos", 4.7f));

        // Hakim Ziyech - Attaquant
        catalog.add(new Player("Hakim Ziyech",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Hakim_Ziyech_2021.jpg/640px-Hakim_Ziyech_2021.jpg",
                "Attaquant", "Galatasaray SK", 4.7f));

        // Brahim Díaz - Attaquant
        catalog.add(new Player("Brahim Díaz",
                "https://tse1.mm.bing.net/th/id/OIP.lp8V3A7G8q_N8gAxFn8_6QHaE8?rs=1&pid=ImgDetMain&o=7&rm=3",
                "Attaquant", "Real Madrid", 4.8f));

        catalog.add(new Player("Sofyan Amrabat",
                "https://tse4.mm.bing.net/th/id/OIP.8mkC3qGZ3R-nmt5M2TTLGwHaHa?rs=1&pid=ImgDetMain&o=7&rm=3",
                "Milieu de terrain", "Fenerbahçe SK", 4.8f));

    }

    @Override public boolean add(Player item)    { return catalog.add(item); }
    @Override public boolean remove(Player item) { return catalog.remove(item); }

    @Override
    public boolean modify(Player item) {
        for (Player p : catalog) {
            if (p.getId() == item.getId()) {
                p.setFullName(item.getFullName());
                p.setPhotoUrl(item.getPhotoUrl());
                p.setScore(item.getScore());
                p.setPosition(item.getPosition());
                p.setClub(item.getClub());
                return true;
            }
        }
        return false;
    }

    @Override
    public Player fetchById(int id) {
        for (Player p : catalog) if (p.getId() == id) return p;
        return null;
    }

    @Override
    public List<Player> fetchAll() { return catalog; }
}