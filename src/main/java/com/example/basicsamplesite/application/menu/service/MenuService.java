package com.example.basicsamplesite.application.menu.service;

import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import com.example.basicsamplesite.domain.menu.entity.Menu;
import com.example.basicsamplesite.domain.menu.repository.MenuRepository;
import com.example.basicsamplesite.infrastructure.exception.menu.DuplicateMenuPathException;
import com.example.basicsamplesite.infrastructure.exception.menu.MenuNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    
    private final MenuRepository menuRepository;
    
    @Transactional
    public MenuApplicationDto createMenu(String menuName, String menuPath, String description) {
        // 중복 경로 체크
        if (menuRepository.findByMenuPath(menuPath).isPresent()) {
            throw new DuplicateMenuPathException("이미 존재하는 메뉴 경로입니다: " + menuPath);
        }
        
        Menu menu = new Menu(menuName, menuPath, description);
        Menu savedMenu = menuRepository.save(menu);
        return MenuApplicationDto.from(savedMenu);
    }
    
    @Transactional
    public MenuApplicationDto updateMenu(Long menuId, String menuName, String menuPath, String description) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
        
        // 다른 메뉴에서 같은 경로를 사용하는지 체크
        menuRepository.findByMenuPath(menuPath)
            .filter(existingMenu -> !existingMenu.getId().equals(menuId))
            .ifPresent(existingMenu -> {
                throw new DuplicateMenuPathException("이미 존재하는 메뉴 경로입니다: " + menuPath);
            });
        
        menu.updateMenu(menuName, menuPath, description);
        Menu updatedMenu = menuRepository.save(menu);
        return MenuApplicationDto.from(updatedMenu);
    }
    
    @Transactional
    public void deactivateMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
        
        menu.deactivate();
        menuRepository.save(menu);
    }
    
    @Transactional
    public void activateMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
        
        menu.activate();
        menuRepository.save(menu);
    }
    
    public List<MenuApplicationDto> getAllActiveMenus() {
        return menuRepository.findAllActiveMenus().stream()
                .map(MenuApplicationDto::from)
                .collect(Collectors.toList());
    }
    
    public List<MenuApplicationDto> getAllMenusForAdmin() {
        return menuRepository.findAllMenusForAdmin().stream()
                .map(MenuApplicationDto::from)
                .collect(Collectors.toList());
    }
    
    public MenuApplicationDto getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
        return MenuApplicationDto.from(menu);
    }
}
