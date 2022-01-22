package ru.zotov.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Created by ZotovES on 10.01.2022
 */
@Getter
@AllArgsConstructor
public class PullRequest {
    private String name;
    private MergedMarker task;
}
