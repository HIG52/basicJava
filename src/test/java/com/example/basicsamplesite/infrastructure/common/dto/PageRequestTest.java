package com.example.basicsamplesite.infrastructure.common.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PageRequest 단위 테스트")
class PageRequestTest {

    @Test
    @DisplayName("기본 페이지 요청 생성 시 1페이지, 10개 사이즈로 설정된다")
    void shouldCreateDefaultPageRequest() {
        // when
        PageRequest pageRequest = PageRequest.of();
        
        // then
        assertThat(pageRequest.page()).isEqualTo(1);
        assertThat(pageRequest.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("페이지 요청 생성 시 유효한 값으로 설정된다")
    void shouldCreatePageRequestWithValidValues() {
        // when
        PageRequest pageRequest = PageRequest.of(2, 15);
        
        // then
        assertThat(pageRequest.page()).isEqualTo(2);
        assertThat(pageRequest.size()).isEqualTo(15);
    }

    @Test
    @DisplayName("페이지 번호가 1보다 작을 때 1로 보정된다")
    void shouldCorrectPageNumberWhenLessThanOne() {
        // when
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        // then
        assertThat(pageRequest.page()).isEqualTo(1);
    }

    @Test
    @DisplayName("페이지 사이즈가 1보다 작을 때 1로 보정된다")
    void shouldCorrectPageSizeWhenLessThanOne() {
        // when
        PageRequest pageRequest = PageRequest.of(1, 0);
        
        // then
        assertThat(pageRequest.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("페이지 사이즈가 100보다 클 때 100으로 보정된다")
    void shouldCorrectPageSizeWhenGreaterThanHundred() {
        // when
        PageRequest pageRequest = PageRequest.of(1, 150);
        
        // then
        assertThat(pageRequest.size()).isEqualTo(100);
    }

    @Test
    @DisplayName("0-based offset이 올바르게 계산된다")
    void shouldCalculateOffsetCorrectly() {
        // given
        PageRequest pageRequest = PageRequest.of(3, 10);
        
        // when
        int offset = pageRequest.getOffset();
        
        // then
        assertThat(offset).isEqualTo(20); // (3-1) * 10 = 20
    }

    @Test
    @DisplayName("첫 번째 페이지의 offset은 0이다")
    void shouldReturnZeroOffsetForFirstPage() {
        // given
        PageRequest pageRequest = PageRequest.of(1, 10);
        
        // when
        int offset = pageRequest.getOffset();
        
        // then
        assertThat(offset).isEqualTo(0);
    }
}
