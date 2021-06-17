package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.service.ZurazuTermsServiceInterface;
import com.zurazu.zurazu_backend.provider.dto.ZurazuTermsDTO;
import com.zurazu.zurazu_backend.provider.repository.ZurazuTermsDAO;
import com.zurazu.zurazu_backend.web.dto.RequestZurazuTermsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ZurazuTermsService implements ZurazuTermsServiceInterface {
    private final ZurazuTermsDAO zurazuTermsDAO;
    @Override
    public Optional<ZurazuTermsDTO> getZurazuTerms(RequestZurazuTermsDTO requestZurazuTermsDTO) {
        return Optional.ofNullable(zurazuTermsDAO.getZurazuTerms(requestZurazuTermsDTO.getType()));
    }
}
