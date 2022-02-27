package org.yossy.demo.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yossy.demo.db.entity.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
}
