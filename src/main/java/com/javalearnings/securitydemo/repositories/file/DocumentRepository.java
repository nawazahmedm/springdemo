package com.javalearnings.securitydemo.repositories.file;

import com.javalearnings.securitydemo.entities.file.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query(value = "select * from documents " +
            "where personId = :personId and documentTypeCode = :documentTypeCode order by DocumentID desc ", nativeQuery = true)
    List<Document> findByPersonIdAndDocumentTypeCode(Integer personId, String documentTypeCode);

}
