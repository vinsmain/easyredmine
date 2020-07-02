package ru.mgusev.easyredminetimer.domain.interactor.project_list;

import java.util.LinkedHashMap;
import java.util.Map;

import ru.mgusev.easyredminetimer.domain.interactor._base.BaseUseCaseParams;

public class GetProjectListParams extends BaseUseCaseParams {

    //https://task.sitesoft.su/projects.json?offset=0&limit=1&key=1555c02cbc52fca31c50383f437bad3cdb73b2ee

    private final int offset;
    private final int limit;
    private final String key;

    public GetProjectListParams(int offset, int limit, String key) {
        this.offset = offset;
        this.limit = limit;
        this.key = key;
    }

    public Map<String, Object> toParams() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("offset", offset);
        params.put("limit", limit);
        params.put("key", key);

        return params;
    }
}