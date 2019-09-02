package io.pgsantos.toggles.service;

import io.pgsantos.toggles.data.vo.ToggleVO;
import io.pgsantos.toggles.data.vo.request.ToggleRequestVO;

import java.util.List;

public interface ToggleService {
    List<ToggleVO> findToggles(String name);
    ToggleVO getToggle(long id);
    ToggleVO createToggle(ToggleRequestVO toggleRequestVO);
    ToggleVO updateToggle(long id, ToggleRequestVO toggleRequestVO);
    void deleteToggle(long id);
}