package com.example.basicsamplesite.infrastructure.common.util;

import com.example.basicsamplesite.infrastructure.common.dto.PageRequest;
import com.example.basicsamplesite.infrastructure.common.dto.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PageUtils 단위 테스트")
class PageUtilsTest {

    @Test
    @DisplayName("전체 리스트에서 첫 번째 페이지 데이터를 올바르게 추출한다")
    void shouldGetFirstPageContent() {
        // given
        List<String> allData = List.of("item1", "item2", "item3", "item4", "item5");
        PageRequest pageRequest = PageRequest.of(1, 3);
        
        // when
        List<String> pageContent = PageUtils.getPageContent(allData, pageRequest);
        
        // then
        assertThat(pageContent).hasSize(3);
        assertThat(pageContent).containsExactly("item1", "item2", "item3");
    }

    @Test
    @DisplayName("전체 리스트에서 두 번째 페이지 데이터를 올바르게 추출한다")
    void shouldGetSecondPageContent() {
        // given
        List<String> allData = List.of("item1", "item2", "item3", "item4", "item5");
        PageRequest pageRequest = PageRequest.of(2, 3);
        
        // when
        List<String> pageContent = PageUtils.getPageContent(allData, pageRequest);
        
        // then
        assertThat(pageContent).hasSize(2);
        assertThat(pageContent).containsExactly("item4", "item5");
    }

    @Test
    @DisplayName("페이지 범위를 벗어나면 빈 리스트를 반환한다")
    void shouldReturnEmptyListWhenPageOutOfRange() {
        // given
        List<String> allData = List.of("item1", "item2", "item3");
        PageRequest pageRequest = PageRequest.of(3, 3);
        
        // when
        List<String> pageContent = PageUtils.getPageContent(allData, pageRequest);
        
        // then
        assertThat(pageContent).isEmpty();
    }

    @Test
    @DisplayName("null 데이터에 대해 빈 리스트를 반환한다")
    void shouldReturnEmptyListForNullData() {
        // given
        List<String> allData = null;
        PageRequest pageRequest = PageRequest.of(1, 10);
        
        // when
        List<String> pageContent = PageUtils.getPageContent(allData, pageRequest);
        
        // then
        assertThat(pageContent).isEmpty();
    }

    @Test
    @DisplayName("빈 데이터에 대해 빈 리스트를 반환한다")
    void shouldReturnEmptyListForEmptyData() {
        // given
        List<String> allData = List.of();
        PageRequest pageRequest = PageRequest.of(1, 10);
        
        // when
        List<String> pageContent = PageUtils.getPageContent(allData, pageRequest);
        
        // then
        assertThat(pageContent).isEmpty();
    }

    @Test
    @DisplayName("전체 데이터로 페이지 응답을 생성한다")
    void shouldCreatePageResponseFromAllData() {
        // given
        List<String> allData = List.of("item1", "item2", "item3", "item4", "item5");
        PageRequest pageRequest = PageRequest.of(1, 3);
        
        // when
        PageResponse<String> pageResponse = PageUtils.createPageResponse(allData, pageRequest);
        
        // then
        assertThat(pageResponse.content()).hasSize(3);
        assertThat(pageResponse.content()).containsExactly("item1", "item2", "item3");
        assertThat(pageResponse.totalElements()).isEqualTo(5);
        assertThat(pageResponse.totalPages()).isEqualTo(2);
        assertThat(pageResponse.currentPage()).isEqualTo(1);
        assertThat(pageResponse.pageSize()).isEqualTo(3);
    }

    @Test
    @DisplayName("이미 페이징된 데이터로 페이지 응답을 생성한다")
    void shouldCreatePageResponseFromPagedData() {
        // given
        List<String> pagedData = List.of("item4", "item5");
        PageRequest pageRequest = PageRequest.of(2, 3);
        int totalElements = 5;
        
        // when
        PageResponse<String> pageResponse = PageUtils.createPageResponse(pagedData, pageRequest, totalElements);
        
        // then
        assertThat(pageResponse.content()).hasSize(2);
        assertThat(pageResponse.content()).containsExactly("item4", "item5");
        assertThat(pageResponse.totalElements()).isEqualTo(5);
        assertThat(pageResponse.totalPages()).isEqualTo(2);
        assertThat(pageResponse.currentPage()).isEqualTo(2);
        assertThat(pageResponse.pageSize()).isEqualTo(3);
    }

    @Test
    @DisplayName("전체 페이지 수를 올바르게 계산한다")
    void shouldCalculateTotalPagesCorrectly() {
        // when & then
        assertThat(PageUtils.calculateTotalPages(0, 10)).isEqualTo(0);
        assertThat(PageUtils.calculateTotalPages(5, 10)).isEqualTo(1);
        assertThat(PageUtils.calculateTotalPages(10, 10)).isEqualTo(1);
        assertThat(PageUtils.calculateTotalPages(11, 10)).isEqualTo(2);
        assertThat(PageUtils.calculateTotalPages(25, 10)).isEqualTo(3);
    }

    @Test
    @DisplayName("유효한 페이지 번호인지 확인한다")
    void shouldValidatePageNumber() {
        // when & then
        assertThat(PageUtils.isValidPage(1, 5)).isTrue();
        assertThat(PageUtils.isValidPage(5, 5)).isTrue();
        assertThat(PageUtils.isValidPage(0, 5)).isFalse();
        assertThat(PageUtils.isValidPage(6, 5)).isFalse();
        assertThat(PageUtils.isValidPage(1, 0)).isTrue(); // totalPages가 0일 때는 1페이지가 유효
    }
}
