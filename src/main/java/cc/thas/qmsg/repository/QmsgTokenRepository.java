package cc.thas.qmsg.repository;

import cc.thas.qmsg.entity.QsmgToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QmsgTokenRepository extends JpaRepository<QsmgToken, Long> {

}
