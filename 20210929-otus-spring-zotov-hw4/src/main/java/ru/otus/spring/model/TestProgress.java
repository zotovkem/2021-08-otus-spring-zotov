package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 01.10.2021
 * Прогресс теста
 */
@Data
@Builder
@AllArgsConstructor
public class TestProgress {
    private String userName;
    private Set<Integer> rightQuestionIds;
    private List<Integer> questions;
}
