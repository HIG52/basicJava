package com.example.basicsamplesite.infrastructure.menu.repository;

import com.example.basicsamplesite.domain.menu.entity.Menu;
import com.example.basicsamplesite.domain.menu.repository.MenuRepository;
import com.example.basicsamplesite.infrastructure.menu.jpa.MenuJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {
    
    private final MenuJpaRepository menuJpaRepository;
    
    @Override
    public Menu save(Menu menu) {
        return menuJpaRepository.save(menu);
    }
    
    @Override
    public Optional<Menu> findById(Long id) {
        return menuJpaRepository.findById(id);
    }
    
    @Override
    public Optional<Menu> findByMenuPath(String menuPath) {
        return menuJpaRepository.findByMenuPathAndActive(menuPath);
    }
    
    @Override
    public List<Menu> findAll() {
        return menuJpaRepository.findAll();
    }
    
    @Override
    public List<Menu> findAllActiveMenus() {
        return menuJpaRepository.findAllActiveMenus();
    }
    
    @Override
    public List<Menu> findAllMenusForAdmin() {
        return menuJpaRepository.findAllMenusForAdmin();
    }
    
    @Override
    public void delete(Menu menu) {
        menuJpaRepository.delete(menu);
    }
}
