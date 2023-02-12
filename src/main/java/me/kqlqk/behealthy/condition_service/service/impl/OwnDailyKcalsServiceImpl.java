package me.kqlqk.behealthy.condition_service.service.impl;

import me.kqlqk.behealthy.condition_service.dto.OwnDailyKcalsDTO;
import me.kqlqk.behealthy.condition_service.exception.exceptions.OwnDailyKcalsAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.OwnDailyKcalsNotFoundException;
import me.kqlqk.behealthy.condition_service.model.OwnDailyKcals;
import me.kqlqk.behealthy.condition_service.repository.OwnDailyKcalsRepository;
import me.kqlqk.behealthy.condition_service.service.OwnDailyKcalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnDailyKcalsServiceImpl implements OwnDailyKcalsService {
    private final OwnDailyKcalsRepository ownDailyKcalsRepository;

    @Autowired
    public OwnDailyKcalsServiceImpl(OwnDailyKcalsRepository ownDailyKcalsRepository) {
        this.ownDailyKcalsRepository = ownDailyKcalsRepository;
    }

    @Override
    public OwnDailyKcals getByUserId(long userId) {
        OwnDailyKcals ownDailyKcals = ownDailyKcalsRepository.findByUserId(userId);

        if (ownDailyKcals == null) {
            throw new OwnDailyKcalsNotFoundException("OwnDailyKcals with userId = " + userId + " not found");
        }

        return ownDailyKcals;
    }

    @Override
    public void save(OwnDailyKcalsDTO ownDailyKcalsDTO) {
        if (ownDailyKcalsRepository.existsByUserId(ownDailyKcalsDTO.getUserId())) {
            throw new OwnDailyKcalsAlreadyExistsException("OwnDailyKcals with userId = " + ownDailyKcalsDTO.getUserId() + " already exists");
        }

        OwnDailyKcals ownDailyKcals = new OwnDailyKcals();
        ownDailyKcals.setProtein(ownDailyKcals.getProtein());
        ownDailyKcals.setFat(ownDailyKcals.getFat());
        ownDailyKcals.setCarb(ownDailyKcals.getCarb());
        ownDailyKcals.setUserId(ownDailyKcals.getUserId());
        ownDailyKcals.setInPriority(ownDailyKcalsDTO.isInPriority());

        ownDailyKcalsRepository.save(ownDailyKcals);
    }

    @Override
    public void update(OwnDailyKcalsDTO ownDailyKcalsDTO) {
        if (!ownDailyKcalsRepository.existsByUserId(ownDailyKcalsDTO.getUserId())) {
            throw new OwnDailyKcalsNotFoundException("OwnDailyKcals with userId = " + ownDailyKcalsDTO.getUserId() + " not found");
        }

        OwnDailyKcals ownDailyKcals = getByUserId(ownDailyKcalsDTO.getUserId());
        ownDailyKcals.setProtein(ownDailyKcalsDTO.getProtein());
        ownDailyKcals.setFat(ownDailyKcalsDTO.getFat());
        ownDailyKcals.setCarb(ownDailyKcalsDTO.getCarb());
        ownDailyKcals.setInPriority(ownDailyKcalsDTO.isInPriority());

        ownDailyKcalsRepository.save(ownDailyKcals);
    }

    @Override
    public void changePriority(OwnDailyKcalsDTO ownDailyKcalsDTO) {
        if (!ownDailyKcalsRepository.existsByUserId(ownDailyKcalsDTO.getUserId())) {
            throw new OwnDailyKcalsNotFoundException("OwnDailyKcals with userId = " + ownDailyKcalsDTO.getUserId() + " not found");
        }

        OwnDailyKcals ownDailyKcals = getByUserId(ownDailyKcalsDTO.getUserId());
        ownDailyKcals.setInPriority(ownDailyKcalsDTO.isInPriority());

        ownDailyKcalsRepository.save(ownDailyKcals);
    }
}
