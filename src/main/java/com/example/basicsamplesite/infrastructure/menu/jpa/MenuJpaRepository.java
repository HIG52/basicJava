package com.example.basicsamplesite.infrastructure.menu.jpa;

import com.example.basicsamplesite.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<Menu, Long> {
    
    @Query(value = "SELECT * FROM menus WHERE menu_path = :menuPath AND is_active = 1", nativeQuery = true)
    Optional<Menu> findByMenuPathAndActive(@Param("menuPath") String menuPath);
    
    @Query(value = "SELECT * FROM menus WHERE is_active = 1 ORDER BY created_at ASC", nativeQuery = true)
    List<Menu> findAllActiveMenus();
    
    @Query(value = "SELECT * FROM menus ORDER BY created_at DESC", nativeQuery = true)
    List<Menu> findAllMenusForAdmin();
}
