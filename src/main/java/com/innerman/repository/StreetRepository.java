package com.innerman.repository;

import com.innerman.dto.StreetEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by petrpopov on 01/07/16.
 */

@Repository
public interface StreetRepository extends PagingAndSortingRepository<StreetEntity, String> {
}
