package com.example.basicsamplesite.infrastructure.common.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PageResponse 단위 테스트")
class PageResponseTest {

    @Test
    @DisplayName("페이지 응답이 올바르게 생성된다")
    void shouldCreatePageResponse() {
        // given
        List<String> content = List.of("item1", "item2", "item3");
        PageRequest pageRequest = PageRequest.of(1, 10);
        int totalElements = 25;
        
        // when
        PageResponse<String> pageResponse = PageResponse.of(content, pageRequest, totalElements);
        
        // then
        assertThat(pageResponse.content()).hasSize(3);
        assertThat(pageResponse.currentPage()).isEqualTo(1);
        assertThat(pageResponse.pageSize()).isEqualTo(10);
        assertThat(pageResponse.totalElements()).isEqualTo(25);
        assertThat(pageResponse.totalPages()).isEqualTo(3); // ceil(25/10) = 3
        assertThat(pageResponse.first()).isTrue();
        assertThat(pageResponse.last()).isFalse();
        assertThat(pageResponse.hasNext()).isTrue();
        assertThat(pageResponse.hasPrevious()).isFalse();
    }

    @Test
    @DisplayName("마지막 페이지일 때 올바른 플래그가 설정된다")
    void shouldSetCorrectFlagsForLastPage() {
        // given
        List<String> content = List.of("item1", "item2");
        PageRequest pageRequest = PageRequest.of(3, 10);
        int totalElements = 25;
        
        // when
        PageResponse<String> pageResponse = PageResponse.of(content, pageRequest, totalElements);
        
        // then
        assertThat(pageResponse.first()).isFalse();
        assertThat(pageResponse.last()).isTrue();
        assertThat(pageResponse.hasNext()).isFalse();
        assertThat(pageResponse.hasPrevious()).isTrue();
    }

    @Test
    @DisplayName("중간 페이지일 때 올바른 플래그가 설정된다")
    void shouldSetCorrectFlagsForMiddlePage() {
        // given
        List<String> content = List.of("item1", "item2");
        PageRequest pageRequest = PageRequest.of(2, 10);
        int totalElements = 25;
        
        // when
        PageResponse<String> pageResponse = PageResponse.of(content, pageRequest, totalElements);
        
        // then
        assertThat(pageResponse.first()).isFalse();
        assertThat(pageResponse.last()).isFalse();
        assertThat(pageResponse.hasNext()).isTrue();
        assertThat(pageResponse.hasPrevious()).isTrue();
    }

    @Test
    @DisplayName("빈 페이지 응답을 생성할 수 있다")
    void shouldCreateEmptyPageResponse() {
        // given
        PageRequest pageRequest = PageRequest.of(1, 10);
        
        // when
        PageResponse<String> pageResponse = PageResponse.empty(pageRequest);
        
        // then
        assertThat(pageResponse.content()).isEmpty();
        assertThat(pageResponse.currentPage()).isEqualTo(1);
        assertThat(pageResponse.pageSize()).isEqualTo(10);
        assertThat(pageResponse.totalElements()).isEqualTo(0);
        assertThat(pageResponse.totalPages()).isEqualTo(0);
        assertThat(pageResponse.first()).isTrue();
        assertThat(pageResponse.last()).isTrue();
        assertThat(pageResponse.hasNext()).isFalse();
        assertThat(pageResponse.hasPrevious()).isFalse();
    }

    @Test
    @DisplayName("단일 페이지일 때 올바른 플래그가 설정된다")
    void shouldSetCorrectFlagsForSinglePage() {
        // given
        List<String> content = List.of("item1", "item2");
        PageRequest pageRequest = PageRequest.of(1, 10);
        int totalElements = 2;
        
        // when
        PageResponse<String> pageResponse = PageResponse.of(content, pageRequest, totalElements);
        
        // then
        assertThat(pageResponse.totalPages()).isEqualTo(1);
        assertThat(pageResponse.first()).isTrue();
        assertThat(pageResponse.last()).isTrue();
        assertThat(pageResponse.hasNext()).isFalse();
        assertThat(pageResponse.hasPrevious()).isFalse();
    }
}
