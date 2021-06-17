package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.ZurazuTermsDTO;
import com.zurazu.zurazu_backend.web.dto.RequestZurazuTermsDTO;

public interface ZurazuTermsDAOInterface {
    ZurazuTermsDTO getZurazuTerms(String type);
}
