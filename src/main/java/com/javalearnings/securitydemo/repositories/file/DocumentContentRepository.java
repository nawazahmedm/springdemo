package com.javalearnings.securitydemo.repositories.file;

import com.javalearnings.securitydemo.entities.file.DocumentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentContentRepository extends JpaRepository<DocumentContent, Integer> {

    @Query(value = "select * from documentcontent dc  " +
            "join documents d on d.documentId = dc.documentContentId " +
            "where personId = :personId order by documentContentId desc ", nativeQuery = true)
    List<DocumentContent> findDocumentContentByPersonId(Integer personId);

}
