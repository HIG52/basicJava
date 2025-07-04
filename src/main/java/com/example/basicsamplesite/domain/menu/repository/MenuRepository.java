package com.example.basicsamplesite.domain.menu.repository;

import com.example.basicsamplesite.domain.menu.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    
    Menu save(Menu menu);
    
    Optional<Menu> findById(Long id);
    
    Optional<Menu> findByMenuPath(String menuPath);
    
    List<Menu> findAll();
    
    List<Menu> findAllActiveMenus();
    
    List<Menu> findAllMenusForAdmin();
    
    void delete(Menu menu);
}
