package com.zurazu.zurazu_backend.core.service;


import com.zurazu.zurazu_backend.provider.dto.ZurazuTermsDTO;
import com.zurazu.zurazu_backend.web.dto.RequestZurazuTermsDTO;

import java.util.Optional;

public interface ZurazuTermsServiceInterface {
    Optional<ZurazuTermsDTO> getZurazuTerms(RequestZurazuTermsDTO requestZurazuTermsDTO);
}
